package com.afshin.csv2dbtask.config;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//Spring Data JPA
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.afshin.csv2dbtask.repository",
        entityManagerFactoryRef = "bizEntityManagerFactory",
        transactionManagerRef = "bizTransactionManager"
)
public class Dbbusiness {

    @Bean(name = "bizDataSource")
    @ConfigurationProperties(prefix = "spring.biz-datasource")
    public DataSource bizdataSource() {return DataSourceBuilder.create().build();}

    @Bean(name = "bizEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean bizEntityManagerFactory(
           @Qualifier("bizDataSource") DataSource dataSource,EntityManagerFactoryBuilder builder){
        return builder.dataSource(dataSource)
                .packages("com.afshin.csv2dbtask.entity")
                .persistenceUnit("dbbusiness")
                .properties(jpaProperties())
                .build();
    }

    private Map<String,Object> jpaProperties(){
        Map<String,Object> props=new HashMap<>();
        props.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
        props.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
        props.put("hibernate.default_schema", "test_general");
        return props;
    }

    @Bean(name = "bizTransactionManager")
    public PlatformTransactionManager bizTransactionManager(
            @Qualifier("bizEntityManagerFactory") LocalContainerEntityManagerFactoryBean todosEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(todosEntityManagerFactory.getObject()));
    }
}