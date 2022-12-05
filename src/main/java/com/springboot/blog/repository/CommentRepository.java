package com.springboot.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.payload.CommentDto;

//@Repositroy annotation not required
//since SimpleJpaRepository is already there having 
//@Repository and is @Transactional as well
public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByPostId(long id);
}
