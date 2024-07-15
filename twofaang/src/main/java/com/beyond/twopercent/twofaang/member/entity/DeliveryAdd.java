package com.beyond.twopercent.twofaang.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_delivery_add")
public class DeliveryAdd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "del_add_id")
    private Long delAddId; // 배송지 번호

    @Column(name = "member_id")
    private Long memberId; // 회원 번호

    @Column(name = "recipient_name")
    private String recipientName; // 수령자 이름

    @Column(name = "recipient_phone")
    private String recipientPhone; // 수령자 전화번호

    @Column(name = "postal_code")
    private String postalCode; // 우편 번호

    @Column(name = "address")
    private String address; // 주소

    @Column(name = "detailed_address")
    private String detailedAddress; // 상세 주소

    @Column(name = "is_default")
    private Boolean isDefault; // 기본 배송지 여부
}