package com.batch.ticketbatch.batchservice.booking;

import com.batch.ticketbatch.BaseEntity;
import com.batch.ticketbatch.batchservice.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Builder
public class Booking extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingSeq;
    private Long passSeq;

    @Enumerated(EnumType.STRING)
    @Setter
    private BookingStatus status;

    @Setter
    private boolean usedPass;

    @Setter
    private boolean attended;

    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private LocalDateTime cancelledAt;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member bmember;

}
