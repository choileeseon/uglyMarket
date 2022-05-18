package com.myapp.uglyMarket.dao;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.annotation.Rollback;

import com.myapp.uglyMarket.entities.Criteria;
import com.myapp.uglyMarket.entities.RecipeVO;

import lombok.extern.java.Log;

@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE) //실제 DB(연결돼 있는)로 테스트
@Rollback(value = false) //테스트할때 rollback 하지 않음 (테스트 후 그전 상태로 안돌아가도록)
@Log
public class RecipeMapperTest {

	@Autowired
	private RecipeMapper recipeMapper;
	
	@Test
	public void test() {
//		
//		RecipeVO vo = new RecipeVO();
//		vo.setTitle("제목테스트");
//		vo.setIngredient("재료테스트");
//		vo.setContent("내용테스트");
//		vo.setWriter("글쓴이");
//		
//		recipeMapper.enroll(vo);
		
//		List<RecipeVO> list = recipeMapper.getList();
		//반복문으로 테스트 방법3가지
		//1.for 반복문
//		for(int i=0; i<list.size(); i++) {
//			log.info("" + list.get(i)); //0~마지막까지 
//		}
		
		//2.foreach 반복문(더 향상됨)
//		for(RecipeVO vo : list) {
//			log.info(""+vo);
//		}
		
		//3.foreach메소드와 람다식
//		list.forEach(board -> log.info(""+board));
		
		/* 게시판 조회 bno*/
//		int id = 1;
//		log.info("" + recipeMapper.getPage(id));
		
		/* 게시판 수정 */
//		RecipeVO board = new RecipeVO();
//		board.setId(1);
//		board.setTitle("수정 된 제목");
//		board.setIngredient("수정 된 재료");
//		board.setContent("수정 된 내용");
//		
//		int result = recipeMapper.modify(board);
//		
//		log.info("결과 : "+result);
		
		
		/* 게시판 삭제 */
   
//        int result = recipeMapper.delete(8);
//        log.info("result : " + result);
		
		
		/* 게시판 목록(페이징 적용)테스트 */
		 
//	     Criteria criteria = new Criteria(); //기본생성자 (1,10)
//	              
//	     criteria.setPageNum(2);
//	     criteria.setAmount(4);
//	     List<RecipeVO> list = recipeMapper.getListPaging(criteria);
//	     
//	     list.forEach(board -> log.info("" + board));

		/* 총 게시글 수*/
//		int result = recipeMapper.getTotalPost();
//		log.info("총 게시글 수 : " +result);
	}
}




