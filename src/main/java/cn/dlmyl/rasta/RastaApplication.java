package cn.dlmyl.rasta;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 瑞斯塔-一个精简的短链系统
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Slf4j
@EnableScheduling
@SpringBootApplication
public class RastaApplication {

    public static void main(String[] args) {
        log.info("====== Rasta Starting... ======");
        ConfigurableApplicationContext ctx = null;
        try {
            ctx = new SpringApplicationBuilder(RastaApplication.class)
                    .web(WebApplicationType.REACTIVE)
                    .bannerMode(Banner.Mode.OFF)
                    .run(args);
            log.info("====== Rasta Started! ======");
        } catch (Exception e) {
            if (ctx != null) {
                log.error("rasta application start failed and exit", e);
                System.exit(SpringApplication.exit(ctx));
            }
        }
    }

}
