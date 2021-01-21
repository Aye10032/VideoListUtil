package com.aye10032.config;

/**
 * @program: VideoListUtil
 * @description: 程序设置信息
 * @author: Aye10032
 * @create: 2021-01-21 22:57
 **/
public class ConfigSet {
    private boolean use_md5;

    public boolean isUse_md5() {
        return use_md5;
    }

    public void setUse_md5(boolean use_md5) {
        this.use_md5 = use_md5;
    }

    @Override
    public String toString() {
        return "ConfigSet{" +
                "use_md5=" + use_md5 +
                '}';
    }
}
