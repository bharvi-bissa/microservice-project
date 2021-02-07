package com.app.aggregator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.app.aggregator.data.ProductsServiceClient;
import com.app.aggregator.entity.Product;

@RestController
@RequestMapping("/client/product")
public class ProductAggregator {

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	ProductsServiceClient productsServiceClient;

	@GetMapping
	public ResponseEntity<List<Product>> getProducts() {

		/*
		 * String productsUrl = "http://PRODUCTS-MS/products" ;
		 * ResponseEntity<List<Product>> response = restTemplate.exchange( productsUrl,
		 * HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() { });
		 */
		List<Product> productList = null;
		productList = productsServiceClient.getProducts();
		return ResponseEntity.status(HttpStatus.OK).body(productList);
	}
}
