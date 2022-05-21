package me.dwliu.ebase.sample.oss.controller;

import lombok.AllArgsConstructor;
import me.dwliu.ebase.sample.oss.minio.CustomOssRule;
import me.dwliu.framework.common.model.Result;
import me.dwliu.framework.core.oss.enums.OssFileNameFormatEnum;
import me.dwliu.framework.core.oss.model.FileInfo;
import me.dwliu.framework.core.oss.rule.OssRule;
import me.dwliu.framework.integration.oss.minio.MinioOssTemplate;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/minio")
public class UploadVideoController {

	//private CustomOssRule ossRule;
	private OssRule ossRule;
	private MinioOssTemplate minioOssTemplate;

	@PostMapping("upload1")
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
			"测试图片", file.getOriginalFilename(), file.getInputStream(), "image/jpg");

		return Result.success(fileInfo);

	}

	@PostMapping("upload3")
	public Result<FileInfo> upload3(@RequestParam("file") MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			return Result.fail("文件不能为空");
		}

		//上传文件
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		FileInfo fileInfo = minioOssTemplate.putFile(file, "image/jpg");

		return Result.success(fileInfo);
	}

	@PostMapping("upload4")
	public Result<FileInfo> upload4(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty()) {
			return Result.fail("文件不能为空");
		}

		String s = ossRule.fileName(file.getOriginalFilename(), OssFileNameFormatEnum.DATE_UUID);
		//上传文件
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		FileInfo fileInfo = minioOssTemplate.putFile(s, file, "image/jpg");

		return Result.success(fileInfo);
	}

	@GetMapping("statFile")
	public Result<FileInfo> statFile(@RequestParam(value = "f") String filename) {

		FileInfo fileInfo = minioOssTemplate.statFile(filename);

		return Result.success(fileInfo);
	}


	@GetMapping("rm")
	public Result<?> rm(@RequestParam(value = "f") String filename) {
		//删除文件
		minioOssTemplate.removeFile(filename);

		return Result.success();
	}

}
