package com.snake.game.bg;

import sun.plugin.viewer.context.AxBridgeAppletContext;

import javax.swing.*;
import java.awt.*;

/**
 * @author swx
 * @since 1.8
 * <p> creat by 2020/3/31 on 17:09
 */
public class Gsnake {
    private   static ImageIcon icon= new ImageIcon("./resource/img/icon.jpg");
    public static void main(String[] args) {
        JFrame frame = new JFrame();//创建窗口
        frame.setTitle("Snake Game");
        //设置窗口图标
        frame.setIconImage(icon.getImage());
        frame.setBounds(600,200,900,720);//在坐标x:600,y:200出现。大小为900*720
        frame.setResizable(false);//不允许调正窗口大小
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //点击×即关闭窗口
        frame.add(new Gpanel());
        frame.setVisible(true);// 展示窗口
    }
}
