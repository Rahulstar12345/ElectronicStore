package com.bikkadit.electronic.store.validate;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImageNameValidator.class)
public @interface ImageNameValid {

    // error mesage
    String message() default "Invalid Image Name !!";

    // represent group of constraints
    Class<?>[] groups() default {};

    // additional information about annotation
    Class<? extends Payload>[] payload() default {};
}
