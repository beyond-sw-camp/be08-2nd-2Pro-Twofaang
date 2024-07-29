package com.beyond.twopercent.twofaang.order.entity;

import com.beyond.twopercent.twofaang.product.entity.Product;
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
@Table(name = "tb_order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId; // 주문 항목 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order; // 주문

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product; // 상품

    @Column(name = "quantity")
    private int quantity; // 수량

    @Column(name = "price")
    private int price; // 가격
}
