package com.myapp.uglyMarket.entities;

import java.util.Arrays;

public class Criteria {
	
	/* 현재 페이지 */
    private int pageNum;
    
    /* 한 페이지 당 보여질 게시물 갯수 */
    private int amount;
    
    /* 스킵할 게시물 수 ( pageNum-1 * amount )*/
    private int skip;
    
    /* 검색어 키워드 */
    private String keyword;
    
    
    /* 검색 타입 (뷰에서 선택됨) */
	private String type;
	
	/* 검색 타입 배열 (type을 배열로 변환) */
	private String[] typeArr;
	
    
    /* 기본 생성자 -> 기본세팅 pageNum=1, amount=10 */
    public Criteria() {
    	this(1,10); // 전체 생성자를 통해 (1,10)을 입력되어 객체 생성
    }
    
    /* 생성자 -> 원하는pageNum, amount */
	public Criteria(int pageNum, int amount) {
		super();
		this.pageNum = pageNum;
		this.amount = amount;
		this.skip = (pageNum-1) * amount;
	}
	
	/*검색 타입 getter setter*/
	public String getType() {
		return type;
	}

	//검색할 타입만 설정되면 typeArr은 자동으로 생성이라 setType에 적음
	public void setType(String type) {
		this.type = type;
		this.typeArr = type.split(""); //한 문자씩 끊어서 배열로 만듬
	}

	public String[] getTypeArr() {
		return typeArr;
	}

	public void setTypeArr(String[] typeArr) {
		this.typeArr = typeArr;
	}
	

	/*getter setter*/
	public int getPageNum() {
		return pageNum;
	}

	//새로 페이지 숫자를 설정 했을때 -> 스킵도 계산
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
		this.skip = (pageNum-1) * this.amount;
	}

	public int getAmount() {
		return amount;
	}

	//페이지당 갯수를 바꿀경우에도 스킵을 다시 계산
	public void setAmount(int amount) {
		this.amount = amount;
		this.skip = (this.pageNum-1) * amount;
	}

	public int getSkip() {
		return skip;
	}

	public void setSkip(int skip) {
		this.skip = skip;
	}
	

	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		return "Criteria [pageNum=" + pageNum + ", amount=" + amount + ", skip=" + skip + ", keyword=" + keyword
				+ ", type=" + type + ", typeArr=" + Arrays.toString(typeArr) + "]";
	}

	

	

	
	
 
}
