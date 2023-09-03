package cn.dlmyl.rasta.manager.cache;

import cn.dlmyl.rasta.infra.common.CacheKey;
import cn.dlmyl.rasta.infra.common.MappingStatus;
import cn.dlmyl.rasta.infra.util.BeanCopierUtils;
import cn.dlmyl.rasta.infra.util.JacksonUtils;
import cn.dlmyl.rasta.model.dto.MappingCacheDTO;
import cn.dlmyl.rasta.model.entity.Mapping;
import cn.dlmyl.rasta.repository.MappingRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Function;

/**
 * 短链映射缓存管理
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class MappingCacheManager implements CommandLineRunner, CacheManager {

    private final MappingRepository mappingRepository;
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void run(String... args) throws Exception {
        initCache();
    }

    @Override
    public void initCache() {
        mappingRepository.selectAll().stream()
                .filter(x -> Objects.equals(MappingStatus.AVAILABLE.getValue(), x.getUrlStatus()))
                .forEach(this::refreshUrlMapCache);
    }

    private final Function<Mapping, MappingCacheDTO> function = urlMap -> {
        MappingCacheDTO urlMapCacheDto = new MappingCacheDTO();
        BeanCopierUtils.X.copy(urlMap, urlMapCacheDto);
        urlMapCacheDto.setEnable(MappingStatus.AVAILABLE.getValue().equals(urlMap.getUrlStatus()));
        return urlMapCacheDto;
    };

    public void refreshUrlMapCache(Mapping urlMap) {
        if (null != urlMap) {
            refreshUrlMapCache(function.apply(urlMap));
        }
    }

    public Mapping loadUrlMapCacheByCompressCode(String compressionCode) {
        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
        String content = hashOperations.get(CacheKey.ACCESS_CODE_HASH.getKey(), compressionCode);
        MappingCacheDTO cacheDto = StringUtils.isNoneBlank(content) ?
                JacksonUtils.X.parse(content, MappingCacheDTO.class) :
                refreshUrlMapCacheByCompressionCodeInternal(compressionCode);
        if (null != cacheDto && Objects.equals(cacheDto.getEnable(), Boolean.TRUE)) {
            Mapping urlMap = new Mapping();
            BeanCopierUtils.X.copy(cacheDto, urlMap);
            return urlMap;
        }
        return null;
    }

    private MappingCacheDTO refreshUrlMapCacheByCompressionCodeInternal(String compressionCode) {
        Mapping mapping = mappingRepository.selectByCompressionCode(compressionCode);
        MappingCacheDTO dto;
        if (null != mapping) {
            dto = function.apply(mapping);
        } else {
            dto = new MappingCacheDTO();
            dto.setCompressionCode(compressionCode);
            dto.setEnable(Boolean.FALSE);
        }
        refreshUrlMapCache(dto);
        return dto;
    }

    private void refreshUrlMapCache(MappingCacheDTO dto) {
        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
        hashOperations.put(CacheKey.ACCESS_CODE_HASH.getKey(), dto.getCompressionCode(), JacksonUtils.X.format(dto));
    }

}
