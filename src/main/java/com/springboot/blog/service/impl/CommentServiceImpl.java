package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	private CommentRepository commentRepository;
	private PostRepository postRepository;

	// constructor for dependency injection, autowired annotation not required
	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
		super();
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
	}

	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
		Comment comment = mapToEntity(commentDto);
		// get post entity by id
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		// set the post to comment entity
		comment.setPost(post);

		// saving the comment entity
		Comment savedComment = commentRepository.save(comment);

		// map to dto now
		CommentDto dto = mapToDto(savedComment);
		return dto;
	}

	// method to map comment to dto
	private CommentDto mapToDto(Comment comment) {
		// id, name, email, body
		CommentDto dto = new CommentDto(comment.getId(), comment.getName(), comment.getEmail(), comment.getBody());
		return dto;
	}

	// mapping dto to entity
	private Comment mapToEntity(CommentDto dto) {
		Comment comment = new Comment();
		// population comment
		comment.setId(dto.getId());
		comment.setName(dto.getName());
		comment.setEmail(dto.getEmail());
		comment.setBody(dto.getBody());
		return comment;
	}

	// method provides all the comments based on the postId
	@Override
	public List<CommentDto> getCommentsByPostId(long postId) {
		// acquiring all comments associated with postId
		List<Comment> comments = commentRepository.findByPostId(postId);
		// convert list of comment entities to list of comment dto's
		return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
	}

	// method provides commentDto having commentId associated with postId
	@Override
	public CommentDto getCommentById(long postId, long commentId) {
		// acquire the post associated with id first
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// acquire comment by id first
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		// check if comment does not belongs to the post
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post");
		}

		CommentDto dto = mapToDto(comment);
		return dto;
	}

	// update comment having commentId, provided it belongs to post having postId
	@Override
	public CommentDto updateCommentById(CommentDto dtoRequest, long postId, long commentId) {
		// get post with postId
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

		// get comment with comment id
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		// check if comment does not belongs to post
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post");
		}
		// update the comment now, ensuring all 3 fields are there
		if (dtoRequest.getName() != null && dtoRequest.getEmail() != null && dtoRequest.getBody() != null) {
			comment.setName(dtoRequest.getName());
			comment.setEmail(dtoRequest.getEmail());
			comment.setBody(dtoRequest.getBody());

			Comment updatedComment = commentRepository.save(comment);
			return mapToDto(updatedComment);
		}

		// if all 3 fields are not there, then return same object without updation
		return mapToDto(comment);

	}

	@Override
	public void deleteCommentById(long postId, long commentId) {
		// get post for specified postId
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		// get comment for specified commentId
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

		// check if comment does not belong to post
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post");
		}

		// deleting the comment
		commentRepository.delete(comment);

	}

}
