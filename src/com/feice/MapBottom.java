package com.feice;

import java.awt.*;

/**
 * 底层绘制方法类
 */
public class MapBottom {
    BottomNum bottomNum = new BottomNum();
    BottomRay bottomRay = new BottomRay();

    {//这个代码块的作用是让程序一运行，就调用生成雷和数字
        bottomRay.newRay();
        bottomNum.newNum();
    }
    void reGame(){//重底层元素内容
        for(int i=1;i<=GameUtil.MAP_W;i++){
            for (int j=1;j<=GameUtil.MAP_H;j++){
                GameUtil.DATA_BOTTOM[i][j] = 0;//吧底层绘制全部清空
            }
        }
        bottomRay.newRay();//重新生成雷
        bottomNum.newNum();//重新生成数字
    }
    public void painSelf(Graphics g){
        g.setColor(Color.white);
        //画竖线
        for (int i = 0; i <= GameUtil.MAP_W; i++) {
            g.drawLine(GameUtil.OFFSET + i * GameUtil.SQUARE_LENGTH,
                    3*GameUtil.OFFSET,
                    GameUtil.OFFSET+i*GameUtil.SQUARE_LENGTH,
                    3*GameUtil.OFFSET+GameUtil.MAP_H*GameUtil.SQUARE_LENGTH);
        }
        //画横线
        for (int i = 0; i <=GameUtil.MAP_H; i++){
            g.drawLine(GameUtil.OFFSET,
                    3*GameUtil.OFFSET+i*GameUtil.SQUARE_LENGTH,
                    GameUtil.OFFSET+GameUtil.MAP_W*GameUtil.SQUARE_LENGTH,
                    3*GameUtil.OFFSET+i*GameUtil.SQUARE_LENGTH);
        }
        for (int i = 1; i <= GameUtil.MAP_W ; i++) {
            for (int j = 1; j <= GameUtil.MAP_H; j++) {
                //雷
                if (GameUtil.DATA_BOTTOM[i][j] == -1) {
                    g.drawImage(GameUtil.lei,
                            GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.SQUARE_LENGTH - 2,
                            GameUtil.SQUARE_LENGTH - 2,
                            null);
                }
                //数字
                if (GameUtil.DATA_BOTTOM[i][j] >=0) {
                    g.drawImage(GameUtil.images[GameUtil.DATA_BOTTOM[i][j]],
                            GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH + 15,
                            GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 5,
                            null);
                }

            }
        }


        //绘制左右两边的数字，左边代表剩余雷数，右边为计时
        GameUtil.drawWord(g,""+(GameUtil.RAY_MAX-GameUtil.flagNum),
                GameUtil.OFFSET,GameUtil.OFFSET*2,35,Color.black);
        GameUtil.drawWord(g,""+(GameUtil.OVER_TIME-GameUtil.START_TIME)/1000,
                GameUtil.OFFSET+GameUtil.SQUARE_LENGTH*(GameUtil.MAP_W-1),
                GameUtil.OFFSET*2,35,Color.black);



        //绘制在不同状态下，窗口上方显示的图片
        switch (GameUtil.state){
            case 0://顶层居中，x:偏移量+雷区的一般
                GameUtil.OVER_TIME = System.currentTimeMillis();//结束时间时时刻刻刷新
                g.drawImage(GameUtil.face,
                        GameUtil.OFFSET+GameUtil.SQUARE_LENGTH*(GameUtil.MAP_W/2),
                        GameUtil.OFFSET,
                        null);
                break;
            case 1:
                g.drawImage(GameUtil.win,
                        GameUtil.OFFSET+GameUtil.SQUARE_LENGTH*(GameUtil.MAP_W/2),
                        GameUtil.OFFSET,
                        null);
                break;
            case 2:
                g.drawImage(GameUtil.over,
                        GameUtil.OFFSET+GameUtil.SQUARE_LENGTH*(GameUtil.MAP_W/2),
                        GameUtil.OFFSET,
                        null);
                break;
        }
    }
}
