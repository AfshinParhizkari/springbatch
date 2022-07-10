package com.afshin.mysql.service;
/**
 * @Project spring-cloud-task-edu-samples
 * @Author Afshin Parhizkari
 * @Date 2022 - 07 - 04
 * @Time 10:05 AM
 * Created by   IntelliJ IDEA
 * Email:       Afshin.Parhizkari@gmail.com
 * Description:
 */
import com.afshin.mysql.entity.Bill;
import com.afshin.mysql.entity.MysqlInput;
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

@Configuration
@EnableTask
@EnableBatchProcessing
public class BillCfg {
	@Autowired public JobBuilderFactory jobBuilderFactory;
	@Autowired public StepBuilderFactory stepBuilderFactory;
	@Value("${usage.file.name:classpath:input2mysql.json}")
	private Resource resource;

	@Bean
	public Job mysqlJob(@Qualifier("mysql-read") ItemReader<MysqlInput> reader,@Qualifier("mysql-proc") ItemProcessor<MysqlInput, Bill> itemProcessor, @Qualifier("mysql-write") ItemWriter<Bill> writer) {
		Step step = stepBuilderFactory.get("BillProcessing")
				.<MysqlInput, Bill>chunk(2)
				.reader(reader)
				.processor(itemProcessor)
				.writer(writer)
				.build();

		return jobBuilderFactory.get("BillJob")
				.incrementer(new RunIdIncrementer())
				.start(step)
				.build();
	}

	@Bean(name = "mysql-read")
	public JsonItemReader<MysqlInput> jsonItemReader() {
		ObjectMapper objectMapper = new ObjectMapper();
		JacksonJsonObjectReader<MysqlInput> jsonObjectReader =
				new JacksonJsonObjectReader<>(MysqlInput.class);
		jsonObjectReader.setMapper(objectMapper);

		return new JsonItemReaderBuilder<MysqlInput>()
				.jsonObjectReader(jsonObjectReader)
				.resource(resource)
				.name("UsageJsonItemReader")
				.build();
	}
	@Bean(name = "mysql-proc") public ItemProcessor<MysqlInput, Bill> billProcessor() {return new BillProcessor();}
	@Bean(name = "mysql-write") public ItemWriter<Bill> jpaBillWriter() {return new BillWrite();}
}