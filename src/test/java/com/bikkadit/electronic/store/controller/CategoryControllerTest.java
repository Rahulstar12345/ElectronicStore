package com.bikkadit.electronic.store.controller;

import com.bikkadit.electronic.store.dto.CategoryDto;
import com.bikkadit.electronic.store.dto.PageableResponse;
import com.bikkadit.electronic.store.entity.Category;
import com.bikkadit.electronic.store.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    private Category category;

    @BeforeEach
    public void init() {
        category = Category.builder()
                .title("IPhone")
                .description("It is the most expensive mobile")
                .coverImage("iphone.png").build();
    }

    // create Category test
    @Test
    public void createCategoryTest() throws Exception {
        CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
        Mockito.when(categoryService.create(Mockito.any())).thenReturn(categoryDto);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(category))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());
    }

    // update category test
    @Test
    public void updateCategoryTest() throws Exception {
        String categoryId="2564gfgr";
        CategoryDto categoryDto = this.mapper.map(category, CategoryDto.class);
//        CategoryDto categoryDto = CategoryDto.builder()
//                .title("WhatsApp")
//                .description("It is used for social media purpose..")
//                .coverImage("wp.png")
//                .build();
        Mockito.when(categoryService.update(Mockito.any(),Mockito.anyString())).thenReturn(categoryDto);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/categories/"+categoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(category))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());
    }

    // delete category test
    @Test
    public void deleteCategoryTest() throws Exception {

        String categoryId="143r45";
        categoryService.delete(categoryId);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/categories/"+categoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(category))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // get All category test
    @Test
    public void getAllCategoryTest() throws Exception {
       CategoryDto dto1 = CategoryDto.builder()
                .title("whatsAppss")
                .description("It is the most expensive mobile")
                .coverImage("iphone.png").build();
       CategoryDto dto2 = CategoryDto.builder()
                .title("vivo")
                .description("It is the most expensive mobile")
                .coverImage("iphone.png").build();
       CategoryDto dto3 = CategoryDto.builder()
                .title("oppo")
                .description("It is the most expensive mobile")
                .coverImage("iphone.png").build();
        PageableResponse<CategoryDto> pageableResponse=new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList(dto1,dto2,dto3));
        pageableResponse.setLastPage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(5);
        pageableResponse.setTotalElements(20);

        Mockito.when(categoryService.getAll(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getCategoryByIdTest() throws Exception {
        String categoryId="33fer5";
        CategoryDto dto1 = CategoryDto.builder()
                .title("whatsAppss")
                .description("It is the most expensive mobile")
                .coverImage("iphone.png").build();

        List<CategoryDto> categoryDtos = Arrays.asList(dto1);
      //  CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
        Mockito.when(categoryService.getCategoryId(categoryId)).thenReturn((CategoryDto) categoryDtos);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/categories/"+categoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    private String convertObjectToJsonString(Object category) {
        try {
            return new ObjectMapper().writeValueAsString(category);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}