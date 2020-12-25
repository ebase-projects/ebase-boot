package me.dwliu.framework.core.oss.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件名称格式枚举
 *
 * @author liudw
 * @date 2019-11-05 13:26
 **/
public enum OssFileNameFormatEnum {
    NONE("无", 0),
    DATE("yyyy/MM/dd", 1),
    DATETIME("yyyyMMddHHmmssSSS", 2),
    UUID("UUID", 3);

    /**
     * 描述
     */
    private String desc;
    /**
     * 枚举值
     */
    private int value;

    OssFileNameFormatEnum(String desc, int value) {
        this.desc = desc;
        this.value = value;
    }

    public static OssFileNameFormatEnum getEnum(int value) {
        OssFileNameFormatEnum resultEnum = null;
        OssFileNameFormatEnum[] enumAry = OssFileNameFormatEnum.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].getValue() == value) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }

    public static Map<String, Map<String, Object>> toMap() {
        OssFileNameFormatEnum[] ary = OssFileNameFormatEnum.values();
        Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
        for (int num = 0; num < ary.length; num++) {
            Map<String, Object> map = new HashMap<String, Object>();
            String key = String.valueOf(getEnum(ary[num].getValue()));
            map.put("value", String.valueOf(ary[num].getValue()));
            map.put("desc", ary[num].getDesc());
            enumMap.put(key, map);
        }
        return enumMap;
    }

    /**
     * 获取枚举和描述
     *
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List toList4Enum() {
        OssFileNameFormatEnum[] ary = OssFileNameFormatEnum.values();
        List list = new ArrayList();
        for (int num = 0; num < ary.length; num++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("value", OssFileNameFormatEnum.getEnum(ary[num].getValue()).name());
            map.put("desc", ary[num].getDesc());
            list.add(map);
        }
        return list;
    }


    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List toList() {
        OssFileNameFormatEnum[] ary = OssFileNameFormatEnum.values();
        List list = new ArrayList();
        for (int i = 0; i < ary.length; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("value", String.valueOf(ary[i].getValue()));
            map.put("desc", ary[i].getDesc());
            list.add(map);
        }
        return list;
    }

    /**
     * 取枚举的json字符串
     *
     * @return
     */
    public static String getJsonStr() {
        OssFileNameFormatEnum[] enums = OssFileNameFormatEnum.values();
        StringBuffer jsonStr = new StringBuffer("[");
        for (OssFileNameFormatEnum senum : enums) {
            if (!"[".equals(jsonStr.toString())) {
                jsonStr.append(",");
            }
            jsonStr.append("{id:'").append(senum).append("',desc:'").append(senum.getDesc()).append("',value:'")
                    .append(senum.getValue()).append("'}");
        }
        jsonStr.append("]");
        return jsonStr.toString();
    }


    public String getDesc() {
        return desc;
    }

    public int getValue() {
        return value;
    }
}
