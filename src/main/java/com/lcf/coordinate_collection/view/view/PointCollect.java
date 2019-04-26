package com.lcf.coordinate_collection.view.view;

import com.lcf.coordinate_collection.view.entity.PointInfo;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.swing.ImageIcon;
import lombok.Data;

/**
 * @author lichunfeng
 */
@Data
public class PointCollect {

    private static ImageIcon POINT_SELECT_ICON = new ImageIcon(
        WjRightPanel.class.getResource("/imgs/point/point_select.png"));
    private static ImageIcon LINE_SELECT_ICON = new ImageIcon(WjRightPanel.class.getResource("/imgs/point/bdz_35.png"));

    /** 当前画布持有的设备点 */
    private List<PointInfo> pointInfoList = new ArrayList<>();

    private List<PointInfo> lineInfoList = new ArrayList<>();

    private Stack<PointInfo> hisPoint = new Stack<>();

    private Integer nextLineId = 1;


    public void addPoint(PointInfo p) {
        pointInfoList.add(p);
        hisPoint.push(p);
    }

    public void addLinePoint(PointInfo p) {
        lineInfoList.add(p);
        hisPoint.push(p);

    }

    public void removeOne() {
        PointInfo pop = hisPoint.pop();
        if (pop.getNum() == null) {
            pointInfoList.remove(pop);
        } else {
            lineInfoList.remove(pop);
            while (true) {
                if (lineInfoList.size() > 0) {

                    PointInfo temp = lineInfoList.get(lineInfoList.size() - 1);
                    if (temp.getId() == nextLineId - 1) {
                        lineInfoList.remove(lineInfoList.size() - 1);
                        hisPoint.pop();
                        continue;
                    }
                }
                nextLineId--;
                break;

            }

        }

    }

    public Integer getNextPointId() {
        return pointInfoList.size() + 1;
    }

    public Integer getNextLineId() {
        return nextLineId;
    }

    /**
     * 绘制设备点
     *
     * @param g g
     */
    public void drawPoints(Graphics g) {
        g.setColor(Color.ORANGE);
        for (PointInfo p : pointInfoList) {
            drawPoint(g, p.getX(), p.getY());
            g.drawString(p.getId().toString(), p.getX(), p.getY() - 20);
        }

        for (int i = 0; i < lineInfoList.size(); i++) {
            drawLinePoint(g, lineInfoList.get(i).getX(), lineInfoList.get(i).getY());
            if (i > 0 && lineInfoList.get(i).getId().equals(lineInfoList.get(i - 1).getId())) {
                g.setColor(Color.BLUE);
                g.drawLine(lineInfoList.get(i - 1).getX(), lineInfoList.get(i - 1).getY(), lineInfoList.get(i).getX(),
                    lineInfoList.get(i).getY());
                g.drawString(lineInfoList.get(i).getId().toString(),
                    (lineInfoList.get(i - 1).getX() + lineInfoList.get(i).getX()) / 2,
                    (lineInfoList.get(i - 1).getY() + lineInfoList.get(i).getY()) / 2);
            }
        }
    }

    public static void drawPoint(Graphics g, int x, int y) {
        int width = 16;
        g.drawImage(POINT_SELECT_ICON.getImage(), x - width / 2, y - width / 2, width, width, null);
    }

    public static void drawLinePoint(Graphics g, int x, int y) {
        int width = 8;
        g.drawImage(LINE_SELECT_ICON.getImage(), x - width / 2, y - width / 2, width, width, null);
    }


}
