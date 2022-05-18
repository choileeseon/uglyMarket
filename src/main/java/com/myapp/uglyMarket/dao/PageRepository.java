package com.myapp.uglyMarket.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myapp.uglyMarket.entities.Page;

public interface PageRepository extends JpaRepository<Page, Integer>{

	//오직 슬러그만 찾는
	Page findBySlug(String slug);

	//Page findBySlugAndId(String slug, int id);

	Page findBySlugAndIdNot(String slug, int id); //슬러그를 찾는데 현재id로 찾는것은 제외

	List<Page> findAllByOrderBySortingAsc();

	

}
