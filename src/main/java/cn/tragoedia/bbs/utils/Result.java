package cn.tragoedia.bbs.utils;

import lombok.Data;

@Data
public class Result<T> {
    int status;
    int code;
    String msg;
    T data;
}
