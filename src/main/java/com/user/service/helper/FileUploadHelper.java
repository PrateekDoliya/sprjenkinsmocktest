package com.user.service.helper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.user.service.exceptions.FileUploadException;

@Component
public class FileUploadHelper {

	public FileUploadHelper() throws IOException {
		
	}

	// public final String UPLOAD_DIR =
	// "D:\\Mokito\\UserService\\src\\main\\resources\\static\\image";
	
	public final String UPLOAD_DIR = new ClassPathResource("static/image/").getFile().getPath();

	public boolean uploadFile(MultipartFile file) {
		boolean isUpload = false;

		try {

			InputStream inputStream = file.getInputStream();
			if(inputStream != null ) {
				byte []data = new byte[inputStream.available()];
				inputStream.read(data);
			}

			// write

//			USING FILE.IO PACKAGE

//			FileOutputStream outputStream = new FileOutputStream(UPLOAD_DIR + File.separator + file.getOriginalFilename());
//			outputStream.write(data);
//			outputStream.flush();
//			outputStream.close();
//			isUpload= true;

//			USING FILE.Nio PAGCKAGE
			Path path = Paths.get(UPLOAD_DIR + File.separator + file.getOriginalFilename());
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			isUpload = true;

		} catch (Exception e) {
			throw new FileUploadException(e.getMessage());
//			e.printStackTrace();
		}

		return isUpload;
	}
}
