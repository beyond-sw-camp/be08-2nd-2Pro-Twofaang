package com.beyond.twopercent.twofaang.member.entity;

import com.beyond.twopercent.twofaang.order.entity.Address;
import com.beyond.twopercent.twofaang.order.entity.Order;
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
@Table(name = "tb_delivery")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long delAddId; // 배송지 번호

    @Embedded
    private Address address;

    @Column(name = "member_id")
    private Long memberId; // 회원 번호

    @Column(name = "recipient_name")
    private String recipientName; // 수령자 이름

    @Column(name = "recipient_phone")
    private String recipientPhone; // 수령자 전화번호

}