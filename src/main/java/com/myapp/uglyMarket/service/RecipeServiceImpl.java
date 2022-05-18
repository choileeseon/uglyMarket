package com.myapp.uglyMarket.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.myapp.uglyMarket.dao.RecipeMapper;
import com.myapp.uglyMarket.entities.Criteria;
import com.myapp.uglyMarket.entities.RecipeVO;

@Service
public class RecipeServiceImpl implements RecipeService{

	
	private RecipeMapper recipeMapper;
	
	public RecipeServiceImpl(RecipeMapper recipeMapper) {
		this.recipeMapper = recipeMapper;
	}

	@Override
	public void enroll(RecipeVO board) {
		recipeMapper.enroll(board);
	}

	@Override
	public List<RecipeVO> getList() {
		return recipeMapper.getList();
	}
	
	@Override
	public RecipeVO getPage(int id) {
		return recipeMapper.getPage(id);
	}

	@Override
	public int modify(RecipeVO board) {
		return recipeMapper.modify(board);
	}

	@Override
	public int delete(int id) {
		return recipeMapper.delete(id);
	}

	@Override
	public List<RecipeVO> getListPaging(Criteria criteria) {
		return recipeMapper.getListPaging(criteria);
	}

	@Override
	public int getTotalPost(Criteria criteria) {
		return recipeMapper.getTotalPost(criteria);
	}

	

	

}
