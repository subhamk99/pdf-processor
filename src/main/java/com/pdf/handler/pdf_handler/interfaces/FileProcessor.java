package com.pdf.handler.pdf_handler.interfaces;

import com.pdf.handler.pdf_handler.dto.request.FileUploadDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface FileProcessor {

	byte[] unlockFile(FileUploadDTO fileUploadDTO) throws IOException;

	byte[] lockFile(FileUploadDTO fileUploadDTO) throws IOException;
}
