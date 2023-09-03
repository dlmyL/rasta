package cn.dlmyl.rasta.infra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Rasta 属性配置
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Configuration
public class RastaConfig {

    public static String DEFAULT_DOMAIN;

    public static Integer COMPRESS_CODE_BATCH;

    public static String ERROR_PAGE_URL;

    public static List<String> EXCLUDE_URIS;

    public static Long SNOWFLAKE_DATA_CENTER_ID;

    public static Long SNOWFLAKE_WORKER_ID;

    @Value("${rasta.default.domain}")
    public void setDefaultDomain(String defaultDomain) {
        DEFAULT_DOMAIN = defaultDomain;
    }

    @Value("${rasta.code.batch}")
    private void setCompressCodeBatch(Integer compressCodeBatch) {
        COMPRESS_CODE_BATCH = compressCodeBatch;
    }

    @Value("${rasta.error.page.url}")
    public void setErrorPageUrl(String errorPageUrl) {
        ERROR_PAGE_URL = errorPageUrl;
    }

    @Value("#{'${rasta.exclude.uris}'.split(',')}")
    public void setExcludeUris(List<String> excludeUris) {
        EXCLUDE_URIS = excludeUris;
    }

    @Value("${rasta.snowflake.data.center.id}")
    private void setSnowflakeDataCenterId(Long dataCenterId) {
        SNOWFLAKE_DATA_CENTER_ID = dataCenterId;
    }

    @Value("${rasta.snowflake.worker.id}")
    private void setSnowflakeWorkerId(Long workerId) {
        SNOWFLAKE_WORKER_ID = workerId;
    }

}
