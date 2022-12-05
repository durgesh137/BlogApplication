package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

public interface PostService {
	//method to create/save post
	PostDto createPost(PostDto postDto);

	//method to get all posts there in database
	PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDirection);

	//3. Method to return Post based on id
	PostDto getPostById(Long id);

	//4. Method to update post by id
	PostDto updatePostById(long id, PostDto updatedPostDto);
	
	//5. method deletes post by id
	void deletePostById(long id);
}
