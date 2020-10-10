package com.tao.logger.printer;

/**
 * @project android_lib_logger
 * @class name：com.nj.baijiayun.logger.priter
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 2019/4/29 5:32 PM
 * @change
 * @time
 * @describe
 */
public interface IPrinter {
    String DEFAULT_TAG = "Logger";

    void print(int priority ,String tag,String msg);
}
