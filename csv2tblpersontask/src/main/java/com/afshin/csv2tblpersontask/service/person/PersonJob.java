package com.afshin.csv2tblpersontask.service.person;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Configuration
//@EnableBatchProcessing
public class PersonJob {

    private final JobBuilderFactory jobBuilderFactory;
    private final PersonJobListener listener;
    private final Step saveToDbUpperCasePersonNameStep;
    private final Step countPersonItemsStep;
    private final Step saveToDbLowerCasePersonNameStep;

    @Autowired
    public PersonJob(JobBuilderFactory jobBuilderFactory,
                     PersonJobListener listener,
                     @Qualifier("saveToDbUpperCasePersonNameStep") Step saveUpperCasePersonNamesToDbStep,
                     @Qualifier("countPersonItemsStep") Step countPersonItemsStep,
                     @Qualifier("saveToDbLowerCasePersonNameStep") Step saveToDbLowerCasePersonNameStep
    ) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.listener = listener;
        this.countPersonItemsStep = countPersonItemsStep;
        this.saveToDbUpperCasePersonNameStep = saveUpperCasePersonNamesToDbStep;
        this.saveToDbLowerCasePersonNameStep = saveToDbLowerCasePersonNameStep;
    }

    @Bean("toUpperCasePersonNameJob")
//    @Scheduled(cron = "0,10,20,30,40,50 * * * * *" )
    public Job toUpperCasePersonNameJob() {
        return jobBuilderFactory.get("toUpperCasePersonNameJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
//                .flow(saveToDbUpperCasePersonNameStep) // was OK
                .start(saveToDbUpperCasePersonNameStep)
                .next(countPersonItemsStep)
//                .end()
                .build();
    }

    @Bean
    public Job toLowerCasePersonNameJob(){
        return jobBuilderFactory.get("toLowerCasePersonNameJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(saveToDbLowerCasePersonNameStep)
                .end()
                .build();
    }
}
