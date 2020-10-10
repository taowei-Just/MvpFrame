package com.tao.logger.log;

/**
 * @project android_lib_logger
 * @class name：com.nj.baijiayun.logger.log
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 2019/4/30 11:22 AM
 * @change
 * @time
 * @describe
 */
class CrashLoggerHandler implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler exceptionHandler;

    public void init() {
        exceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Logger.e(e);
        exceptionHandler.uncaughtException(t, e);
    }
}
