package com.springboot.blog.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Data annotation is A shortcut for @ToString, @EqualsAndHashCode,
 * @Getter on all fields, @Setter on all non-final fields,
 *         and @RequiredArgsConstructor! ie, it generates all boilerplate
 *         normally associated with POJO
 */
@Getter
@Setter
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
