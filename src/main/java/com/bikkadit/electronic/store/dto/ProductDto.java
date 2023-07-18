package com.bikkadit.electronic.store.dto;

import com.bikkadit.electronic.store.entity.Category;
import com.bikkadit.electronic.store.validate.ImageNameValid;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private String productId;

    private String title;

    private String description;

    private int price;

    private int discountedPrice;

    private int quantity;

    private Date addedDate;

    private boolean live;

    private boolean stock;

    @ImageNameValid
    private String productImageName;
    private CategoryDto category;


}
