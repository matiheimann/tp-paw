package ar.edu.itba.pawddit.webapp.dto;

import java.util.List;

public class PostsDto {
	
	private List<PostDto> posts;
	private int pageCount;
	
	public static PostsDto fromPosts(List<PostDto> posts, int pageCount) {
		final PostsDto dto = new PostsDto();
		dto.posts = posts;
		dto.pageCount = pageCount;
		return dto;
	}

	public List<PostDto> getPosts() {
		return posts;
	}

	public void setPosts(List<PostDto> posts) {
		this.posts = posts;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

}
