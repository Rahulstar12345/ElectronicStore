package com.bikkadit.electronic.store.service;

import com.bikkadit.electronic.store.dto.ProductDto;
import com.bikkadit.electronic.store.entity.Product;
import com.bikkadit.electronic.store.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.function.Supplier;

@SpringBootTest
public class ProductServiceTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper mapper;

    Product product;

    @BeforeEach
    public void init(){
        product=Product.builder()
                .title("iPhone")
                .description("It s very expensive mobile")
                .price(150000)
                .discountedPrice(120000)
                .quantity(20)
                .live(true)
                .stock(true)
                .productImageName("ihpone.png")
                .build();
    }

    // create product test
    @Test
    public void createProductTest(){

        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        ProductDto productDto = productService.create(mapper.map(product, ProductDto.class));
        System.out.println(productDto.getTitle());
        Assertions.assertEquals("iPhone",productDto.getTitle());
    }

    // update product test
    @Test
    public void updateProductTest(){

        String productId="dtevdrwr4";
        ProductDto productDto = ProductDto.builder()
                .title("iPhone 13 pro")
                .description("It s very expensive mobile")
                .price(160000)
                .discountedPrice(130000)
                .quantity(20)
                .live(true)
                .stock(true)
                .productImageName("ihpone.png")
                .build();

        Mockito.when(productRepository.findById("dtevdrwr4")).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        ProductDto updateProduct = productService.updateProduct(productDto, productId);
        System.out.println(updateProduct.getTitle());
        Assertions.assertEquals(productDto.getTitle(),updateProduct.getTitle(),"title not matched...");
    }

    // delete product test
    @Test
    public void deleteProductTest(){
        String productId="43grf43r";
        Mockito.when(productRepository.findById("43grf43r")).thenReturn(Optional.of(product));
        productService.delete(productId);
        Mockito.verify(productRepository,Mockito.times(1)).delete(product);
    }

    // get single product id test
    @Test
    public void getSingleProductIdTest(){
        String productId="prov454df5";
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        ProductDto singleId = productService.getSingleId(productId);
        System.out.println(singleId.getTitle());
        Assertions.assertEquals(product.getTitle(),singleId.getTitle(),"product name not matched!!");
    }

}
