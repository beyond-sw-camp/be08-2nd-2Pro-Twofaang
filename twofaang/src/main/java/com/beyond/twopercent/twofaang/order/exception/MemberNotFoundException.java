package com.beyond.twopercent.twofaang.order.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(Long memberId) {
        super("Member not found with id: " + memberId);
    }
}