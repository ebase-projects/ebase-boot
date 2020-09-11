package me.dwliu.framework.plugin.security.handler;

/**
 * 简单响应的封装类
 *
 * @author liudw
 * @date 2019-07-03 18:46
 **/
public class SimpleResponse {

    public SimpleResponse(Object content) {
        this.content = content;
    }

    private Object content;

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

}
