package com.myapp.uglyMarket.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.uglyMarket.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	Category findByName(String name);

	List<Category> findAllByOrderBySortingAsc();

	Category findBySlug(String slug);


}
