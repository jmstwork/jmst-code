package com.founder.web.dto;

public class PagingDto {
	
	private Integer currentPage;
	private Integer totalSize;
	private Integer totalPage;
	private Integer jumpToPage;
	private Integer pageCount;

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getJumpToPage() {
		return jumpToPage;
	}

	public void setJumpToPage(Integer jumpToPage) {
		this.jumpToPage = jumpToPage;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

}
