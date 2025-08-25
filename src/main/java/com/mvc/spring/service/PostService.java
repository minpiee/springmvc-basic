package com.mvc.spring.service;

import com.mvc.spring.dto.PostCreateRequestDTO;

public interface PostService {
	Long createPost(PostCreateRequestDTO postCreateRequestDTO);
}