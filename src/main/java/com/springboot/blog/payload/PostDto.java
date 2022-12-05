package com.springboot.blog.payload;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
	private long id;
	private String title;
	private String description;
	private String content;
	private Set<CommentDto> comments;
	//id, title, description, content
}
