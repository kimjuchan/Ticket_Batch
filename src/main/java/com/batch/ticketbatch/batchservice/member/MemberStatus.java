package com.batch.ticketbatch.batchservice.member;

public enum MemberStatus {
    ACTIVE("사용중"),
    INACTIVE("중지");

    final String description;

    MemberStatus(String description) {
        this.description = description;
    }
}
