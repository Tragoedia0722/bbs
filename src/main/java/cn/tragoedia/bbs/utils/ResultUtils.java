package cn.tragoedia.bbs.utils;

import lombok.Data;

public class ResultUtils {
    public static <T> Result<T> Success(String msg, T data) {
        Result<T> result = new Result<>();
        result.status = 1;
        result.code = 200;
        result.msg = msg;
        result.data = data;
        return result;
    }

    public static <T> Result<T> Success(String msg) {
        Result<T> result = new Result<>();
        result.status = 1;
        result.code = 200;
        result.msg = msg;
        return result;
    }

    public static <T> Result<T> Failure(String msg) {
        Result<T> result = new Result<>();
        result.status = -1;
        result.code = 200;
        result.msg = msg;
        return result;
    }
}
