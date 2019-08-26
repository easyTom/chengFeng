package com.tom.cf.core.dao.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

/**
 * @method: JPA配置
 */
@Configuration
@EnableTransactionManagement  //启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
//扫描repository位置
@EnableJpaRepositories(entityManagerFactoryRef="entityManagerFactory",transactionManagerRef="transactionManager",basePackages= {"com.tom.cf.core.dao.repository"})
public class RepositoryConfig {
	@Autowired
    private JpaProperties jpaProperties;

    @Autowired
    private DataSource dataSource;

    @Bean(name = "entityManager")
    @Primary
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        EntityManagerFactory entityManagerFactory =entityManagerFactoryDecorate(builder).getObject();
        if(entityManagerFactory == null){
            return null;
        }
        return entityManagerFactory.createEntityManager();
    }

    @Bean(name = "entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryDecorate (EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource)
                .properties(getVendorProperties())
                 //设置实体类所在位置
                .packages("com.tom.cf.core.entity","com.tom.cf.core.mobile")
                .persistenceUnit("PersistenceUnit")
                .build();
    }


    private Map<String, Object> getVendorProperties() {
        return jpaProperties.getHibernateProperties(new HibernateSettings());
    }


    @Bean(name = "transactionManager")
    @Primary
    PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryDecorate(builder).getObject());
    }
}
