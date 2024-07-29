package com.beyond.twopercent.twofaang.product.controller;

import com.beyond.twopercent.twofaang.product.dto.CategoryDto;
import com.beyond.twopercent.twofaang.product.dto.CategoryInputDto;
import com.beyond.twopercent.twofaang.product.entity.Product;
import com.beyond.twopercent.twofaang.product.model.ServiceResult;
import com.beyond.twopercent.twofaang.product.repository.ProductRepository;
import com.beyond.twopercent.twofaang.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminMainController {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    @RequestMapping("/main.do")
    public String main(Model model
            , @RequestParam(required = false, defaultValue = "") String searchText
            , @PageableDefault(size = 8, sort = "productId", direction = Sort.Direction.DESC) Pageable pageable){

        Page<Product> products =
                productRepository.findByProductNameContainingOrDescriptionContainingOrKeywordContaining(searchText, searchText, searchText, pageable);

        int startPage = 1;
        int endPage = products.getTotalPages();

        model.addAttribute("products", products);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "admin/main";
    }

    @GetMapping("/category/list.do")
    public String categoryList(Model model){

        List<CategoryDto> list = categoryService.list();

        model.addAttribute("list", list);
        return "admin/category/list";
    }

    @PostMapping("/category/add.do")
    public String categoryAdd(CategoryInputDto parameter, Model model){

        ServiceResult result = categoryService.add(parameter);
        if(!result.isResult()){
            model.addAttribute("message",result.getMessage());
            return "common/error";
        }

        return "redirect:/admin/category/list.do";
    }

    @PostMapping("/category/delete.do")
    public String categoryDel(CategoryInputDto parameter){

        boolean result = categoryService.delete(parameter);

        return "redirect:/admin/category/list.do";
    }

}
