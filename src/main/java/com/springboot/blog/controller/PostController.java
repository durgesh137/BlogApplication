package com.springboot.blog.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;

@RestController // for @ResponseBody and @Controller
@RequestMapping("/api/posts")
public class PostController {

	// Autowired annotation not used, check at end
	private PostService postService;

	// constructor dependency injection
	public PostController(PostService postService) {
		super();
		this.postService = postService;
	}

	@PreAuthorize("hasRole('ADMIN')")
	// 1. create blog post
	@PostMapping
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
		return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);

	}

	// 2. Get all posts stored in database
	// adding pagination and sorting support now with pageNo pageSize,
	// further dynamic ascending or descending order based on request
	@GetMapping
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDirection) {
		return new ResponseEntity<PostResponse>(postService.getAllPosts(pageNo, pageSize, sortBy, sortDirection),
				HttpStatus.OK);
	}

	// 3. method provides the Post based on the id
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long postId) {
		return new ResponseEntity<PostDto>(postService.getPostById(postId), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	// 4. method updates post based on id
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePostById(@PathVariable("id") long postId,
			@Valid @RequestBody PostDto updatedPostDto) {
		return new ResponseEntity<PostDto>(postService.updatePostById(postId, updatedPostDto), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	// 5. Delete post by id REST method
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePostById(@PathVariable long id) {
		// deleting method by id
		postService.deletePostById(id);
		return new ResponseEntity<String>("Post entity is deleted successfully", HttpStatus.OK);

	}
}

/**
 * From Spring 4.3 onwards If a class is configured a spring bean, further it
 * only has one constructor, then we can omit the @Autowired annotation.
 * 
 */
