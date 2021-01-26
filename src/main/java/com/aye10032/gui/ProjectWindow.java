package com.aye10032.gui;

import com.aye10032.background.ListVideos;
import com.aye10032.background.PercentCalculate;
import com.aye10032.config.ConfigIO;
import com.aye10032.config.ConfigSet;
import com.aye10032.config.LocalConfig;
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
import java.awt.event.*;
import java.util.List;

/**
 * @program: VideoListUtil
 * @description: 项目列表图形界面
 * @author: Aye10032
 * @create: 2021-01-21 23:51
 **/
public class ProjectWindow extends JDialog {

    Logger logger;

    private JTextField search_input;
    private JCheckBoxMenuItem hide_item;
    private JCheckBoxMenuItem finish_item;
    private JPanel list_panel;

    private int select_id = -1;

    public ProjectWindow() {
        logger = Logger.getLogger(ProjectWindow.class);
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
        search_input = new JTextField();
        JButton sort_button = new JButton();
        sort_button.setIcon(new FlatSVGIcon("com/aye10032/icon/listFiles.svg"));

        JPopupMenu sort_menu = new JPopupMenu();
        hide_item = new JCheckBoxMenuItem("显示隐藏项");
        finish_item = new JCheckBoxMenuItem("显示已完成");
        finish_item.setState(true);
        JMenu sort_type_menu = new JMenu("排序方式");
        sort_menu.add(hide_item);
        sort_menu.add(finish_item);
        sort_menu.add(new JSeparator());
        sort_menu.add(sort_type_menu);

        JScrollPane scrollPane;
        {
            list_panel = new JPanel(new MigLayout(new LC().fillX(), new AC(), new AC()));

            List<Directory> roots = ListVideos.getRoots();

            for (Directory directory : roots) {
                Integer id = directory.getId();
                int percent = PercentCalculate.getProjectPercent(id);
                JPanel card_panel = cardPanel(id, directory.getName(), percent,
                        directory.getParent() + "\\" + directory.getName(), percent == 1000, directory.isAvailable());
                list_panel.add(card_panel, new CC().wrap().growX().gapY("5", "5"));
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

        search_input.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    logger.debug(search_input.getText());
                    list_panel.removeAll();
                    search();
                }
            }
        });

        sort_button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sort_menu.show(sort_button, 0, sort_button.getY());
                sort_menu.setVisible(true);
            }
        });

        hide_item.addActionListener(e -> update_list());
        finish_item.addActionListener(e -> update_list());
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

            set_button.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    logger.debug(set_menu.getWidth());
                    set_menu.show(set_button, 0, set_button.getY());
                    set_menu.setVisible(true);
                }
            });

            hide_item.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String msg = "";
                    boolean now_stat = hide_item.getState();
                    logger.debug("hidden_item: " + now_stat);

                    if (now_stat) {
                        msg = "将会隐藏这个项目（但不会从数据库中删除，要删除项目，可\n选择删除选项），确定要隐藏这个项目吗？";
                    } else {
                        msg = "将会重新显示这个项目，确定吗？";
                    }
                    int result = JOptionPane.showConfirmDialog(null, msg, "提示", JOptionPane.YES_NO_OPTION);
                    logger.debug(result);
                    if (result == 0) {
                        ListVideos.setRootHidden(Integer.parseInt(id_label.getText()), !now_stat);
                        update_list();
                    } else {
                        hide_item.setState(!now_stat);
                    }
                }
            });

            done_item.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int result = JOptionPane.showConfirmDialog(null,
                            "会将整个项目的所有视频设为已观看状态，确定吗？", "提示", JOptionPane.YES_NO_OPTION);
                    logger.debug(result);
                    if (result == 0) {
                        ListVideos.setProjectDone(Integer.parseInt(id_label.getText()));
                    }
                }
            });
        }
        return panel;
    }

    private void search() {
        List<Directory> roots = ListVideos.getRoots();

        for (Directory directory : roots) {
            if (directory.getName().contains(search_input.getText())) {
                Integer id = directory.getId();
                int percent = PercentCalculate.getProjectPercent(id);
                JPanel card_panel = cardPanel(id, directory.getName(), percent,
                        directory.getParent() + "\\" + directory.getName(), percent == 1000, directory.isAvailable());
                list_panel.add(card_panel, new CC().wrap().growX().gapY("5", "5"));
            }
        }
        update_panel();
    }

    private void update_list() {
        List<Directory> list = null;
        DaoImpl dao = new DaoImpl();
        if (hide_item.getState()) {
            list = dao.getAllRoots();
        } else {
            list = dao.getRoots();
        }

        list_panel.removeAll();

        for (Directory directory : list) {
            Integer id = directory.getId();
            int percent = PercentCalculate.getProjectPercent(id);

            if (!finish_item.getState() && percent == 1000) {
                continue;
            } else if (search_input.getText() != null
                    && !search_input.getText().equals("")
                    && !directory.getName().contains(search_input.getText())) {
                continue;
            }

            JPanel card_panel = cardPanel(id, directory.getName(), percent,
                    directory.getParent() + "\\" + directory.getName(), percent == 1000, directory.isAvailable());
            list_panel.add(card_panel, new CC().wrap().growX().gapY("5", "5"));

        }
        update_panel();
    }

    private void update_panel() {
        list_panel.updateUI();
        list_panel.invalidate();
        list_panel.validate();
        list_panel.repaint();
        repaint();
    }

    private void onSelectProject(String id) {
        int ID = Integer.parseInt(id);

        ConfigSet config = ConfigIO.loadConfig();
        config.addHistory(ID);
        ConfigIO.saveConfig(config);

        this.select_id = ID;
        logger.debug("ID is " + ID);
        dispose();
    }

    public int getSelect_id() {
        return select_id;
    }
}
