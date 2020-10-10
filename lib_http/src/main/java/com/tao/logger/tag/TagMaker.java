package com.tao.logger.tag;


import com.tao.logger.utils.Utils;

import io.reactivex.annotations.Nullable;

/**
 * @project android_lib_logger
 * @class name：com.nj.baijiayun.logger.tag
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 2019/4/30 9:39 AM
 * @change
 * @time
 * @describe
 */
public class TagMaker {
    private String tag;

    @Nullable
    public String formatTag(@Nullable String tag) {
        if (!com.tao.logger.utils.Utils.isEmpty(tag) && !Utils.equals(this.tag, tag)) {
            return this.tag + "-" + tag;
        }
        return this.tag;
    }
}
