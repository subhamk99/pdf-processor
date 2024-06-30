package com.pdf.handler.pdf_handler.services;

import com.pdf.handler.pdf_handler.dto.request.FileUploadDTO;
import com.pdf.handler.pdf_handler.interfaces.FileProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


@Service
@Slf4j
public class PDFFileProcessorImpl implements FileProcessor {

	public byte[] unlockFile(FileUploadDTO fileUploadDTO) throws IOException {
		byte[] unlockedFileBytes;
		byte[] inputFileBytes = fileUploadDTO.getFile().getBytes();

		try (ByteArrayOutputStream unlockedFileOutputStream = new ByteArrayOutputStream();
		     PDDocument document = Loader.loadPDF(inputFileBytes, fileUploadDTO.getFilePassword())) {
			document.setAllSecurityToBeRemoved(true);
			document.save(unlockedFileOutputStream);
			log.info("Removed password!");

			unlockedFileBytes = unlockedFileOutputStream.toByteArray();
		}
		return unlockedFileBytes;
	}

	public byte[] lockFile(FileUploadDTO fileUploadDTO) throws IOException {
		byte[] lockedFileBytes;
		byte[] inputFileBytes = fileUploadDTO.getFile().getBytes();

		try (ByteArrayOutputStream unlockedFileOutputStream = new ByteArrayOutputStream();
		     PDDocument document = Loader.loadPDF(inputFileBytes)) {

			AccessPermission accessPermission = new AccessPermission();
			accessPermission.setCanPrint(false);
			StandardProtectionPolicy protectionPolicy = new
					StandardProtectionPolicy(fileUploadDTO.getFilePassword(), fileUploadDTO.getFilePassword(), accessPermission);

			document.protect(protectionPolicy);
			document.save(unlockedFileOutputStream);
			log.info("Added password!");

			lockedFileBytes = unlockedFileOutputStream.toByteArray();
		}
		return lockedFileBytes;
	}
}
