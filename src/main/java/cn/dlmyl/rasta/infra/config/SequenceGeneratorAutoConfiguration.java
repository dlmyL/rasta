package cn.dlmyl.rasta.infra.config;

import cn.dlmyl.rasta.infra.support.keygen.SequenceGenerator;
import cn.dlmyl.rasta.infra.support.keygen.SnowflakeSequenceGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 雪花算法自动装配
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Configuration
public class SequenceGeneratorAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SequenceGenerator snowflakeSequenceGenerator(RastaConfig rastaConfig) {
        SnowflakeSequenceGenerator sequenceGenerator = new SnowflakeSequenceGenerator(
                RastaConfig.SNOWFLAKE_WORKER_ID, RastaConfig.SNOWFLAKE_DATA_CENTER_ID);
        sequenceGenerator.init();
        return sequenceGenerator;
    }

}
