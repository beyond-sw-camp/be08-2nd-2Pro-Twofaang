package com.beyond.twopercent.twofaang.order.entity;

import com.beyond.twopercent.twofaang.member.entity.Member;
import com.beyond.twopercent.twofaang.product.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId; // 주문 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 회원 번호

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>(); // 주문 항목 목록

    @Column(name = "del_add_id")
    private Long delAddId; // 배송지 번호

    @Column(name = "order_date")
    private LocalDateTime orderDate; // 주문일

    @Column(name = "request_msg")
    private String requestMsg; // 배송 요청사항

    @Column(name = "total_payment")
    private int totalPayment; // 주문 총 가격

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod; // 결제 수단

    @Column(name = "grade_discount")
    private int gradeDiscount; // 등급 할인액

    @Column(name = "coupon_discount")
    private int couponDiscount; // 쿠폰 할인액

    @Column(name = "real_amount")
    private int realPayment; // 실 결제가격

    @Column(name = "order_state")
    @Enumerated(EnumType.STRING)
    private OrderState orderState = OrderState.ORDERED; // 주문 상태
}