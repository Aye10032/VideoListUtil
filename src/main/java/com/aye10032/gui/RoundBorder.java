package com.aye10032.gui;

import javax.swing.border.Border;
import java.awt.*;

/**
 * @program: VideoListUtil
 * @description: 重写边框，绘制圆角
 * @author: Aye10032
 * @create: 2021-01-28 22:23
 **/
public class RoundBorder implements Border {
    private Color color;

    public RoundBorder(Color color) {
        this.color = color;
    }

    public RoundBorder() {
//        this.color = Color.GRAY;
    }


    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(c.getBackground().darker());
        g.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, 15, 15);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(0, 0, 0, 0);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}
