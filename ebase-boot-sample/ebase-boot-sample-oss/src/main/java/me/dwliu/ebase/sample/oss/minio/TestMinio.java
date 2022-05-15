package me.dwliu.ebase.sample.oss.minio;

import io.minio.*;
import io.minio.messages.Bucket;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;


public class TestMinio {
	public static void main(String[] args) throws Exception {

		MinioClient minioClient = MinioClient.builder()
			.endpoint("http://101.200.134.49:9000")
			.credentials("admin", "admin123")
			.build();


		String bucketName = "test";
		//http://39.106.141.187:9000/test2/2019/11/02/Lighthouse.jpg
		boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());

		if (bucketExists) {
			System.out.println("bucketExists：" + bucketExists + "");
		} else {
			minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
			minioClient.setBucketPolicy(SetBucketPolicyArgs.builder()
				.bucket(bucketName).config(getPolicyType(bucketName, PolicyType.READ)).build());
		}
		List<Bucket> buckets = minioClient.listBuckets();
		for (Bucket bucket : buckets) {
			System.out.println(bucket.name());
		}


		String fileName = "Lighthouse.jpg";
//        String fileName = "烟台一职数字化智慧校园一卡通项目招标文件定稿.doc";
//        String filePath = "/Users/liudw/Downloads/" + fileName;
		String filePath = "/Users/liudw/Pictures/" + fileName;

//        minioClient.putObject(bucketName, fileName, filePath);

		//File file = new File(filePath);
		//FileInputStream fileInputStream = new FileInputStream(file);

//        minioClient.putObject(bucketName, "hello1.doc", filePath, 0L, null, null, "application/octet-stream");


		// minioClient.putObject(bucketName, "2019/11/02/" + fileName, fileInputStream, Long.valueOf(fileInputStream.available()), null, null, "application/octet-stream");


//        Iterable<Result<Item>> results = minioClient.listObjects(bucketName);
//        for (Result<Item> result : results) {
//            System.out.println("----------------");
//            System.out.println(result.get().objectName());
//            System.out.println(result.get().isDir());
//            System.out.println(result.get().objectSize());
//            System.out.println(result.get().owner().name);
//            System.out.println(result.get().lastModified());
//        }
//
//
//		String s = minioClient.presignedGetObject(bucketName, "2019/11/02/" + fileName, 60);
//		System.out.println(s);


		//3.上传
		minioClient.uploadObject(UploadObjectArgs.builder()
			.bucket(bucketName)
			.object(fileName)
			.filename(filePath)
			.build());
		System.out.println("上传成功");


	}

	////文件下载
	//public void download(HttpServletResponse response, String fileName){
	//	InputStream inputStream = null;
	//	try {
	//		//获取文件源信息
	//		StatObjectResponse statObject = minioClient.statObject(StatObjectArgs.builder()
	//			.bucket(bucketName)
	//			.object(fileName)
	//			.build());
	//		//设置响应的内容类型 --浏览器对应不同类型做不同处理
	//		response.setContentType(statObject.contentType());
	//		//设置响应头
	//		response.setHeader("Content-Disposition", "attachment;filename=" +
	//			URLEncoder.encode(fileName, "UTF-8"));
	//
	//		//文件下载
	//		inputStream = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName)
	//			.object(fileName).build());
	//
	//		//利用apache的IOUtils
	//		IOUtils.copy(inputStream, response.getOutputStream());
	//	} catch (Exception e) {
	//		e.printStackTrace();
	//	}finally {
	//		if (inputStream != null){
	//			try {
	//				inputStream.close();
	//			} catch (IOException e) {
	//				e.printStackTrace();
	//			}
	//		}
	//	}
	//}
	//原文链接：https://blog.csdn.net/weixin_48638578/article/details/119860256
	//

	/**
	 * 获取存储桶策略
	 *
	 * @param bucketName 存储桶名称
	 * @param policyType 策略枚举
	 * @return String
	 */
	public static String getPolicyType(String bucketName, PolicyType policyType) {
		StringBuilder builder = new StringBuilder();
		builder.append("{\n");
		builder.append("    \"Statement\": [\n");
		builder.append("        {\n");
		builder.append("            \"Action\": [\n");

		switch (policyType) {
			case WRITE:
				builder.append("                \"s3:GetBucketLocation\",\n");
				builder.append("                \"s3:ListBucketMultipartUploads\"\n");
				break;
			case READ_WRITE:
				builder.append("                \"s3:GetBucketLocation\",\n");
				builder.append("                \"s3:ListBucket\",\n");
				builder.append("                \"s3:ListBucketMultipartUploads\"\n");
				break;
			default:
				builder.append("                \"s3:GetBucketLocation\"\n");
				break;
		}

		builder.append("            ],\n");
		builder.append("            \"Effect\": \"Allow\",\n");
		builder.append("            \"Principal\": \"*\",\n");
		builder.append("            \"Resource\": \"arn:aws:s3:::");
		builder.append(bucketName);
		builder.append("\"\n");
		builder.append("        },\n");
		if (PolicyType.READ.equals(policyType)) {
			builder.append("        {\n");
			builder.append("            \"Action\": [\n");
			builder.append("                \"s3:ListBucket\"\n");
			builder.append("            ],\n");
			builder.append("            \"Effect\": \"Deny\",\n");
			builder.append("            \"Principal\": \"*\",\n");
			builder.append("            \"Resource\": \"arn:aws:s3:::");
			builder.append(bucketName);
			builder.append("\"\n");
			builder.append("        },\n");

		}
		builder.append("        {\n");
		builder.append("            \"Action\": ");

		switch (policyType) {
			case WRITE:
				builder.append("[\n");
				builder.append("                \"s3:AbortMultipartUpload\",\n");
				builder.append("                \"s3:DeleteObject\",\n");
				builder.append("                \"s3:ListMultipartUploadParts\",\n");
				builder.append("                \"s3:PutObject\"\n");
				builder.append("            ],\n");
				break;
			case READ_WRITE:
				builder.append("[\n");
				builder.append("                \"s3:AbortMultipartUpload\",\n");
				builder.append("                \"s3:DeleteObject\",\n");
				builder.append("                \"s3:GetObject\",\n");
				builder.append("                \"s3:ListMultipartUploadParts\",\n");
				builder.append("                \"s3:PutObject\"\n");
				builder.append("            ],\n");
				break;
			default:
				builder.append("\"s3:GetObject\",\n");
				break;
		}

		builder.append("            \"Effect\": \"Allow\",\n");
		builder.append("            \"Principal\": \"*\",\n");
		builder.append("            \"Resource\": \"arn:aws:s3:::");
		builder.append(bucketName);
		builder.append("/*\"\n");
		builder.append("        }\n");
		builder.append("    ],\n");
		builder.append("    \"Version\": \"2012-10-17\"\n");
		builder.append("}\n");
		System.out.println(builder.toString());
		return builder.toString();
	}
}
