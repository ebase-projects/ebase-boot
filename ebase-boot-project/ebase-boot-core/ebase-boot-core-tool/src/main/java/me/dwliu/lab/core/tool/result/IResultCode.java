package me.dwliu.lab.core.tool.result;

import java.io.Serializable;

/**
 * 全局状态码接口
 *
 * @author liudw
 * @date 2019-03-21 16:01
 **/
public interface IResultCode extends Serializable {

    /**
     * 返回消息码
     *
     * @return code
     */
    Integer getCode();

    /**
     * 返回消息内容
     *
     * @return msg
     */
    String getMsg();

}
