package cn.dlmyl.rasta.infra.support.lock;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

/**
 * 分布式锁工厂
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class DistributedLockFactory {

    private static final String DISTRIBUTED_LOCK_PATH_PREFIX = "dl:";
    private final RedissonClient redissonClient;

    public DistributedLock provideDistributedLock(String lockKey) {
        String lockPath = DISTRIBUTED_LOCK_PATH_PREFIX + lockKey;
        return new RedissonDistributedLock(redissonClient, lockPath);
    }

}
