package com.jinglining.core;

import java.util.List;

public class Page<T> {
	
	private int pagesize = 5;
	private int pageNum = 1;
	private int offset = 0;
	private Long totalPages ;
	private Long totalCount; 
	private List<T> result;
	private String order;
	private String orderBy;
	
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
		
		if(pageNum < 1){
			this.pageNum = 1 ;
		}
		
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public Long getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Long totalPages) {
		this.totalPages = totalPages;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
		
		Long totalPageNum = totalCount / pagesize;
		
		if(totalCount % pagesize != 0 ){
			totalPageNum+=1;
		}
		setTotalPages(totalPageNum);
		setOffset((this.pageNum-1)*getPagesize());
	}
	public List<T> getResult() {
		return result;
	}
	public void setResult(List<T> result) {
		this.result = result;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	

}
