package com.tao.logger.printer;

import android.util.Log;

import com.tao.logger.utils.Utils;


/**
 * @project android_lib_logger
 * @class name：com.nj.baijiayun.logger.priter
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 2019/4/29 5:31 PM
 * @change
 * @time
 * @describe
 */
class LogcatPrinter implements IPrinter {

    public static final int MAX_LOG_LENGTH = 1900;

    @Override
    public void print(int priority, String tag, String msg) {
        Utils.checkNotNull(msg);

        if (tag == null) {
            tag = DEFAULT_TAG;
        }
        int lastLength = msg.length();
        int originLength = msg.length();
        int start = 0;
        int separatorOffset = System.lineSeparator().length() - 1;
        if (originLength < MAX_LOG_LENGTH + 90) {
            printActual(priority, tag, msg.substring(start));
            return;
        }
        while (lastLength > MAX_LOG_LENGTH) {
            int index = msg.substring(start, start + MAX_LOG_LENGTH).
                    lastIndexOf(System.lineSeparator(), start + MAX_LOG_LENGTH - 100);
            int nextStart;
            if (index <= 0) {
                nextStart = start + MAX_LOG_LENGTH - separatorOffset;
            } else {
                nextStart = start + index - separatorOffset;
            }
            String logString = msg.substring(start, nextStart);
            if (start != 0) {
                logString = "|" + logString;
            }
            printActual(priority, tag, logString);
            start = nextStart;
            lastLength = originLength - start;
        }

        printActual(priority, tag, "|" + msg.substring(start));
    }

    private int printActual(int priority, String tag, String msg) {
        return Log.println(priority, tag, msg);
    }
}
