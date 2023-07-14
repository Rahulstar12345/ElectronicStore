package com.bikkadit.electronic.store.repository;

import com.bikkadit.electronic.store.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,String> {

}
