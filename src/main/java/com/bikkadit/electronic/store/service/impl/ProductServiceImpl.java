package com.bikkadit.electronic.store.service.impl;

import com.bikkadit.electronic.store.constant.AppConstants;
import com.bikkadit.electronic.store.dto.PageableResponse;
import com.bikkadit.electronic.store.dto.ProductDto;
import com.bikkadit.electronic.store.entity.Category;
import com.bikkadit.electronic.store.entity.Product;
import com.bikkadit.electronic.store.exception.ResourceNotFoundException;
import com.bikkadit.electronic.store.helper.Helper;
import com.bikkadit.electronic.store.repository.CategoryRepository;
import com.bikkadit.electronic.store.repository.ProductRepository;
import com.bikkadit.electronic.store.service.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private static Logger logger= LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Value("${product.image.path}")
    private String imageUploadPath;

    @Override
    public ProductDto create(ProductDto productDto) {
        logger.info("Initiating the Service call for the save product data ");
        Product product = mapper.map(productDto, Product.class);
        // product id
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);
        //added date
        product.setAddedDate(new Date());
        Product saveProduct = productRepository.save(product);
        logger.info("Complete the Service call for the save product data ");
        return mapper.map(saveProduct,ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {
        logger.info("Initiating the Service call for the update product data with productId : {}",productId);
        // fetch the product of given id
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT_NOT_FOUND));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        product.setProductImageName(productDto.getProductImageName());
        // save the entity
        Product updatedProduct = productRepository.save(product);
        logger.info("Complete the Service call for the update product data with productId : {}",productId);
        return mapper.map(updatedProduct,ProductDto.class);
    }

    @Override
    public void delete(String productId) {
        logger.info("Initiating the Service call for the delete product data with productId : {}",productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT_NOT_FOUND));
        // delete user profile image
        // images/user/abc.png
        String fullPath = imageUploadPath + product.getProductImageName();
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }catch (NoSuchFileException ex){
            logger.info("User image not found in folder !!");
            ex.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        productRepository.delete(product);
        logger.info("Complete the Service call for the delete product data with productId : {}",productId);
    }

    @Override
    public ProductDto getSingleId(String productId) {
        logger.info("Initiating the Service call for the get single product data with productId : {}",productId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT_NOT_FOUND));
        logger.info("Complete the Service call for the get single product data with productId : {}",productId);
        return mapper.map(product,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        logger.info("Initiating the Service call for the get all product data ");
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        PageRequest pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<Product> page = productRepository.findAll(pageable);
        logger.info("Complete the Service call for the get all product data ");
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir) {
        logger.info("Initiating the Service call for the get all live product data ");
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        PageRequest pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<Product> page = productRepository.findByLiveTrue(pageable);
        logger.info("Complete the Service call for the get all live product data ");
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir) {
        logger.info("Initiating the Service call for the search product by title with subTitle : {} ",subTitle);
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        PageRequest pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<Product> page = productRepository.findByTitleContaining(subTitle,pageable);
        logger.info("Complete the Service call for the search product by title with subTitle : {} ",subTitle);
        return Helper.getPageableResponse(page,ProductDto.class);    }

    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {
        logger.info("Initiating the Service call for the create withe Category in  product with categoryId : {} ",categoryId);
        // fetch the category from db
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND));
        Product product = mapper.map(productDto, Product.class);
       // product id
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);
        //added date
        product.setAddedDate(new Date());
        product.setCategory(category);
        Product saveProduct = productRepository.save(product);
        logger.info("Complete the Service call for the create withe Category in  product with categoryId : {} ",categoryId);
        return mapper.map(saveProduct,ProductDto.class);
    }

    @Override
    public ProductDto updateCategory(String productId, String categoryId) {
        logger.info("Initiating the Service call for the update Category in  product with categoryId : {} ",categoryId);
        // product fetch
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT_NOT_FOUND));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND));
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        logger.info("Complete the Service call for the update Category in  product with categoryId : {} ",categoryId);
        return mapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllByCategory(String categoryId, int pageNumber,int pageSize,String sortBy,String sortDir) {
        logger.info("Initiating the Service call for the get all Category in  product with categoryId : {} ",categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND));
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByCategory(category,pageable);
        logger.info("Complete the Service call for the get all Category in product with categoryId : {} ",categoryId);
        return Helper.getPageableResponse(page,ProductDto.class);
    }
}
