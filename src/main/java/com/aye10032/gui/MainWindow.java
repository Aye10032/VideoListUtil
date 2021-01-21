package com.aye10032.gui;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @program: VideoListUtil
 * @description: 主程序窗口
 * @author: Aye10032
 * @create: 2021-01-19 20:23
 **/
public class MainWindow extends JFrame{

    public MainWindow() {
        JPanel panel = new JPanel(new MigLayout("debug", "[] [] []", "[] []"));

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu1 = new JMenu("文件");
        JMenu menu2 = new JMenu("编辑");
        JMenu menu3 = new JMenu("视图");
        menuBar.add(menu1);
        menuBar.add(menu2);
        menuBar.add(menu3);
        JMenuItem item1 = new JMenuItem("打开");
        JMenuItem item2 = new JMenuItem("保存");
        JMenuItem item3 = new JMenuItem("另存为");
        JMenuItem item4 = new JMenuItem("退出");
        menu1.add(item1);
        menu1.add(item2);
        menu1.add(item3);
        menu1.addSeparator();
        menu1.add(item4);

        add(panel, BorderLayout.CENTER);

    }

    private void initFrame(){

    }
}
