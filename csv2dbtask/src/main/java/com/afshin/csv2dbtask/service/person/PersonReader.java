package com.afshin.csv2dbtask.service.person;

import com.afshin.csv2dbtask.entity.Person;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
public class PersonReader {

    private final DataSource dataSource;

    public PersonReader(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Bean("personFileItemReader")
//    @Qualifier("")
    public FlatFileItemReader<Person> personFileItemReader(){
        return new FlatFileItemReaderBuilder<Person>()
                .name("personFileItemReader")
                .resource(new ClassPathResource("mysql-dml-person.csv"))
                .comments("personFileItemReader-comments")
                .delimited()
                .names(new String[]{"firstName","lastName"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                        setTargetType(Person.class);
                    }})
                .build();
    }

    @Bean("dbPersonReader")
    public JdbcCursorItemReader<Person> dbPersonReader(){
        return new JdbcCursorItemReaderBuilder<Person>()
                .name("dbPersonReader")
                .dataSource(dataSource)
                .sql("SELECT FIRST_NAME, LAST_NAME FROM test_general.people")
                .rowMapper((rs, rowNum) -> new Person(rs.getString(1),rs.getString(2)))
                .build();
    }

//    @Bean("dbPersonReader")
//    public JdbcCursorItemReader<Person> dbPersonReader(){
//        JdbcCursorItemReader<Person> jdbcCursorItemReader = new JdbcCursorItemReader<Person>();
//        System.out.println("************");
//        jdbcCursorItemReader.setName("dbPersonReader");
//        jdbcCursorItemReader.setDataSource(dataSource);
//        jdbcCursorItemReader.setSql("SELECT FIRST_NAME, LAST_NAME FROM test_general.people");
//        jdbcCursorItemReader.setRowMapper((rs, rowNum) -> new Person(rs.getString(1),rs.getString(2)));
//        return jdbcCursorItemReader;
//    }


}
