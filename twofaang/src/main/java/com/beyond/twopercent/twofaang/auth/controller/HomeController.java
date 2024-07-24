package com.beyond.twopercent.twofaang.auth.controller;

import com.beyond.twopercent.twofaang.product.entity.Product;
import com.beyond.twopercent.twofaang.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductRepository productRepository;

    @GetMapping("/")
    public String home(Model model,
                       @RequestParam(required = false, defaultValue = "") String searchText,
                       @PageableDefault(size = 8, sort = "productId", direction = Sort.Direction.DESC) Pageable pageable,
                       Principal principal) {

        Page<Product> list = productRepository.findByProductNameContainingOrDescriptionContaining(searchText, searchText, pageable);

        int startPage = 1;
        int endPage = list.getTotalPages();

        int cartCount;

        if (principal == null) {
            cartCount = 0;
        } else {
            // member 가 있을 경우
            cartCount = 1;
        }

        model.addAttribute("list", list);
        model.addAttribute("endPage", endPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("cartCount", cartCount);

        return "home";
    }
}
