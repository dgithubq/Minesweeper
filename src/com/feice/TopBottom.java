package com.feice;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * 顶层绘制方法类
 */
public class TopBottom {
    //格子位置
    int temp_x;
    int temp_y;

    void reGame(){//重置上层游戏绘制
        for(int i=1;i<=GameUtil.MAP_W;i++){
            for (int j=1;j<=GameUtil.MAP_H;j++){
                GameUtil.DATA_TOP[i][j] = 0;//吧上层绘制全部清空
            }
        }
    }


    boolean boom(){//失败判定(返回true为失败，返回false为还没失败
        if (GameUtil.flagNum==GameUtil.RAY_MAX){//如果旗子插完了，就会翻开其他格子
            for (int i = 1; i <= GameUtil.MAP_W ; i++) {
                for (int j = 1; j <= GameUtil.MAP_H; j++) {
                    if (GameUtil.DATA_TOP[i][j]==0){
                        GameUtil.DATA_TOP[i][j] = -1;
                    }
                }
            }
        }
        for (int i = 1; i <= GameUtil.MAP_W ; i++) {
            for (int j = 1; j <= GameUtil.MAP_H; j++) {
                if (GameUtil.DATA_BOTTOM[i][j]==-1&&GameUtil.DATA_TOP[i][j]==-1){
                    GameUtil.state=2;
                    seeBoom();
                    return true;
                }
            }
        }
        return false;
    }

    //失败显示
    void seeBoom(){
        for (int i = 1; i <= GameUtil.MAP_W ; i++) {
            for (int j = 1; j <= GameUtil.MAP_H; j++) {
                if (GameUtil.DATA_BOTTOM[i][j]==-1&&GameUtil.DATA_TOP[i][j]!=1){
                    GameUtil.DATA_TOP[i][j] = -1;
                }
                if (GameUtil.DATA_BOTTOM[i][j]!=-1&&GameUtil.DATA_TOP[i][j]==1){
                    GameUtil.DATA_TOP[i][j] = 2;
                }
            }
        }
    }


    boolean win(){//胜利判定
        int count = 0;//统计没有打开的格子数，插错旗的除外
        for (int i = 1; i <= GameUtil.MAP_W ; i++) {
            for (int j = 1; j <= GameUtil.MAP_H; j++) {
                if (GameUtil.DATA_TOP[i][j]==1||GameUtil.DATA_TOP[i][j]==0){
                    count++;
                }
            }
        }
        if (count == GameUtil.RAY_MAX) {
            GameUtil.state=1;
            for (int i = 1; i <= GameUtil.MAP_W ; i++) {
                for (int j = 1; j <= GameUtil.MAP_H; j++) {
                    if (GameUtil.DATA_TOP[i][j]==0){
                        GameUtil.DATA_TOP[i][j]=1;
                    }
                }
            }
        }
        return false;
    }




    //判断鼠标事件逻辑
    void logic(){
        temp_x = 0;
        temp_y = 0;
        if (GameUtil.MOUSE_X>GameUtil.OFFSET && GameUtil.MOUSE_Y>3*GameUtil.OFFSET){//判定鼠标指针位置
            temp_x = (GameUtil.MOUSE_X-GameUtil.OFFSET)/GameUtil.SQUARE_LENGTH+1;
            temp_y = (GameUtil.MOUSE_Y-3*GameUtil.OFFSET)/GameUtil.SQUARE_LENGTH+1;
        }
        if (temp_x>=1 && temp_x<=GameUtil.MAP_W && temp_y>=1 && temp_y<=GameUtil.MAP_H) {
            if (GameUtil.MOUSE_LEFT) {//左键监听
                //覆盖则翻开
                if (GameUtil.DATA_TOP[temp_x][temp_y]==0){
                    GameUtil.DATA_TOP[temp_x][temp_y]=-1;
                }
                spaceOpen(temp_x,temp_y);
                spaceNum(temp_x,temp_y);
                GameUtil.MOUSE_LEFT = false;
            }
            if (GameUtil.MOUSE_RIGHT) {
                //覆盖则插旗
                if (GameUtil.DATA_TOP[temp_x][temp_y]==0){
                    GameUtil.DATA_TOP[temp_x][temp_y]=1;
                    GameUtil.flagNum++;
                } else if (GameUtil.DATA_TOP[temp_x][temp_y]==1){// 插旗则取消
                    GameUtil.DATA_TOP[temp_x][temp_y]=0;
                    GameUtil.flagNum--;
                }
                GameUtil.MOUSE_RIGHT = false;
            }
        }
        boom();
        win();
    }



    void spaceOpen(int x,int y) {//打开空格
        if (GameUtil.DATA_BOTTOM[x][y] == 0 && GameUtil.DATA_TOP[x][y] != 1) {//底层是空格，而且上层没有插旗就可以左键点开
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    //该区域是被覆盖的，所以才会被打开，如果该区域被插了旗子，就不会被翻开，而且此处底层是雷也不会被打开
                    if (GameUtil.DATA_TOP[i][j] != -1 && GameUtil.DATA_TOP[i][j] != 1 && GameUtil.DATA_BOTTOM[i][j] != -1) {
                        GameUtil.DATA_TOP[i][j] = -1;
                        //加一个限定，必须在雷区当中
                        if (i >= 1 && j >= 1 && i <= GameUtil.MAP_W && j <= GameUtil.MAP_H) {
                            spaceOpen(i, j);
                        }
                    }
                }
            }
        }
    }
    void spaceNum(int x,int y){
        if (GameUtil.DATA_BOTTOM[x][y]>0&&GameUtil.DATA_TOP[x][y]==-1){//如果左键点击的地方是数字，该3*3的方格中已经插了旗子数和数字相同，就打开其他方格,此处限制上层中没有覆盖元素
            int flagNum = 0;
            for (int i = x-1;i<=x+1;i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    if (GameUtil.DATA_TOP[i][j] == 1){
                        flagNum++;
                    }
                }
            }
            if (GameUtil.DATA_BOTTOM[x][y] == flagNum){
                for (int i = x-1;i<=x+1;i++) {
                    for (int j = y - 1; j <= y + 1; j++) {
                        if (GameUtil.DATA_TOP[i][j]!=1) {//只要是没有插旗的，都翻开
                            GameUtil.DATA_TOP[i][j] = -1;
                        }
                        if (i >= 1 && j >= 1 && i <= GameUtil.MAP_W && j <= GameUtil.MAP_H) {
                            spaceOpen(i, j);
                        }
                    }
                }
            }
        }
    }



    public void painSelf(Graphics g){
        //绘制顶层图片
        logic();
        for (int i = 1; i <= GameUtil.MAP_W ; i++) {
            for (int j = 1; j <= GameUtil.MAP_H; j++) {
                //覆盖
                if (GameUtil.DATA_TOP[i][j] == 0) {
                    g.drawImage(GameUtil.top,
                            GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.SQUARE_LENGTH - 2,
                            GameUtil.SQUARE_LENGTH - 2,
                            null);
                }
                //插旗
                if (GameUtil.DATA_TOP[i][j] == 1) {
                    g.drawImage(GameUtil.flag,
                            GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.SQUARE_LENGTH - 2,
                            GameUtil.SQUARE_LENGTH - 2,
                            null);
                }
                //差错旗
                if (GameUtil.DATA_TOP[i][j] == 2) {
                    g.drawImage(GameUtil.noFlag,
                            GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.SQUARE_LENGTH - 2,
                            GameUtil.SQUARE_LENGTH - 2,
                            null);
                }


            }
        }
    }
}
