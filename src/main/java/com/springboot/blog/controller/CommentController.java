package com.springboot.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {

	private CommentService commentService;

	// constructor based dependency injection
	public CommentController(CommentService commentService) {
		super();
		this.commentService = commentService;
	}

	// creating a comment related to post
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId,
			@Valid @RequestBody CommentDto commentDto) {
		return new ResponseEntity<CommentDto>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);

	}

	// method provides all comments
	@GetMapping("/posts/{postId}/comments")
	public ResponseEntity<List<CommentDto>> getComments(@PathVariable(value = "postId") long postId) {
		return new ResponseEntity<List<CommentDto>>(commentService.getCommentsByPostId(postId), HttpStatus.OK);
	}

	// method provides the comment having stated commentId associate with postId
	@GetMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") long postId,
			@PathVariable(value = "commentId") long commentId) {
		return new ResponseEntity<CommentDto>(commentService.getCommentById(postId, commentId), HttpStatus.OK);
	}

	// method to update the comment
	@PutMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> updateCommentById(@PathVariable(value = "postId") long postId,
			@PathVariable(value = "commentId") long commentId, @Valid @RequestBody CommentDto dto) {
		// updating the comment by id
		CommentDto updatedComment = commentService.updateCommentById(dto, postId, commentId);
		return new ResponseEntity<CommentDto>(updatedComment, HttpStatus.OK);
	}

	//method to delete the comment having commentId related to postId
	@DeleteMapping("posts/{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteCommentById(@PathVariable(value = "postId") long postId,
			@PathVariable(value = "commentId") long commentId) {
		commentService.deleteCommentById(postId, commentId);
		return new ResponseEntity<String>("Comment deleted Successfully", HttpStatus.OK);
	}
}
