package com.aye10032.gui;

import com.aye10032.background.ListVideos;
import com.aye10032.config.ConfigIO;
import com.aye10032.config.ConfigSet;
import com.aye10032.config.LocalConfig;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.File;
import java.io.IOException;

/**
 * @program: VideoListUtil
 * @description: 项目初始化窗口
 * @author: Aye10032
 * @create: 2021-01-25 23:09
 **/
public class NewProjectWindow extends JDialog {

    Logger logger;

    private Integer id = null;

    private JTextArea log_area;
    private JProgressBar progressBar;

    private JButton close_button;

    public NewProjectWindow(File file) {
        logger = Logger.getLogger(ProjectWindow.class);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        ConfigSet config = ConfigIO.loadConfig();
        int windowWidth = (int) (config.getWINDOW_WIDTH() * LocalConfig.NEW_PROJECT_WINDOW_WIDTH_PROPORTION);
        int windowHeight = (int) (config.getWINDOW_HEIGHT() * LocalConfig.NEW_PROJECT_WINDOW_HEIGHT_PROPORTION);

        setTitle("项目初始化");
        Image icon = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/com/aye10032/icon.png").getFile());
        setIconImage(icon);
        setBounds((screenWidth - windowWidth) / 2, (screenHeight - windowHeight) / 2, windowWidth, windowHeight);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        initFrame();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    id = ListVideos.getList(file, log_area);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ConfigSet config = ConfigIO.loadConfig();
                config.addHistory(id);
                ConfigIO.saveConfig(config);
                progressBar.setIndeterminate(false);
                progressBar.setValue(100);
                progressBar.setString("加载完毕");
                close_button.setEnabled(true);
                close_button.setVisible(true);
            }
        }).start();
    }

    private void initFrame() {
        LC lc = new LC().fill().wrap();
        AC colC = new AC();
        AC rowC = new AC().grow(100, 1).grow(5, 0, 2);
        JPanel panel = new JPanel(new MigLayout(lc, colC, rowC));

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setString("正在初始化数据库...");
        progressBar.setIndeterminate(true);

        log_area = new JTextArea();
        log_area.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(log_area);

        close_button = new JButton("确定");

        panel.add(progressBar, new CC().growX().gap("10", "10", "10", "10").alignX("center").wrap());
        panel.add(scrollPane, new CC().growX().gap("10", "10", "", "5").growY().wrap());
        panel.add(close_button, new CC().alignX("right").gapRight("15").gapBottom("5"));
        close_button.setEnabled(false);
        close_button.setVisible(false);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        contentPane.add(panel, BorderLayout.CENTER);

        scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                if (e.getAdjustmentType() == AdjustmentEvent.TRACK) {
                    scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getModel().getMaximum() - scrollPane.getVerticalScrollBar().getModel().getExtent());
                }
            }
        });
        close_button.addActionListener(e -> done());
    }

    private void done(){
        this.dispose();
    }

    public Integer getId(){
        return this.id;
    }

}
