package com.springboot.blog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	// @Autowired no need to use,constructor based dependency injection
	private PostRepository postRepository;

	// @Autowired no need to use,
	public PostServiceImpl(PostRepository postRepository) {
		super();
		this.postRepository = postRepository;
	}

	@Override
	public PostDto createPost(PostDto postDto) {
		// convert dto to entity
		Post post = mapToEntity(postDto);

		// saving the post
		Post newPost = postRepository.save(post);

		// convert entity to dto
		PostDto postResponse = mapToDto(newPost);

		return postResponse;
	}

	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDirection) {
		// 1. create Pageable instance having specified pageNnumber and page-size
		// Pageable pageable = PageRequest.of(pageNo, pageSize); //sorting support not
		// there
		Pageable pageable = null;
		if (sortDirection.equalsIgnoreCase("asc"))
			pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
		else
			pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());

		// 2. finding all pages from postRepository
		Page<Post> posts = postRepository.findAll(pageable);

		// 3. get content from pages object
		List<Post> postsList = posts.getContent();
		// 4. convert posts to PostDto list
		List<PostDto> postDtosContent = new ArrayList<PostDto>();
		// 5. populating PostDto lists
		for (Post post : postsList) {
			PostDto dto = mapToDto(post);
			postDtosContent.add(dto);
		}
		// 6. Populating the PostResponse object
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtosContent);

		// set page number and page size
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		// total elements and pages
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		// setting status if page is last one
		postResponse.setLast(posts.isLast());

		return postResponse;
	}

	// 3. method to obtain the post by id
	@Override
	public PostDto getPostById(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		PostDto postDto = mapToDto(post);
		return postDto;
		// get post by id long way
//		Optional<Post> post = postRepository.findById(id);
//		//check if post exists
//		if(post.isPresent()) {
//			PostDto postDto = mapToDto(post.get());
//		}
//		throw new ResourceNotFoundException("Post", "Id", );
	}

	// utility method to map Post to PostDto object
	private PostDto mapToDto(Post post) {
		// id, title, description, content
		PostDto dto = new PostDto(post.getId(), post.getTitle(), post.getDescription(), post.getContent());
		return dto;
	}

	// method to map dto to entity, forExample, saving Post
	private Post mapToEntity(PostDto dto) {
		Post post = new Post();// dto.getId(), dto.getTitle(), dto.getDescription(), dto.getContent()
		post.setId(dto.getId());
		post.setTitle(dto.getTitle());
		post.setDescription(dto.getDescription());
		post.setContent(dto.getContent());
		return post;
	}

	// 4. method updates post based on specified id
	@Override
	public PostDto updatePostById(long id, PostDto updatedPostDto) {
		// 1.get post as per id from database
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

		// 2. updating values of existing post
		post.setContent(updatedPostDto.getContent());
		post.setDescription(updatedPostDto.getDescription());
		post.setTitle(updatedPostDto.getTitle());

		// 3. post exists, now update that record, this will update
		// https://stackoverflow.com/questions/11881479/how-do-i-update-an-entity-using-spring-data-jpa
		Post updatedPost = postRepository.save(post);

		// 4. Return PostDto
		return mapToDto(updatedPost);
	}

	// 5. method deletes post by id
	@Override
	public void deletePostById(long id) {
		// check if post with id is there or not
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id ", id));
		// post is there, deleting post
		postRepository.delete(post);
	}

}

/**
 * From Spring 4.3 onwards If a class is configured a spring bean, further it
 * only has one constructor, then we can omit the @Autowired annotation.
 * 
 */
