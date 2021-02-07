package com.app.aggregator.data;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import com.app.aggregator.entity.Product;

import feign.FeignException;
import feign.hystrix.FallbackFactory;

@FeignClient(name = "products-ms", fallbackFactory = ProductsFallbackFactory.class)
@Component
public interface ProductsServiceClient {

	@GetMapping("/products")
	public List<Product> getProducts();

}

@Component
class ProductsFallbackFactory implements FallbackFactory<ProductsServiceClient> {

	@Override
	public ProductsServiceClient create(Throwable cause) {
		return new ProductsServiceClientFallback(cause);
	}
}

class ProductsServiceClientFallback implements ProductsServiceClient {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	private final Throwable cause;

	public ProductsServiceClientFallback(Throwable cause) {
		this.cause = cause;
	}

	@Override
	public List<Product> getProducts() {

		if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
			logger.error("404 took place when getProducts was called :: " + cause.getLocalizedMessage());
		} else {
			logger.error("Other Error took place :: " + cause.getLocalizedMessage());
		}
		return new ArrayList<>();
	}

}
