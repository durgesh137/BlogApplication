package com.springboot.blog.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
	private long id;

	// validate name should not be null or empty
	// further it should have minimum 2 characters
	@NotEmpty
	@Size(min = 3, message = "Name should have atleast 3 characters")
	private String name;

	// email should not be null or empty and validate email
	@NotEmpty(message = "Email should not be null or empty")
	@Email
	private String email;

	// comment must have at-least 10 characters, should not be null or empty
	@NotEmpty
	@Size(min = 10, message = "Comment body must be minimum of 10 characters")
	private String body;
}
