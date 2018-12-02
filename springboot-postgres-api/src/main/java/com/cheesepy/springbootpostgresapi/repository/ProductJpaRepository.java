package com.cheesepy.springbootpostgresapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.cheesepy.springbootpostgresapi.model.Category;
import com.cheesepy.springbootpostgresapi.model.Product;

@Component
public interface ProductJpaRepository extends JpaRepository<Product, Long>{
	
	List<Product> findByCategoryCategory(Long catId);
	Product findByProdId(Long prodId);

}
