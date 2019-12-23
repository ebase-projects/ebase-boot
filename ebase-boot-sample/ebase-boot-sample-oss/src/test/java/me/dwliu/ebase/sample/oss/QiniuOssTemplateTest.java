package me.dwliu.ebase.sample.oss;

import lombok.extern.slf4j.Slf4j;
import me.dwliu.framework.core.oss.enums.OssFileNameFormatEnum;
import me.dwliu.framework.core.oss.model.FileInfo;
import me.dwliu.framework.plugin.oss.qiniu.QiniuOssTemplate;
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
public class QiniuOssTemplateTest {

    @Autowired
    private QiniuOssTemplate qiniuOssTemplate;

    @Autowired
    private OssRule ossRule;

    //    private String bucketName = UUID.randomUUID().toString().replace("-", "");
    private String bucketName = "1010199";

    private String fileName = "Lighthouse.jpg";
    //String fileName = "烟台一职数字化智慧校园一卡通项目招标文件定稿.doc";
    private String filePath = "/Users/liudw/Pictures/" + fileName;

    @Test
    public void makeBucket() {
        qiniuOssTemplate.makeBucket(bucketName);
    }

    @Test
    public void removeBucket() {
        qiniuOssTemplate.removeBucket(bucketName);
    }

    @Test
    public void bucketExists() {
        boolean b = qiniuOssTemplate.bucketExists("544e492e39d247d48ed41a1085bd278d");
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
        FileInfo fileInfo = qiniuOssTemplate.statFile("blog", "image/20191107144801-GpeJ9K.png");
        log.info("fileInfo:" + fileInfo);
    }

    @Test
    public void statFile1() {
    }

    @Test
    public void filePath() {
        String s = qiniuOssTemplate.filePath(fileName);
        log.info(s);
    }

    @Test
    public void filePath1() {
    }

    @Test
    public void fileUrl() {
        String s = qiniuOssTemplate.fileUrl(fileName);
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

        FileInfo fileInfo = qiniuOssTemplate.putFile(fileName, fileInputStream);
        log.info(fileInfo.toString());

    }

    @Test
    public void putFile1() throws FileNotFoundException {
        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);

        FileInfo fileInfo = qiniuOssTemplate.putFile(bucketName, "2019/11/" + fileName, fileInputStream);
        log.info(fileInfo.toString());
    }

    @Test
    public void putFile2() throws FileNotFoundException {

        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        String fileName = ossRule.fileName(this.fileName, OssFileNameFormatEnum.UUID);

        FileInfo fileInfo = qiniuOssTemplate.putFile("2019/11/" + fileName, fileInputStream);
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
        qiniuOssTemplate.removeFile(fileName);
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
