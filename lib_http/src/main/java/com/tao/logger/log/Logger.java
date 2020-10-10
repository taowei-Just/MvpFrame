package com.tao.logger.log;

import android.content.Context;
import android.os.Looper;

import android.util.Log;

import com.tao.logger.intercepter.OkHttpLogInterceptor;

import io.reactivex.annotations.NonNull;

public final class Logger {

    //==============常量================//
    /**
     * 默认tag
     */
    public final static String DEFAULT_FULL_LOG_TAG = "[Logger]";
    /**
     * 最大日志优先级【日志优先级为最大等级，所有日志都不打印】
     */
    private final static int MAX_LOG_PRIORITY = Log.ASSERT;
    /**
     * 最小日志优先级【日志优先级为最小等级，所有日志都打印】
     */
    public final static int MIN_LOG_PRIORITY = Log.VERBOSE;

    //==============属性================//
    /**
     * 默认的日志记录为Logcat
     */
    private static com.tao.logger.log.ILogger sILogger;

    private static String sFullTag = DEFAULT_FULL_LOG_TAG;
    /**
     * 是否打开log
     */
    private static boolean sIsEnable = false;
    /**
     * 日志打印优先级
     */
    private static int sLogPriority = MAX_LOG_PRIORITY;
    private static boolean isInit;
    private static com.tao.logger.log.CrashLoggerHandler crashLoggerHandler;

    //==============属性设置================//

    /**
     * 设置日志记录者的接口
     *
     * @param logger
     */
    public static void setLogger(@NonNull com.tao.logger.log.ILogger logger) {
        sILogger = logger;
    }

    /**
     * 设置日志的tag
     *
     * @param tag
     */
    public static void setTag(String tag) {
        sFullTag = tag;
    }

    /**
     * 设置是否是调试模式
     *
     * @param isEnable
     */
    public static void setEnable(boolean isEnable) {
        sIsEnable = isEnable;
    }

    /**
     * 设置打印日志的等级（只打印改等级以上的日志）
     *
     * @param priority
     */
    public static void setPriority(int priority) {
        sLogPriority = priority;
    }

    //=============打印方法===============//

    /**
     * 打印任何（所有）信息
     *
     * @param msg
     */
    public static void v(String msg) {
        if (enableLog(Log.VERBOSE)) {
            getLogger().log(Log.VERBOSE, sFullTag, msg, null);
        }
    }

    private static ILogger getLogger() {
        if (!isInit) {
            throw new IllegalStateException("you should init Logger with Logger.init() method before " +
                    "any Log method invoke! ");
        }
        return sILogger;
    }

    /**
     * 初始化Logger
     */

    public static void init(Context context) {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new IllegalThreadStateException("you should init Logger in the main thread!");
        }
        if (sILogger == null) {
            isInit = true;
            sILogger = com.tao.logger.log.LoggerFactory.getDefaultLogger(sFullTag);
            crashLoggerHandler = new com.tao.logger.log.CrashLoggerHandler();
            crashLoggerHandler.init();
//                    Thread.UncaughtExceptionHandler handler = Thread.getDefaultUncaughtExceptionHandler();
        }
    }

    /**
     * 打印任何（所有）信息
     *
     * @param tag
     * @param msg
     */
    public static void vTag(String tag, String msg) {
        if (enableLog(Log.VERBOSE)) {
            getLogger().log(Log.VERBOSE, tag, msg, null);
        }
    }

    /**
     * 打印调试信息
     *
     * @param msg
     */
    public static void d(String msg) {
        if (enableLog(Log.DEBUG)) {
            getLogger().log(Log.DEBUG, msg, null);
        }
    }

    /**
     * 打印调试信息
     *
     * @param tag
     * @param msg
     */
    public static void dTag(String tag, String msg) {
        if (enableLog(Log.DEBUG)) {
            getLogger().log(Log.DEBUG, tag, msg, null);
        }
    }

    /**
     * 打印提示性的信息
     *
     * @param msg
     */
    public static void i(String msg) {
        if (enableLog(Log.INFO)) {
            getLogger().log(Log.INFO, msg, null);
        }
    }

    /**
     * 打印提示性的信息
     *
     * @param tag
     * @param msg
     */
    public static void iTag(String tag, String msg) {
        if (enableLog(Log.INFO)) {
            getLogger().log(Log.INFO, tag, msg, null);
        }
    }

    /**
     * 打印warning警告信息
     *
     * @param msg
     */
    public static void w(String msg) {
        if (enableLog(Log.WARN)) {
            getLogger().log(Log.WARN, msg, null);
        }
    }

    /**
     * 打印warning警告信息
     *
     * @param tag
     * @param msg
     */
    public static void wTag(String tag, String msg) {
        if (enableLog(Log.WARN)) {
            getLogger().log(Log.WARN, tag, msg, null);
        }
    }

    /**
     * 打印出错信息
     *
     * @param msg
     */
    public static void e(String msg) {
        if (enableLog(Log.ERROR)) {
            getLogger().log(Log.ERROR, msg, null);
        }
    }

    /**
     * 打印出错信息
     *
     * @param tag
     * @param msg
     */
    public static void eTag(String tag, String msg) {
        if (enableLog(Log.ERROR)) {
            getLogger().log(Log.ERROR, tag, msg, null);
        }
    }

    /**
     * 打印出错堆栈信息
     *
     * @param t
     */
    public static void e(Throwable t) {
        if (enableLog(Log.ERROR)) {
            getLogger().log(Log.ERROR, null, t);
        }
    }

    /**
     * 打印出错堆栈信息
     *
     * @param tag
     * @param t
     */
    public static void eTag(String tag, Throwable t) {
        if (enableLog(Log.ERROR)) {
            getLogger().log(Log.ERROR, tag, null, t);
        }
    }


    /**
     * 打印出错堆栈信息
     *
     * @param msg
     * @param t
     */
    public static void e(String msg, Throwable t) {
        if (enableLog(Log.ERROR)) {
            getLogger().log(Log.ERROR, msg, t);
        }
    }

    /**
     * 打印出错堆栈信息
     *
     * @param tag
     * @param msg
     * @param t
     */
    public static void eTag(String tag, String msg, Throwable t) {
        if (enableLog(Log.ERROR)) {
            getLogger().log(Log.ERROR, tag, msg, t);
        }
    }

    /**
     * 打印严重的错误信息
     *
     * @param msg
     */
    public static void wtf(String msg) {
        if (enableLog(Log.ASSERT)) {
            getLogger().log(Log.ASSERT, msg, null);
        }
    }

    /**
     * 打印严重的错误信息
     *
     * @param tag
     * @param msg
     */
    public static void wtfTag(String tag, String msg) {
        if (enableLog(Log.ASSERT)) {
            getLogger().log(Log.ASSERT, tag, msg, null);
        }
    }

    /**
     * 能否打印
     *
     * @param logPriority
     * @return
     */
    private static boolean enableLog(int logPriority) {
        return getLogger() != null && sIsEnable && logPriority >= sLogPriority;
    }

    public static OkHttpLogInterceptor getOkHttpInterceptor() {
        if (!isInit) {
            throw new IllegalStateException("you should init Logger with Logger.init() method before " +
                    "any Log method invoke! ");
        }
        return com.tao.logger.log.LoggerFactory.getOkHttpInterceptor(sFullTag);
    }
}