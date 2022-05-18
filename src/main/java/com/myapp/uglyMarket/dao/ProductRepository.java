package com.myapp.uglyMarket.dao;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.uglyMarket.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{


	Product findByName(String name);

	Product findBySlugAndIdNot(String slug, int id);

	List<Product> findAllByCategoryId(String categoryId, Pageable pageable);

	long countByCategoryId(int categoryId);

	long countByCategoryId(String categoryId);

	
}
