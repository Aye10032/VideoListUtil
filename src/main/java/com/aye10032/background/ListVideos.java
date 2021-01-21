package com.aye10032.background;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * @program: VideoListUtil
 * @description: 遍历视频总类
 * @author: Aye10032
 * @create: 2021-01-21 00:32
 **/
public class ListVideos {

    public static void getList(File file) throws IOException {
        File[] fs = file.listFiles();
        for (File f : fs) {
            if (f.isDirectory()) {
                System.out.println(f.getParentFile().getName() + " <-" + f.getName());
                getList(f);
            }
            if (f.isFile()) {
                System.out.println(f.getParentFile().getName() + " <-" + f.getName());
                System.out.println("        " + DigestUtils.md5Hex(new FileInputStream(f)));
            }
        }
    }

}
