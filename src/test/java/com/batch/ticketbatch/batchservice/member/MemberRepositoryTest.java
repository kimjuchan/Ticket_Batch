package com.batch.ticketbatch.batchservice.member;

import com.batch.ticketbatch.batchservice.course.Course;
import com.batch.ticketbatch.batchservice.course.CourseRepository;
import com.batch.ticketbatch.batchservice.pass.Pass;
import com.batch.ticketbatch.batchservice.pass.PassRepository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@SpringBootTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private PassRepository passRepository;

    @Test
    public void test_멤버등록(){
        //given
        Course course = new Course();
        course.setCount(10L);
        course.setPeriod(60L);
        course.setCouresName("Spring_Event PT ticket");

        courseRepository.save(course);

        Pass pass = new Pass();
        pass.setPeriod(60L);
        pass.setCourse(course);
        pass.setRCount(10L);

        Member member = new Member();

        member.setLoginId("user_1");
        member.setUserName("user_test");
        member.setUserStatus(MemberStatus.ACTIVE);
        member.setUserPhoneNumber("010-1234-1234");

        pass.setTmember(member);
        passRepository.save(pass);

        member.addPass(pass);
        memberRepository.save(member);

        Optional<Member> getMember = memberRepository.findById(member.getMemberId());

        System.out.println("============");
        System.out.println("============Member info " + getMember.get().getMemberId() + "//" + getMember.get().getUserName() + "============");
        System.out.println("============Pass info " + getMember.get().getPassList().get(0).getCourse().getCouresName() + "//" + getMember.get().getPassList().get(0).getRCount() + "============");
        System.out.println("============");
    }

}