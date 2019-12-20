package me.dwliu.lab.core.oss.rule;

import lombok.AllArgsConstructor;
import me.dwliu.lab.core.oss.OssConfigProperties;
import me.dwliu.lab.core.oss.OssFileNameFormatEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * 默认的存储规则实现
 *
 * @author liudw
 * @date 2019-11-05 14:49
 **/
@AllArgsConstructor
public class DefaultOssRule implements OssRule {

    private OssConfigProperties properties;


    @Override
    public String bucketName(String bucketName) {
        return bucketName;
    }

    @Override
    public String fileName(String originalFileName, OssFileNameFormatEnum format) {

        StringBuilder builder = new StringBuilder();
        builder.append(StringUtils.isBlank(properties.getPrefix()) ? "" : properties.getPrefix());
        if (format == OssFileNameFormatEnum.NONE) {
            builder.append(originalFileName);
        } else if (format == OssFileNameFormatEnum.UUID) {
            builder.append(UUID.randomUUID().toString().replace("-", ""));
            builder.append("-");
            builder.append(originalFileName);
        } else {
            builder.append(originalFileName);
        }

        return builder.toString();

    }
}
