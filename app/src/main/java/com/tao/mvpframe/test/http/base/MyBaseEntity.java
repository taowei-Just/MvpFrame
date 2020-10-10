package com.tao.mvpframe.test.http.base;

import okhttp3.MediaType;
import okio.BufferedSource;

public class MyBaseEntity<T> extends BaseEntity<T> {

    @Override
    public MediaType contentType() {
        return null;
    }

    @Override
    public long contentLength() {
        return 0;
    }

    @Override
    public BufferedSource source() {
        return null;
    }
}
