package com.app.products.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.products.entity.Product;

@Service
public class ProductServiceImpl implements ProductService{

	@Override
	public List<Product> getAllProducts() {
		List<Product> productList = new ArrayList<>();
		
		Product product1 = new Product();
		product1.setProductId(1);
		product1.setName("Apple Iphone 12");
		product1.setPrice("56000");
		product1.setCurrency("rupee");
		product1.setCategory("Mobile");
		
		Product product2 = new Product();
		product2.setProductId(2);
		product2.setName("Samsung Galaxy S20");
		product2.setPrice("56000");
		product2.setCurrency("rupee");
		product2.setCategory("Mobile");
		
		Product product3 = new Product();
		product3.setProductId(3);
		product3.setName("One Plus Nord");
		product3.setPrice("46000");
		product3.setCurrency("rupee");
		product3.setCategory("Mobile");
		
		productList.add(product1);
		productList.add(product2);
		productList.add(product3);
		
		return productList;
	}

}
