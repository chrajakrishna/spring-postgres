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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cheesepy.springbootpostgresapi.exception.ResourceNotFoundException;
import com.cheesepy.springbootpostgresapi.model.Product;
import com.cheesepy.springbootpostgresapi.repository.ProductJpaRepository;

@RestController
@RequestMapping("/products")
public class ProductController {
 
	@Autowired
	private ProductJpaRepository productJpaRepository;

	@GetMapping(value ="/all")
	public Page<Product> findAll(Pageable pageable){
		return productJpaRepository.findAll(pageable);
	}
	
	@GetMapping(value ="/categories/{catId}")
	public List<Product> findByCategory(@PathVariable final Long catId){
		return productJpaRepository.findByCategoryCategory(catId);
		
	}
	
	@PostMapping(value ="/post")
	public Product post(@RequestBody final Product product) {
		productJpaRepository.save(product);
		return productJpaRepository.findByProdId(product.getProdId());
	}
	
	@PutMapping("/updateproduct/{prodId}")
    public Product updateProduct(@PathVariable Long prodId,
                                   @Valid @RequestBody Product productRequest) {
        return productJpaRepository.findById(prodId)
                .map(product -> {
                    product.setActor(productRequest.getActor());
                    product.setCategory(productRequest.getCategory());
                    product.setPrice(productRequest.getPrice());
                    product.setTitle(productRequest.getTitle());
                    return productJpaRepository.save(product);
                }).orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + prodId));
    }


    @DeleteMapping("/deleteprod/{prodId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long prodId) {
        return productJpaRepository.findById(prodId)
                .map(product -> {
                	productJpaRepository.delete(product);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + prodId));
    }
}
