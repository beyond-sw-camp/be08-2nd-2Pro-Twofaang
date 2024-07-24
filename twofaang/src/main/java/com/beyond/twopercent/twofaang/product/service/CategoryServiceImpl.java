package com.beyond.twopercent.twofaang.product.service;

import com.beyond.twopercent.twofaang.product.dto.CategoryDto;
import com.beyond.twopercent.twofaang.product.dto.CategoryInputDto;
import com.beyond.twopercent.twofaang.product.entity.Category;
import com.beyond.twopercent.twofaang.product.model.ServiceResult;
import com.beyond.twopercent.twofaang.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public ServiceResult add(CategoryInputDto parameter) {

        Optional<Category> optionalCategory = categoryRepository.findByCategoryName(parameter.getCategoryName());

        if (optionalCategory.isPresent()) {
            return new ServiceResult(false, "이미 존재하는 카테고리입니다.");
        } else {
            Category category = Category.builder()
                    .categoryName(parameter.getCategoryName())
                    .build();

            categoryRepository.save(category);

            return new ServiceResult();
        }
    }

    @Override
    public List<CategoryDto> list() {

        List<Category> categories = categoryRepository.findAll();

        return CategoryDto.of(categories);
    }

    @Override
    public boolean delete(CategoryInputDto parameter) {

        categoryRepository.deleteById(parameter.getCategoryId());

        return true;
    }
}
