package com.afshin.csv2tblpersontask.config;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//Spring Data JDBC
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "sdfEntityManagerFactory",
        transactionManagerRef = "sdfTransactionManager"
)
public class Dbdataflow {

    @Primary
    @BatchDataSource
    @Bean(name = "sdfDataSource")
    @ConfigurationProperties(prefix = "spring.sdf-datasource")
    public DataSource sdfDataSource() {return DataSourceBuilder.create().build();}

    @Primary
    @Bean(name = "sdfEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean sdfEntityManagerFactory(
            @Qualifier("sdfDataSource") DataSource dataSource, EntityManagerFactoryBuilder builder){
        return builder.dataSource(dataSource)
                .packages("com.afshin.csv2tblpersontask.entity")
                .persistenceUnit("dbdataflow")
                .properties(jpaProperties())
                .build();
    }

    private Map<String,Object> jpaProperties(){
        Map<String,Object> props=new HashMap<>();
        props.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
        props.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
        props.put("hibernate.default_schema", "dataflow");
        return props;
    }

    @Primary
    @Bean(name = "sdfTransactionManager")
    public PlatformTransactionManager sdfTransactionManager(
            @Qualifier("sdfEntityManagerFactory") LocalContainerEntityManagerFactoryBean todosEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(todosEntityManagerFactory.getObject()));
    }
}
