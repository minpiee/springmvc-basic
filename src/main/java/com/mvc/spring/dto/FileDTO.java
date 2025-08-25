package com.mvc.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO {
	private String originalFilename; // 사용자가 업로드한 원본 파일명
	private String storedFilename;   // 서버에 저장된 파일명 (예: UUID.확장자)
	private String dateDir;          // 날짜 기반 디렉터리
}