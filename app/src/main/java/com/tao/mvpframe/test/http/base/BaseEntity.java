package com.tao.mvpframe.test.http.base;

import okhttp3.ResponseBody;

public  abstract class BaseEntity<T> extends ResponseBody {
    int code;
    String msg;
    boolean success;
    T data;
    public boolean isCodeSuccess() {
        return code == 200 || success;
    }
    @Override
    public String toString() {
        return "BaseEntity{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }

}
