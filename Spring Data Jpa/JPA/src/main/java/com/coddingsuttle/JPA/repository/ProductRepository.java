package com.coddingsuttle.JPA.repository;

import com.coddingsuttle.JPA.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByTitleLike(String title);

    //Pagination
    List<Product> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    List<Product> findByCreatedAtAfter(LocalDateTime after);

    List<Product> findByQuantityAndPrice(Integer quantity, BigDecimal price);

    List<Product> findByQuantityGreaterThanAndPriceLessThan(Integer quantity, BigDecimal price);

    //@Query("select e.title, e.price from Product e where e.title=:title and e.price=:price")
    @Query("SELECT new com.coddingsuttle.JPA.entity.Product(p.title, p.price) FROM Product p WHERE p.title = ?1 AND p.price = ?2")
    Optional<Product> findByTitleAndPrice(String title, BigDecimal price);

    //sorting(Methods- OrderBy)
    //  List<Product> findByOrderByPrice()
    List<Product> findByTitleContainingOrderByPrice(String title);

    //sort Parameters
    //find all the items and sort it as per the order using sort object
    List<Product> findBy(Sort sort);
}
