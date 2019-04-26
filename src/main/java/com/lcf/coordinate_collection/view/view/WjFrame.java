package com.lcf.coordinate_collection.view.view;

import com.alibaba.fastjson.JSONObject;
import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileSystemView;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;

/**
 * @author lichunfeng
 */
@Service
@Getter
public class WjFrame {

    @Value("${hwa.client.left.panel.width}")
    private Integer width;

    @Value("${hwa.client.left.panel.height}")
    private Integer height;

    @Resource
    private WjRightPanel jp;

    private static final String FRAME_NAME = "WJRightFrame";

    public void showFrame() {
        // 创建JFrame
        JFrame frame = new JFrame(FRAME_NAME);
        // JFrame关闭时的操作
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
       /* //设置为无边框
        frame.setUndecorated(true);*/
        //设置框架的大小和位置
        frame.setBounds(0, 0, width, height);
        frame.add(jp);
        jp.addMouseListener(jp);
        jp.addMouseMotionListener(jp);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        // 注册应用程序全局键盘事件, 所有的键盘事件都会被此事件监听器处理.
        toolkit.addAWTEventListener((AWTEvent event) -> {
            if (event.getClass() == KeyEvent.class && event.getID() == KeyEvent.KEY_PRESSED) {
                KeyEvent kE = ((KeyEvent) event);
                // 处理按键事件 Art+i
                if ((kE.getKeyCode() == 73)
                    && (((InputEvent) event)
                    .isAltDown())) {

                    File file = selectFilesAndDir();
                    if (file != null) {
                        this.getJp().setCurImg(file.getAbsoluteFile().toString().replace("\\", "\\\\"));
                        this.getJp().setLineId(0);
                        this.getJp().setPointId(0);
                        this.getJp().setPointInfoList(new ArrayList<>());
                        this.getJp().repaint();
                    }
                }
                // 处理按键事件 Ctrl+s
                if ((kE.getKeyCode() == 83)
                    && (((InputEvent) event)
                    .isControlDown())) {
                    outFile(JSONObject.toJSON(this.getJp().getPointInfoList()).toString());
                }
            }
            if (event.getClass() == KeyEvent.class && event.getID() == KeyEvent.KEY_RELEASED) {
                KeyEvent kE = ((KeyEvent) event);
                System.out.println(kE.getKeyCode());
                if (kE.getKeyCode() == 16) {
                    if (this.getJp().isShiftDown()) {
                        this.getJp().setOrderNum(0);
                        this.getJp().setLineId(this.getJp().getLineId() + 1);
                    }
                    this.getJp().setShiftDown(false);
                }

            }
        }, java.awt.AWTEvent.KEY_EVENT_MASK);

        // 显示JFrame
        frame.setVisible(true);

    }

    /**
     * 选择路径或文件
     *
     * @return
     */
    public File selectFilesAndDir() {
        JFileChooser jfc = new JFileChooser();
        //设置当前路径为桌面路径,否则将我的文档作为默认路径
        FileSystemView fsv = FileSystemView.getFileSystemView();
        jfc.setCurrentDirectory(fsv.getHomeDirectory());
        //JFileChooser.FILES_AND_DIRECTORIES 选择路径和文件
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        //弹出的提示框的标题
        jfc.showDialog(new JLabel(), "确定");
        //用户选择的路径或文件
        File file = jfc.getSelectedFile();
        return file;
    }

    /**
     * @param s
     */
    public void outFile(String s) {
        String path = new ApplicationHome(getClass()).getSource().getParentFile().getPath();
        path = path.concat("/1.txt");
        System.out.println(path);
        File file = new File(path);
        try {
            createNewFile(file);
            FileOutputStream fop = new FileOutputStream(file);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fop, "utf-8");

            BufferedWriter writer = new BufferedWriter(outputWriter);
            writer.write(s);
            writer.flush();
            System.out.println("已经生成文件到:" + path);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void createNewFile(File file) {
        if (!file.getParentFile().exists()) {
            try {
                file.getParentFile().mkdirs();
                if (!file.exists()) {
                    file.createNewFile();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
