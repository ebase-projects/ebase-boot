package me.dwliu.framework.plugin.oss.qiniu;

import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.core.oss.rule.OssRule;
import me.dwliu.framework.core.oss.model.FileInfo;
import me.dwliu.framework.core.oss.template.OssWithBucketTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 七牛存储配置
 *
 * @author liudw
 * @date 2019-11-07 15:47
 **/
@AllArgsConstructor
@Slf4j
public class QiniuOssTemplate implements OssWithBucketTemplate {

	private final String endpoint;
	private final String zone;
	private final String bucketName;
	private final Auth auth;
	private final BucketManager bucketManager;
	private final UploadManager uploadManager;
	private final OssRule ossRule;


	@Override
	@SneakyThrows
	public void makeBucket(String bucketName) {
		String[] buckets = bucketManager.buckets();
		if (buckets != null && buckets.length > 0) {
			List<String> list = Arrays.asList(buckets);
			if (!list.contains(bucketName)) {
				//Response bucket = bucketManager.createBucket(getBucketName(bucketName), Zone.autoZone().getRegion());
				bucketManager.createBucket(getBucketName(bucketName),
					StringUtils.isBlank(zone) ? Zone.autoZone().getRegion() : zone);

			} else {
				log.info("已经存在bucketName：{}", bucketName);
			}
		} else {
			//Response bucket = bucketManager.createBucket(getBucketName(bucketName), Zone.autoZone().getRegion());
			bucketManager.createBucket(getBucketName(bucketName),
				StringUtils.isBlank(zone) ? Zone.autoZone().getRegion() : zone);
		}
	}

	@Override
	public void removeBucket(String bucketName) {
		//无
	}

	@Override
	@SneakyThrows
	public boolean bucketExists(String bucketName) {
		String[] buckets = bucketManager.buckets();
		if (buckets != null && buckets.length > 0) {
			List<String> list = Arrays.asList(buckets);
			if (list.contains(bucketName)) {
				return true;
			}
		}
		return false;
	}

	@Override
	@SneakyThrows
	public void copyFile(String bucketName, String fileName, String destBucketName) {
		bucketManager.copy(getBucketName(bucketName), fileName, getBucketName(destBucketName), fileName);
	}

	@Override
	@SneakyThrows
	public void copyFile(String bucketName, String fileName, String destBucketName, String destFileName) {
		bucketManager.copy(getBucketName(bucketName), fileName, getBucketName(destBucketName), destFileName);
	}

	@Override
	@SneakyThrows
	public FileInfo statFile(String bucketName, String fileName) {
		com.qiniu.storage.model.FileInfo stat = bucketManager.stat(getBucketName(bucketName), fileName);
		FileInfo fileInfo = new FileInfo();
		fileInfo.setFileName(StringUtils.isBlank(stat.key) ? fileName : stat.key);
		fileInfo.setFileUrl(fileUrl(fileInfo.getFileName()));
		fileInfo.setHash(stat.hash);
		fileInfo.setFileSize(stat.fsize);
		fileInfo.setUploadDate(new Date(stat.putTime / 10000));
		fileInfo.setContentType(stat.mimeType);
		return fileInfo;
	}

	@Override
	public FileInfo statFile(String fileName) {
		return this.statFile(getBucketName(), fileName);
	}

	@Override
	public String filePath(String bucketName, String fileName) {
		return getBucketName(bucketName).concat("/").concat(fileName);
	}

	@Override
	public String filePath(String fileName) {
		return getBucketName().concat("/").concat(fileName);
	}

	@Override
	public String fileUrl(String bucketName, String fileName) {
		return endpoint.concat("/").concat(fileName);
	}

	@Override
	public String fileUrl(String fileName) {
		return endpoint.concat("/").concat(fileName);
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
	@SneakyThrows
	public FileInfo putFile(MultipartFile file) {
		return this.putFile(getBucketName(), file.getOriginalFilename(), file.getInputStream());
	}

	@Override
	@SneakyThrows
	public FileInfo putFile(String fileName, MultipartFile file) {
		return this.putFile(getBucketName(), file.getOriginalFilename(), file.getInputStream());
	}


	@Override
	@SneakyThrows
	public void removeFile(String bucketName, String fileName) {
		bucketManager.delete(getBucketName(bucketName), fileName);
	}

	@Override
	@SneakyThrows
	public void removeFile(String fileName) {
		bucketManager.delete(getBucketName(), fileName);
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

	/**
	 * 获取上传凭证，普通上传
	 */
	private String getUploadToken(String bucketName) {
		return auth.uploadToken(getBucketName(bucketName));
	}

	/**
	 * 获取上传凭证，覆盖上传
	 */
	private String getUploadToken(String bucketName, String key) {
		return auth.uploadToken(getBucketName(bucketName), key);
	}

	/**
	 * 文件上传私有
	 *
	 * @param bucketName 存储桶文件
	 * @param stream     流文件
	 * @param key        文件名
	 * @param cover      是否覆盖上传
	 * @return
	 */
	@SneakyThrows
	private FileInfo putFile(String bucketName, InputStream stream, String key, boolean cover) {
		makeBucket(bucketName);
		// 覆盖上传
		if (cover) {
			uploadManager.put(stream, key, getUploadToken(bucketName, key), null, null);
		} else {
			Response response = uploadManager.put(stream, key, getUploadToken(bucketName), null, null);
			int retry = 0;
			int retryCount = 5;
			while (response.needRetry() && retry < retryCount) {
				response = uploadManager.put(stream, key, getUploadToken(bucketName), null, null);
				retry++;
			}
		}
		FileInfo fileInfo = new FileInfo();
		fileInfo.setFileName(key);
		fileInfo.setOriginalName(key);
		fileInfo.setFileUrl(fileUrl(key));
		//fileInfo.setFileSize();
		fileInfo.setUploadDate(new Date());
		return fileInfo;
	}
}
