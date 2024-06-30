package com.pdf.handler.pdf_handler.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class FileUploadDTO {

	private String fileName;
	private String filePassword;
	private MultipartFile file;
}
