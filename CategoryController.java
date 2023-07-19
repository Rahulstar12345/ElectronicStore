package com.bikkadit.electronic.store.controller;

import com.bikkadit.electronic.store.constant.AppConstants;
import com.bikkadit.electronic.store.dto.*;
import com.bikkadit.electronic.store.service.CategoryService;
import com.bikkadit.electronic.store.service.FileService;
import com.bikkadit.electronic.store.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private static Logger logger=LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${category.profile.image.path}")
    private String imageUploadPath;

    // create

    /**
     * @author Rahul_Sonawane
     * @apiNote create category
     * @param categoryDto
     * @return
     */
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        // create service to save object
        logger.info("Entering the request for the save category data ");
        CategoryDto categoryDto1 = categoryService.create(categoryDto);
        logger.info("Complete the request for the save category data ");
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    // update

    /**
     * @author Rahul_Sonawane
     * @apiNote update category
     * @param categoryDto
     * @param categoryId
     * @return
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable String categoryId) {
        logger.info("Entering the request for update the category data with categoryId : {}",categoryId);
        CategoryDto updatedCategory = categoryService.update(categoryDto, categoryId);
        logger.info("Complete the request for update the category data with categoryId : {}",categoryId);
        return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
    }

        // delete

    /**
     * @author Rahul_Sonawane
     * @apiNote delete category
     * @param categoryId
     * @return
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId) {
        logger.info("Entering the request for delete the category data with categoryId : {}",categoryId);
        categoryService.delete(categoryId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message(AppConstants.CATEGORY_DELETED).success(true).status(HttpStatus.OK).build();
        logger.info("Complete the request for delete the category data with categoryId : {}",categoryId);
        return  new ResponseEntity<>(responseMessage,HttpStatus.OK);

    }

        // get all

    /**
     * @author Rahul_Sonawane
     * @apiNote get all categories
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>>  getAll(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ) {
        logger.info("Entering the request for get all category data");
        PageableResponse<CategoryDto> pageableResponse = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Complete the request for get all category data");
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);


    }

   // get single category detail

    /**
     * @author Rahul_Sonawane
     * @apiNote get single category id
     * @param categoryId
     * @return
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto>  getCategoryId(@PathVariable String categoryId) {
        logger.info("Entering the request for get category data with categoryId : {} ",categoryId);
        CategoryDto categoryDto = categoryService.getCategoryId(categoryId);
        logger.info("Complete the request for get category data with categoryId : {} ",categoryId);
        return ResponseEntity.ok(categoryDto);

    }

    // search category

    // cover category image

    /**
     * @author Rahul_Sonawane
     * @apiNote upload category image
     * @param image
     * @param categoryId
     * @return
     * @throws IOException
     */
    @PostMapping("/image/{categoryId}")
    public ResponseEntity<ImageResponse> coverCategoryImage(@RequestParam("categoryImage")MultipartFile image,@PathVariable String categoryId) throws IOException {
        logger.info("Entering the request for upload category Image with categoryId : {}",categoryId);
        String imageName = fileService.uploadFile(image, imageUploadPath);
        CategoryDto category = categoryService.getCategoryId(categoryId);
        category.setCoverImage(imageName);
        CategoryDto categoryDto = categoryService.update(category, categoryId);
        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).message(AppConstants.IMAGE_UPLOADED).success(true).status(HttpStatus.CREATED).build();
        logger.info("Complete the request for upload category Image with categoryId : {}",categoryId);
        return  new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }

    // serve category image

    /**
     * @author Rahul_Sonawane
     * @apiNote serve category image
     * @param categoryId
     * @param response
     * @throws IOException
     */
    @GetMapping("/image/{categoryId}")
    public void serveCategoryImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {
        logger.info("Entering the request for Serve category Image with categoryId : {}",categoryId);
        CategoryDto category = categoryService.getCategoryId(categoryId);
        logger.info("Category image name : {} ",category.getCoverImage());
        InputStream resource = fileService.getResource(imageUploadPath, category.getCoverImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
        logger.info("Complete the request for Serve category Image with categoryId : {} ",categoryId);

    }

    // create product with category

    /**
     * @author Rahul_Sonawane
     * @apiNote create product with category
     * @param categoryId
     * @param productDto
     * @return
     */
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductWithCategory(
            @PathVariable String categoryId,
            @RequestBody ProductDto productDto
    ){
        logger.info("Entering the request for create product category with categoryId : {}",categoryId);
        ProductDto productWithCategory = productService.createWithCategory(productDto, categoryId);
        logger.info("Complete the request for create product category with categoryId : {}",categoryId);
        return new ResponseEntity<>(productWithCategory,HttpStatus.CREATED);
    }

    // update category with product

    /**
     * @author Rahul_Sonawane
     * @apiNote upload category with product
     * @param productId
     * @param categoryId
     * @return
     */
    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductDto> updateCategoryoOfProduct(@PathVariable String productId,@PathVariable String categoryId) {
        logger.info("Entering the request for update category product with categoryId : {}",categoryId);
        ProductDto productDto = productService.updateCategory(productId, categoryId);
        logger.info("Complete the request for update category product with categoryId : {}",categoryId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }

// get All By Category

    /**
     * @author Rahul_Sonawane
     * @apiNote get all by category
     * @param categoryId
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<PageableResponse<ProductDto>> getAllByCategory
    (@PathVariable String categoryId,
    @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
    @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
    @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
    @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir) {
        logger.info("Entering the request for get all by category with categoryId : {}",categoryId);
        PageableResponse<ProductDto> response = productService.getAllByCategory(categoryId,pageNumber,pageSize,sortBy,sortDir);
        logger.info("Complete the request for get all by category with categoryId : {}",categoryId);
        return new ResponseEntity<>(response,HttpStatus.OK);

}


}

