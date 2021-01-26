package com.aye10032.gui;

import com.aye10032.database.dao.DaoImpl;
import com.aye10032.database.pojo.Directory;
import com.aye10032.database.pojo.Video;
import com.aye10032.util.Util;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @program: VideoListUtil
 * @description: 动态加载面板
 * @author: Aye10032
 * @create: 2021-01-26 18:19
 **/
public class CardPanel {

    private static Logger logger = Logger.getLogger(CardPanel.class);

    public static JPanel cardPanel(Integer id, String name, int percent, boolean done) {
        LC layC = new LC().fill().wrap();
        AC colC = new AC().grow();
        AC rowC = new AC();
        JPanel panel = new JPanel(new MigLayout(layC, colC, rowC));
        panel.setBorder(new EtchedBorder());
        if (name.length() > 10) {
            List<String> names = Util.getStrList(name, 15);
            StringBuilder builder = new StringBuilder();
            builder.append("<html><body>");
            for (String sub_name : names) {
                builder.append(sub_name)
                        .append("<br> ");
            }
            builder.append("<body></html>");
            name = builder.toString();
        }
        JLabel name_label = new JLabel(name);
        name_label.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
        JProgressBar progressBar = new JProgressBar(0, 1000);
        progressBar.setValue(percent);
        JLabel id_label = new JLabel(id.toString());
        id_label.setVisible(false);

        JLabel done_label = new JLabel();
        if (done) {
            done_label.setIcon(new FlatSVGIcon("com/aye10032/icon/selected.svg"));
        } else {
            done_label.setIcon(new FlatSVGIcon("com/aye10032/icon/close.svg"));
        }

        JPopupMenu set_menu = new JPopupMenu();
        JMenuItem done_item = new JMenuItem("全部完成");
        JCheckBoxMenuItem hide_item = new JCheckBoxMenuItem("隐藏");
        JMenuItem del_item = new JMenuItem("删除");
        if (done) {
            done_item.setEnabled(false);
        }
        set_menu.add(done_item);
        set_menu.add(hide_item);
        set_menu.add(new JSeparator());
        set_menu.add(del_item);

        panel.add(name_label, new CC().growX().alignX("left").wrap());
        panel.add(progressBar, new CC().growX().gapLeft("15").wrap());
        panel.add(id_label, new CC().split(2).growX());
        panel.add(done_label, new CC().alignX("right"));

//        panel.setComponentPopupMenu(set_menu);

        return panel;
    }

    public static JPanel video_card(Video video) {
        LC layC = new LC().fill().wrap();
        AC colC = new AC().grow(100, 0).count(2);
        AC rowC = new AC();
        JPanel panel = new JPanel(new MigLayout(layC, colC, rowC));
        panel.setBorder(new EtchedBorder());

        String name = video.getName();
        StringBuilder path_builder = new StringBuilder();
        {
            DaoImpl dao = new DaoImpl();
            Directory directory;

            directory = dao.selectDirectoryWithID(video.getParent_id()).get(0);
            path_builder.append("\\")
                    .append(directory.getName());
            while (!directory.isIs_root()) {
                Integer parent_id = directory.getParent_id();
                directory = dao.selectDirectoryWithID(parent_id).get(0);
                path_builder.append("\\").append(directory.getName());
            }
            path_builder.append("\\").append(directory.getParent());
        }
        String path = path_builder.toString();
        logger.debug("path is: " + path);
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String update_date = ft.format(video.getDate());

        if (name.length() > 50) {
            List<String> names = Util.getStrList(name, 15);
            StringBuilder builder = new StringBuilder();
            builder.append("<html><body>");
            for (String sub_name : names) {
                builder.append(sub_name)
                        .append("<br> ");
            }
            builder.append("<body></html>");
            name = builder.toString();
        }
        JLabel name_label = new JLabel(name);
        name_label.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        JButton play = new JButton(new FlatSVGIcon("com/aye10032/icon/play.svg"));
        JLabel date_label = new JLabel("上次: " + update_date);
        date_label.setForeground(Color.DARK_GRAY);
        JLabel done_label = new JLabel();
        if (video.isHas_done()) {
            done_label.setIcon(new FlatSVGIcon("com/aye10032/icon/selected.svg"));
        } else {
            done_label.setIcon(new FlatSVGIcon("com/aye10032/icon/close.svg"));
        }

        JPopupMenu set_menu = new JPopupMenu();
        JMenuItem done_item = new JMenuItem("全部完成");
        JCheckBoxMenuItem hide_item = new JCheckBoxMenuItem("隐藏");
        JMenuItem del_item = new JMenuItem("删除");
        if (video.isHas_done()) {
            done_item.setEnabled(false);
        }
        set_menu.add(done_item);
        set_menu.add(hide_item);
        set_menu.add(new JSeparator());
        set_menu.add(del_item);

        panel.add(name_label, new CC().growX().alignX("left"));
        panel.add(play, new CC().gapLeft("15").wrap());
        panel.add(date_label, new CC().growX());
        panel.add(done_label, new CC().alignX("right"));

        panel.setComponentPopupMenu(set_menu);

        return panel;
    }


}
