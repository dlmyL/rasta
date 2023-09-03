package cn.dlmyl.rasta.infra.support.keygen;

import lombok.RequiredArgsConstructor;

/**
 * 雪花算法序列号生成器
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@RequiredArgsConstructor
public class SnowflakeSequenceGenerator implements SequenceGenerator {

    private final long workerId;
    private final long dataCenterId;

    private JavaSnowflake javaSnowflake;

    public void init() {
        this.javaSnowflake = new JavaSnowflake(workerId, dataCenterId);
    }

    @Override
    public long generate() {
        return javaSnowflake.nextId();
    }

}
