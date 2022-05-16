package me.dwliu.framework.integration.oss.aliyun;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import me.dwliu.framework.core.oss.model.FileInfo;
import me.dwliu.framework.core.oss.rule.OssRule;
import me.dwliu.framework.core.oss.template.OssWithBucketTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * aliyun 存储
 *
 * <p>
 * https://help.aliyun.com/document_detail/32008.html?spm=a2c4g.11186623.2.9.159f5d26bvj3OJ#concept-32008-zh
 * </p>
 *
 * @author liudw
 * @date 2019-11-08 21:29
 **/
@AllArgsConstructor
public class AliyunOssTemplate implements OssWithBucketTemplate {

	private final String endpoint;
	private final String bucketName;
	private final OSS ossClient;
	private final OssRule ossRule;

	@Override
	public void makeBucket(String bucketName) {
		if (!bucketExists(bucketName)) {
			ossClient.createBucket(getBucketName(bucketName));
		}
	}

	@Override
	public void removeBucket(String bucketName) {
		ossClient.deleteBucket(getBucketName(bucketName));
	}

	@Override
	public boolean bucketExists(String bucketName) {
		boolean b = ossClient.doesBucketExist(getBucketName(bucketName));
		return b;
	}

	@Override
	public void copyFile(String bucketName, String fileName, String destBucketName) {
		ossClient.copyObject(getBucketName(bucketName), fileName, destBucketName, fileName);
	}

	@Override
	public void copyFile(String bucketName, String fileName, String destBucketName, String destFileName) {
		ossClient.copyObject(getBucketName(bucketName), fileName, destBucketName, destFileName);
	}

	@Override
	public FileInfo statFile(String bucketName, String fileName) {
		ObjectMetadata stat = ossClient.getObjectMetadata(bucketName, fileName);
		FileInfo fileInfo = new FileInfo();
		fileInfo.setFileName(fileName);
		fileInfo.setFileUrl(fileUrl(fileInfo.getFileName()));
		fileInfo.setHash(stat.getContentMD5());
		fileInfo.setFileSize(stat.getContentLength());
		//fileInfo.setUploadDate(stat.getLastModified());
		fileInfo.setContentType(stat.getContentType());
		return fileInfo;
	}

	@Override
	public FileInfo statFile(String fileName) {
		return statFile(getBucketName(), fileName);
	}

	@Override
	public String filePath(String bucketName, String fileName) {
		String bucketNameTmp = "";
		if (org.apache.commons.lang3.StringUtils.isBlank(bucketName)) {
			bucketNameTmp = getBucketName();
		} else {
			bucketNameTmp = getBucketName(bucketName);
		}
		return bucketNameTmp
			.concat(".")
			.concat(endpoint)
			.concat("/")
			.concat(fileName);
	}

	@Override
	public String filePath(String fileName) {
		return filePath(null, fileName);
	}

	@Override
	public String fileUrl(String bucketName, String fileName) {
		return null;
	}

	@Override
	public FileInfo putFile(String bucketName, String originFileName, String fileName, InputStream stream, String contentType) {
		return null;
	}

	/**
	 * 上传文件
	 *
	 * @param bucketName     存储桶名称
	 * @param originFileName 原始文件名称
	 * @param fileName       文件名称
	 * @param stream         文件流
	 * @return
	 */
	@Override
	public FileInfo putFile(String bucketName, String originFileName, String fileName, InputStream stream) {
		return null;
	}

	@Override
	public String fileUrl(String fileName) {
		return null;
	}

	@Override
	public FileInfo putFile(String bucketName, String fileName, InputStream stream) {
		return this.putFile(bucketName, stream, fileName, false);
	}

	@Override
	@SneakyThrows
	public FileInfo putFile(String bucketName, String fileName, MultipartFile file) {
		return this.putFile(bucketName, fileName, file.getInputStream());
	}

	@Override
	public FileInfo putFile(String fileName, InputStream stream) {
		return this.putFile(getBucketName(), fileName, stream);
	}

	@Override
	public FileInfo putFile(MultipartFile file, String contentType) {
		return null;
	}

	@Override
	@SneakyThrows
	public FileInfo putFile(MultipartFile file) {
		return this.putFile(getBucketName(), file.getOriginalFilename(), file.getInputStream());
	}

	@Override
	@SneakyThrows
	public FileInfo putFile(String fileName, MultipartFile file) {
		return this.putFile(getBucketName(), fileName, file.getInputStream());
	}


	@Override
	public void removeFile(String bucketName, String fileName) {
		ossClient.deleteObject(getBucketName(bucketName), fileName);

	}

	@Override
	public void removeFile(String fileName) {
		ossClient.deleteObject(getBucketName(), fileName);

	}

	@Override
	public void removeFiles(String bucketName, List<String> fileNames) {
		fileNames.forEach(fileName -> removeFile(getBucketName(bucketName), fileName));
	}


	@Override
	public void removeFiles(List<String> fileNames) {
		fileNames.forEach(this::removeFile);

	}


	/**
	 * 根据规则生成存储桶名称规则
	 *
	 * @return String
	 */
	public String getBucketName() {
		return getBucketName(bucketName);
	}

	/**
	 * 根据规则生成存储桶名称规则
	 *
	 * @param bucketName 存储桶名称
	 * @return String
	 */
	public String getBucketName(String bucketName) {
		return ossRule.bucketName(bucketName);
	}


	@SneakyThrows
	private FileInfo putFile(String bucketName, InputStream stream, String key, boolean cover) {
		makeBucket(bucketName);
		String originalName = key;
//        key = getFileName(key);
		// 覆盖上传
		if (cover) {
			ossClient.putObject(getBucketName(bucketName), key, stream);
		} else {
			PutObjectResult response = ossClient.putObject(getBucketName(bucketName), key, stream);
			int retry = 0;
			int retryCount = 5;
			while (StringUtils.isEmpty(response.getETag()) && retry < retryCount) {
				response = ossClient.putObject(getBucketName(bucketName), key, stream);
				retry++;
			}
		}
		FileInfo fileInfo = new FileInfo();
		fileInfo.setFileName(key);
		fileInfo.setOriginalName(key);
		fileInfo.setFileUrl(fileUrl(key));
		//fileInfo.setFileSize();
		//fileInfo.setUploadDate(new Date());
		return fileInfo;
	}


}
