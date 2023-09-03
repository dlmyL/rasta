package cn.dlmyl.rasta.infra.support.lock;

import cn.dlmyl.rasta.infra.exception.LockException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * Redisson实现分布式锁
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Slf4j
class RedissonDistributedLock implements DistributedLock {

    private final RedissonClient redissonClient;
    private final String lockPath;
    private final RLock internalLock;

    RedissonDistributedLock(RedissonClient redissonClient, String lockPath) {
        this.redissonClient = redissonClient;
        this.lockPath = lockPath;
        this.internalLock = initInternalLock();
    }

    private RLock initInternalLock() {
        return redissonClient.getLock(lockPath);
    }

    @Override
    public boolean isLock() {
        return internalLock.isLocked();
    }

    @Override
    public boolean isHeldByCurrentThread() {
        return internalLock.isHeldByCurrentThread();
    }

    @Override
    public void lock(long leaseTime, TimeUnit unit) {
        internalLock.lock(leaseTime, unit);
    }

    @Override
    public boolean tryLock(long waitTime, long leaseTime, TimeUnit unit) {
        try {
            return internalLock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new LockException(String.format("Acquire lock fail by thread interrupted,path:%s", lockPath), e);
        }
    }

    @Override
    public void unlock() {
        try {
            internalLock.unlock();
        } catch (IllegalMonitorStateException ex) {
            log.warn("Unlock path:{} error for thread status change in concurrency", lockPath, ex);
        }
    }

}
