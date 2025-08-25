package com.mvc.spring.service;

import java.io.IOException;

import com.mvc.spring.dto.PostCreateRequestDTO;

public interface PostService {
	Long createPost(PostCreateRequestDTO postCreateRequestDTO) throws IOException;
}