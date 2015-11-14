package com.alan.dm.common.exception;

/**
 * 自定义顶层异常
 *
 * Created by zhangbinalan on 15/8/15.
 */
public class DMException extends Exception{

    public static Integer code = 10001;

    public DMException(String message) {
        super(message);
    }

    public DMException(String message, Throwable cause, Integer code) {
        super(message, cause);
        code = code;
    }
    public DMException(Integer code) {
        code = code;
    }
    public DMException(Throwable cause, Integer code) {
        super(cause);
        code = code;
    }
    public DMException(String message, Integer code) {
        super(message);
        code = code;
    }
    public DMException(Throwable cause) {
        super(cause);
    }
    public DMException(String message, Throwable cause) {
        super(message, cause);
    }
    public Integer getCode() {
        return code;
    }
}
