package me.dwliu.ebase.sample.oss;

import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.core.oss.enums.OssFileNameFormatEnum;
import me.dwliu.framework.core.oss.model.FileInfo;
import me.dwliu.framework.integration.oss.minio.MinioOssTemplate;
import me.dwliu.framework.core.oss.rule.OssRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MinioOssTemplateTest {

	@Autowired
	private MinioOssTemplate minioOssTemplate;

	@Autowired
	private OssRule ossRule;

	//    private String bucketName = UUID.randomUUID().toString().replace("-", "");
	private String bucketName = "101065";

	private String fileName = "Koala.jpg";
	//String fileName = "烟台一职数字化智慧校园一卡通项目招标文件定稿.doc";
	private String filePath = "/Users/liudw/Pictures/" + fileName;

	@Test
	public void makeBucket() {
		minioOssTemplate.makeBucket(bucketName);
	}

	@Test
	public void removeBucket() {
		minioOssTemplate.removeBucket(bucketName);
	}

	@Test
	public void bucketExists() {
		boolean b = minioOssTemplate.bucketExists(bucketName);
		log.info("bucketExists:「{}」", b);
	}

	@Test
	public void copyFile() {
	}

	@Test
	public void copyFile1() {
	}

	@Test
	public void statFile() {
	}

	@Test
	public void statFile1() {
	}

	@Test
	public void filePath() {
		String s = minioOssTemplate.filePath(fileName);
		log.info(s);
	}

	@Test
	public void filePath1() {
	}

	@Test
	public void fileUrl() {
		String s = minioOssTemplate.fileUrl(fileName);
		log.info(s);
	}

	@Test
	public void fileUrl1() {
	}

	@Test
	public void putFile() throws FileNotFoundException {
//        String fileName = "Lighthouse.jpg";
//        String fileName = "烟台一职数字化智慧校园一卡通项目招标文件定稿.doc";
//        String filePath = "/Users/liudw/Downloads/" + fileName;
//        String filePath = "/Users/liudw/Pictures/" + fileName;

//        minioClient.putObject(bucketName, fileName, filePath);

		File file = new File(filePath);
		FileInputStream fileInputStream = new FileInputStream(file);

		FileInfo fileInfo = minioOssTemplate.putFile(fileName, fileInputStream);
		log.info(fileInfo.toString());

	}

	@Test
	public void putFile1() throws FileNotFoundException {
		File file = new File(filePath);
		FileInputStream fileInputStream = new FileInputStream(file);

		FileInfo fileInfo = minioOssTemplate.putFile(bucketName, fileName, fileInputStream);
		log.info(fileInfo.toString());
	}

	@Test
	public void putFile2() throws FileNotFoundException {

		File file = new File(filePath);
		FileInputStream fileInputStream = new FileInputStream(file);
		String originFileName = this.fileName;
		// String fileName = ossRule.fileName(this.fileName, OssFileNameFormatEnum.DATETIME);
		String fileName = ossRule.fileName(this.fileName, OssFileNameFormatEnum.DATE);

		FileInfo fileInfo = minioOssTemplate.putFile(bucketName, originFileName, fileName, fileInputStream);
		log.info(fileInfo.toString());
	}

	@Test
	public void putFile3() {
	}

	@Test
	public void putFile4() {
	}

	@Test
	public void removeFile() {
		minioOssTemplate.removeFile(fileName);
	}

	@Test
	public void removeFile1() {
	}

	@Test
	public void removeFiles() {
	}

	@Test
	public void removeFiles1() {
	}

}
