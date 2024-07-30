package com.beyond.twopercent.twofaang.member.service;

import com.beyond.twopercent.twofaang.member.dto.CartResponseDto;
import java.util.List;

public interface CartService {
    void addCart(String email, Long productId, int amount, int price);

    List<CartResponseDto> getCartItems(String email);

    void removeCartItem(Long cartId);
}