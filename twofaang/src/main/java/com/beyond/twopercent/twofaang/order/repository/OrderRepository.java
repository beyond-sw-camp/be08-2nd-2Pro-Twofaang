package com.beyond.twopercent.twofaang.order.repository;

import com.beyond.twopercent.twofaang.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // 회원 ID로 주문 목록 조회
    List<Order> findByMember_MemberId(Long memberId);

    // 회원의 총 주문 금액 조회
    @Query("select sum(o.totalPayment) from Order o where o.member.memberId = :memberId")
    Integer totalPayment_MemberId(Long memberId);
}

