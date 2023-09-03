package cn.dlmyl.rasta;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(classes = RastaApplication.class, properties = "spring.profiles.active=local")
public class RastaApplicationTest {

    @Test
    void test_Context() {
        log.info("====== Rasta Started! ======");
    }

}