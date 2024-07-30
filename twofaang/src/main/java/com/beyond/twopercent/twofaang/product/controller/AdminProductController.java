package com.beyond.twopercent.twofaang.product.controller;

import com.beyond.twopercent.twofaang.product.dto.CategoryDto;
import com.beyond.twopercent.twofaang.product.dto.ProductAddDto;
import com.beyond.twopercent.twofaang.product.dto.ProductDto;
import com.beyond.twopercent.twofaang.product.entity.Category;
import com.beyond.twopercent.twofaang.product.repository.CategoryRepository;
import com.beyond.twopercent.twofaang.product.service.CategoryService;
import com.beyond.twopercent.twofaang.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/product")
@Tag(name = "관리자 상품 관리 APIs", description = "관리자 상품 관리 API 리스트")
public class AdminProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;


    @GetMapping("/add.do")
    @Operation(summary = "관리자 상품 등록", description = "관리자가 상품 등록 페이지로 이동한다.")
    public String addProductPage(Model model){

        List<Category> categories = categoryRepository.findAll();

        List<CategoryDto> categoryList = CategoryDto.of(categories);

        model.addAttribute("categoryList", categoryList);
        return "admin/product/add";
    }

    @PostMapping("/add.do")
    @Operation(summary = "관리자 상품 등록", description = "관리자가 상품을 등록한다.")
    public String addProduct(ProductAddDto parameter, MultipartFile file) throws IOException {

        if (parameter.getSaleYn() == null) {
            parameter.setSaleYn(false);
        }

        // 파일 저장 로직
        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files/";

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        parameter.setFilename(fileName);
        parameter.setUrlFilename("/files/" + fileName);

        boolean result = productService.add(parameter);

        return "redirect:/admin/main.do";
    }

    @GetMapping("/update.do")
    @Operation(summary = "관리자 상품 수정", description = "관리자가 상품 수정 페이지로 이동한다.")
    public String updateProductPage(Model model, @RequestParam("id") long id){

        ProductDto detail = productService.update(id);

        Optional<Category> optionalCategory =  categoryRepository.findById(detail.getCategoryId());
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            model.addAttribute("categoryName", category.getCategoryName());
        }

        model.addAttribute("categoryList", categoryService.list());
        model.addAttribute("detail", detail);

        return "admin/product/update";
    }

    @PostMapping("/update.do")
    @Operation(summary = "관리자 상품 수정", description = "관리자가 상품을 수정한다.")
    public String updateProduct(Model model, ProductAddDto parameter, MultipartFile file) throws IOException {

        if (parameter.getSaleYn() == null) {
            parameter.setSaleYn(false);
        }

        // 파일 저장 로직
        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files/";

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        parameter.setFilename(fileName);
        parameter.setUrlFilename("/files/" + fileName);

        boolean result = productService.updateProduct(parameter);

        return "redirect:/admin/main.do";
    }

    @GetMapping("/delete.do")
    @Operation(summary = "관리자 상품 삭제", description = "관리자가 상품을 삭제한다.")
    public String deleteProduct(@RequestParam("id") long id){

        boolean result = productService.deleteProduct(id);

        return "redirect:/admin/main.do";
    }
}
