package com.beyond.twopercent.twofaang.product.service;

import com.beyond.twopercent.twofaang.product.dto.ProductAddDTO;
import com.beyond.twopercent.twofaang.product.entity.Product;
import com.beyond.twopercent.twofaang.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.apache.juli.DateFormatCache;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    private LocalDate getLocalDate(String value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(value, formatter);
        } catch (Exception e) {
        }

        return null;
    }

    @Override
    public boolean add(ProductAddDTO parameter) {

        LocalDate discountEndDate = getLocalDate(parameter.getDiscountEndDateText());

        Product product = Product.builder()
                .categoryId(parameter.getCategoryId())
                .productName(parameter.getProductName())
                .registerDate(LocalDateTime.now())
                .updateDate(parameter.getUpdateDate())
                .description(parameter.getDescription())
                .quantity(parameter.getQuantity())
                .price(parameter.getPrice())
                .saleYn(parameter.isSaleYn())
                .discountRate(parameter.getDiscountRate())
                .discountEndDate(discountEndDate)
                .filename(parameter.getFilename())
                .urlFilename(parameter.getUrlFilename())
                .build();

        productRepository.save(product);

        return true;
    }
}
