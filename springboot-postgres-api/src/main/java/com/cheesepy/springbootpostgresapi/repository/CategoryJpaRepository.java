package com.cheesepy.springbootpostgresapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.cheesepy.springbootpostgresapi.model.Category;


@Component
public interface CategoryJpaRepository extends JpaRepository<Category, Long> {
 
	Category findByCategory(Long category);
}
