package com.tao.logger.formater;

/**
 * @project android_lib_logger
 * @class name：com.nj.baijiayun.logger.format
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 2019/4/29 5:51 PM
 * @change
 * @time
 * @describe
 */
public interface IFormatter<T> {

    /**
     * Format the data to a readable and loggable string.
     *
     * @param data the data to format
     * @return the formatted string data
     */
    String format(T data) throws FormatException;
}

