package com.afshin.csv2tblpersontask.service.person;

import com.afshin.csv2tblpersontask.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

@Configuration
public class PersonWriter {

    Logger logger = LoggerFactory.getLogger(PersonWriter.class);
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonWriter(DataSource dataSource,
                        JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Bean("writePersonNameToDb")
    public JdbcBatchItemWriter writePersonNameToDb() {
        JdbcBatchItemWriter<Person> personJdbcBatchItemWriter = new JdbcBatchItemWriterBuilder<Person>()
                .dataSource(dataSource)
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO test_general.people(FIRST_NAME, LAST_NAME) VALUES(:firstName, :lastName)")
                .build();
        return personJdbcBatchItemWriter;
    }
}
