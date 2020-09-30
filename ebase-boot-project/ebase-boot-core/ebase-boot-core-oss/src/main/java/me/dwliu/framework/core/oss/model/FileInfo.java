package me.dwliu.framework.core.oss.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 文件信息
 *
 * @author liudw
 * @date 2019-03-15 13:12
 **/
@Data
public class FileInfo {
    /**
     * 初始文件名
     */
    private String originalName;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件hash值
     */
    private String hash;
    /**
     * 文件拓展名
     */
    private String fileExtension;
    /**
     * 文件大小（KB）
     */
    private Long fileSize;
    /**
     * 文件路径
     */
    private String fileUrl;
    /**
     * 文件上传时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date uploadDate;
    /**
     * 文件contentType
     */
    private String contentType;


}


