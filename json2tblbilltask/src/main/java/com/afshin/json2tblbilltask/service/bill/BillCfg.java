package com.afshin.json2tblbilltask.service.bill;
/**
 * @Project spring-cloud-task-edu-samples
 * @Author Afshin Parhizkari
 * @Date 2022 - 07 - 04
 * @Time 10:05 AM
 * Created by   IntelliJ IDEA
 * Email:       Afshin.Parhizkari@gmail.com
 * Description:
 */
import com.afshin.json2tblbilltask.entity.Bill;
import com.afshin.json2tblbilltask.entity.Billdto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class BillCfg {
	@Autowired public JobBuilderFactory jobBuilderFactory;
	@Autowired public StepBuilderFactory stepBuilderFactory;
	@Value("${usage.file.name:classpath:mysql-dml-bill.json}") private Resource resource;

	@Bean("BillProcessingStep")
	public Job mysqlJob(@Qualifier("json-reader") ItemReader<Billdto> reader,@Qualifier("bill-process") ItemProcessor<Billdto, Bill> itemProcessor, @Qualifier("db-insert") ItemWriter<Bill> writer) {
		//__________________________________ create only one step from items:
		Step step = stepBuilderFactory.get("BillStep")
				.<Billdto, Bill>chunk(2)
				.reader(reader)
				.processor(itemProcessor)
				.writer(writer)
				.build();
		//__________________________________ create job from steps:
		return jobBuilderFactory.get("BillJob")
				.incrementer(new RunIdIncrementer())
				//.listener()
				//.flow()
				.start(step)
				//.next()
				//.end()
				.build();
	}
//__________________________________ ITEMS(read,process,write):
	@Bean(name = "json-reader")
	public JsonItemReader<Billdto> jsonItemReader() {
		ObjectMapper objectMapper = new ObjectMapper();
		JacksonJsonObjectReader<Billdto> jsonObjectReader =
				new JacksonJsonObjectReader<>(Billdto.class);
		jsonObjectReader.setMapper(objectMapper);

		return new JsonItemReaderBuilder<Billdto>()
				.jsonObjectReader(jsonObjectReader)
				.resource(resource)
				.name("UsageJsonItemReader")
				.build();
	}
	@Bean(name = "bill-process") public ItemProcessor<Billdto, Bill> billProcessor() {return new BillProcessor();}
	@Bean(name = "db-insert") public ItemWriter<Bill> jpaBillWriter() {return new BillWrite();}
}