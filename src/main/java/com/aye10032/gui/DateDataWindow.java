package com.aye10032.gui;

import com.aye10032.config.ConfigIO;
import com.aye10032.config.ConfigSet;
import com.aye10032.config.LocalConfig;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

/**
 * @program: VideoListUtil
 * @description: 日期进度窗口
 * @author: Aye10032
 * @create: 2021-02-14 18:47
 **/
public class DateDataWindow extends JFrame {

    public DateDataWindow() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        ConfigSet config = ConfigIO.loadConfig();
        int windowWidth = (int) (config.getWINDOW_WIDTH() * LocalConfig.DATE_DATA_WINDOW_WIDTH_PROPORTION);
        int windowHeight = (int) (config.getWINDOW_HEIGHT() * LocalConfig.DATE_DATA_WINDOW_HEIGHT_PROPORTION);

        setTitle("进度一览");
        setIconImage(new FlatSVGIcon("com/aye10032/icon.svg").getImage());
        setBounds((screenWidth - windowWidth) / 2, (screenHeight - windowHeight) / 2, windowWidth, windowHeight);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        initFrame();
    }

    private void initFrame() {
        LC lc = new LC().debug().fill();
        AC colC = new AC().grow();
        AC rowC = new AC().grow();

        JPanel panel = new JPanel(new MigLayout(lc, colC, rowC));

        JTable table = new JTable(7,25);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);
        table.setValueAt("aaa",0,0);
        panel.add(table, new CC().growY());

        JScrollPane scrollPane = new JScrollPane(panel);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        contentPane.add(scrollPane, BorderLayout.CENTER);
    }

    private String[][] getData(int year) {
        int date[] = new int[365];
        String data[][] = null;

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, Calendar.JANUARY, 1);

        int first_day = calendar.get(Calendar.DAY_OF_WEEK);

        int all_week = (365-(7-first_day))/7;
        System.out.println(all_week);

        data = new String[7][all_week];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < all_week; j++) {
                data[i][j] = "a";
            }
        }

        return data;
    }

}
