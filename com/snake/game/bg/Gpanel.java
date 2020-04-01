package com.snake.game.bg;

import com.sun.org.apache.xml.internal.security.Init;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author swx
 * @since 1.8
 * <p> creat by 2020/3/31 on 17:08
 */
public class Gpanel extends JPanel implements KeyListener {
    private ImageIcon title = new ImageIcon("./img/title.jpg");
    private ImageIcon left = new ImageIcon("./img/left.png");
    private ImageIcon right = new ImageIcon("./img/right.png");
    private ImageIcon down = new ImageIcon("./img/down.jpg");
    private ImageIcon up = new ImageIcon("./img/up.png");
    private ImageIcon body = new ImageIcon("./img/body.png");
    private ImageIcon food = new ImageIcon("./img/food.png");
    private Font font = new Font("arial", Font.CENTER_BASELINE, 40);
    private ImageIcon head;
    int len;
    private int foodx;
    private int score;
    private int foody;
    private Random random=new Random();
    private boolean gstart = false;
    private boolean gover = false;
    private Timer timer = new Timer(300, new ActionListener() {
        @Override//时间到了便调用这个方法
        public void actionPerformed(ActionEvent e) {
            gameover();
            if(gstart==true && gover==false){
                for (int i =len-1; i >0 ; i--) {
                    snakex.set(i,snakex.get(i-1));
                    snakey.set(i,snakey.get(i-1));
                }
                across();
                    if (fx=="R")
                        snakex.set(0,snakex.get(0)+25);
                    else if (fx=="L")
                        snakex.set(0,snakex.get(0)-25);
                    else if (fx=="D")
                        snakey.set(0,snakey.get(0)+25);
                    else if (fx=="U")
                        snakey.set(0,snakey.get(0)-25);
                    eatFood();
           }
            repaint();
            speedIncrease();
            timer.start();
        }
    });//delay:多长时间到点，listener到点后做什么
    private String fx = "R";//方向 U D R L
    private ArrayList<Integer> snakex = new ArrayList<Integer>();
    private ArrayList<Integer> snakey = new ArrayList<Integer>();

    public Gpanel() {
        initSnake();
        this.setFocusable(true);//获取焦点事件
        this.addKeyListener(this);//键盘事件
        timer.start();
    }

    /**
     * 功能描述:
     * 〈由paint调用
     * 在JPanel构建的时候,由虚拟机隐式调用〉
     *
     * @param g :
     * @return : void
     */
    public void paintComponent(Graphics g) {
        //调用父类的本函数，在此基础上增加功能
        super.paintComponent(g);
        //白色背景
        this.setBackground(Color.WHITE);
        //蓝色标题
        title.paintIcon(this, g, 25, 11);
        g.setColor(Color.WHITE);
        g.drawString("Len:"+len,820,40);
        g.drawString("Score:"+score,820,60);
        //黑色游戏区
        g.setColor(Color.BLACK);
        g.fillRect(25, 75, 850, 600);
        switch (fx) {
            case "R":
                head = right;
                break;
            case "L":
                head = left;
                break;
            case "U":
                head = up;
                break;
            case "D":
                head = down;
                break;
            default:
                break;
        }
        head.paintIcon(this, g, snakex.get(0), snakey.get(0));
        for (int i = 1; i < len; i++) {
            body.paintIcon(this, g, snakex.get(i), snakey.get(i));
        }
        food.paintIcon(this,g,foodx,foody);
        if (gover==true && gstart==true) {
            g.setColor(Color.BLUE);
            g.setFont(font);
            g.drawString("Game Over!!!", 400, 350);
            g.drawString("Press Space to Restart", 400, 400);
            repaint();
        }
        //画笔设置底色,避免相同颜色显示不了开始提示
        if (gstart == false && gover==false) {
            g.setColor(Color.WHITE);
            g.setFont(font);
            g.drawString("Press Space to Start", 400, 350);
        }


    }

    /**
     * 功能描述:
     * 〈初始化蛇头蛇身〉
     *
     * @param :
     * @return : void
     */
    public void initSnake() {
        len = 3;
        score=100;
        fx="R";
        snakex.add(0, 100);
        snakey.add(0, 100);
        snakex.add(1, 75);
        snakey.add(1, 100);
        snakex.add(2, 50);
        snakey.add(2, 100);
        foodx=25+25*random.nextInt(34);
        foody=75+25*random.nextInt(24);
    }
    public void across(){
        if(snakex.get(0)>825||snakex.get(0)<25){
            gover=true;
        }
        if(snakey.get(0)>625||snakey.get(0)<75){
            gover=true;
        }

    }
    public void speedIncrease(){
       if(score>=20 && score <50){
           timer.setDelay(100);
       }
       else if(score >=50 && score<100){
           timer.setDelay(50);
       }
       else if(score>=100){
           timer.setDelay(20);
       }
    }
    public void gameover(){
        for (int i=1;i<len;i++){
            if(snakex.get(i)==snakex.get(0)&&snakey.get(i)==snakey.get(0)){
                gover=true;
            }
        }
    }
    public void eatFood(){
        if(snakex.get(0)==foodx && snakey.get(0)==foody){
            snakex.add(len,0);  //增加集合长度
            snakey.add(len,0);  //增加集合长度
            len++;
            score+=2;
            foodx=25+25*random.nextInt(34);
            foody=75+25*random.nextInt(24);
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {  //发生击键事件时被触发

    }

    @Override
    public void keyPressed(KeyEvent e) {  //按键被按下（长按也可）被触发
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_SPACE:
                if(gover==true){
                    initSnake();
                    gstart=false;
                    gover=false;
                }else{
                    gstart=!gstart;
                }
                repaint();
                break;
            case KeyEvent.VK_W:
                if(head==down)
                    gover=true;
                if(gstart==true)
                    fx="U";
                repaint();
                break;
            case KeyEvent.VK_S:
                if(head==up)
                    gover=true;
                if(gstart==true)
                    fx="D";
                repaint();
                break;
            case KeyEvent.VK_A:
                if(head==right)
                    gover=true;
                if(gstart==true)
                    fx="L";
                break;
            case KeyEvent.VK_D:
                if(head==left)
                    gover=true;
                if(gstart==true)
                    fx="R";
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { //按键被释放时被触发

    }
}
