package com.batch.ticketbatch.batchservice.booking;

public enum BookingStatus {
    READY("예약 준비"),
    IN_PROGRESS("예약 진행중"),
    COMPLETED("예약 완료"),
    CANCELLED("예약 취소");

    final String description;

     BookingStatus(String description){
        this.description = description;
    }

}
