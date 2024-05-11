package com.batch.ticketbatch.config.batch.pass;

import com.batch.ticketbatch.batchservice.pass.Pass;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ExpiredPassesChkJobConfig {

    private final int CHUNK_SIZE = 5;

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job expiredPassesChkJob(){
        return this.jobBuilderFactory.get("expiredPassesChkJob")
                .start(expiredPassesChkStep())
                .build();
    }

    @Bean
    public Step expiredPassesChkStep(){
        return this.stepBuilderFactory.get("expiredPassesChkStep")
                //input, output 형태 선언
                .<Pass, Pass>chunk(CHUNK_SIZE)
                .reader(expiredPassesItemReader())
                .processor(expiredPassesItemProcessor())
                .writer(expiredPassesItemWriter())
                .build();
    }

    /**
     * JpaCursorItemReader : JpaPagingItemReader만 지원하다가 Spring 4.3부터 추가됨
     * 페이징 기법보다 높은 성능 가지고 있음. 테이터 변경에 무관한 무결성 조회가 가능.
     */
    @Bean
    @StepScope
    public JpaCursorItemReader<Pass> expiredPassesItemReader(){
        return new JpaCursorItemReaderBuilder<Pass>()
                .name("expiredPassesItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT P FROM PASS P WHERE P.STATUS = :STATUS AND P.END_DT <= :END_DT")
                .parameterValues(Map.of("STATUS", "ing" , "END_DT", LocalDateTime.now()))
                .build();
    }

    @Bean
    public ItemProcessor<Pass,Pass> expiredPassesItemProcessor(){
        return Pass -> {
            Pass.setExpired_dt(LocalDateTime.now());
            Pass.setStatus("expired");
            return Pass;
        };
    }


    //용도
    @Bean
    public JpaItemWriter<Pass> expiredPassesItemWriter(){
        return new JpaItemWriterBuilder<Pass>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }



}
