package com.mvc.spring.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostCreateRequestDTO {

	private Long id;        // 글 ID
	private String title;   // 글 제목
	private String content; // 글 내용
}