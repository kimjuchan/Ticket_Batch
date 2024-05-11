package com.batch.ticketbatch.config.batch.pass;

import com.batch.ticketbatch.batchservice.course.Course;
import com.batch.ticketbatch.batchservice.course.CourseRepository;
import com.batch.ticketbatch.batchservice.member.Member;
import com.batch.ticketbatch.batchservice.member.MemberRepository;
import com.batch.ticketbatch.batchservice.member.MemberStatus;
import com.batch.ticketbatch.batchservice.pass.Pass;
import com.batch.ticketbatch.batchservice.pass.PassRepository;
import com.batch.ticketbatch.config.TestBatchConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import javax.batch.runtime.JobExecution;
import javax.batch.runtime.JobInstance;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest
@SpringBatchTest
@ActiveProfiles("local")
@ContextConfiguration(classes = {ExpiredPassesChkJobConfig.class, TestBatchConfig.class})
class ExpiredPassesChkJobConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private PassRepository passRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void 테스트_만료이용권_배치Step() throws Exception{
        //given
        addPass(10);

        //when
        JobExecution jobExecution = (JobExecution) jobLauncherTestUtils.launchJob();


        //then
        System.out.println("=====================================================");
        System.out.println("실행되고 있는 Job Status : " + jobExecution.getExitStatus());
        System.out.println("실행되고 있는 Job Name : " + jobExecution.getJobName());
        System.out.println("=====================================================");

    }


    //테스트 Pass 이용권 insert
    private void addPass(int size){
        final LocalDateTime now = LocalDateTime.now();
        final Random random = new Random();

        for(int i=0; i<size; i++){
            Course testCourse = new Course();
            testCourse.setCouresName("A이용권" + i);
            testCourse.setCount(random.nextLong());
            testCourse.setPeriod(10L);

            courseRepository.save(testCourse);

            Member member = new Member();
            member.setUserName("userA" + i);
            member.setLoginId("userLoginId" + i);
            member.setUserStatus(MemberStatus.ACTIVE);
            member.setUserPhoneNumber("123");

            memberRepository.save(member);

            Pass pass = new Pass();
            pass.setCourse(testCourse);
            pass.setTmember(member);
            pass.setStatus("ing");
            pass.setStart_dt(now.minusDays(60));
            pass.setEnd_dt(now.minusDays(1));

            passRepository.save(pass);
            //나중에 이부분을 setMemeber 값 넣어줄때 같이 등록해주면 될 것 같음.
            member.addPass(pass);
        }

    }


}