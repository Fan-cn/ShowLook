package com.hltx.lamic.lamicpay.bean;

/**
 * <pre>
 *     author: Fan
 *     time  : 2019-06-25 18:16
 *     desc  :
 * </pre>
 */
public class HttpModel<T> {
    private int code;
    private String msg;
    private String subCode;
    private String subMsg;
    private T data;

    public HttpModel() {

    }

    public HttpModel(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public HttpModel(int code, String msg, String subCode, String subMsg) {
        this.code = code;
        this.msg = msg;
        this.subCode = subCode;
        this.subMsg = subMsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getSubMsg() {
        return subMsg;
    }

    public void setSubMsg(String subMsg) {
        this.subMsg = subMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
