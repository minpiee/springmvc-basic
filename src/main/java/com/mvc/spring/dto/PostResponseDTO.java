package com.mvc.spring.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDTO {

	private Long id;                 // 글 ID
	private String title;            // 글 제목
	private String content;          // 글 내용
	private String originalFilename; // 원본 파일명
	private String storedFilename;   // 서버에 저장된 파일명 (예: UUID.확장자)
	private String dateDir;          // 날짜 디렉터리 (예: 20250822)
	private LocalDate updatedAt;     // 글 수정일

	// 날짜를 "yyyy/MM/dd" 형식의 문자열로 변환한다
	public String getUpdatedAtFormatted() {
		return this.updatedAt.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
	}

	// 웹에서 접근할 수 있는 이미지 파일 경로를 만든다 (예: /images/20250822/UUID.확장자)
	// 실제 서버 파일 경로(C:/upload/)를 직접 노출하지 않고, 보안을 위해 가상 경로(/images/**)를 사용한다
	public String getImageUrl() {
		return "/images/" + dateDir + "/" + storedFilename;
	}
}