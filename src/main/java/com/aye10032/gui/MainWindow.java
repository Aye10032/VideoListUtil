package com.aye10032.gui;

import com.aye10032.background.ListVideos;
import com.aye10032.background.PercentCalculate;
import com.aye10032.background.ProjectInit;
import com.aye10032.config.ConfigIO;
import com.aye10032.config.ConfigSet;
import com.aye10032.database.dao.DaoImpl;
import com.aye10032.database.pojo.Directory;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Queue;

/**
 * @program: VideoListUtil
 * @description: 主程序窗口
 * @author: Aye10032
 * @create: 2021-01-19 20:23
 **/
public class MainWindow extends JFrame {

    private Integer ID;
    private Logger logger;

    public MainWindow() {
        logger = Logger.getLogger(MainWindow.class);
        initFrame();
    }

    private void initFrame() {
        LC lc = new LC().fill().debug();
        AC colC = new AC();
        AC rowC = new AC();
        JPanel panel = new JPanel(new MigLayout(lc, colC, rowC));

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        {
            JMenu file_menu = new JMenu("文件");
            JMenu menu2 = new JMenu("编辑");
            JMenu menu3 = new JMenu("视图");
            menuBar.add(file_menu);
            menuBar.add(menu2);
            menuBar.add(menu3);
            JMenuItem new_item = new JMenuItem("新建项目");
            JMenuItem open_item = new JMenuItem("打开...");
            JMenu open_menu = new JMenu("打开最近");
            JMenuItem exit_item = new JMenuItem("退出");
            file_menu.add(new_item);
            file_menu.add(open_item);
            file_menu.add(open_menu);
            file_menu.addSeparator();
            file_menu.add(exit_item);

            Queue<Integer> id_queue = ConfigIO.loadConfig().getHistory_id();
            for (int i = 0; i < id_queue.size(); i++) {
                Integer id = id_queue.poll();
                if (!id.equals(ID)) {
                    JMenuItem project_item = new JMenuItem(ListVideos.getDirectory(id).get(0).getName());
                    open_menu.add(project_item);

                    project_item.addActionListener(new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            onSelectProject(id+"");
                        }
                    });
                }
            }

            new_item.addActionListener(e -> CreatNewProject());
            open_item.addActionListener(e -> OpenNewProject());
            exit_item.addActionListener(e -> Exit());
        }

        JSplitPane main_panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        panel.add(main_panel, new CC().growY().growX().spanY().spanX());

        {
            JTabbedPane project_tab = new JTabbedPane();

            JPanel project_tree_panel;
            {
                project_tree_panel = new JPanel(new MigLayout(new LC().fill().wrap().debug()));

                JPanel list_panel1 = new JPanel(new MigLayout(new LC().fill().wrap().debug()));

                JScrollPane sp1 = new JScrollPane(list_panel1);
                project_tree_panel.add(sp1, new CC());
            }

            JPanel roots_panel;
            {
                roots_panel = new JPanel(new MigLayout(new LC().fill().wrap().debug()));
            }

            project_tab.add("此项目", project_tree_panel);
            project_tab.add("计划列表", roots_panel);

            JPanel main_win;
            {
                main_win = new JPanel(new MigLayout(new LC().fill().debug()));
            }

            main_panel.setLeftComponent(project_tab);
            main_panel.setRightComponent(main_win);
            main_panel.setOneTouchExpandable(true);
        }

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        contentPane.add(panel, BorderLayout.CENTER);

        main_panel.setDividerLocation((int) (ConfigIO.loadConfig().getWINDOW_WIDTH() * 0.2));
    }

    private JPanel cardPanel(Integer id, String name, int percent, String path, boolean done, boolean hide) {
        LC layC = new LC().fill().wrap();
        AC colC = new AC()
                .gap("10")
                .grow()
                .shrink(200, 2, 3).shrink(100, 0, 1).shrink(25, 4, 5)
                .fill()
                .size("100px", 2, 3).size("50px", 5)
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

        JPopupMenu set_menu = new JPopupMenu();
        JMenuItem done_item = new JMenuItem("全部完成");
        JCheckBoxMenuItem hide_item = new JCheckBoxMenuItem("隐藏");
        hide_item.setState(!hide);
        JMenuItem del_item = new JMenuItem("删除");
        if (done) {
            done_item.setEnabled(false);
        }
        set_menu.add(done_item);
        set_menu.add(hide_item);
        set_menu.add(new JSeparator());
        set_menu.add(del_item);

        panel.add(name_label, new CC());
        panel.add(id_label, new CC());
        panel.add(progressBar, new CC().spanX(2).growX());
        panel.add(percent_label, new CC().skip().wrap());
        panel.add(path_label, new CC().spanX(2).growX());
        panel.add(done_label, new CC().skip(2));
        panel.add(set_button, new CC());

        {
            name_label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    onSelectProject(id_label.getText());
                }
            });

            id_label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    onSelectProject(id_label.getText());
                }
            });

            path_label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    onSelectProject(id_label.getText());
                }
            });
        }
        return panel;
    }

    private void update_list(JPanel panel) {
        List<Directory> list = null;
        DaoImpl dao = new DaoImpl();

        panel.removeAll();

        for (Directory directory : list) {
            Integer id = directory.getId();
            int percent = PercentCalculate.getProjectPercent(id);


            JPanel card_panel = cardPanel(id, directory.getName(), percent,
                    directory.getParent() + "\\" + directory.getName(), percent == 1000, directory.isAvailable());
            panel.add(card_panel, new CC().wrap().growX().gapY("5", "5"));

        }
        update_panel(panel);
    }

    private void update_panel(JPanel panel) {
        panel.updateUI();
        panel.invalidate();
        panel.validate();
        panel.repaint();
        repaint();
    }

    private void onSelectProject(String id) {
        int ID = Integer.parseInt(id);
        logger.debug("ID is " + ID);
    }

    private void CreatNewProject() {
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = jfc.showDialog(new JLabel(), "选择项目");
        File file = jfc.getSelectedFile();

        if (result == JFileChooser.APPROVE_OPTION) {
            NewProjectWindow window = new NewProjectWindow(file);
            window.setVisible(true);
        }
    }

    private void OpenNewProject() {
        ProjectWindow.setDefaultLookAndFeelDecorated(true);
        ProjectWindow window = new ProjectWindow();

        window.setVisible(true);
    }

    private void Exit() {
        System.exit(0);
    }
}
