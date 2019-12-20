package me.dwliu.lab.core.oss.exception;

/**
 * 自定义存储异常
 *
 * @author liudw
 * @email ldw4033 at 163.com
 * @date 2016年10月27日 下午10:11:27
 */
public class OssException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public OssException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public OssException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public OssException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;

    }

    public OssException(int code, String msg, Throwable e) {
        super(msg, e);
        this.code = code;
        this.msg = msg;

    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
