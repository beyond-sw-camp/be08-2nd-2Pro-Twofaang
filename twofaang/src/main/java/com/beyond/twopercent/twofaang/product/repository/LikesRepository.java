package com.beyond.twopercent.twofaang.product.repository;

import com.beyond.twopercent.twofaang.product.entity.Likes;
import jakarta.transaction.Transactional;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByMemberIdAndProductId(long memberId, long productId);

    @Transactional
    @Modifying
    @Query("delete from Likes pl where pl.memberId = ?1 and pl.productId = ?2")
    void deleteByMemberIdAndProductId(@Param("member_id") long memberId, @Param("product_id") long productId);
}
