package com.afshin.csv2dbtask.service.person;

import com.afshin.csv2dbtask.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class PersonJobListener extends JobExecutionListenerSupport {
    @Value("${info.app.name}")
    private String artifactId;
    private static final Logger log = LoggerFactory.getLogger(PersonJobListener.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonJobListener(@Qualifier("jdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("Before job execution. Good luck...");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");
            System.out.println("ooooooooooooooo :  " + artifactId);

            log.info("Checking now by listener........");
            jdbcTemplate.query("SELECT FIRST_NAME, LAST_NAME FROM test_general.people",
                    (rs, row) -> new Person(
                            rs.getString(1),
                            rs.getString(2))
            ).forEach(person -> log.info("Found <" + person + "> in the database."));
        }
    }
}
