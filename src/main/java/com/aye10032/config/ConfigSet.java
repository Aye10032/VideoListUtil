package com.aye10032.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * @program: VideoListUtil
 * @description: 程序设置信息
 * @author: Aye10032
 * @create: 2021-01-21 22:57
 **/
public class ConfigSet {
    private double WINDOW_WIDTH;
    private double WINDOW_HEIGHT;

    private boolean use_md5;
    private boolean open_recent;
    private boolean dark_theme;

    public double getWINDOW_WIDTH() {
        return WINDOW_WIDTH;
    }

    public double getWINDOW_HEIGHT() {
        return WINDOW_HEIGHT;
    }

    public boolean isUse_md5() {
        return use_md5;
    }

    public boolean isOpen_recent() {
        return open_recent;
    }

    public boolean isDark_theme() {
        return dark_theme;
    }

    public void setWINDOW_WIDTH(double WINDOW_WIDTH) {
        this.WINDOW_WIDTH = WINDOW_WIDTH;
    }

    public void setWINDOW_HEIGHT(double WINDOW_HEIGHT) {
        this.WINDOW_HEIGHT = WINDOW_HEIGHT;
    }

    public void setUse_md5(boolean use_md5) {
        this.use_md5 = use_md5;
    }

    public void setOpen_recent(boolean open_recent) {
        this.open_recent = open_recent;
    }

    public void setDark_theme(boolean dark_theme) {
        this.dark_theme = dark_theme;
    }

    @Override
    public String toString() {
        return "ConfigSet{" +
                "WINDOW_WIDTH=" + WINDOW_WIDTH +
                ", WINDOW_HEIGHT=" + WINDOW_HEIGHT +
                ", use_md5=" + use_md5 +
                ", open_recent=" + open_recent +
                ", dark_theme=" + dark_theme +
                '}';
    }
}
