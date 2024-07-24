package com.beyond.twopercent.twofaang.product.dto;

import com.beyond.twopercent.twofaang.product.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private long categoryId; // 카테고리 번호

    private String categoryName; // 카테고리명

    public static CategoryDto of(Category category){

        return CategoryDto.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .build();
    }

    public static List<CategoryDto> of(List<Category> categories){
        if (categories == null) {
            return null;
        }
        List<CategoryDto> categoryDtoList = new ArrayList<>();

        for (Category category : categories) {
            categoryDtoList.add(of(category));
        }
        return categoryDtoList;
    }
}
