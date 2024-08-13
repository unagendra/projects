package com.coddingsuttle.JPA;

import com.coddingsuttle.JPA.entity.Product;
import com.coddingsuttle.JPA.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class JpaApplicationTests {

	@Autowired
	ProductRepository productRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void saveDataRepository(){
		Product product=Product.builder()
				.sku("Nestle123")
				.title("Nestles")
				.price(new BigDecimal("12.45"))
				.quantity(4)
				.build();

		productRepository.save(product);

	}

	@Test
	void  getDataRepository(){
//		List<Product> products=productRepository.findAll();
//		System.out.println(products);

//		List<Product>  product2=productRepository.findByTitleContaining("Coca");
//		System.out.println(product2);

//		List<Product>  product=productRepository.findByTitleLike("%Coco%");
//		System.out.println(product2);

		//Find the records after particular creation data
//		List<Product> products=productRepository.findByCreatedAtAfter(
//				LocalDateTime.of(2024, 1, 1, 0, 0, 0 ));
//		System.out.println(products);
//
//		List<Product> products1=productRepository.findByQuantityGreaterThanAndPriceLessThan(1,BigDecimal.valueOf(30.00));
//		System.out.println(products1);


	}

	@Test
	void getSingleFromRepository() {
		Optional<Product> productEntity = productRepository.findByTitleAndPrice("Pasta", BigDecimal.valueOf(17.80));
		//print optional value into console
		productEntity.ifPresent(System.out::println);
	}
}
