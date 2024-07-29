package com.beyond.twopercent.twofaang.product.controller;

import com.beyond.twopercent.twofaang.member.dto.MemberResponseDto;
import com.beyond.twopercent.twofaang.member.entity.Member;
import com.beyond.twopercent.twofaang.member.repository.MemberRepository;
import com.beyond.twopercent.twofaang.member.service.MemberService;
import com.beyond.twopercent.twofaang.product.dto.ProductDto;
import com.beyond.twopercent.twofaang.product.entity.Product;
import com.beyond.twopercent.twofaang.product.repository.ProductRepository;
import com.beyond.twopercent.twofaang.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

import static java.lang.Long.parseLong;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final MemberService memberService;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @GetMapping("/detail")
    public String detatilProdcct(Model model, @RequestParam("id") long id
            , @PageableDefault(size = 5, sort = "regDt", direction = Sort.Direction.DESC) Pageable pageable
            , Principal principal){

        ProductDto detail = productService.detail(id);
        model.addAttribute("detail", detail);

        return "/member/product/detail";
    }

    @GetMapping("/detail/order")
    public String detailOrder(@RequestParam("productId") String productId,
                              @RequestParam("amount") String amount,
                              @RequestParam("price") String price,
                              Model model){

        Product product = productRepository.findById(parseLong(productId))
                .orElseThrow(() -> new RuntimeException("존재하지 않는 상품"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName(); // 사용자 Email 가져옴

        Optional<Member> optionalMember = memberRepository.findByEmail(userId);

        if(optionalMember.isPresent()){
            Member member = optionalMember.get();
        }



        model.addAttribute("product", product);
        model.addAttribute("amount", amount);
        model.addAttribute("price", price);

        return "member/product/order";
    }


}
