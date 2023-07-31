package com.bikkadit.electronic.store.service;

import com.bikkadit.electronic.store.dto.CategoryDto;
import com.bikkadit.electronic.store.dto.PageableResponse;
import com.bikkadit.electronic.store.entity.Category;
import com.bikkadit.electronic.store.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CategoryServiceTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper mapper;


    Category category;

    @BeforeEach
    public void init(){
        category=Category.builder()
                .title("IPhone")
                .description("It is the most expensive mobile")
                .coverImage("iphone.png").build();
    }

    // create category test

    @Test
    public void createCategoryTest(){

        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
        CategoryDto categoryDto = categoryService.create(mapper.map(category, CategoryDto.class));
        System.out.println(categoryDto.getTitle());
        Assertions.assertEquals("IPhone",categoryDto.getTitle());

    }

    // update category Test
    @Test
    public void updateCategoryTest(){
        String categoryId="";
        CategoryDto categoryDto = CategoryDto.builder()
                .title("WhatsApp")
                .description("It is used for social media purpose..")
                .coverImage("wp.png")
                .build();

        Mockito.when(categoryRepository.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);

        CategoryDto update = categoryService.update(categoryDto, categoryId);
        System.out.println(update.getTitle());
     //   Assertions.assertNotNull(categoryDto);
        Assertions.assertEquals(categoryDto.getTitle(),update.getTitle(),"Name is not match !!");
    }

    // delete category test
    @Test
    public void deleteCategoryTest(){

        String categoryId="45eftwey5";
        Mockito.when(categoryRepository.findById("45eftwey5")).thenReturn(Optional.of(category));
        categoryService.delete(categoryId);
     //   System.out.println(category.getCategoryId());
        Mockito.verify(categoryRepository,Mockito.times(1)).delete(category);
    }

    // get all category test
    @Test
    public void getAllCategoryTest(){
        Category category1 = Category.builder()
                .title("Oppo")
                .description("It is have best camera quality..")
                .coverImage("op.png")
                .build();

        Category category2 = Category.builder()
                .title("samsung")
                .description("It is have low price..")
                .coverImage("sm.png")
                .build();

        Category category3 = Category.builder()
                .title("vivo")
                .description("It is have low price..")
                .coverImage("sm.png")
                .build();

        List<Category> categoryList= Arrays.asList(category,category1,category2,category3);
        Page<Category> page=new PageImpl<>(categoryList);
        Mockito.when(categoryRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<CategoryDto> all = categoryService.getAll(1, 2, "title", "asc");
       System.out.println(all.getTotalElements());
        Assertions.assertEquals(4,all.getContent().size());
    }

    // get category by id test
    @Test
    public void getCategoryByIdTest(){

        String categoryId="fke334412";
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        CategoryDto categoryDto = categoryService.getCategoryId(categoryId);
        System.out.println(categoryDto.getTitle());
        Assertions.assertEquals(category.getTitle(),categoryDto.getTitle(),"Name is not match!!");

    }
}
