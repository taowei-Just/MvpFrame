package com.tao.logger.strategy;


import com.tao.logger.printer.IPrinter;



import static com.tao.logger.utils.Utils.checkNotNull;

/**
 * @project android_lib_logger
 * @class name：com.nj.baijiayun.logger.strategy
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 2019/4/29 6:01 PM
 * @change
 * @time
 * @describe
 */
public abstract class Strategy {
    private final IPrinter printer;

    Strategy(IPrinter printer) {
        checkNotNull(printer);
        this.printer = printer;
    }

    public abstract void log(int priority,   String tag,  String message);


    /**
     * you should call this method to log the final message
     */
    public void logActual(int priority,   String tag,  String message) {
        printer.print(priority, tag, message);
    }
}
