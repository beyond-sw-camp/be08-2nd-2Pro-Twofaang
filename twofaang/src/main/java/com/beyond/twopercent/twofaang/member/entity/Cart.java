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
@Table(name = "tb_cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;                 // 카트 식별 번호

    @Column(name = "member_id")
    private int memberId;               // 멤버 식별 번호

    @Column(name = "product_id")
    private int productId;              // 상품 식별 번호

    @Column(name = "quantity")
    private int quantity;               // 상품의 개수

    @Column(name = "is_selected")
    private String isSelected;      //상품이 선택되어있는지의 여부

    @Column(name = "total_price")
    private int totalPrice;        // 총 가격
}