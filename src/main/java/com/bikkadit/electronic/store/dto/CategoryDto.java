package com.bikkadit.electronic.store.dto;

import com.bikkadit.electronic.store.validate.ImageNameValid;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {

    private String categoryId;

    @NotBlank(message = "title is required !!")
    @Size(min = 4,message = "title must be of minimum 4 characters !!")
    private String title;

    @NotBlank(message = "Description required !!")
    private String description;

    @NotBlank(message = "Image is Required !!")
    @ImageNameValid
    private String coverImage;

}
