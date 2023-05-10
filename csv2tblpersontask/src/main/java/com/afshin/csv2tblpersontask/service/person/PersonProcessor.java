package com.afshin.csv2tblpersontask.service.person;

import com.afshin.csv2tblpersontask.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//public class UppercasePersonNameProcessor implements ItemProcessor<Person, Person> {
public class PersonProcessor {
    private static final Logger log = LoggerFactory.getLogger(PersonProcessor.class);

    @Bean(value = "convertToUpperCaseProcessor")
    public ItemProcessor<Person, Person> convertToUpperCaseProcessor() {
        return new ItemProcessor<Person, Person>() {
            @Override
            public Person process(Person person) throws Exception {
                final String firstName = person.getFirstName().toUpperCase();
                final String lastName = person.getLastName().toUpperCase();
                final Person transformedPerson = new Person(firstName, lastName);
                log.info("Converting (" + person + ") into (" + transformedPerson + ")");
                return transformedPerson;
            }
        };
    }

    @Bean(name = "countPersonFromDbProcessor")
    public ItemProcessor<Person, Integer> countPersonFromDbProcessor() {
        final int[] itemCount = {0};
        return new ItemProcessor<Person, Integer>() {
            @Override
            public Integer process(Person person) throws Exception {
                log.info("Item(s) count yet : " + ++itemCount[0]);
                return itemCount[0];
            }
        };
    }

    @Bean("convertToLowerCaseProcessor")
    public ItemProcessor<Person, Person> convertToLowerCaseProcessor() {
        return new ItemProcessor<Person, Person>() {
            @Override
            public Person process(Person person) throws Exception {
                Person transformedPerson = new Person(person.getFirstName().toLowerCase(), person.getLastName().toLowerCase());
                log.info("Converting to lowercase " + person + "to " + transformedPerson);
                return transformedPerson;
            }
        };
    }

}
//    @Override
//    public Person process(final Person person) throws Exception {
//        final String firstName = person.getFirstName().toUpperCase();
//        final String lastName = person.getLastName().toUpperCase();
//
//        final Person transformedPerson = new Person(firstName, lastName);
//
//        log.info("Converting (" + person + ") into (" + transformedPerson + ")");
//
//        return transformedPerson;
//    }