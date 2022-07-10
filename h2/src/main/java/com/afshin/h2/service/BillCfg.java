package com.afshin.h2.service;
/**
 * @Project spring-cloud-task-edu-samples
 * @Author Afshin Parhizkari
 * @Date 2022 - 07 - 04
 * @Time 10:44 AM
 * Created by   IntelliJ IDEA
 * Email:       Afshin.Parhizkari@gmail.com
 * Description:
 */

import com.afshin.h2.entity.Bill;
import com.afshin.h2.entity.H2Input;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

@Configuration
@EnableTask
@EnableBatchProcessing
public class BillCfg {
	@Autowired public JobBuilderFactory jobBuilderFactory;
	@Autowired public StepBuilderFactory stepBuilderFactory;
	@Value("${usage.file.name:classpath:input2H2.json}")
	private Resource resource;

	@Bean
	public Job h2Job(@Qualifier("h2-read") ItemReader<H2Input> reader, @Qualifier("h2-proc")  ItemProcessor<H2Input, Bill> itemProcessor, @Qualifier("h2-writ") ItemWriter<Bill> writer) {
		Step step = stepBuilderFactory.get("BillProcessing")
				.<H2Input, Bill>chunk(2)
				.reader(reader)
				.processor(itemProcessor)
				.writer(writer)
				.build();

		return jobBuilderFactory.get("BillJob")
				.incrementer(new RunIdIncrementer())
				.start(step)
				.build();
	}

	@Bean(name = "h2-read")
	public JsonItemReader<H2Input> jsonItemReader() {
		ObjectMapper objectMapper = new ObjectMapper();
		JacksonJsonObjectReader<H2Input> jsonObjectReader =
				new JacksonJsonObjectReader<>(H2Input.class);
		jsonObjectReader.setMapper(objectMapper);

		return new JsonItemReaderBuilder<H2Input>()
				.jsonObjectReader(jsonObjectReader)
				.resource(resource)
				.name("UsageJsonItemReader")
				.build();
	}

	@Bean(name = "h2-writ")
	public ItemWriter<Bill> jdbcBillWriter(DataSource dataSource) {
		JdbcBatchItemWriter<Bill> writer = new JdbcBatchItemWriterBuilder<Bill>()
				.beanMapped()
				.dataSource(dataSource)
				.sql("INSERT INTO BILL_STATEMENTS (id, first_name, last_name, minutes, data_usage,bill_amount) VALUES (:id, :firstName, :lastName, :minutes, :dataUsage, :billAmount)")
				.build();
		return writer;
	}
	@Bean(name = "h2-proc") ItemProcessor<H2Input, Bill> billProcessor() {return new BillProcessor();}
}