package com.beyond.twopercent.twofaang.order.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(String email) {
        super("Member not found with email: " + email);
    }
}