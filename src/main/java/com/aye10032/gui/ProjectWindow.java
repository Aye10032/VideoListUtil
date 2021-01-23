package com.aye10032.gui;

import com.aye10032.config.ConfigIO;
import com.aye10032.config.ConfigSet;
import com.aye10032.config.LocalConfig;
import com.aye10032.config.LocalConfig.*;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.FlatSVGUtils;
import com.formdev.flatlaf.icons.FlatCheckBoxIcon;
import javafx.scene.control.RadioButton;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * @program: VideoListUtil
 * @description: 项目列表图形界面
 * @author: Aye10032
 * @create: 2021-01-21 23:51
 **/
public class ProjectWindow extends JFrame {

    public ProjectWindow() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        ConfigSet config = ConfigIO.loadConfig();
        int windowWidth = (int) (config.getWINDOW_WIDTH() * LocalConfig.PROJECT_WINDOW_WIDTH_PROPORTION);
        int windowHeight = (int) (config.getWINDOW_HEIGHT() * LocalConfig.PROJECT_WINDOW_HEIGHT_PROPORTION);

        setTitle("计划选择");
        Image icon = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/com/aye10032/icon.png").getFile());
        setIconImage(icon);
        setBounds((screenWidth - windowWidth) / 2, (screenHeight - windowHeight) / 2, windowWidth, windowHeight);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        initFrame();
    }

    private void initFrame() {
        LC layC = new LC().fill();
        AC colC = new AC().align("center", 0).grow(50, 1).fill(1).gap("15", 0, 1).count(3);
        AC rowC = new AC().grow(100, 2).gap("20", 0, 1, 2).count(3);
        JPanel panel = new JPanel(new MigLayout(layC, colC, rowC));

        JLabel search_icon = new JLabel();
        search_icon.setIcon(new FlatSVGIcon("com/aye10032/icon/search.svg"));
        JTextField search_input = new JTextField();
        JScrollPane scrollPane;
        JButton sort_button = new JButton();
        sort_button.setIcon(new FlatSVGIcon("com/aye10032/icon/listFiles.svg"));

        {
            JPanel list_panel = new JPanel(new MigLayout(new LC().fill().debug(), new AC(), new AC().gap("15")));

            for (int i = 0; i < 5; i++) {
                list_panel.add(
                        cardPanel(i, "2021计算机组成原理", 80, "E:\\考研\\2021计算机组成原理", true),
                        new CC().wrap().alignX("center").growX());
                list_panel.add(
                        cardPanel(i, "05 高数", 80, "E:\\考研\\高数", false),
                        new CC().wrap().alignX("center").growX());
            }

            scrollPane = new JScrollPane(list_panel);
        }

        panel.add(search_icon, new CC().growY());
        panel.add(search_input, new CC().growX());
        panel.add(sort_button, new CC().wrap());
        panel.add(new JSeparator(), new CC().spanX().growX());

        panel.add(scrollPane, new CC().spanX().growX().spanY().growY());

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        contentPane.add(panel, BorderLayout.CENTER);
    }

    private JPanel cardPanel(Integer id, String name, int percent, String path, boolean done) {
        LC layC = new LC().fill().wrap();
        AC colC = new AC()
                .gap("10")
                .grow()
                .shrink(200, 2, 3).shrink(100, 0, 1).shrink(25, 4, 5)
                .fill()
                .size("100px", 2, 3)
                .align("right", 0, 4).align("center", 5);
        AC rowC = new AC().count(2);
        JPanel panel = new JPanel(new MigLayout(layC, colC, rowC));

        JLabel name_label = new JLabel(name);
        JLabel id_label = new JLabel(id.toString());
        id_label.setVisible(false);
        JProgressBar progressBar = new JProgressBar();
        progressBar.setValue(percent);
        JLabel percent_label = new JLabel(Integer.toString(percent));
        JLabel path_label = new JLabel(path);
        JLabel done_label = new JLabel();
        if (done) {
            done_label.setIcon(new FlatSVGIcon("com/aye10032/icon/selected.svg"));
        } else {
            done_label.setIcon(new FlatSVGIcon("com/aye10032/icon/close.svg"));
        }
        JButton set_button = new JButton();
        set_button.setIcon(new FlatSVGIcon("com/aye10032/icon/setting.svg"));

        panel.add(name_label, new CC());
        panel.add(id_label, new CC());
        panel.add(progressBar, new CC().spanX(2).growX());
        panel.add(percent_label, new CC().skip().wrap());
        panel.add(path_label, new CC().spanX(2).growX());
        panel.add(done_label, new CC().skip(2));
        panel.add(set_button, new CC());

        return panel;
    }

}
