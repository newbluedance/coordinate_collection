/*
package com.lcf.coordinate_collection.view.view;

import com.lcf.coordinate_collection.view.entity.PointInfo;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoableEdit;
import org.springframework.stereotype.Component;

*/
/**
 * 定义一个继承自JPanel的类，重写它的paint方法
 *
 * @author lichunfeng
 *//*

@Component
public class HoverPanel extends JPanel implements MouseMotionListener{



    Point start = new Point();
    int r = 20;  //设定擦除精度

    */
/**
     * repaint方法会调用paint方法，并自动获得Graphics对像 然后可以用该对像进行2D画图 注：该方法是重写了JPanel的paint方法
     *//*

    @Override
    public void paint(Graphics g) {
        //调用的super.paint(g),让父类做一些事前的工作，如刷新屏幕
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;


    }


    */
/**
     * @param g2d g2d
     * @param img img
     *//*

    private void drawImg(Graphics2D g2d, Image img) {

        g2d.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }



    @Override
    public void mouseDragged(MouseEvent e) {
     */
/*   Graphics g = getGraphics();  //获取组件绘图环境
        //判断是否是鼠标左键
        if (!e.isMetaDown()) {
            g.setColor(Color.BLACK);
            g.drawLine(start.x, start.y, e.getX(), e.getY()); //绘线
        } else {
            g.setColor(getBackground());
            g.fillOval(e.getX() - r, e.getY() - r, 2 * r, 2 * r); //擦除
        }
        start.setLocation(e.getX(), e.getY());
        g.dispose();*//*

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Graphics g = getGraphics();  //获取组件绘图环境
        //判断是否是鼠标左键
        g.setColor(Color.RED);
        for (PointInfo p : WjRightPanel.getPointList()) {
            if (p.getX() - e.getX() > -3 && p.getX() - e.getX() < 3) {

                start.setLocation(p.getX(), p.getY());
                if (start.x != 0) {

                    g.drawLine(start.x, start.y, start.x, e.getY()); //绘线}

                }
            } else {
//                this.repaint();
            }
        }
        g.dispose();


        }


}

*/
