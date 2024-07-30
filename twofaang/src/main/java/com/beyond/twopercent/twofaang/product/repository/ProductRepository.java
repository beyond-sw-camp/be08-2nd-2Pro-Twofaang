package com.beyond.twopercent.twofaang.product.repository;

import com.beyond.twopercent.twofaang.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByProductNameContainingOrDescriptionContainingOrKeywordContaining(String productName, String description, String keyword, Pageable pageable);

    @Query("select p.productName from Product p where p.productId = :productId")
    String findProductNameByProductId(Long productId);
}

