package com.myapp.uglyMarket.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.myapp.uglyMarket.entities.Criteria;
import com.myapp.uglyMarket.entities.RecipeVO;

@Mapper
public interface RecipeMapper {

	/* 게시판 등록 */
	public void enroll(RecipeVO board);
	
	/* 게시판 목록 */
	public List<RecipeVO> getList();
	
	/* 게시판 목록 (페이지 적용): pageNum, amount를 입력받고 객체criteria 생성 없으면 기본(1,10)*/
	public List<RecipeVO> getListPaging(Criteria criteria); //keyword도 추가
	
	/* 게시판 조회 */
	public RecipeVO getPage(int id);
	
	/* 게시판 글수정 */
	public int modify(RecipeVO board);

	/* 게시판 삭제 */
	public int delete(int id);
	
	/* 게시글 총 갯수 */
    public int getTotalPost(Criteria criteria);
}
