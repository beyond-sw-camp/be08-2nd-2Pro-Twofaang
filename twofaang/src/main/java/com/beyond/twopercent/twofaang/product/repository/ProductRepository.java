package com.beyond.twopercent.twofaang.product.repository;

import com.beyond.twopercent.twofaang.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByProductNameContainingOrDescriptionContaining(String productName, String description, Pageable pageable);

}

