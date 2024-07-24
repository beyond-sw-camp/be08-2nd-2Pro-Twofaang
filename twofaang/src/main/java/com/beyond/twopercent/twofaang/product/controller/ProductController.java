package com.beyond.twopercent.twofaang.product.controller;

import com.beyond.twopercent.twofaang.product.dto.CategoryDto;
import com.beyond.twopercent.twofaang.product.dto.ProductAddDTO;
import com.beyond.twopercent.twofaang.product.entity.Category;
import com.beyond.twopercent.twofaang.product.repository.CategoryRepository;
import com.beyond.twopercent.twofaang.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    // 파일 저장 로직
    private String[] getNewSaveFile(String baseLocalPath, String baseUrlPath, String originalFilename) {

        LocalDate now = LocalDate.now();

        String[] dirs = {
                String.format("%s/%d/", baseLocalPath,now.getYear()),
                String.format("%s/%d/%02d/", baseLocalPath, now.getYear(),now.getMonthValue()),
                String.format("%s/%d/%02d/%02d/", baseLocalPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth())};

        String urlDir = String.format("%s/%d/%02d/%02d/", baseUrlPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth());

        for(String dir : dirs) {
            File file = new File(dir);
            if (!file.isDirectory()) {
                file.mkdir();
            }
        }

        String fileExtension = "";
        if (originalFilename != null) {
            int dotPos = originalFilename.lastIndexOf(".");
            if (dotPos > -1) {
                fileExtension = originalFilename.substring(dotPos + 1);
            }
        }

        String uuid = UUID.randomUUID().toString().replaceAll("-", ""); // -로 생성시 없애주기
        String newFilename = String.format("%s%s", dirs[2], uuid);
        String newUrlFilename = String.format("%s%s", urlDir, uuid);
        if (fileExtension.length() > 0) {
            newFilename += "." + fileExtension;
            newUrlFilename += "." + fileExtension;
        }

        return new String[]{newFilename, newUrlFilename};
    }

    @GetMapping("/admin/product/add.do")
    public String addProductPage(Model model){

        List<Category> categories = categoryRepository.findAll();

        List<CategoryDto> categoryList = CategoryDto.of(categories);

        model.addAttribute("categoryList", categoryList);
        return "admin/product/add";
    }

    @PostMapping("/admin/product/add.do")
    public String addProduct(ProductAddDTO parameter, MultipartFile file){

        System.out.println("=================CONTROLLER==================");
        System.out.println("parameter = " + parameter);
        String saveFilename = "";
        String urlFilename = "";

        if (file != null) {
            System.out.println("========= file exist ===============");
            String originalFilename = file.getOriginalFilename();
            System.out.println("========= originalFilename ===============" + originalFilename);
            String baseLocalPath = "C:/Users/Playdata/Documents/be08-2nd-2Pro-Twofaang/twofaang/files";
            String baseUrlPath = "/files";

            String[] arrFilename = getNewSaveFile(baseLocalPath, baseUrlPath, originalFilename);

            saveFilename = arrFilename[0];
            urlFilename = arrFilename[1];

            System.out.println("========= saveFilename ===============" + saveFilename);
            System.out.println("========= urlFilename ===============" + urlFilename);

            try {
                File newFile = new File(saveFilename);
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newFile));
            } catch (IOException e) {
            }
        }

        parameter.setFilename(saveFilename);
        parameter.setUrlFilename(urlFilename);

        System.out.println("parameter = " + parameter);

        boolean result = productService.add(parameter);

        return "redirect:/admin/main.do";
    }



}
