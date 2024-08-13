package com.coddingsuttle.JPA.controller;

import com.coddingsuttle.JPA.entity.Product;
import com.coddingsuttle.JPA.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("products")
public class ProductController {
    ProductRepository productRepository;

    private final int PAGE_SIZE=5;

    //Sort using methods
//    @GetMapping
//    List<Product> getAllProducts(String title){
//        return productRepository.findByTitleContainingOrderByPrice("Coca");
//    }

//Sort using sort parameter(Sort sort)
//    @GetMapping
//    List<Product> getAllProducts(@RequestParam(defaultValue = "id") String sortBy){
//        //http://localhost:8080/products?sortBy=title
//
//       // return productRepository.findBy(Sort.by(sortBy));
//      //  return productRepository.findBy(Sort.by(Sort.Direction.DESC,sortBy,"price"));
//        return productRepository.findBy(Sort.by(Sort.Order.desc(sortBy),
//                Sort.Order.desc("price")));
//    }

    //Pagination
//    @GetMapping
//    Page<Product> getAllProducts(@RequestParam(defaultValue = "0")Integer pageNumber,
//                                 @RequestParam(defaultValue = "id") String sortBy){
//
//        //http://localhost:8080/products?sortBy=id&pageNumber=3
//
//        Pageable pageable= PageRequest.of(pageNumber,PAGE_SIZE,Sort.by(sortBy));
//
//        return productRepository.findAll(pageable);
//
//    }

    //Pageable using custom methods
    @GetMapping
    List<Product> getAllProducts(  @RequestParam(defaultValue = "") String title, @RequestParam(defaultValue = "0")Integer pageNumber,
                                  @RequestParam(defaultValue = "id") String sortBy){
        //Filter(Title),Sort and Page
        //http://localhost:8080/products?title=parle&pageNumber=0&sortBy=price

       return productRepository.findByTitleContainingIgnoreCase(title,
               PageRequest.of(pageNumber,PAGE_SIZE,Sort.by(sortBy)));

    }

}
