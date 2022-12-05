package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.payload.CommentDto;

public interface CommentService {
	CommentDto createComment(long id, CommentDto commentDto);

	// get all comments associated with the postid
	List<CommentDto> getCommentsByPostId(long postId);

	// get particular comment having commentId associated with postId
	CommentDto getCommentById(long postId, long commentId);

	// update comment by commentId, provided it belongs to post having postId
	CommentDto updateCommentById(CommentDto dto, long postId, long commentId);

	//delete Comment associated with commentId and postId
	void deleteCommentById(long postId, long commentId);
}
