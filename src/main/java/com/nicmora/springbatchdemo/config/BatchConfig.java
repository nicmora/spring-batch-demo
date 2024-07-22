package com.nicmora.springbatchdemo.config;

import com.nicmora.springbatchdemo.entity.in.Customer;
import com.nicmora.springbatchdemo.service.ClientService;
import com.nicmora.springbatchdemo.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
//@EnableBatchProcessing(dataSourceRef = "batchDataSource",
//        transactionManagerRef = "batchTransactionManager",
//        tablePrefix = "batch.BATCH_"
//)
@RequiredArgsConstructor
public class BatchConfig {

    @Bean
    public Job batchJob(JobRepository jobRepository,
                        Step retrieveStep,
                        Step persistStep) {
        return new JobBuilder("batchJob", jobRepository)
                .start(retrieveStep)
                .next(persistStep)
                .build();
    }

    private final CustomerService customerService;

    @Bean
    public Step retrieveStep(JobRepository jobRepository, @Qualifier("batchTransactionManager") PlatformTransactionManager transactionManager) {
        return new StepBuilder("retrieveStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    List<Customer> customers = customerService.findAll();
                    chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("customers", customers);
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    private final ClientService clientService;

    @Bean
    public Step persistStep(JobRepository jobRepository, @Qualifier("batchTransactionManager") PlatformTransactionManager transactionManager) {
        return new StepBuilder("persistStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    List<Customer> customers = (List<Customer>) chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("customers");
                    clientService.saveAll(customers);
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

}
