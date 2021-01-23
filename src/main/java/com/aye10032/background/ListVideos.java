package com.aye10032.background;

import com.aye10032.config.ConfigIO;
import com.aye10032.database.dao.DaoImpl;
import com.aye10032.database.pojo.Directory;
import com.aye10032.database.pojo.Video;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @program: VideoListUtil
 * @description: 遍历视频总类
 * @author: Aye10032
 * @create: 2021-01-21 00:32
 **/
public class ListVideos {
    private static final Logger logger = Logger.getLogger(ListVideos.class);

    public static void getList(File file) throws IOException {
        DaoImpl dao = new DaoImpl();

//        Directory directory = new Directory();
//        directory.setName(file.getName());
//        directory.setIs_root(1);
//        directory.setParent(file.getParentFile().getAbsolutePath());
//        directory.setAvailable(1);

        Integer new_id = addDirectory(
                dao, file.getName(), true, file.getParentFile().getAbsolutePath(), null,
                null, null, true);

        File[] fs = file.listFiles();
        for (File f : Objects.requireNonNull(fs)) {
            if (f.isDirectory()) {
                logger.info(f.getParentFile().getName() + " <-" + f.getName());
                getList(f, new_id);
            }
            if (f.isFile()) {
                logger.info(f.getParentFile().getName() + " <-" + f.getName());

//                Video video = new Video();
//                video.setName(f.getName());
//                video.setParent(f.getParentFile().getName());
//                video.setParent_id(new_id);
//                video.setHas_done(0);
                String md5 = null;
                if (ConfigIO.loadConfig().isUse_md5()) {
                    md5 = DigestUtils.md5Hex(new FileInputStream(f));
                }
                addVideo(dao, f.getName(), f.getParentFile().getName(), new_id,
                        f.getParentFile().getName(), new_id, false, md5);
            }
        }
    }

    public static void getList(File file, Integer parent_id) throws IOException {
        DaoImpl dao = new DaoImpl();

//        Directory directory = new Directory();
//        directory.setName(file.getName());
//        directory.setIs_root(0);
//        directory.setParent(file.getParentFile().getName());
//        directory.setParent_id(parent_id);
//        directory.setAvailable(1);
        Directory parent = dao.selectDirectoryWithID(parent_id).get(0);
        String root = null;
        Integer root_id = null;
        if (parent.isIs_root()) {
            root = parent.getName();
            root_id = parent_id;
        } else {
            root = parent.getRoot();
            root_id = parent.getRoot_id();
        }

        Integer new_id = addDirectory(dao, file.getName(), false, file.getParentFile().getName(), parent_id,
                root, root_id, true);

        File[] fs = file.listFiles();
        for (File f : Objects.requireNonNull(fs)) {
            if (f.isDirectory()) {
                logger.info(f.getParentFile().getName() + " <-" + f.getName());
                getList(f, new_id);
            }
            if (f.isFile()) {
//                String md5 = DigestUtils.md5Hex(new FileInputStream(f));
                logger.info(f.getParentFile().getName() + " <-" + f.getName());
//                System.out.println("        " + md5);
//                Video video = new Video();
//                video.setName(f.getName());
//                video.setParent(f.getParentFile().getName());
//                video.setParent_id(new_id);
//                video.setHas_done(0);
                String md5 = null;
                if (ConfigIO.loadConfig().isUse_md5()) {
                    md5 = DigestUtils.md5Hex(new FileInputStream(f));
                }
                addVideo(dao, f.getName(), f.getParentFile().getName(), new_id, root, root_id, false, md5);
            }
        }
    }

    /**
     * @param dao       传入接口
     * @param name      文件夹名
     * @param is_root   是否为根目录
     * @param parent    父文件夹名
     * @param parent_id 父文件夹ID
     * @param root      根目录名
     * @param root_id   根目录ID
     * @param available 项目状态
     * @return 此条目数据库ID
     */
    public static Integer addDirectory(DaoImpl dao,
                                       String name, boolean is_root,
                                       String parent, Integer parent_id,
                                       String root, Integer root_id,
                                       boolean available) {

        Directory directory = new Directory();
        directory.setName(name);
        directory.setIs_root(is_root);
        directory.setParent(parent);
        directory.setParent_id(parent_id);
        directory.setRoot(root);
        directory.setRoot_id(root_id);
        directory.setAvailable(available);

        Date date = new Date();
        directory.setCreat_date(date);
        directory.setUpdate_date(date);

        Integer new_id = dao.insert(directory);

        return new_id;
    }

    /**
     * @param dao       传入接口
     * @param name      文件名
     * @param parent    父文件夹名
     * @param parent_id 父文件夹ID
     * @param root      根目录名
     * @param root_id   根目录ID
     * @param has_done  观看状态
     * @param md5       MD5码（可选）
     * @return 此条目数据库ID
     */
    public static Integer addVideo(DaoImpl dao,
                                   String name,
                                   String parent, Integer parent_id,
                                   String root, Integer root_id,
                                   boolean has_done, String md5) {
        Video video = new Video();
        video.setName(name);
        video.setParent(parent);
        video.setParent_id(parent_id);
        video.setRoot(root);
        video.setRoot_id(root_id);
        video.setHas_done(has_done);
        video.setMd5(md5);

        Date date = new Date();
        video.setDate(date);

        Integer id = dao.insert(video);

        return id;
    }

    public static List<Directory> getRoots(){
        List<Directory> list = null;
        DaoImpl dao = new DaoImpl();

        list = dao.getRoots();

        return list;
    }

}
