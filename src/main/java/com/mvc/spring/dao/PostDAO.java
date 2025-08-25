package com.mvc.spring.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.mvc.spring.dto.PostCreateRequestDTO;
import com.mvc.spring.dto.PostResponseDTO;

@Repository
public class PostDAO {

	private final SqlSessionTemplate sqlSessionTemplate;

	// 생성자가 하나뿐이면 @Autowired 생략 가능 (Spring 4.3+)
	public PostDAO(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public Long insert(PostCreateRequestDTO postCreateRequestDTO) {
		sqlSessionTemplate.insert("TB_POSTS.insert", postCreateRequestDTO);
		return postCreateRequestDTO.getId();
	}

	public PostResponseDTO findByPost(Long id) {
		return sqlSessionTemplate.selectOne("TB_POSTS.findByPost", id);
	}
}