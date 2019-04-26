package com.lcf.coordinate_collection.view.view;

import com.lcf.coordinate_collection.view.entity.PointInfo;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * 定义一个继承自JPanel的类，重写它的paint方法
 *
 * @author lichunfeng
 */
@Component
@Setter
@Getter
public class WjRightPanel extends JPanel implements MouseListener, MouseMotionListener {


    /** 背景图 */
    private static ImageIcon BACK_GROUND_ICON = new ImageIcon(
        WjRightPanel.class.getResource("/imgs/wj_background.png"));


    private Integer curX;
    private Integer curY;
    private int orderNum = 0;
    private boolean shiftDown = false;

    /** 当前画布持有的设备点 */
    private PointCollect collect=new PointCollect();

    PointInfo curXP;
    PointInfo curYP;

    //当前底图
    private String curImg;


    Point start = new Point();
    //设定擦除精度
    int r = 5;

    /**
     * repaint方法会调用paint方法，并自动获得Graphics对像 然后可以用该对像进行2D画图 注：该方法是重写了JPanel的paint方法
     */
    @Override
    public void paint(Graphics g) {
        //调用的super.paint(g),让父类做一些事前的工作，如刷新屏幕
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        collect.drawPoints(g2d);
    }



    @Override
    public void mouseClicked(MouseEvent e) {

        //获取组件绘图环境
        Graphics g = getGraphics();
        g.setColor(Color.ORANGE);

        int x = e.getX();
        int y = e.getY();
        //判断是否是鼠标左键
        if (curX != null) {
            x = curX;
        }
        if (curY != null) {
            y = curY;
        }

        if (e.isMetaDown() && e.isShiftDown()) {
            shiftDown=true;
            orderNum++;
            PointInfo newLinePoint = new PointInfo(collect.getNextLineId(), x, y, orderNum);
            System.out.println("Line" + newLinePoint);
            collect.addLinePoint(newLinePoint);
            PointCollect.drawLinePoint(g,x,y);

        } else if (!e.isMetaDown()) {

            PointInfo newPoint = new PointInfo(collect.getNextPointId(), x, y, null);
            g.drawString(String.valueOf(collect.getNextPointId()), x, y - 20);
            System.out.println("Point" +newPoint);
            collect.addPoint(newPoint);
            PointCollect.drawPoint(g,x,y);
        }
        g.dispose();


    }



    @Override
    public void mouseMoved(MouseEvent e) {
        if (e.isAltDown()) {
            curX = null;
            curY = null;
            this.repaint();
            return;
        }
        //获取组件绘图环境
        Graphics g = getGraphics();
        g.setColor(Color.RED);

        if (curX != null && curY != null && (!compareCop(curX, e.getX()) || !compareCop(curY, e.getY()))) {
            curX = null;
            curY = null;
            this.repaint();
        }

        boolean has = false;

        for (PointInfo p : collect.getHisPoint()) {
            if (compareCop(p.getX(), e.getX())) {
                start.setLocation(p.getX(), p.getY());
                g.drawLine(start.x, start.y, start.x, e.getY());
                curX = start.x;
                has = true;
            }
            if (compareCop(p.getY(), e.getY())) {
                start.setLocation(p.getX(), p.getY());
                g.drawLine(start.x, start.y, e.getX(), start.y);
                curY = start.y;
                has = true;
            }
        }
        if (!has) {
            curX = null;
            curY = null;
            this.repaint();
        }


    }

    private boolean compareCop(int a, int b) {
        if (a - b > -10 && a - b < 10) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (BACK_GROUND_ICON != null) {
            //下面这行是为了背景图片可以跟随窗口自行调整大小，可以自己设置成固定大小
            g.drawImage(BACK_GROUND_ICON.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
        }
        if (curImg != null) {
            ImageIcon imageIcon = new ImageIcon(curImg);
            //下面这行是为了背景图片可以跟随窗口自行调整大小，可以自己设置成固定大小
            g.drawImage(imageIcon.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    @Override
    public void mouseDragged(MouseEvent e) {

    }


}

