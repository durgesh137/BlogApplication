package com.springboot.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.entity.Post;


//No need to use @Repository or @Transactional annotations
public interface PostRepository extends JpaRepository<Post, Long>{
	//1. Long type is due to primary key type id
	//2. Post is the entity for which this PostRepository is defined

	//3. The JPARepository provides implementation of 
	//all major manipulation with database
	//4. It also provides support for pagination and sorting
	
	//No need to use @Repository annotation here
	//since SimpleJpaRepository class has @Repository annotation which implements JpaRepository interface
	//so there is no need to use @Repository annotation here
	//Also, the SimpleJpaRepository already has @Transactional annotation 
}
