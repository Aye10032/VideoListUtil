package com.aye10032.gui;

import com.aye10032.background.*;
import com.aye10032.config.ConfigIO;
import com.aye10032.config.ConfigSet;
import com.aye10032.database.dao.DaoImpl;
import com.aye10032.database.pojo.Directory;
import com.aye10032.database.pojo.History;
import com.aye10032.database.pojo.Video;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
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
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import static com.aye10032.config.LocalConfig.PROJECT_SIDE_PANEL;
import static com.aye10032.config.LocalConfig.ROOTS_SIDE_PANEL;
import static com.aye10032.gui.CardPanel.cardPanel;

/**
 * @program: VideoListUtil
 * @description: 主程序窗口
 * @author: Aye10032
 * @create: 2021-01-19 20:23
 **/
public class MainWindow extends JFrame {

    private Integer ID;
    private Integer PARENT_ID;
    private final Logger logger;

    private JToolBar project_path;
    private JPanel tool_bars;
    private JMenu open_menu;

    private JSplitPane main_panel;
    private JTabbedPane project_tab;
    private JPanel list_panel1;
    private JPanel list_panel2;
    private JPanel list_panel3;

    public MainWindow(Integer id, Integer last_parent) {
        this.ID = id;
        this.PARENT_ID = last_parent;
        logger = Logger.getLogger(MainWindow.class);
        logger.info("ID分别是：" + ID + " " + PARENT_ID);
        initFrame();
        update_list(list_panel1, PROJECT_SIDE_PANEL);
        update_list(list_panel2, ROOTS_SIDE_PANEL);
        update_history_menu();
        update_path_toolbar();
    }

    private void initFrame() {
        LC lc = new LC().fill();
        AC colC = new AC();
        AC rowC = new AC();
        JPanel panel = new JPanel(new MigLayout(lc, colC, rowC));

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        {
            JMenu file_menu = new JMenu("文件");
            JMenu graph_menu = new JMenu("视图");
            JMenu menu3 = new JMenu("帮助");
            menuBar.add(file_menu);
            menuBar.add(graph_menu);
            menuBar.add(menu3);

            {
                JMenuItem new_item = new JMenuItem("新建项目");
                JMenuItem open_item = new JMenuItem("打开...");
                open_menu = new JMenu("打开最近");
                JMenuItem exit_item = new JMenuItem("退出");
                file_menu.add(new_item);
                file_menu.add(open_item);
                file_menu.add(open_menu);
                file_menu.addSeparator();
                file_menu.add(exit_item);

                new_item.addActionListener(e -> CreatNewProject());
                open_item.addActionListener(e -> OpenNewProject());
                exit_item.addActionListener(e -> Exit());
            }

            {
                JRadioButtonMenuItem light_theme = new JRadioButtonMenuItem("明亮主题");
                JRadioButtonMenuItem dark_theme = new JRadioButtonMenuItem("深色主题");
                ButtonGroup theme_group = new ButtonGroup();
                theme_group.add(light_theme);
                theme_group.add(dark_theme);

                if (ConfigIO.loadConfig().isDark_theme()) {
                    dark_theme.setSelected(true);
                } else {
                    light_theme.setSelected(true);
                }

                JMenuItem reload_UI = new JMenuItem("重载界面");

                graph_menu.add(light_theme);
                graph_menu.add(dark_theme);
                graph_menu.add(new JSeparator());
                graph_menu.add(reload_UI);

                light_theme.addActionListener(e -> update_theme(false));
                dark_theme.addActionListener(e -> update_theme(true));
                reload_UI.addActionListener(e -> reload_ui());
            }
        }

        JToolBar toolBar = new JToolBar();
        {
            toolBar.setFloatable(false);
            toolBar.setBorderPainted(true);
            JButton new_button = new JButton(UIManager.getIcon("FileChooser.newFolderIcon"));
            JButton open_button = new JButton(UIManager.getIcon("FileView.directoryIcon"));
            JButton refresh_button = new JButton(new FlatSVGIcon("com/aye10032/icon/refresh.svg"));

            toolBar.add(new_button);
            toolBar.add(open_button);
            toolBar.addSeparator();
            toolBar.add(refresh_button);

            new_button.addActionListener(e -> CreatNewProject());
            open_button.addActionListener(e -> OpenNewProject());
            refresh_button.addActionListener(e -> reload_ui());
        }

        project_path = new JToolBar();
        {
//            project_path.setLayout(new MigLayout(new LC().debug()));
            project_path.setFloatable(false);
        }

        tool_bars = new JPanel(new GridLayout(0, 1));
        tool_bars.add(toolBar);
        tool_bars.add(project_path);
        tool_bars.add(new JSeparator(JSeparator.HORIZONTAL));

        main_panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        panel.add(main_panel, new CC().growY().growX().spanY().spanX());

        {
            project_tab = new JTabbedPane();

//            项目详情面板
            JPanel project_tree_panel;
            {
                project_tree_panel = new JPanel(new MigLayout(new LC().fill().wrap()));

                list_panel1 = new JPanel(new MigLayout(new LC().fillX()));

                JScrollPane sp1 = new JScrollPane(list_panel1);
                project_tree_panel.add(sp1, new CC().spanX().spanY().growX().growY());
            }

//            根目录面板
            JPanel roots_panel;
            {
                roots_panel = new JPanel(new MigLayout(new LC().fill().wrap()));

                list_panel2 = new JPanel(new MigLayout(new LC().fillX()));

                JScrollPane sp2 = new JScrollPane(list_panel2);
                roots_panel.add(sp2, new CC().spanX().spanY().growX().growY());
            }

            project_tab.add("此项目", project_tree_panel);
            project_tab.add("计划列表", roots_panel);

            JPanel main_win;
            {
                main_win = new JPanel(new MigLayout(new LC().fill()));

                list_panel3 = new JPanel(new MigLayout(new LC().fillX()));

                JScrollPane sp3 = new JScrollPane(list_panel3);
                main_win.add(sp3, new CC().spanX().spanY().growX().growY());
            }

            list_panel1.setBackground(list_panel1.getBackground().brighter());
            list_panel2.setBackground(list_panel2.getBackground().brighter());
            list_panel3.setBackground(list_panel3.getBackground().brighter());

            main_panel.setLeftComponent(project_tab);
            main_panel.setRightComponent(main_win);
            main_panel.setOneTouchExpandable(true);
        }

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        contentPane.add(panel, BorderLayout.CENTER);
        contentPane.add(tool_bars, BorderLayout.NORTH);

        main_panel.setDividerLocation((int) (ConfigIO.loadConfig().getWINDOW_WIDTH() * 0.3));
    }

    private void update_theme(boolean dark_theme) {
        try {
            FlatAnimatedLafChange.showSnapshot();
            if (dark_theme) {
                UIManager.setLookAndFeel(new FlatDarkLaf());
            } else {
                UIManager.setLookAndFeel(new FlatIntelliJLaf());
            }
            ConfigSet config = ConfigIO.loadConfig();
            config.setDark_theme(dark_theme);
            ConfigIO.saveConfig(config);
            FlatLaf.updateUI();
            FlatAnimatedLafChange.hideSnapshotWithAnimation();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private void update_main() {
        list_panel3.removeAll();
        List<Video> video_list = ListVideos.getVideoWithParent(this.PARENT_ID);

        for (Video video : video_list) {
            JPanel video_card = CardPanel.video_card(video);
            video_card.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    logger.info("选择了视频:" + video.getName());
                }
            });
            list_panel3.add(video_card, new CC().wrap().growX().gapY("0", "5"));
        }
        update_panel(list_panel3);
    }

    private void update_list(JPanel panel, int panel_type) {
        List<Directory> list = null;

        switch (panel_type) {
            case PROJECT_SIDE_PANEL:
                list = ListVideos.getDirectoryWithParent(PARENT_ID);
                if (list.size() == 0) {
                    update_main();
//                    this.PARENT_ID = ListVideos.getDirectory(PARENT_ID).get(0).getParent_id();
                    list.clear();
                    Integer parent_id = ListVideos.getDirectory(PARENT_ID).get(0).getParent_id();
                    list = ListVideos.getDirectoryWithParent(parent_id);
                    break;
                } else {
                    break;
                }
            case ROOTS_SIDE_PANEL:
                list = ListVideos.getRoots();
                break;
        }

        panel.removeAll();

        for (Directory directory : list) {
            Integer id = directory.getId();
            int percent = panel_type ==
                    PROJECT_SIDE_PANEL ? PercentCalculate.getPercent(id) : PercentCalculate.getProjectPercent(id);
            JPanel card_panel = cardPanel(id, directory.getName(), percent, percent == 1000);
            card_panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    logger.debug("测试：选择了：" + id);
                    if (panel_type == ROOTS_SIDE_PANEL) {
                        onSelectProject(id);
                    } else {
                        onSelectParent(id);
                    }
                }
            });
            panel.add(card_panel, new CC().wrap().growX().gapY("0", "5"));
        }
        update_panel(panel);
        update_path_toolbar();
    }

    private void update_panel(JComponent component) {
        component.updateUI();
        component.invalidate();
        component.validate();
        component.repaint();
        repaint();
    }

    private void update_history_menu() {
        open_menu.removeAll();
        List<History> history_queue = ListHistory.getLastHistory();
        for (History history : history_queue) {
            Integer id = history.getRoot_id();
            if (!id.equals(ID)) {
                JMenuItem project_item = new JMenuItem(ListVideos.getDirectory(id).get(0).getName());
                open_menu.add(project_item);

                project_item.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        onSelectProject(id);
                    }
                });
            }
        }
    }

    private void update_path_toolbar() {
        project_path.removeAll();
        Set<Map.Entry<Integer, String>> path_set = ListPath.getPath(PARENT_ID);
        logger.info(path_set.size());

        for (Map.Entry<Integer, String> entry : path_set) {
            if (entry.getKey() != -1) {
                JLabel path = new JLabel(entry.getValue());
                path.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            //TODO 下拉菜单
                            List<Directory> list = ListVideos.getDirectoryWithParent(
                                    ListVideos.getDirectory(entry.getKey()).get(0).getParent_id());
                            JPopupMenu popupMenu = new JPopupMenu();
                            for (Directory directory : list) {
                                if (!directory.getId().equals(entry.getKey())) {
                                    JMenuItem item = new JMenuItem(directory.getName());
                                    popupMenu.add(item);

                                    item.addActionListener(e1 -> onSelectParent(directory.getId()));
                                }
                            }
                            popupMenu.show(path, 0, path.getY() + 15);
                            popupMenu.setVisible(true);
                        }
                    }
                });
                project_path.add(new JLabel(new FlatSVGIcon("com/aye10032/icon/split.svg")));
                project_path.add(path);
                logger.info("add " + entry.getValue());
            }
        }

        update_panel(project_path);
        update_panel(tool_bars);
    }

    private void reload_ui() {
        update_list(list_panel1, PROJECT_SIDE_PANEL);
        update_list(list_panel2, ROOTS_SIDE_PANEL);
        update_history_menu();
        update_path_toolbar();
    }

    private void onSelectProject(Integer id) {
        this.ID = id;
        this.PARENT_ID = id;

        ListHistory.addHistory(ID, PARENT_ID);

        update_list(list_panel1, PROJECT_SIDE_PANEL);
        update_history_menu();
        update_main();
        setTitle(ListVideos.getDirectory(ID).get(0).getName());
        logger.debug("ID is " + ID);
        project_tab.setSelectedIndex(0);
    }

    private void onSelectParent(Integer id) {
        logger.info("选择了:" + id);
        this.PARENT_ID = id;
        update_list(list_panel1, PROJECT_SIDE_PANEL);
        ListHistory.addHistory(ID, PARENT_ID);
    }

    private void CreatNewProject() {
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = jfc.showDialog(new JLabel(), "选择项目");
        File file = jfc.getSelectedFile();

        if (result == JFileChooser.APPROVE_OPTION) {
            NewProjectWindow window = new NewProjectWindow(file);
            window.setModal(true);
            window.setVisible(true);

            this.ID = window.getId();
            update_list(list_panel2, ROOTS_SIDE_PANEL);
            project_tab.setSelectedIndex(0);
        }
    }

    private void OpenNewProject() {
        ProjectWindow.setDefaultLookAndFeelDecorated(true);
        ProjectWindow window = new ProjectWindow();
        window.setModal(true);
        window.setVisible(true);

        int open_id = window.getSelect_id();
        if (open_id != -1) {
            this.ID = open_id;
            update_list(list_panel1, PROJECT_SIDE_PANEL);
            setTitle(ListVideos.getDirectory(ID).get(0).getName());
        }
    }

    private void Exit() {
        System.exit(0);
    }
}
