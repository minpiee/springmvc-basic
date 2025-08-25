package com.mvc.spring.file;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mvc.spring.dto.FileDTO;

@Component
public class FileStorage implements InitializingBean {

	// 파일을 저장할 디렉터리
	@Value("${uploadDir}")
	private String uploadDir;

	// 허용되는 이미지 파일 확장자 목록
	private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif");

	// 애플리케이션이 실행될 때 한 번만 실행된다
	@Override
	public void afterPropertiesSet() throws IOException {
		// 업로드 디렉터리가 없으면 만든다
		Files.createDirectories(Paths.get(uploadDir));
	}

	// 파일 업로드 처리
	public FileDTO uploadFile(MultipartFile file) throws IOException {

		if (file.isEmpty()) {
			throw new IllegalArgumentException("파일이 비어 있거나 첨부되지 않았습니다.");
		}

		String originalFilename = file.getOriginalFilename();
		if (!StringUtils.hasText(originalFilename)) {
			throw new IllegalArgumentException("파일명이 유효하지 않습니다.");
		}

		int dot = originalFilename.lastIndexOf(".");
		if (dot == -1 || dot == originalFilename.length() - 1) {
			throw new IllegalArgumentException("파일 확장자가 없습니다.");
		}

		String extension = originalFilename.substring(dot + 1).toLowerCase();
		if (!ALLOWED_EXTENSIONS.contains(extension)) {
			throw new IllegalArgumentException("허용되지 않는 파일 형식입니다: " + extension);
		}

		BufferedImage image = ImageIO.read(file.getInputStream());
		if (image == null) {
			throw new IllegalArgumentException("이미지 파일이 아닙니다.");
		}

		// UUID를 사용해 고유한 파일명을 만든다
		String storedFilename = UUID.randomUUID() + "." + extension;

		// 날짜 형식으로 하위 디렉터리를 만든다 (예: C:/upload/20250822)
		String dateDir = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
		Path dir = Paths.get(uploadDir, dateDir);
		Files.createDirectories(dir);

		// 파일 저장 경로 설정 (예: C:/upload/20250822/UUID.확장자)
		Path savePath = dir.resolve(storedFilename);

		// 파일을 실제 경로에 저장한다
		try (InputStream in = file.getInputStream()) {
			Files.copy(in, savePath, StandardCopyOption.REPLACE_EXISTING);
		}

		// 업로드 결과 반환 (원본 이름, 저장된 이름, 날짜 폴더)
		return new FileDTO(originalFilename, storedFilename, dateDir);
	}

	// 파일 삭제 처리
	public void deleteFile(String dateDir, String storedFilename) throws IOException {

		Path filePath = Paths.get(uploadDir, dateDir, storedFilename);
		Files.deleteIfExists(filePath);
	}
}