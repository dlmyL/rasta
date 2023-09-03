package cn.dlmyl.rasta.service;

import cn.dlmyl.rasta.infra.util.JacksonUtils;
import cn.dlmyl.rasta.infra.util.UserAgentUtils;
import cn.dlmyl.rasta.model.entity.TransformEventRecord;
import cn.dlmyl.rasta.repository.TransformEventRecordRepository;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Optional;

/**
 * 记录转换事件
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TransformEventService {

    private final TransformEventRecordRepository transformEventRecordRepository;

    public void recordTransformEvent(TransformEventRecord record) {
        String uniqueIdentity = DigestUtils.sha1Hex(record.getClientIp() + "-" + record.getUserAgent());
        record.setUniqueIdentity(uniqueIdentity);
        record.setShortUrlDigest(DigestUtils.sha1Hex(record.getShortUrl()));
        record.setLongUrlDigest(DigestUtils.sha1Hex(record.getLongUrl()));
        try {
            URL url = new URL(record.getShortUrl());
            if (StringUtils.isNotEmpty(url.getQuery())) {
                record.setQueryParam(url.getQuery());
            }
        } catch (Exception e) {
            log.warn("解析TransformEvent事件中短链的查询参数异常,事件内容:{}", JacksonUtils.X.format(record), e);
        }
        if (StringUtils.isNotEmpty(record.getUserAgent())) {
            try {
                UserAgent userAgent = UserAgent.parseUserAgentString(record.getUserAgent());
                OperatingSystem operatingSystem = userAgent.getOperatingSystem();
                Optional.ofNullable(operatingSystem).ifPresent(x -> {
                    record.setOsType(x.getName());
                    record.setOsVersion(x.getName());
                    Optional.ofNullable(x.getDeviceType()).ifPresent(deviceType -> {
                        record.setDeviceType(deviceType.getName());
                        if (DeviceType.MOBILE == deviceType) {
                            UserAgentUtils.UserAgentExtractResult result
                                    = UserAgentUtils.X.extractSystemType(record.getUserAgent());
                            record.setPhoneType(result.getPhoneType());
                            record.setOsType(result.getSystemType());
                            record.setOsVersion(result.getSystemVersion());
                        }
                    });
                });
                Browser browser = userAgent.getBrowser();
                Optional.ofNullable(browser).ifPresent(x -> record.setBrowserType(x.getGroup().getName()));
                Version browserVersion = userAgent.getBrowserVersion();
                Optional.ofNullable(browserVersion).ifPresent(x -> record.setBrowserVersion(x.getVersion()));
            } catch (Exception e) {
                log.error("解析TransformEvent中的UserAgent异常,事件内容:{}", JacksonUtils.X.format(record), e);
            }
        }
        transformEventRecordRepository.insertSelective(record);
    }

}
