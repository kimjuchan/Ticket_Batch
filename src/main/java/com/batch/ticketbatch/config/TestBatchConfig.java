package com.batch.ticketbatch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/*

@Configuration
@EnableBatchProcessing
@Slf4j
@RequiredArgsConstructor
public class TestBatchConfig {
    */
/*
     * Spring Batch 5.2 이상부터 BuilderFactory deprecated  --> 가장 흔한 4.x 버젼 기준으로 작업 진행.
     *
     * @EnableBatchProcessing 을 통해서 Spring batch 설정
     *
     * Step 등록
     * Job 등록 시 해당 시행할 Step 추가
     *
     * *//*


    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean
    public Step TestStep(){
        return this.stepBuilderFactory.get("teststep").tasklet(
                (e,c) -> {
                    System.out.println("first test step");
                }
        ).build();
    }

    @Bean
    public Job TestJob(){
        return this.jobBuilderFactory.get("testJob").start(TestStep()).build();
    }


}*/
