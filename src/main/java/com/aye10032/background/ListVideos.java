package com.aye10032.background;

import com.aye10032.config.ConfigIO;
import com.aye10032.database.dao.DaoImpl;
import com.aye10032.database.pojo.Directory;
import com.aye10032.database.pojo.Video;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @program: VideoListUtil
 * @description: 遍历视频总类
 * @author: Aye10032
 * @create: 2021-01-21 00:32
 **/
public class ListVideos {
    private static final Logger logger = Logger.getLogger(ListVideos.class);

    public static Integer getList(File file, JTextArea log_area) throws IOException {
        DaoImpl dao = new DaoImpl();

        Integer new_id = addDirectory(
                dao, file.getName(), true, file.getParentFile().getAbsolutePath(), null,
                null, null, true);

        File[] fs = file.listFiles();
        for (File f : Objects.requireNonNull(fs)) {
            if (f.isDirectory()) {
                log_area.append(f.getParentFile().getName() + " <-" + f.getName() + "\n");
                logger.info(f.getParentFile().getName() + " <-" + f.getName());
                getList(f, new_id, log_area);
            }
            if (f.isFile()) {
                String[] file_name = f.getName().split("\\.");
                String type = file_name[file_name.length - 1];
                if (type.equalsIgnoreCase("mp4")
                        || type.equalsIgnoreCase("avi")
                        || type.equalsIgnoreCase("wmv")
                        || type.equalsIgnoreCase("flv")
                        || type.equalsIgnoreCase("webm")
                        || type.equalsIgnoreCase("mkv")) {
                    log_area.append(f.getParentFile().getName() + " <-" + f.getName() + "\n");
                    logger.info(f.getParentFile().getName() + " <-" + f.getName());

                    String md5 = null;
                    if (ConfigIO.loadConfig().isUse_md5()) {
                        md5 = DigestUtils.md5Hex(new FileInputStream(f));
                    }
                    addVideo(dao, f.getName(), f.getParentFile().getName(), new_id,
                            f.getParentFile().getName(), new_id, false, md5);
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log_area.updateUI();
                log_area.invalidate();
                log_area.validate();
                log_area.repaint();
            }
        }

        return new_id;
    }

    public static void getList(File file, Integer parent_id, JTextArea log_area) throws IOException {
        DaoImpl dao = new DaoImpl();

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
                log_area.append(f.getParentFile().getName() + " <-" + f.getName() + "\n");
                logger.info(f.getParentFile().getName() + " <-" + f.getName());
                getList(f, new_id, log_area);
            }
            if (f.isFile()) {
                String[] file_name = f.getName().split("\\.");
                String type = file_name[file_name.length - 1];
                if (type.equalsIgnoreCase("mp4")
                        || type.equalsIgnoreCase("avi")
                        || type.equalsIgnoreCase("wmv")
                        || type.equalsIgnoreCase("flv")
                        || type.equalsIgnoreCase("webm")
                        || type.equalsIgnoreCase("mkv")) {
//                String md5 = DigestUtils.md5Hex(new FileInputStream(f));
                    log_area.append(f.getParentFile().getName() + " <-" + f.getName() + "\n");
                    logger.info(f.getParentFile().getName() + " <-" + f.getName());

                    String md5 = null;
                    if (ConfigIO.loadConfig().isUse_md5()) {
                        md5 = DigestUtils.md5Hex(new FileInputStream(f));
                    }
                    addVideo(dao, f.getName(), f.getParentFile().getName(), new_id, root, root_id, false, md5);
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log_area.updateUI();
                log_area.invalidate();
                log_area.validate();
                log_area.repaint();
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

    public static List<Directory> getDirectory(Integer id) {
        List<Directory> list = null;
        DaoImpl dao = new DaoImpl();

        list = dao.selectDirectoryWithID(id);

        return list;
    }

    public static List<Directory> getDirectoryWithRoot(Integer id) {
        List<Video> video_list = null;
        DaoImpl dao = new DaoImpl();

        List<Integer> temp_list = new ArrayList<>();

        video_list = dao.selectWithRoot(id);

        for (Video video : video_list) {
            if (!temp_list.contains(video.getParent_id())) {
                temp_list.add(video.getParent_id());
            }
        }

        List<Directory> result_list = new ArrayList<>();

        for (Integer ID : temp_list) {
            result_list.add(dao.selectDirectoryWithID(ID).get(0));
        }

        return result_list;
    }

    public static List<Directory> getDirectoryWithParent(Integer parent_id) {
        List<Directory> list = null;
        DaoImpl dao = new DaoImpl();

        list = dao.selectDirectoryWithParentID(parent_id);

        return list;
    }

    public static List<Directory> getRoots() {
        List<Directory> list = null;
        DaoImpl dao = new DaoImpl();

        list = dao.getRoots();

        return list;
    }

    public static List<Video> getVideoWithParent(Integer parent_id) {
        List<Video> list = null;
        DaoImpl dao = new DaoImpl();

        list = dao.selectWithParent(parent_id);

        return list;
    }

    public static void setRootHidden(Integer root_id, boolean hide) {
        DaoImpl dao = new DaoImpl();

        Directory directory = new Directory();
        directory.setRoot_id(root_id);
        directory.setAvailable(hide);
        directory.setUpdate_date(new Date());

        dao.updateRootAvailable(directory);
    }

    public static void setParentHidden(Integer id, boolean hide) {
        DaoImpl dao = new DaoImpl();

        Directory directory = new Directory();
        directory.setId(id);
        directory.setAvailable(hide);
        directory.setUpdate_date(new Date());

        dao.updateDirectoryAvailable(directory);
    }

    public static void setProjectDone(Integer root_id) {
        DaoImpl dao = new DaoImpl();

        Video video = new Video();
        video.setHas_done(true);
        video.setRoot_id(root_id);
        video.setDate(new Date());

        dao.setRootVideoDone(video);
    }

    public static void deleteRoot(Integer root_id){
        DaoImpl dao = new DaoImpl();

        dao.deleteVideoWithRootID(root_id);
        dao.deleteDirectoryWithRootID(root_id);
        dao.deleteHistoryRoot(root_id);
    }

}
