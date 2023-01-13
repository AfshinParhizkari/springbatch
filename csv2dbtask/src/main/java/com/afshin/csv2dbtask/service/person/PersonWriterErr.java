package com.afshin.csv2dbtask.service.person;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Configuration
public class PersonWriterErr implements ItemWriter<Object> {

    Logger logger = LoggerFactory.getLogger(PersonWriterErr.class);

    @Override
    public void write(List<? extends Object> items) throws Exception {
        items.forEach(item -> logger.info("NoOperationItemWriter for item : " + item));
    }
}
