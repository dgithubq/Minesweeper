package com.feice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameWin extends JFrame {

    GameSelect gameSelect = new GameSelect();
    BottomRay bottomRay = new BottomRay();
    BottomNum bottomNum = new BottomNum();
    int wigth = 2 * GameUtil.OFFSET + GameUtil.MAP_W * GameUtil.SQUARE_LENGTH;
    int height = 4 * GameUtil.OFFSET + GameUtil.MAP_H * GameUtil.SQUARE_LENGTH;
    boolean begin = false;//判断是否点击到

    Image imageSe = null;
    MapBottom mapBottom = new MapBottom();
    TopBottom topBottom = new TopBottom();


    void launch(){//设置窗口的属性的方法
        GameUtil.START_TIME = System.currentTimeMillis();
        this.setVisible(true);
        if (GameUtil.state==3){//如果状态是选择难度界面，就出一个500,500 窗口大小的页面窗口
            this.setSize(500,500);
        }else {
            this.setSize(wigth, height);
        }
        this.setLocationRelativeTo(null);//设置组件在窗口中央
        this.setTitle("扫雷");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {//监听
                super.mouseClicked(e);

                switch (GameUtil.state){//按照游戏状态来搞监听机制，游戏结束了点击就不会有效果
                    case 0:
                        if (e.getButton()==1){//鼠标左键点击设置是==1
                            GameUtil.MOUSE_X = e.getX();
                            GameUtil.MOUSE_Y = e.getY();
                            GameUtil.MOUSE_LEFT = true;
                        }
                        if (e.getButton()==3){//鼠标右键点击是==3
                            GameUtil.MOUSE_X = e.getX();
                            GameUtil.MOUSE_Y = e.getY();
                            GameUtil.MOUSE_RIGHT = true;
                        }

                    case 1:

                    case 2:
                        if(e.getButton()==1){//失败点击最上方的头像重置游戏
                            if(e.getX()>GameUtil.OFFSET + GameUtil.SQUARE_LENGTH*(GameUtil.MAP_W/2)
                                    && e.getX()<GameUtil.OFFSET + GameUtil.SQUARE_LENGTH*(GameUtil.MAP_W/2) + GameUtil.SQUARE_LENGTH
                                    && e.getY()>GameUtil.OFFSET
                                    && e.getY()<GameUtil.OFFSET+GameUtil.SQUARE_LENGTH){
                                mapBottom.reGame();
                                topBottom.reGame();
                                GameUtil.flagNum=0;
                                GameUtil.START_TIME = System.currentTimeMillis();
                                GameUtil.state=0;
                            }
                        }
                        if (e.getButton()==2){//滚轮选难度
                            GameUtil.state=3;
                            begin = true;
                        }
                        break;
                    case 3://当游戏状态为选择难度的时候
                        if (e.getButton()==1){//鼠标左键是==1
                            GameUtil.MOUSE_X = e.getX();
                            GameUtil.MOUSE_Y = e.getY();
                            begin = gameSelect.bard();//判断是否点击到
                        }
                        break;
                }
            }
        });

        while (true){
            repaint();//重绘窗口
            begin();//不断调用游戏开始
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    void begin(){//游戏开始的方法
        if (begin){
            begin = false;//重置begin变量
            gameSelect.hard(GameUtil.level);//进入到不同难度
            dispose();
            GameWin gameWin = new GameWin();
            GameUtil.START_TIME = System.currentTimeMillis();//重置开始时间
            GameUtil.flagNum = 0;
            mapBottom.reGame();
            topBottom.reGame();
            gameWin.launch();//调用绘制窗口
        }
    }

    @Override
    public void paint(Graphics g) {//新建一个画布，把底层和顶层都放在这个画布上，再把整个画布放到窗口上
        if (GameUtil.state == 3){
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0,0,500,500);
            gameSelect.paintSelf(g);
        }else {
            imageSe = this.createImage(wigth, height);//设置新画布的大小
            Graphics graphics = imageSe.getGraphics();//新画布的画笔
            graphics.setColor(Color.cyan);//画笔设置颜色
            graphics.fillRect(0, 0, wigth, height);//颜色覆盖全部，设置背景
            mapBottom.painSelf(graphics);
            topBottom.painSelf(graphics);
            g.drawImage(imageSe, 0, 0, null);
        }
    }

    public static void main(String[] args) {
        GameWin gameWin = new GameWin();
        gameWin.launch();
    }
}
