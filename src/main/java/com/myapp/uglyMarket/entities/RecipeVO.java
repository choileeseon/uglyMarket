package com.myapp.uglyMarket.entities;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RecipeVO {
	
	/* 게시판 번호 */
	private int id;
	
	/* 게시판 제목 */
	private String title;
	
	/* 재료  */
	private String ingredient;
	
	/* 게시판 내용 */
	private String content;
	
	/* 게시판 작가 */
	private String writer;
	
	/* 등록 날짜 */
	private LocalDateTime registerdate; //timestamp에서 날짜시간을 가져오는 자바 날짜시간데이터
	
	/* 수정 날짜 */
	private LocalDateTime updateDate;
	
}
