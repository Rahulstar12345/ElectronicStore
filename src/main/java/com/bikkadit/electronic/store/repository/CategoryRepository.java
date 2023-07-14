package com.bikkadit.electronic.store.repository;

import com.bikkadit.electronic.store.entity.Category;
import com.bikkadit.electronic.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,String> {



}
