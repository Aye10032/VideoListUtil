package com.aye10032.gui;

import com.aye10032.background.ListVideos;
import com.aye10032.background.PercentCalculate;
import com.aye10032.config.ConfigIO;
import com.aye10032.config.ConfigSet;
import com.aye10032.config.LocalConfig;
import com.aye10032.config.LocalConfig.*;
import com.aye10032.database.pojo.Directory;
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
import java.util.List;

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
        AC rowC = new AC().grow(100, 1).gap("15", 0, 1).count(2);
        JPanel panel = new JPanel(new MigLayout(layC, colC, rowC));

        JLabel search_icon = new JLabel();
        search_icon.setIcon(new FlatSVGIcon("com/aye10032/icon/search.svg"));
        JTextField search_input = new JTextField();
        JScrollPane scrollPane;
        JButton sort_button = new JButton();
        sort_button.setIcon(new FlatSVGIcon("com/aye10032/icon/listFiles.svg"));

        {
            JPanel list_panel = new JPanel(new MigLayout(new LC().fillX(), new AC().gap("10"), new AC().gap("10")));

            List<Directory> roots = ListVideos.getRoots();

            for (Directory directory : roots) {
                Integer id = directory.getId();
                int percent = PercentCalculate.getProjectPercent(id);
                JPanel card_panel = cardPanel(id, directory.getName(), percent,
                        directory.getParent() + "\\" + directory.getName(), percent == 1000);
                list_panel.add(card_panel, new CC().wrap().growX().gapY("5", "5"));
            }

            for (int i = 0; i < 5; i++) {
                list_panel.add(
                        cardPanel(i, "2021计算机组成原理", 80, "E:\\考研\\2021计算机组成原理", false),
                        new CC().wrap().growX().gapY("5", "5"));
                list_panel.add(
                        cardPanel(i, "05 高数", 1000, "E:\\考研\\高数", true),
                        new CC().wrap().growX().gapY("5", "5"));
            }

            scrollPane = new JScrollPane(list_panel);
        }

        panel.add(search_icon, new CC().growY().gapTop("15"));
        panel.add(search_input, new CC().growX().gapTop("15"));
        panel.add(sort_button, new CC().gapTop("15").wrap());
//        panel.add(new JSeparator(), new CC().spanX().growX().growY().height("20px").wrap());
        panel.add(scrollPane, new CC().spanX().growX().spanY().growY().gapTop("20").gapBottom("10"));

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
                .size("100px", 2, 3).size("50px",5)
                .align("right", 0, 4).align("center", 5);
        AC rowC = new AC().count(2);
        JPanel panel = new JPanel(new MigLayout(layC, colC, rowC));

        JLabel name_label = new JLabel(name);
        name_label.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
        JLabel id_label = new JLabel(id.toString());
        id_label.setVisible(false);
        JProgressBar progressBar = new JProgressBar(0, 1000);
        progressBar.setValue(percent);
        JLabel percent_label = new JLabel(percent * 0.1 + "%");
        JLabel path_label = new JLabel(path);
        path_label.setForeground(Color.DARK_GRAY);
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
