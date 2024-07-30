package com.beyond.twopercent.twofaang.member.controller;

import com.beyond.twopercent.twofaang.member.dto.CartResponseDto;
import com.beyond.twopercent.twofaang.member.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    // 회원의 장바구니에 상품 추가
    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") Long productId,
                            @RequestParam("amount") int amount,
                            @RequestParam("price") int price,
                            Authentication authentication) {

        String email = authentication.getName();

        cartService.addCart(email, productId, amount, price);
        return "redirect:/carts";
    }

    // 장바구니의 상품들을 모두 출력
    @GetMapping
    public String getCart(Authentication authentication, Model model) {
        String email = authentication.getName();

        List<CartResponseDto> cartItems = cartService.getCartItems(email);
        model.addAttribute("cartItems", cartItems);

        return "cart/myCart";
    }

    // 장바구니에서 상품 삭제
    @PostMapping("/delete")
    public String deleteFromCart(@RequestParam("cartId") long cartId) {
        cartService.removeCartItem(cartId);
        return "redirect:/carts";
    }
}