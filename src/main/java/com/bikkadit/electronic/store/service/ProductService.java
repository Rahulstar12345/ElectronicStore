package com.bikkadit.electronic.store.service;

import com.bikkadit.electronic.store.dto.PageableResponse;
import com.bikkadit.electronic.store.dto.ProductDto;

import java.util.List;

public interface ProductService {
    // create

    ProductDto create(ProductDto productDto);

    // update

    ProductDto updateProduct(ProductDto productDto,String productId);

    // delete

    void delete(String productId);

    // get single

    ProductDto getSingleId(String productId);

    // get all

    PageableResponse<ProductDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir );

    // get all live

    PageableResponse<ProductDto> getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir);


    // search product

    PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir);


    // crete product with category

    ProductDto createWithCategory(ProductDto productDto,String categoryId);



}
