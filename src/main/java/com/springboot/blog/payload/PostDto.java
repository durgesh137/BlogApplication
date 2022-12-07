package com.springboot.blog.payload;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
	private long id;

	// validate title such that, it is not null or empty
	// further it should have minimum 2 characters
	@NotEmpty
	@Size(min = 2, message = "Post title should have at least 2 characters")
	private String title;
	
	//should not be null or empty, 
	//should have minimum 10 characters
	@NotEmpty
	@Size(min=10, message="Post description should have atleast 10 characters")
	private String description;
	
	//post content should not be null or empty
	@NotEmpty
	private String content;
	private Set<CommentDto> comments;
	// id, title, description, content
}
