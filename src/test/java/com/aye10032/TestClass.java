package com.aye10032;

import com.aye10032.background.ListPath;
import com.aye10032.background.ListVideos;
import com.aye10032.background.PercentCalculate;
import com.aye10032.config.ConfigIO;
import com.aye10032.config.ConfigSet;
import com.aye10032.database.dao.DaoImpl;
import com.aye10032.database.pojo.Directory;
import com.aye10032.database.pojo.Video;
import com.aye10032.gui.ProjectWindow;
import com.formdev.flatlaf.FlatIntelliJLaf;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @program: VideoListUtil
 * @description: 测试类
 * @author: Aye10032
 * @create: 2021-01-21 15:22
 **/
public class TestClass {

    public static void main(String[] args) {
        new TestClass().GUITest();
    }

    @Test
    public void TestFileList() throws IOException {
        String path = "E:\\考研\\2021考研操作系统";

//        ListVideos.getList(new File(path));
    }

    @Test
    public void TestCreatTable() {
        File file = new File("videolist.db");
        if (!file.exists()) {
            System.out.println("未找到");

            DaoImpl dao = new DaoImpl();

            dao.createVideoTable();
            dao.creatDirectoryTable();
        } else {
            System.out.println("find");
        }
    }

    @Test
    public void TestDataBase() {
        DaoImpl dao = new DaoImpl();

//        List<Video> list = dao.getAllVideo();
//
//        for (Video video:list){
//            System.out.println(video);
//        }

        Directory directory = new Directory();
        directory.setName("2021计算机组成原理");
        directory.setParent("E:\\考研");
        directory.setIs_root(true);
        directory.setAvailable(true);

        dao.insert(directory);

    }

    @Test
    public void TestJson() {
//        ConfigSet configSet = new ConfigSet();
//        configSet.setUse_md5(true);
//        ConfigIO.saveConfig(configSet);

        ConfigSet configSet = ConfigIO.loadConfig();
        System.out.println(configSet.isUse_md5());
    }

    @Test
    public void TestLog() {
        Logger logger = Logger.getLogger(TestClass.class);

        logger.info("test");
    }

    @Test
    public void GUITest() {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
            UIManager.put("Button.arc", 6);
            UIManager.put("Component.arc", 6);
            UIManager.put("CheckBox.arc", 6);
            UIManager.put("ProgressBar.arc", 6);
            UIManager.put("CheckBox.icon.style", "filled");
            UIManager.put("Component.arrowType", "triangle");
            UIManager.put("Component.focusWidth", 2);
            UIManager.put("ScrollBar.showButtons", true);
            UIManager.put("ScrollBar.width", 16);
            UIManager.put("ScrollBar.thumbArc", 6);
            UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));

            UIManager.put("TitlePane.menuBarEmbedded", true);
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        ProjectWindow.setDefaultLookAndFeelDecorated(true);
        ProjectWindow window = new ProjectWindow();

        window.setVisible(true);
    }

    @Test
    public void TestPercent() {
        int percent = PercentCalculate.getPercent(3);
        System.out.println(percent);
    }

    @Test
    public void TestPath() {
        Set<Map.Entry<Integer, String>> list = ListPath.getPath(40);

        for (Map.Entry<Integer, String> path : list) {
            System.out.println(path.getValue());
        }

        System.out.println(ListPath.getPathString(40));
    }
}
