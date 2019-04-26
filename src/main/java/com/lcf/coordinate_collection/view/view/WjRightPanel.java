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
    private static ImageIcon POINT_SELECT_ICON = new ImageIcon(
        WjRightPanel.class.getResource("/imgs/point/bdz_35_select.png"));
    private static ImageIcon LINE_SELECT_ICON = new ImageIcon(WjRightPanel.class.getResource("/imgs/point/bdz_35.png"));

    private int pointId;
    private int lineId=1;
    private Integer curX;
    private Integer curY;
    private int orderNum = 0;
    private boolean shiftDown = false;

    //当前画布持有的设备点
    private List<PointInfo> pointInfoList = new ArrayList<>();

    //当前画布持有的设备点
    private List<PointInfo> lineInfoList = new ArrayList<>();

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
        drawPoints(g2d);
    }

    /**
     * 绘制设备点
     *
     * @param g2d g2d
     */
    private void drawPoints(Graphics2D g2d) {
        g2d.setColor(Color.ORANGE);
        for (PointInfo p : pointInfoList) {
            g2d.drawImage(POINT_SELECT_ICON.getImage(), p.getX() - 8, p.getY() - 8, 16, 16, this);
            g2d.drawString(p.getId().toString(), p.getX(), p.getY() - 20);
        }

        for (int i = 0; i < lineInfoList.size(); i++) {
            g2d.drawImage(LINE_SELECT_ICON.getImage(), lineInfoList.get(i).getX() - 8, lineInfoList.get(i).getY() - 8, 16, 16, this);
            if(i>0 && lineInfoList.get(i).getId().equals(lineInfoList.get(i-1).getId())){
                g2d.setColor(Color.BLUE);
                g2d.drawLine(lineInfoList.get(i-1).getX(),lineInfoList.get(i-1).getY(),lineInfoList.get(i).getX(),lineInfoList.get(i).getY());
                g2d.drawString(lineInfoList.get(i).getId().toString(),(lineInfoList.get(i-1).getX()+lineInfoList.get(i).getX())/2, (lineInfoList.get(i-1).getY()+lineInfoList.get(i).getY())/2);
            }
        }

    }


    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        //获取组件绘图环境
        Graphics g = getGraphics();
        g.setColor(Color.ORANGE);
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
            System.out.println("Line" + (lineId) + " :X:" + x + "Y:" + y+ "num:" + orderNum);
            lineInfoList.add(new PointInfo(lineId, x, y, orderNum));
            g.drawImage(LINE_SELECT_ICON.getImage(), x - 8, y - 8, 16, 16, this);

        } else if (!e.isMetaDown()) {

            pointId++;
            System.out.println("Point" + (pointId) + " X:" + x + "Y:" + y);
            pointInfoList.add(new PointInfo(pointId, x, y, null));
            g.drawImage(POINT_SELECT_ICON.getImage(), x - 8, y - 8, 16, 16, this);
            g.drawString(String.valueOf(pointId), x, y - 20);
        }
        g.dispose();


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

        List<PointInfo> all=new ArrayList<>();
        all.addAll(pointInfoList);
        all.addAll(lineInfoList);

        for (PointInfo p : all) {
            if (compareCop(p.getX(), e.getX())) {
                start.setLocation(p.getX(), p.getY());
                g.drawLine(start.x, start.y, start.x, e.getY());
                curX = start.x;
                has = true;
            } else if (compareCop(p.getY(), e.getY())) {
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


}

