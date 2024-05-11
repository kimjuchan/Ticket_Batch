package com.batch.ticketbatch.batchservice.course;


import com.batch.ticketbatch.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Course extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    private String couresName;
    private Long count;
    private Long period;

    //그냥 단순히 courseId 기반으로   정보 노출해주는 entity인데 양방향으로 꼭 설정해줄 필요 없을 것 같아서 단반향으로 설정함.
    //연관관계 설정.
    //강좌 정보는 말그대로 PASS 이용권에 대한 강좌 정보를 제공하므로 1:1로 설정
    /* @OneToOne
    @JoinColumn(name = "mt_id")
    private MemberTicket memberTicket;
    */

}
