package com.mvc.spring.service.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvc.spring.dao.PostDAO;
import com.mvc.spring.dto.FileDTO;
import com.mvc.spring.dto.PostCreateRequestDTO;
import com.mvc.spring.dto.PostResponseDTO;
import com.mvc.spring.file.FileStorage;
import com.mvc.spring.service.PostService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

	private final PostDAO postDAO;
	private final FileStorage fileStorage;

	// 생성자가 하나뿐이면 @Autowired 생략 가능 (Spring 4.3+)
	public PostServiceImpl(PostDAO postDAO, FileStorage fileStorage) {
		this.postDAO = postDAO;
		this.fileStorage = fileStorage;
	}

	@Transactional
	@Override
	public Long createPost(PostCreateRequestDTO postCreateRequestDTO) throws IOException {

		FileDTO fileDTO = null;

		try {
			if (postCreateRequestDTO.getImageFile() != null && !postCreateRequestDTO.getImageFile().isEmpty()) {

				// 파일을 업로드하고, 업로드 결과를 FileDTO로 받는다
				fileDTO = fileStorage.uploadFile(postCreateRequestDTO.getImageFile());

				postCreateRequestDTO.setOriginalFilename(fileDTO.getOriginalFilename());
				postCreateRequestDTO.setStoredFilename(fileDTO.getStoredFilename());
				postCreateRequestDTO.setDateDir(fileDTO.getDateDir());
			}

			postDAO.insert(postCreateRequestDTO);
			return postCreateRequestDTO.getId();

		} catch (Exception e) {
			log.error("게시글 등록 실패", e);

			// 이미 업로드된 파일을 삭제한다
			if (fileDTO != null) {
				fileStorage.deleteFile(fileDTO.getDateDir(), fileDTO.getStoredFilename());
			}
			throw e;
		}
	}

	@Override
	public PostResponseDTO getPost(Long id) {
		return postDAO.findByPost(id);
	}
}