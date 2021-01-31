package com.aye10032;

import com.aye10032.background.ListHistory;
import com.aye10032.background.ListVideos;
import com.aye10032.background.ProjectInit;
import com.aye10032.config.ConfigIO;
import com.aye10032.config.ConfigSet;
import com.aye10032.database.pojo.History;
import com.aye10032.gui.MainWindow;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: VideoListUtil
 * @description: 函数主入口
 * @author: Aye10032
 * @create: 2021-01-19 20:19
 **/
public class MainRun {

    public static void main(String[] args) {
        ProjectInit.init();
        new MainRun();
    }

    public MainRun() {
        try {
            if (ConfigIO.loadConfig().isDark_theme()) {
                UIManager.setLookAndFeel(new FlatDarkLaf());
            }else {
                UIManager.setLookAndFeel(new FlatIntelliJLaf());
            }
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


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        ConfigSet config = ConfigIO.loadConfig();
        int windowWidth = (int) config.getWINDOW_WIDTH();
        int windowHeight = (int) config.getWINDOW_HEIGHT();

        List<History> history = ListHistory.getLastHistory();
        Integer last_id = -1;
        Integer last_parent = -1;
        String title = "规划工具箱";
        if (history.size() != 0) {
            last_id = history.get(0).getRoot_id();
            last_parent = history.get(0).getParent_id();
            title = ListVideos.getDirectory(last_id).get(0).getName();
        }

        MainWindow.setDefaultLookAndFeelDecorated(true);
        MainWindow window = new MainWindow(last_id,last_parent);
        window.setTitle(title);

        window.setIconImage(new FlatSVGIcon("com/aye10032/icon.svg").getImage());
//        window.setIconImages(icons);

        window.setBounds((screenWidth - windowWidth) / 2, (screenHeight - windowHeight) / 2, windowWidth, windowHeight);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);
    }
}
