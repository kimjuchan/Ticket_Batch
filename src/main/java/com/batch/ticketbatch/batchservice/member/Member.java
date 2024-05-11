package com.batch.ticketbatch.batchservice.member;

import com.batch.ticketbatch.BaseEntity;
import com.batch.ticketbatch.batchservice.booking.Booking;
import com.batch.ticketbatch.batchservice.pass.Pass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    private String loginId;

    private String userName;

    @Enumerated(EnumType.STRING)
    private MemberStatus userStatus;

    private String userPhoneNumber;

    //여분의 컬럼
    //private String meta;

    //memberTicket 연관관계 설정
    @OneToMany(mappedBy = "tmember")
    private List<Pass> passList = new ArrayList<>();

    public void addPass(Pass pass){
        this.passList.add(pass);
        pass.setTmember(this);
    }

    //booking 연관관계 설정
    @OneToMany(mappedBy = "bmember")
    //@JoinColumn(name = "booking_id")
    List<Booking> bookingList = new ArrayList<>();

    public void addBooking(Booking booking){
        this.bookingList.add(booking);
        booking.setBmember(this);
    }


}
