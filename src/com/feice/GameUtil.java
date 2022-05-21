package com.feice;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * 工具类
 * 存放静态参数
 * 工具方法
 */
public class GameUtil {
    //地雷个数
    static int RAY_MAX = 100;
    //地图的宽
    static int MAP_W = 40;
    //地图的高
    static int MAP_H = 40;
    //雷区偏移量
    static int OFFSET = 45;
    //格子边长
    static int SQUARE_LENGTH = 50;



    //鼠标事件参数
    //坐标
    static int MOUSE_X;
    static int MOUSE_Y;
    //状态
    static boolean MOUSE_LEFT = false;
    static boolean MOUSE_RIGHT = false;




    //底层元素  -1 雷 ,0 空 ,1-8 表示对应数字
    static int[][] DATA_BOTTOM = new int[MAP_W+2][MAP_H+2];

    //顶层元素-1 无覆盖, 0 覆盖, 1插旗, 2 插错旗
    static int[][] DATA_TOP = new int[MAP_W+2][MAP_H+2];
    //旗子数
    static int flagNum = 0;



    //设置游戏状态,0：游戏中，1：胜利，2：失败,3：难度选择
    static int state = 3;



    //游戏难度
    static int level;



    //设置倒计时的参数
    static long START_TIME;//开始时间
    static long OVER_TIME;//结束时间




    //载入图片
    //雷：
    static Image lei = Toolkit.getDefaultToolkit().getImage("img/lei.png");
    //最顶层，方块
    static Image top = Toolkit.getDefaultToolkit().getImage("img/top.gif");
    //旗子
    static Image flag = Toolkit.getDefaultToolkit().getImage("img/flag.gif");
    //插错旗
    static Image noFlag = Toolkit.getDefaultToolkit().getImage("img/noflag.gif");

    //胜利
    static Image win = Toolkit.getDefaultToolkit().getImage("img/win.png");
    //失败
    static Image over = Toolkit.getDefaultToolkit().getImage("img/over.png");
    //进行中
    static Image face = Toolkit.getDefaultToolkit().getImage("img/face.png");


    static Image[] images = new Image[9];
    static {
        for (int i = 1; i <=8 ; i++) {
            images[i] = Toolkit.getDefaultToolkit().getImage("img/num/"+i+".png");
        }
    }

    static void drawWord(Graphics g,String str,int x,int y,int size,Color color){
        //绘制左右两边的数字，左边代表剩余雷数，右边为计时
        g.setColor(color);
        g.setFont(new Font("宋体",Font.BOLD,size));
        g.drawString(str,x,y);

    }
}
