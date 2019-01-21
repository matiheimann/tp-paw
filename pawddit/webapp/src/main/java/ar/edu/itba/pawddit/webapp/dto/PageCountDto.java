package ar.edu.itba.pawddit.webapp.dto;

public class PageCountDto {
	
	private int pageCount;
	
	public static PageCountDto fromPageCount(int pageCount) {
		final PageCountDto dto = new PageCountDto();
		dto.pageCount = pageCount;
		return dto;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
}
