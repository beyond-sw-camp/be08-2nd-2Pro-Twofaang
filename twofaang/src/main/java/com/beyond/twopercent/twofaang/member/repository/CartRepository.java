package com.beyond.twopercent.twofaang.member.repository;

import com.beyond.twopercent.twofaang.member.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByMemberId(long memberId);

    int countByMemberId(long memberId);

    @Query("select c from Cart c where c.memberId = :memberId and c.productId = :productId")
    Cart findCartByMemberIdAAndProductId(long memberId, Long productId);
}