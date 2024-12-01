package com.skooltest.attachment.user.service;

import static com.skooltest.common.constants.ExceptionConstants.DOCUMENT_PROCESSING_ERROR;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.skooltest.common.exceptions.SkooltestCommonException;
import com.skooltest.entities.tables.attachment.Attachment;

@Service
public class FileStorageService {

	private Path fileStorageLocation = Paths.get(" ".concat(File.separator).concat("Uploads"));

	public String uploadFile(MultipartFile file, Integer userId, String fileName) {
		Path targetLocation;
		String folderName = "USER_".concat(userId.toString());
		try {
			// create a folder for user if it doesn't exist
			new File(this.fileStorageLocation.normalize().toString().concat(File.separator).concat(folderName))
					.mkdirs();
			targetLocation = this.fileStorageLocation.resolve(folderName.concat(File.separator).concat(fileName));
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new SkooltestCommonException(DOCUMENT_PROCESSING_ERROR);
		}
		return folderName.concat(File.separator).concat(fileName);
	}

	public ByteArrayOutputStream downloadFile(Attachment attachment) throws IOException {
		Path path = Paths.get(this.fileStorageLocation.normalize().toString().concat(File.separator)
				.concat(attachment.getLocation()));
		byte[] byteArray = Files.readAllBytes(path);
		ByteArrayOutputStream bos = new ByteArrayOutputStream(byteArray.length);
		bos.write(byteArray, 0, byteArray.length);
		return bos;
	}
}
