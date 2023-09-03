package cn.dlmyl.rasta.infra;

import cn.dlmyl.rasta.infra.util.IdUtils;
import lombok.NonNull;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

/**
 * 转换事件过程追踪
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Component
public class MappedDiagnosticContextProvider {

    public void process(@NonNull Runnable runnable) {
        MDC.put("TRACE_ID", IdUtils.X.simpleUUID());
        try {
            runnable.run();
        } finally {
            MDC.remove("TRACE_ID");
        }
    }

}
