package ar.edu.itba.pawddit.webapp.dto;

import java.util.List;

public class CommentsDto {
	
	private List<CommentDto> comments;
	private int pageCount;
	
	public static CommentsDto fromComments(List<CommentDto> comments, int pageCount) {
		final CommentsDto dto = new CommentsDto();
		dto.comments = comments;
		dto.pageCount = pageCount;
		return dto;
	}

	public List<CommentDto> getComments() {
		return comments;
	}

	public void setComments(List<CommentDto> comments) {
		this.comments = comments;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

}
