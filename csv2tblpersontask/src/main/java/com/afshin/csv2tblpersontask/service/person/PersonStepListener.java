package com.afshin.csv2tblpersontask.service.person;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonStepListener extends StepExecutionListenerSupport {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println(stepExecution.getStepName() + " is starting....");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println(stepExecution.getStepName() + " : " + stepExecution.getExitStatus());
        return stepExecution.getExitStatus();
    }


}
