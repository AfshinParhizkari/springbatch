package com.afshin.json2dbtask.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.task.configuration.DefaultTaskConfigurer;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class TaskConfig extends DefaultTaskConfigurer {

    public TaskConfig(@Qualifier("sdfDataSource") DataSource batchDataSource) {
        super(batchDataSource);
    }
}
