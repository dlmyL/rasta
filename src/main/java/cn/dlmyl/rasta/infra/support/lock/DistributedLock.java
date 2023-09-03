package cn.dlmyl.rasta.infra.support.lock;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁行为抽象
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
public interface DistributedLock {

    void lock(long leaseTime, TimeUnit unit);

    boolean tryLock(long waitTime, long leaseTime, TimeUnit unit);

    void unlock();

    boolean isLock();

    boolean isHeldByCurrentThread();

}
