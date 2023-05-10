package com.afshin.csv2tblpersontask.service.person;

import com.afshin.csv2tblpersontask.entity.Person;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Configuration
//@Configuration [BOTH ARE OK]
public class PersonStep {

    private final StepBuilderFactory stepBuilderFactory;
    private final FlatFileItemReader personFileItemReader;
    private final ItemProcessor convertToUpperCaseProcessor;
    private final ItemProcessor countPersonFromDbProcessor;
    private final PersonWriter personWriter;
    private final JdbcCursorItemReader dbPersonReader;
    private final PersonWriterErr personWriterErr;
    private final ItemProcessor convertToLowerCaseProcessor;
    private final PersonStepListener personStepListener;

    @Autowired
    public PersonStep(StepBuilderFactory stepBuilderFactory,
                      @Qualifier("personFileItemReader") FlatFileItemReader personFileItemReader,
                      @Qualifier("convertToUpperCaseProcessor") ItemProcessor convertToUpperCaseProcessor,
                      @Qualifier("countPersonFromDbProcessor") ItemProcessor countPersonFromDbProcessor,
                      PersonWriter personWriter,
                      @Qualifier("dbPersonReader") JdbcCursorItemReader dbPersonReader,
                      PersonWriterErr personWriterErr,
                      @Qualifier("convertToLowerCaseProcessor") ItemProcessor convertToLowerCaseProcessor,
                      PersonStepListener personStepListener) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.personFileItemReader = personFileItemReader;
        this.convertToUpperCaseProcessor = convertToUpperCaseProcessor;
        this.countPersonFromDbProcessor = countPersonFromDbProcessor;
        this.personWriter = personWriter;
        this.dbPersonReader = dbPersonReader;
        this.personWriterErr = personWriterErr;
        this.convertToLowerCaseProcessor = convertToLowerCaseProcessor;
        this.personStepListener = personStepListener;
    }

    @Bean("saveToDbUpperCasePersonNameStep")
    public Step saveToDbUpperCasePersonNameStep() {
        return stepBuilderFactory.get("saveToDbUpperCasePersonNameStep")
                .listener(personStepListener)
                .<Person, Person>chunk(1)
                .reader(personFileItemReader)
//                .processor(convertToUpperCaseProcessor)
                .writer(personWriter.writePersonNameToDb())
                .writer(personWriterErr)
                .build();
    }

    @Bean("countPersonItemsStep")
    public Step countPersonItemsStep() {
        return stepBuilderFactory.get("countPersonItemsStep")
                .listener(personStepListener)
                .<Person, Integer>chunk(1)
                .reader(dbPersonReader)
                .processor(countPersonFromDbProcessor)
                .writer(personWriterErr)
                .build();
    }

    @Bean("saveToDbLowerCasePersonNameStep")
    public Step saveToDbLowerCasePersonNameStep() {
        return stepBuilderFactory.get("saveToDbLowerCasePersonNameStep")
                .listener(personStepListener)
                .<Person,Person> chunk(10)
                .reader(personFileItemReader)
                .processor(convertToLowerCaseProcessor)
                .writer(personWriter.writePersonNameToDb())
                .build();
    }
}
