package com.beyond.twopercent.twofaang.product.service;

import com.beyond.twopercent.twofaang.product.dto.ProductAddDto;
import com.beyond.twopercent.twofaang.product.dto.ProductDto;
import com.beyond.twopercent.twofaang.product.entity.Category;
import com.beyond.twopercent.twofaang.product.entity.Product;
import com.beyond.twopercent.twofaang.product.repository.CategoryRepository;
import com.beyond.twopercent.twofaang.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private LocalDate getLocalDate(String value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(value, formatter);
        } catch (Exception e) {
        }

        return null;
    }

    @Override
    public boolean add(ProductAddDto parameter) {

        LocalDate discountEndDate = getLocalDate(parameter.getDiscountEndDateText());

        Optional<Category> optionalCategory = categoryRepository.findById(parameter.getCategoryId());

        if(optionalCategory.isEmpty()){
            return false;
        }

        Category category = optionalCategory.get();

        Product product = Product.builder()
                .categoryId(parameter.getCategoryId())
                .keyword(category.getCategoryName())
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

    @Override
    public ProductDto update(long id) {

        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isEmpty()) {
            return null;
        }

        Product product = optionalProduct.get();

        return ProductDto.of(product);
    }

    @Override
    public boolean updateProduct(ProductAddDto parameter) {

        LocalDate discountEndDate = getLocalDate(parameter.getDiscountEndDateText());

        Optional<Product> optionalProduct = productRepository.findById(parameter.getProductId());

        if(optionalProduct.isEmpty()){
            return false;
        }

        System.out.println("@@@@@@@@@@@@parameter = " + parameter);

        Product product = optionalProduct.get();
        product.setCategoryId(parameter.getCategoryId());
        product.setKeyword(parameter.getCategoryName());
        product.setProductName(parameter.getProductName());
        product.setUpdateDate(LocalDateTime.now());
        product.setDescription(parameter.getDescription());
        product.setQuantity(parameter.getQuantity());
        product.setPrice(parameter.getPrice());
        product.setSaleYn(parameter.isSaleYn());
        product.setDiscountRate(parameter.getDiscountRate());
        product.setDiscountEndDate(discountEndDate);

        productRepository.save(product);

        return true;
    }

    @Override
    public boolean deleteProduct(long id) {

        productRepository.deleteById(id);

        return true;
    }

    @Override
    public ProductDto detail(long id) {

        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isEmpty()) {
            return null;
        }

        Product product = optionalProduct.get();

        return ProductDto.of(product);
    }


}
