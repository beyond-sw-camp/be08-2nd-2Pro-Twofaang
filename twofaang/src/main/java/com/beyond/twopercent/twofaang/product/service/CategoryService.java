package com.beyond.twopercent.twofaang.product.service;

import com.beyond.twopercent.twofaang.product.dto.CategoryDto;
import com.beyond.twopercent.twofaang.product.dto.CategoryInputDto;
import com.beyond.twopercent.twofaang.product.model.ServiceResult;

import java.util.List;

public interface CategoryService {
    ServiceResult add(CategoryInputDto parameter);

    List<CategoryDto> list();

    boolean delete(CategoryInputDto parameter);
}
