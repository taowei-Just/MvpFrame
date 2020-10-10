package com.tao.logger.log;


import com.tao.logger.intercepter.OkHttpLogInterceptor;
import com.tao.logger.printer.PrinterFactory;
import com.tao.logger.strategy.PrettyFormatStrategy;
import com.tao.logger.strategy.Strategy;


/**
 * @project android_lib_logger
 * @class name：com.nj.baijiayun.logger.log
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 2019/4/29 4:28 PM
 * @change
 * @time
 * @describe
 */
class LoggerFactory {
    public static ILogger getDefaultLogger(String sTag) {
        Strategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(1)
                .logPrinter(com.tao.logger.printer.PrinterFactory.getPrinter(sTag))
                .methodOffset(0)
                .build();
        return new LoggerImpl(formatStrategy);
    }

    public static OkHttpLogInterceptor getOkHttpInterceptor(String tag) {
        return new OkHttpLogInterceptor(PrinterFactory.getPrinter(tag));
    }
}
