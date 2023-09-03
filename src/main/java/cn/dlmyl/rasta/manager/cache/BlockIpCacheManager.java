package cn.dlmyl.rasta.manager.cache;

import cn.dlmyl.rasta.infra.common.CacheKey;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 短链IP黑名单缓存管理
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class BlockIpCacheManager implements CommandLineRunner, CacheManager {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void run(String... args) throws Exception {
        initCache();
    }

    @Override
    public void initCache() {
        // ignore for this function
    }

    public boolean isInBlockList(String clientIp) {
        SetOperations<String, String> opsForSet = stringRedisTemplate.opsForSet();
        return Boolean.TRUE.equals(opsForSet.isMember(CacheKey.BLOCK_IP_SET.getKey(), clientIp));
    }

}

