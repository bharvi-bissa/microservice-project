package com.app.products.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.products.entity.Product;
import com.app.products.service.ProductServiceImpl;

@RestController
@RequestMapping("/products")
public class ProductsController {
	
	@Autowired
	ProductServiceImpl productService;
	
	@GetMapping
	public ResponseEntity<List<Product>> getProducts() {
		return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProducts());
	}

}
