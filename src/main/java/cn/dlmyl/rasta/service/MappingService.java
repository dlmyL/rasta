package cn.dlmyl.rasta.service;

import cn.dlmyl.rasta.filter.TransformContext;
import cn.dlmyl.rasta.filter.TransformFilterChain;
import cn.dlmyl.rasta.filter.TransformFilterChainFactory;
import cn.dlmyl.rasta.infra.common.CommonConstant;
import cn.dlmyl.rasta.infra.common.CompressionCodeStatus;
import cn.dlmyl.rasta.infra.common.LockKey;
import cn.dlmyl.rasta.infra.config.RastaConfig;
import cn.dlmyl.rasta.infra.support.keygen.SequenceGenerator;
import cn.dlmyl.rasta.infra.support.lock.DistributedLock;
import cn.dlmyl.rasta.infra.support.lock.DistributedLockFactory;
import cn.dlmyl.rasta.infra.util.ConversionUtils;
import cn.dlmyl.rasta.manager.cache.MappingCacheManager;
import cn.dlmyl.rasta.manager.transaction.UrlServiceTransactionManager;
import cn.dlmyl.rasta.model.entity.Code;
import cn.dlmyl.rasta.model.entity.Domain;
import cn.dlmyl.rasta.model.entity.Mapping;
import cn.dlmyl.rasta.repository.CodeRepository;
import cn.dlmyl.rasta.repository.DomainRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 创建短链、访问短链
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MappingService {

    private final CodeRepository codeRepository;
    private final DomainRepository domainRepository;

    private final MappingCacheManager mappingCacheManager;
    private final UrlServiceTransactionManager selfTransactionManage;

    private final SequenceGenerator sequenceGenerator;

    private final DistributedLockFactory distributedLockFactory;

    private final TransformFilterChainFactory transformFilterChainFactory;

    private final UrlValidator urlValidator = new UrlValidator(new String[]{
            CommonConstant.HTTP_PROTOCOL, CommonConstant.HTTPS_PROTOCOL}
    );

    public String createMapping(String domain, Mapping mapping) {
        DistributedLock lock = distributedLockFactory.provideDistributedLock(LockKey.CREATE_URL_MAP.getCode());
        try {
            lock.lock(LockKey.CREATE_URL_MAP.getReleaseTime(), TimeUnit.MILLISECONDS);

            Code code = getAvailableCompressCode();
            Assert.isTrue(Objects.nonNull(code) &&
                            CompressionCodeStatus.AVAILABLE.getValue().equals(code.getCodeStatus()),
                    "压缩码不存在或者已经被使用");
            String longUrl = mapping.getLongUrl();
            Assert.isTrue(urlValidator.isValid(longUrl), String.format("链接[%s]非法", longUrl));
            Domain domainConf = domainRepository.selectByDomain(domain);
            Assert.notNull(domainConf, String.format("域名不存在[c:%s]", domain));

            Mapping urlMap = new Mapping();
            urlMap.setLongUrl(mapping.getLongUrl());
            String shortUrlCode = code.getCompressionCode();
            String shortUrl = String.format("%s://%s/%s", domainConf.getProtocol(), domainConf.getDomainValue(), shortUrlCode);
            urlMap.setShortUrl(shortUrl);
            urlMap.setCompressionCode(shortUrlCode);
            urlMap.setUrlStatus(mapping.getUrlStatus());
            urlMap.setDescription(mapping.getDescription());
            urlMap.setShortUrlDigest(DigestUtils.sha1Hex(urlMap.getShortUrl()));
            urlMap.setLongUrlDigest(DigestUtils.sha1Hex(urlMap.getLongUrl()));
            Code updater = new Code();
            updater.setCodeStatus(CompressionCodeStatus.USED.getValue());
            updater.setId(code.getId());

            selfTransactionManage.saveUrlMapAndUpdateCompressCode(urlMap, updater);

            mappingCacheManager.refreshUrlMapCache(urlMap);
            return shortUrl;
        } finally {
            lock.unlock();
        }
    }

    public void processTransform(TransformContext transformContext) {
        long start = System.nanoTime();
        if (log.isDebugEnabled()) {
            log.debug("Entry TransformFilterChain...");
        }
        TransformFilterChain chain = transformFilterChainFactory.buildTransformFilterChain(transformContext);
        try {
            chain.doFilter(transformContext);
        } finally {
            chain.release();
            transformContext.release();
            if (log.isDebugEnabled()) {
                log.debug("Exit TransformFilterChain,cost {} ms...", TimeUnit.NANOSECONDS.toMillis((System.nanoTime() - start)));
            }
        }
    }

    private Code getAvailableCompressCode() {
        Code compressionCode = codeRepository.getLatestAvailableCompressionCode();
        if (Objects.isNull(compressionCode)) {
            generateBatchCompressionCodes();
            return Objects.requireNonNull(codeRepository.getLatestAvailableCompressionCode());
        }
        return compressionCode;
    }

    private void generateBatchCompressionCodes() {
        for (int i = 0; i < RastaConfig.COMPRESS_CODE_BATCH; i++) {
            long sequence = sequenceGenerator.generate();
            Code compressionCode = new Code();
            compressionCode.setSequenceValue(String.valueOf(sequence));
            String code = ConversionUtils.X.encode62(sequence);
            code = code.substring(code.length() - 6);
            compressionCode.setCompressionCode(code);
            codeRepository.insertSelective(compressionCode);
        }
    }

}
