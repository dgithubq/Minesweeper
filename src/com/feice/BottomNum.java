package com.feice;

/**
 * 底层数字类
 */
public class BottomNum {
    void newNum() {
        for (int i = 1; i <= GameUtil.MAP_W; i++) {
            for (int j = 1; j <= GameUtil.MAP_H; j++) {
                if (GameUtil.DATA_BOTTOM[i][j] != -1) {
                    continue;
                }
                for (int k = i - 1; k <= i + 1; k++) {
                    for (int l = j - 1; l <= j + 1; l++) {
                        if (GameUtil.DATA_BOTTOM[k][l] >= 0&&GameUtil.DATA_BOTTOM[k][l] <8) {//防止越界,数字最多到8
                            GameUtil.DATA_BOTTOM[k][l]++;
                        }
                    }
                }
            }

        }
    }
}
