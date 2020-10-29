package me.dwliu.framework.core.datascope.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据权限查询类型枚举
 *
 * @author liudw
 * @date 2020-01-01 22:27
 **/
public enum DataScopeViewEnum {


    /**
     * 本人可见
     */
    OWN("本人可见", 10),

    /**
     * 所在机构可见
     */
    DEPT("所在部门可见", 20),

    /**
     * 所在机构及子级可见
     */
    DEPT_CHILD("所在部门及子部门可见", 30),

    /**
     * 自定义
     */
    CUSTOM("自定义", -10),

    /**
     * 全部数据
     */
    ALL("全部", 99);

    /**
     * 枚举值
     */
    private int value;
    /**
     * 描述
     */
    private String desc;

    DataScopeViewEnum(String desc, int value) {
        this.desc = desc;
        this.value = value;
    }

    public static DataScopeViewEnum getEnum(int value) {
        DataScopeViewEnum resultEnum = null;
        DataScopeViewEnum[] enumAry = DataScopeViewEnum.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].getValue() == value) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }

    public static Map<String, Map<String, Object>> toMap() {
        DataScopeViewEnum[] ary = DataScopeViewEnum.values();
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
        DataScopeViewEnum[] ary = DataScopeViewEnum.values();
        List list = new ArrayList();
        for (int num = 0; num < ary.length; num++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("value", DataScopeViewEnum.getEnum(ary[num].getValue()).name());
            map.put("desc", ary[num].getDesc());
            list.add(map);
        }
        return list;
    }


    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List toList() {
        DataScopeViewEnum[] ary = DataScopeViewEnum.values();
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
        DataScopeViewEnum[] enums = DataScopeViewEnum.values();
        StringBuffer jsonStr = new StringBuffer("[");
        for (DataScopeViewEnum senum : enums) {
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

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static void main(String[] args) {
        System.out.println(DataScopeViewEnum.getEnum(1));
        System.out.println(DataScopeViewEnum.getJsonStr());
        System.out.println(DataScopeViewEnum.toList());
        System.out.println(DataScopeViewEnum.toMap());
        System.out.println(DataScopeViewEnum.toList4Enum());

    }
}
