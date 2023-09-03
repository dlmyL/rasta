package cn.dlmyl.rasta.manager.cache;

import cn.dlmyl.rasta.infra.common.CacheKey;
import cn.dlmyl.rasta.infra.common.DomainStatus;
import cn.dlmyl.rasta.model.entity.Domain;
import cn.dlmyl.rasta.repository.DomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 短链域名白名单缓存管理
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class AccessDomainCacheManager implements CommandLineRunner, CacheManager {

    private final StringRedisTemplate stringRedisTemplate;
    private final DomainRepository domainRepository;

    @Override
    public void run(String... args) throws Exception {
        initCache();
    }

    @Override
    public void initCache() {
        SetOperations<String, String> opsForSet = stringRedisTemplate.opsForSet();
        String[] values = domainRepository.selectAll()
                .stream()
                .filter(x -> Objects.equals(DomainStatus.AVAILABLE.getValue(), x.getDomainStatus()))
                .map(Domain::getDomainValue)
                .map(value -> value.split(":")[0])
                .toArray(String[]::new);
        if (values.length > 0) {
            opsForSet.add(CacheKey.ACCESS_DOMAIN_SET.getKey(), values);
        }
    }

    public boolean checkDomainValid(String domain) {
        SetOperations<String, String> opsForSet = stringRedisTemplate.opsForSet();
        return Boolean.TRUE.equals(opsForSet.isMember(CacheKey.ACCESS_DOMAIN_SET.getKey(), domain));
    }

}
