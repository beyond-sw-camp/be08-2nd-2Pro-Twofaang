package com.beyond.twopercent.twofaang.auth.controller;

import com.beyond.twopercent.twofaang.auth.dto.form.CustomMemberDetails;
import com.beyond.twopercent.twofaang.auth.service.ReissueService;
import com.beyond.twopercent.twofaang.member.entity.Member;
import com.beyond.twopercent.twofaang.member.repository.CartRepository;
import com.beyond.twopercent.twofaang.member.repository.MemberRepository;
import com.beyond.twopercent.twofaang.product.entity.Product;
import com.beyond.twopercent.twofaang.product.repository.ProductRepository;
import com.beyond.twopercent.twofaang.review.repository.ReviewRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Tag(name = "회원 메인 페이지 API", description = "회원 메인 페이지 API")
public class HomeController {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final ReviewRepository reviewRepository;
    private final ReissueService reissueService;

    @GetMapping("/")
    @Operation(summary = "회원 메인", description = "회원 메인 페이지로 이동한다.")
    public String home(Model model,
                       @RequestParam(required = false, defaultValue = "") String searchText,
                       @PageableDefault(size = 8, sort = "productId", direction = Sort.Direction.DESC) Pageable pageable
    ) {


        Page<Product> list = productRepository.findByProductNameContainingOrDescriptionContainingOrKeywordContaining(searchText, searchText, searchText, pageable);

        int startPage = 1;
        int endPage = list.getTotalPages();

        int cartCount;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            cartCount = 0;
        } else {
            String memberEmail = authentication.getName();

            Member member = memberRepository.findByEmail(memberEmail)
                    .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다"));

            cartCount = cartRepository.countByMemberId(member.getMemberId());
        }

        model.addAttribute("cartCount", cartCount);
        model.addAttribute("list", list);
        model.addAttribute("endPage", endPage);
        model.addAttribute("startPage", startPage);

        return "home";
    }
}
