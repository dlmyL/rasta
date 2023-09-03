package cn.dlmyl.rasta.infra.config;

import cn.dlmyl.rasta.infra.support.mybatis.inteceptor.SqlBeautyInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * MyBatis-Plus 自动装配
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableTransactionManagement
@ConditionalOnClass({DataSource.class, SqlSessionFactory.class, SqlSessionFactoryBean.class})
public class MybatisPlusConfiguration implements CommandLineRunner {

    private final DataSource dataSource;

    @Bean
    @ConditionalOnProperty(name = {"rasta.sql.show"}, havingValue = "true")
    public SqlBeautyInterceptor sqlBeautyInterceptor() {
        return new SqlBeautyInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }

    @Bean
    @ConditionalOnMissingBean
    public PlatformTransactionManager platformTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    @Override
    public void run(String... args) throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            log.info(
                    "\n\n {} \n " +
                    "db_url:[{}] \n " +
                    "db_type:[{}] \n " +
                    "db_version:[{}] \n " +
                    "{} \n ",
                    "====== DB MetaData ======",
                    conn.getMetaData().getURL(),
                    conn.getMetaData().getDatabaseProductName(),
                    conn.getMetaData().getDatabaseProductVersion(),
                    "====== DB MetaData ======");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
