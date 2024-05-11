package com.batch.ticketbatch.batchservice.pass;

import com.batch.ticketbatch.BaseEntity;
import com.batch.ticketbatch.batchservice.course.Course;
import com.batch.ticketbatch.batchservice.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pass extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pass_id")
    private Long id;

    //남은 횟수
    private Long rCount;
    //기간
    private Long period;

    private String status;

    //시작 일자
    private LocalDateTime start_dt;

    //종료 일자
    private LocalDateTime end_dt;

    //만료 일자
    private LocalDateTime expired_dt;

    //member와 연관관계 설정
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member tmember;

    //course와 연관관계 설정
    @OneToOne
    @JoinColumn(name="coure_id")
    private Course course;

}
