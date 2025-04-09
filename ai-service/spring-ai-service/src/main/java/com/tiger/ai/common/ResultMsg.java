package com.tiger.ai.common;

public class ResultMsg<T> {

    private Integer code;
    private String msg;
    private T data;

    public ResultMsg() {
    }

    public static ResultMsg SUCCESS() {
        return new ResultMsg(200, "success", null);
    }

    public static ResultMsg SUCCESS(Object data) {
        return new ResultMsg(200, "success", data);
    }

    public static ResultMsg FAILED(Object data) {
        return new ResultMsg(201, "failed", data);
    }

    public static ResultMsg FAILED() {
        return new ResultMsg(201, "failed", null);
    }

    public static ResultMsg FAILED(String msg) {
        return new ResultMsg(201, msg, null);
    }

    public ResultMsg(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public ResultMsg<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResultMsg<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResultMsg<T> setData(T data) {
        this.data = data;
        return this;
    }
}
