package com.mune.system.utils;

import com.mune.system.entity.Result;

import java.util.Objects;

public class ResultVO {

    private static ThreadLocal<Result> threadLocal = new ThreadLocal<>();
    private int code;
    private String msg;
    private Object data;

    private static synchronized Result  getReult(){
        Result result = threadLocal.get();
        if (Objects.isNull(result)) {
            result = new Result();
            threadLocal.set(result);
        }
        return result;
    }


    public static Result  success(String msg,Object data) {
        Result result = getReult();
        result.setCode(1);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result  success() {
        return success("成功",null);
    }

    public static Result  success(String msg) {
        return success(msg, null);
    }

    public static Result  success(Object data) {
        return success("成功", data);
    }

    public static Result  error(String msg,Object data) {
        Result result = getReult();
        result.setCode(-1);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result  error() {
        return error("失败", null);
    }

    public static Result  error(String msg) {
        return error(msg, null);
    }

    public static Result  error(Object data) {
        return error("失败", data);
    }
}
