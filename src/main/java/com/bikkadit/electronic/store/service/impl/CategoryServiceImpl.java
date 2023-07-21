package com.bikkadit.electronic.store.service.impl;

import com.bikkadit.electronic.store.constant.AppConstants;
import com.bikkadit.electronic.store.dto.CategoryDto;
import com.bikkadit.electronic.store.dto.PageableResponse;
import com.bikkadit.electronic.store.entity.Category;
import com.bikkadit.electronic.store.exception.ResourceNotFoundException;
import com.bikkadit.electronic.store.helper.Helper;
import com.bikkadit.electronic.store.repository.CategoryRepository;
import com.bikkadit.electronic.store.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static Logger logger= LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Value("${category.profile.image.path}")
    private String imageUploadPath;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        logger.info("Initiating the Service call for the save category data ");

        // creating categoryId :randomly
        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);

        Category category = mapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        logger.info("Complete the Service call for the save category data ");
        return mapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
        logger.info("Initiating the Service call for the update category data with categoryId : {}",categoryId);
        // get category of given id
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND));
        // update category details

        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updatedCategory = categoryRepository.save(category);
        logger.info("Complete the Service call for the update category data with categoryId : {}",categoryId);
        return mapper.map(updatedCategory,CategoryDto.class);
    }

    @Override
    public void delete(String categoryId) {
        logger.info("Initiating the Service call for the delete category data with categoryId : {}",categoryId);
        // get category of given id
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND));

        // delete user profile image
        // images/user/abc.png
        String fullPath = imageUploadPath + category.getCoverImage();
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }catch (NoSuchFileException ex){
            logger.info("User image not found in folder !!");
            ex.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        categoryRepository.delete(category);
        logger.info("Complete the Service call for the delete category data with categoryId : {}",categoryId);


    }

    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir) {
        logger.info("Initiating the Service call for the get all category data ");
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

        PageRequest pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<Category> page = categoryRepository.findAll(pageable);
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);
        logger.info("Complete the Service call for the get all category data");
        return pageableResponse;
    }

    @Override
    public CategoryDto getCategoryId(String categoryId) {
        logger.info("Initiating the Service call for the get category id data with categoryId : {}",categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND));
        logger.info("Complete the Service call for the get category id data with categoryId : {}",categoryId);
        return mapper.map(category,CategoryDto.class);
    }


}
