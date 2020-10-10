package com.tao.logger.log;


public interface ILogger {

    /**
     * 打印信息
     *
     * @param priority 优先级
     * @param message  信息
     * @param t        出错信息
     */
    void log(int priority, String message, Throwable t);

    /**
     * 打印信息
     *
     * @param priority 优先级
     * @param onceOnlyTag 额外标签
     * @param message  信息
     * @param t        出错信息
     */
    void log(int priority, String onceOnlyTag, String message, Throwable t);
}