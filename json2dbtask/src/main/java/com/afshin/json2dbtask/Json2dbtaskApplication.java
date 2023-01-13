package com.afshin.json2dbtask;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;

/**
 * @Project springbatch
 * @Author Afshin Parhizkari
 * @Date 2023 - 01 - 13
 * @Time 1:54 PM
 * Created by   IntelliJ IDEA
 * Email:       Afshin.Parhizkari@gmail.com
 * Description:
 */
@SpringBootApplication
@EnableBatchProcessing
@EnableTask
public class Json2dbtaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(Json2dbtaskApplication.class, args);
    }
}
