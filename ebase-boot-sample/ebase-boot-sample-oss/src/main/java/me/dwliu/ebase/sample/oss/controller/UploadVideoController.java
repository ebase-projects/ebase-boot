package me.dwliu.ebase.sample.oss.controller;

import lombok.AllArgsConstructor;
import me.dwliu.framework.common.model.Result;
import me.dwliu.framework.core.oss.model.FileInfo;
import me.dwliu.framework.integration.oss.minio.MinioOssTemplate;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/upload")
public class UploadVideoController {

	private MinioOssTemplate minioOssTemplate;

	@PostMapping("uploadVideo")
	public Result<FileInfo> uploadVideo(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return Result.fail("文件不能为空");
		}

		//上传文件
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		FileInfo fileInfo = minioOssTemplate.putFile(file);

		return Result.success(fileInfo);

	}

	@PostMapping("upload2")
	public Result<FileInfo> upload2(@RequestParam("file") MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			return Result.fail("文件不能为空");
		}

		//上传文件
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		FileInfo fileInfo = minioOssTemplate.putFile("ceshiimage",
			"测试图片",file.getOriginalFilename(),file.getInputStream(),"image/jpg");

		return Result.success(fileInfo);

	}


	@PostMapping("upload3")
	public Result<FileInfo> upload3(@RequestParam("file") MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			return Result.fail("文件不能为空");
		}

		//上传文件
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		FileInfo fileInfo = minioOssTemplate.putFile(file,"image/jpg");

		return Result.success(fileInfo);

	}



}
