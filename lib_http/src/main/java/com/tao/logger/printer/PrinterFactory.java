package com.tao.logger.printer;

/**
 * @project android_lib_logger
 * @class name：com.nj.baijiayun.logger.printer
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 2019/4/29 5:42 PM
 * @change
 * @time
 * @describe
 */
public class PrinterFactory {

    private static final com.tao.logger.printer.IPrinter DEFAULT_PRINTER = new com.tao.logger.printer.LogcatPrinter();

    public static com.tao.logger.printer.IPrinter getDefaultPrinter() {
        return DEFAULT_PRINTER;
    }

    public static IPrinter getPrinter(String tag){
        return new TagPrinter(tag);
    }
}
