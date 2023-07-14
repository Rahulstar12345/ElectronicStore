package com.bikkadit.electronic.store.service;

import com.bikkadit.electronic.store.dto.CategoryDto;
import com.bikkadit.electronic.store.dto.PageableResponse;

public interface CategoryService {
    // create

    CategoryDto create (CategoryDto categoryDto);

    // update

    CategoryDto update(CategoryDto categoryDto,String categoryId);

    // delete

    void delete (String categoryId);

    // get all

    PageableResponse<CategoryDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir);

    // get single category detail

    CategoryDto getCategoryId(String categoryId);

    // search
}
