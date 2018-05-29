package com.welltech.framework.aop.pagination.bean;

/**
 * 
 * 简化分页
 * @author wangxin
 *
 */
public class MyPage{
	
	/**
	 * 当前页
	 */
	private int currentPage = 1;
	
	/**
	 * 总页数
	 */
	private int totalPages;

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	@Override
	public String toString() {
		return "MyPage [currentPage=" + currentPage + ", totalPages=" + totalPages + "]";
	}
	
}
