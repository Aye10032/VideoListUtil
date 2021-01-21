package com.aye10032.background;

import com.aye10032.database.dao.DaoImpl;
import com.aye10032.database.pojo.Directory;
import com.aye10032.database.pojo.Video;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @program: VideoListUtil
 * @description: 遍历视频总类
 * @author: Aye10032
 * @create: 2021-01-21 00:32
 **/
public class ListVideos {

    public static void getList(File file) throws IOException {
        DaoImpl dao = new DaoImpl();

        Directory directory = new Directory();
        directory.setName(file.getName());
        directory.setIs_root(1);
        directory.setParent(file.getParentFile().getAbsolutePath());
        directory.setAvailable(1);

        Integer new_id = dao.insert(directory);

        File[] fs = file.listFiles();
        for (File f : Objects.requireNonNull(fs)) {
            if (f.isDirectory()) {
                System.out.println(f.getParentFile().getName() + " <-" + f.getName());
                getList(f, new_id);
            }
            if (f.isFile()) {
                String md5 = DigestUtils.md5Hex(new FileInputStream(f));
                System.out.println(f.getParentFile().getName() + " <-" + f.getName());
                System.out.println("        " + md5);

                Video video = new Video();
                video.setName(f.getName());
                video.setParent(f.getParentFile().getName());
                video.setParent_id(new_id);
                video.setHas_done(0);
                video.setMd5(md5);

                dao.insert(video);
            }
        }
    }

    public static void getList(File file, Integer parent_id) throws IOException {
        DaoImpl dao = new DaoImpl();

        Directory directory = new Directory();
        directory.setName(file.getName());
        directory.setIs_root(0);
        directory.setParent(file.getParentFile().getName());
        directory.setParent_id(parent_id);
        directory.setAvailable(1);

        Integer new_id = dao.insert(directory);

        File[] fs = file.listFiles();
        for (File f : Objects.requireNonNull(fs)) {
            if (f.isDirectory()) {
                System.out.println(f.getParentFile().getName() + " <-" + f.getName());
                getList(f, new_id);
            }
            if (f.isFile()) {
                String md5 = DigestUtils.md5Hex(new FileInputStream(f));
                System.out.println(f.getParentFile().getName() + " <-" + f.getName());
                System.out.println("        " + md5);

                Video video = new Video();
                video.setName(f.getName());
                video.setParent(f.getParentFile().getName());
                video.setParent_id(new_id);
                video.setHas_done(0);
                video.setMd5(md5);

                dao.insert(video);
            }
        }
    }

}
