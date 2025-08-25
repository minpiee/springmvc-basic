package com.mvc.spring.service;

import java.io.IOException;

import com.mvc.spring.dto.PostCreateRequestDTO;
import com.mvc.spring.dto.PostResponseDTO;

public interface PostService {
	Long createPost(PostCreateRequestDTO postCreateRequestDTO) throws IOException;

	PostResponseDTO getPost(Long id);
}