package cn.dlmyl.rasta.service;

import cn.dlmyl.rasta.RastaApplication;
import cn.dlmyl.rasta.infra.common.MappingStatus;
import cn.dlmyl.rasta.infra.config.RastaConfig;
import cn.dlmyl.rasta.model.entity.Mapping;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RastaApplication.class, properties = "spring.profiles.active=dev")
public class MappingServiceTest implements BeanFactoryAware {

    private MappingService mappingService;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.mappingService = beanFactory.getBean(MappingService.class);
    }

    @Test
    public void createUrlMap() {
        Mapping urlMap = Mapping.builder()
                .urlStatus(MappingStatus.AVAILABLE.getValue())
                .longUrl("https://ai.360.cn/?eqid=d4579ad0000f581300000003646dded1")
                .description("360智脑")
                .build();
        String url = mappingService.createMapping(RastaConfig.DEFAULT_DOMAIN, urlMap);
        log.info("创建短链接成功，生成的短链码: {}", url);
    }

}