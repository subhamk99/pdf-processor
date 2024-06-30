package com.pdf.handler.pdf_handler.controllers;

import com.pdf.handler.pdf_handler.dto.request.FileUploadDTO;
import com.pdf.handler.pdf_handler.interfaces.FileProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;


@RestController
@RequestMapping("/process")
@Slf4j
public class PDFProcessorController {

	private final FileProcessor fileProcessor;

	public PDFProcessorController(FileProcessor fileProcessor) {
		this.fileProcessor = fileProcessor;
	}

	@GetMapping("/ping")
	public ResponseEntity<String> ping() {
		return new ResponseEntity<>("Ok!", HttpStatusCode.valueOf(200));
	}

	@PostMapping("/unlock")
	public ResponseEntity<byte[]> unlock(@ModelAttribute FileUploadDTO fileUploadDTO) throws IOException {

		byte[] unlockedFileBytes = fileProcessor.unlockFile(fileUploadDTO);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("attachment", fileUploadDTO.getFileName());

		return ResponseEntity.ok()
				.headers(headers)
				.body(unlockedFileBytes);

	}

	@PostMapping("/lock")
	public ResponseEntity<byte[]> lock(@ModelAttribute FileUploadDTO fileUploadDTO) throws IOException {

		byte[] unlockedFileBytes = fileProcessor.lockFile(fileUploadDTO);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("attachment", fileUploadDTO.getFileName());

		return ResponseEntity.ok()
				.headers(headers)
				.body(unlockedFileBytes);

	}

}
