package com.beyond.twopercent.twofaang.product.controller;

import com.beyond.twopercent.twofaang.product.dto.CategoryDto;
import com.beyond.twopercent.twofaang.product.dto.ProductAddDTO;
import com.beyond.twopercent.twofaang.product.entity.Category;
import com.beyond.twopercent.twofaang.product.repository.CategoryRepository;
import com.beyond.twopercent.twofaang.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    @GetMapping("/admin/product/add.do")
    public String addProductPage(Model model){

        List<Category> categories = categoryRepository.findAll();

        List<CategoryDto> categoryList = CategoryDto.of(categories);

        model.addAttribute("categoryList", categoryList);
        return "admin/product/add";
    }

    @PostMapping("/admin/product/add.do")
    public String addProduct(ProductAddDTO parameter, MultipartFile file) throws IOException {

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



}
