package com.bikkadit.electronic.store.controller;

import com.bikkadit.electronic.store.constant.AppConstants;
import com.bikkadit.electronic.store.dto.ApiResponseMessage;
import com.bikkadit.electronic.store.dto.CategoryDto;
import com.bikkadit.electronic.store.dto.PageableResponse;
import com.bikkadit.electronic.store.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // create
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        // create service to save object

        CategoryDto categoryDto1 = categoryService.create(categoryDto);
       return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    // update

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable String categoryId) {
        CategoryDto updatedCategory = categoryService.update(categoryDto, categoryId);
        return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
    }

        // delete

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId) {
      categoryService.delete(categoryId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message(AppConstants.CATEGORY_DELETED).success(true).status(HttpStatus.OK).build();
        return  new ResponseEntity<>(responseMessage,HttpStatus.OK);

    }

        // get all
    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>>  getAll(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ) {
        PageableResponse<CategoryDto> pageableResponse = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);


    }

   // get single category detail
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto>  getCategoryId(@PathVariable String categoryId) {
        CategoryDto categoryDto = categoryService.getCategoryId(categoryId);
        return ResponseEntity.ok(categoryDto);

    }

    }

