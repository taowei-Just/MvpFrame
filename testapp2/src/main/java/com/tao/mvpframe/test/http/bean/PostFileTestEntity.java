package com.tao.mvpframe.test.http.bean;

import com.tao.mvpframe.test.http.base.BaseEntity;

import okhttp3.MediaType;
import okio.BufferedSource;

public class PostFileTestEntity<T> extends BaseEntity <T>{
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
