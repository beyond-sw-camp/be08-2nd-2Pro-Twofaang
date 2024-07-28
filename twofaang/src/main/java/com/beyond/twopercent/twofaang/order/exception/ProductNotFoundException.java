package com.beyond.twopercent.twofaang.order.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long itemId) {
        super("Product not found with id: " + itemId);
    }
}
