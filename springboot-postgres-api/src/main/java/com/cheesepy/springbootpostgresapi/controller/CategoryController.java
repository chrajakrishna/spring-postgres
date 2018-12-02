package com.cheesepy.springbootpostgresapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cheesepy.springbootpostgresapi.exception.ResourceNotFoundException;
import com.cheesepy.springbootpostgresapi.model.Category;
import com.cheesepy.springbootpostgresapi.model.Product;
import com.cheesepy.springbootpostgresapi.repository.CategoryJpaRepository;
import com.cheesepy.springbootpostgresapi.repository.ProductJpaRepository;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private CategoryJpaRepository categoryJpaRepository;
	
	@GetMapping(value ="/all")
	public Page<Category> findAll(Pageable pageable){
		return categoryJpaRepository.findAll(pageable);
	}
	
	@GetMapping(value ="/{catId}")
	public Category findByCategory(@PathVariable final Long catId){
		return categoryJpaRepository.findByCategory(catId);
		
	}
	
	@PostMapping(value ="/post")
	public Category post(@RequestBody final Category category) {
		categoryJpaRepository.save(category);
		return categoryJpaRepository.findByCategory(category.getCategory());
	}
	
	@PutMapping("/updatecategory/{catId}")
    public Category updateCategory(@PathVariable Long catId,
                                   @Valid @RequestBody Category categoryRequest) {
		return categoryJpaRepository.findById(catId)
				.map(category -> {
					category.setCategoryname(categoryRequest.getCategoryname());
                    return categoryJpaRepository.save(category);
                }).orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + catId));
    }


    @DeleteMapping("/deletecategory/{catId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long catId) {
        return categoryJpaRepository.findById(catId)
                .map(category -> {
                	categoryJpaRepository.delete(category);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + catId));
    }
	
}
