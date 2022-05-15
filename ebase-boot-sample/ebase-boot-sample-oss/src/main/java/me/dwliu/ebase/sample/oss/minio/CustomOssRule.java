package me.dwliu.ebase.sample.oss.minio;

import me.dwliu.framework.autoconfigure.oss.OssConfigProperties;
import me.dwliu.framework.core.oss.enums.OssFileNameFormatEnum;
import me.dwliu.framework.core.oss.rule.OssRule;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

//@Component
public class CustomOssRule implements OssRule {
    private String prefix;
    @Autowired
    private OssConfigProperties properties;

    public String bucketName(String bucketName) {
        return bucketName;
    }

    public String fileName(String originalFileName, OssFileNameFormatEnum format) {
        StringBuilder builder = new StringBuilder();
        builder.append(StringUtils.isBlank(this.prefix) ? "" : this.prefix);
        if (format == OssFileNameFormatEnum.NONE) {
            builder.append(originalFileName);
        } else if (format == OssFileNameFormatEnum.UUID) {
            String[] split = StringUtils.split(originalFileName, ".");
            builder.append(split[0]);
            builder.append("-");
            builder.append(UUID.randomUUID().toString().replace("-", ""));
            builder.append("." + split[1]);
        } else if (format == OssFileNameFormatEnum.DATETIME) {
            String[] split = StringUtils.split(originalFileName, ".");
            builder.append(split[0]);
            builder.append("-");
            builder.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
            builder.append("." + split[1]);
        }else {
            builder.append(originalFileName);
        }

        return builder.toString();
    }

    public CustomOssRule(OssConfigProperties properties) {
        this.prefix = properties.getPrefix();
    }
}
