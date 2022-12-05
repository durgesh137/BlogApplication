package com.springboot.blog.payload;

import java.util.List;

import lombok.Data;

@Data
public class PostResponse {
	//defining instance variables
	private List<PostDto> content;
	private int pageNo;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	private boolean last;
	
}
