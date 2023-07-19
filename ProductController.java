package com.bikkadit.electronic.store.controller;

import com.bikkadit.electronic.store.constant.AppConstants;
import com.bikkadit.electronic.store.dto.*;
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

@RestController
@RequestMapping("/products")
public class ProductController {

    private static Logger logger= LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;
    @Autowired
    private FileService fileService;
    @Value("${product.image.path}")
    private String imageUploadPath;

    // create

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        logger.info("Entering the request for the save product data ");
        ProductDto createdProduct = productService.create(productDto);
        logger.info("Complete the request for the save product data ");
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    //update

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto,@PathVariable String productId) {
        logger.info("Entering the request for the update product data with productId : {} ",productId);
        ProductDto updatedProduct = productService.updateProduct(productDto,productId);
        logger.info("Complete the request for the update product data with productId : {} ",productId);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    // delete

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String productId){
        logger.info("Entering the request for the delete product data with productId : {} ",productId);
        productService.delete(productId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message(AppConstants.PRODUCT_DELETED).success(true).status(HttpStatus.OK).build();
        logger.info("Complete the request for the update product data with productId : {} ",productId);
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    // get single id

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getSingleId(@PathVariable String productId) {
        logger.info("Entering the request for the get single  product data with productId : {} ",productId);
        ProductDto productDto = productService.getSingleId(productId);
        logger.info("Complete the request for the get single product data with productId : {} ",productId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }

    // get all

    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAll(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ){
        logger.info("Entering the request for the get all product data ");
        PageableResponse<ProductDto> pageableResponse = productService.getAll(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Complete the request for the get all product data ");
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

    // get all live

    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLive(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ){
        logger.info("Entering the request for the get all live product data ");
        PageableResponse<ProductDto> pageableResponse = productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Complete the request for the get all live product data ");
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

    // search all

    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse<ProductDto>> searchProduct(
            @PathVariable String query,
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ){
        logger.info("Entering the request for the search all product data ");
        PageableResponse<ProductDto> pageableResponse = productService.searchByTitle(query,pageNumber, pageSize, sortBy, sortDir);
        logger.info("Complete the request for the search all product data ");
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

    // upload image

    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(@RequestParam("productImage")MultipartFile image,
                                                            @PathVariable String productId) throws IOException {
        logger.info("Entering the request for the upload  product image with productId : {} ",productId);

        String fileName = fileService.uploadFile(image, imageUploadPath);

        ProductDto product = productService.getSingleId(productId);

        product.setProductImageName(fileName);

        ProductDto updateProduct = productService.updateProduct(product, productId);


        ImageResponse response = ImageResponse.builder().imageName(updateProduct.getProductImageName()).message(AppConstants.PRODUCT_IMAGE)
                .success(true).status(HttpStatus.CREATED).build();
        logger.info("Complete the request for the upload  product image with productId : {} ",productId);
        return  new ResponseEntity<>(response,HttpStatus.CREATED);
    }


    //serve product image

    @GetMapping("/image/{productId}")
    public void serveUserImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        logger.info("Entering the request for the Serve  product image with productId : {} ",productId);
        ProductDto productDto = productService.getSingleId(productId);
        InputStream resource = fileService.getResource(imageUploadPath, productDto.getProductImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
        logger.info("Complete the request for the Serve  product image with productId : {} ",productId);

    }

    }
