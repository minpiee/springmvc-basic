package com.mvc.spring.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvc.spring.dao.PostDAO;
import com.mvc.spring.dto.PostCreateRequestDTO;
import com.mvc.spring.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	private final PostDAO postDAO;

	// 생성자가 하나뿐이면 @Autowired 생략 가능 (Spring 4.3+)
	public PostServiceImpl(PostDAO postDAO) {
		this.postDAO = postDAO;
	}

	@Transactional
	@Override
	public Long createPost(PostCreateRequestDTO postCreateRequestDTO) {
		return postDAO.insert(postCreateRequestDTO);
	}
}