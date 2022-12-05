package com.springboot.blog.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Data annotation is A shortcut for @ToString, @EqualsAndHashCode,
 * @Getter on all fields, @Setter on all non-final fields,
 *         and @RequiredArgsConstructor! ie, it generates all boilerplate
 *         normally associated with POJO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity //
@Table(name = "posts", uniqueConstraints = { @UniqueConstraint(columnNames = { "title" }) })
public class Post {

	@Id // specifying primary key id of type long is primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "content", nullable = false)
	private String content;

	//bi-directional relationship specified
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Comment> comments = new HashSet<>();
}