package com.beyond.twopercent.twofaang.order.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {

    private String postalCode; // 우편 번호

    private String address; // 주소

    private String detailedAddress; // 상세 주소

    private Boolean isDefault; // 기본 배송지 여부

    protected Address() {
    }

    public Address(String postalCode, String address, String detailedAddress, Boolean isDefault) {
        this.postalCode = postalCode;
        this.address = address;
        this.detailedAddress = detailedAddress;
        this.isDefault = isDefault;
    }
}
