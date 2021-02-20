package com.mavis.chattingroom.pojo;

/**
 * @Author： xinjingjie
 * @Date：2021/1/26 15:28
 **/
public class ResponseEntity {
    private int code;
    private String message;
    private Object data;

    public ResponseEntity() {
    }

    public ResponseEntity(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
