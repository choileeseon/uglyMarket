package com.myapp.uglyMarket.entities;

public class PageMakerDTO {

	 /* 시작 페이지 */
    private int startPage;
    
    /* 끝 페이지 */
    private int endPage;
    
    /* 이전 페이지, 다음 페이지 존재유무 */
    private boolean prev, next;
    
    /*총 게시물 수*/
    private int totalPost;
    
    /* 현재 페이지, 페이지당 게시물 표시수 정보 */
    private Criteria criteria;

    /* 마지막페이지,시작페이지,실제끝페이지,이전버튼,다음버튼 */
	public PageMakerDTO(int totalPost, Criteria criteria) {
		this.totalPost = totalPost;
		this.criteria = criteria;
		
		/* 마지막 페이지 :1~10 => 10, 11~20 => 20 */
		/* Math.ceil : 1페이지/10.0 = 0.1 을 올림(ceil)하면 1이다 *10 = 10페이지 */
        this.endPage = (int)(Math.ceil(criteria.getPageNum()/10.0))*10;
        
        /* 시작 페이지 */
        this.startPage = this.endPage -9;
        
        /* 실제 끝 페이지 */
        //200*10.0 = 200.0 / 10개만 보여지기 => ceil(20.0)= 20페이지 
        // 총 게시글 201 / 10 => 20.1 = 21페이지
        int realEnd = (int)(Math.ceil(totalPost * 1.0/criteria.getAmount()));

        //실제 끝 페이지가 마지막 페이지보다 작을 경우(마지막이 10페이지인데 게시글이 50개밖에 안됨) 
        if(realEnd < this.endPage) {
            this.endPage = realEnd; //실제 끝페이지가 = 마지막 페이지로!
        }
        
        /* '<' 이전페이지 참? 시작 페이지가 1보다 큰 경우 이전버튼 */
        this.prev = this.startPage > 1;

        /* '>' 다음페이지 참? 마지막 페이지(20)보다 실제 끝페이지(21)일때 다음버튼 */
        this.next = this.endPage < realEnd;
	
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getTotalPost() {
		return totalPost;
	}

	public void setTotalPost(int totalPost) {
		this.totalPost = totalPost;
	}

	public Criteria getCriteria() {
		return criteria;
	}

	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public String toString() {
		return "PageMakerDTO [startPage=" + startPage + ", endPage=" + endPage + ", prev=" + prev + ", next=" + next
				+ ", totalPost=" + totalPost + ", criteria=" + criteria + "]";
	}

		

    
}
