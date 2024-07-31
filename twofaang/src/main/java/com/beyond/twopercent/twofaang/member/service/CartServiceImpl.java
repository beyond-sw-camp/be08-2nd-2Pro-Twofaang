package com.beyond.twopercent.twofaang.member.service;

import com.beyond.twopercent.twofaang.member.dto.CartResponseDto;
import com.beyond.twopercent.twofaang.member.entity.Cart;
import com.beyond.twopercent.twofaang.member.entity.Member;
import com.beyond.twopercent.twofaang.member.repository.CartRepository;
import com.beyond.twopercent.twofaang.member.repository.MemberRepository;
import com.beyond.twopercent.twofaang.product.entity.Product;
import com.beyond.twopercent.twofaang.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {


    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Override
    public void addCart(String email, Long productId, int amount, int price) {
        Optional<Member> member = memberRepository.findByEmail(email);

        Cart existingcart = cartRepository.findCartByMemberIdAAndProductId(member.get().getMemberId(), productId);

        if(existingcart == null) {
            Cart cart = Cart.builder()
                    .memberId(member.get().getMemberId())
                    .productId(productId)
                    .quantity(amount)
                    .totalPrice(price)
                    .build();

            cartRepository.save(cart);
        }else{
            existingcart.setQuantity(existingcart.getQuantity() + amount);
            existingcart.setTotalPrice(existingcart.getTotalPrice() + price);

            cartRepository.save(existingcart);
        }


    }

    @Override
    public List<CartResponseDto> getCartItems(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        List<Cart> cart = cartRepository.findByMemberId(member.get().getMemberId());
        List<CartResponseDto> cartItems = new ArrayList<>();

        for(Cart cartItem : cart) {
            Product product = productRepository.findById(cartItem.getProductId()).orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
            CartResponseDto cartItemDto = CartResponseDto.builder()
                    .cartId(cartItem.getCartId())
                    .productId(cartItem.getProductId())
                    .productName(product.getProductName())
                    .quantity(cartItem.getQuantity())
                    .price(cartItem.getTotalPrice())
                    .productImage(product.getUrlFilename())
                    .build();

            cartItems.add(cartItemDto);
        }

        return cartItems;
    }

    @Override
    public void removeCartItem(Long cartId) {
        cartRepository.deleteById(cartId);
    }
}