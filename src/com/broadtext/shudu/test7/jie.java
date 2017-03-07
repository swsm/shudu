package com.broadtext.shudu.test7;

import java.awt.Point;

public class jie {
    public static boolean SolveSudoku(int[][] b) { //判断数独b是否能解
        int cout = 0;
        int[][] temp = new int[9][9]; //复制数独b
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                temp[i][j] = b[i][j];
                if (temp[i][j] == 0) {
                    cout++;
                }
            }
        }
        Point[] fill = new Point[cout]; //储存空位子的横纵坐标
        int k = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (temp[i][j] == 0) {
                    fill[k] = new Point(i, j);
                    k++; //空位子数目
                }
            }
        }
        if (test(temp) == 0) {
            return false;
        } else if (k == 0 && test(temp) == 1) { //玩家填满格子，并且正确，再来求答案
            System.out.println("答案正确");
            return true;
        } else if (put(temp, 0, fill)) {
            //输出结果
            for (int i = 0; i < 9; i++) {
                System.out.println();
                for (int j = 0; j < 9; j++) {
                    System.out.print(temp[i][j] + "  ");
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean put(int b[][], int n, Point fill[]) { //在第n个空位子放入数字
        if (n < fill.length) {
            for (int i = 1; i < 10; i++) {
                b[fill[n].x][fill[n].y] = i;
                if (test(b, fill[n].x, fill[n].y) == 1 && put(b, n + 1, fill)) {
                    return true;
                }
            }
            b[fill[n].x][fill[n].y] = 0;
            return false;
        } else {
            return true;
        }
    }

    public static int test(int[][] b) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (b[i][j] != 0 && test(b, i, j) == 0) {
                    return 0;
                }
            }
        }
        return 1;
    }

    public static int test(int[][] b, int i, int j) { //检验位置i,j 数字num是否可行
        int m = 0, n = 0, p = 0, q = 0; //m,n是计数器，p,q用于确定test点的方格位置
        for (m = 0; m < 9; m++) {
            if (m != i && b[m][j] == b[i][j]) {
                return 0;
            }
        }
        for (n = 0; n < 9; n++) {
            if (n != j && b[i][n] == b[i][j]) {
                return 0;
            }
        }
        for (p = i / 3 * 3, m = 0; m < 3; m++) {
            for (q = j / 3 * 3, n = 0; n < 3; n++) {
                if ((p + m != i || q + n != j) && (b[p + m][q + n] == b[i][j])) {
                    return 0;
                }
            }
        }
        return 1;
    }

    public static void main(String[] args) {
        long a = System.currentTimeMillis();
        //        int[][] data = { { 0, 6, 0, 5, 9, 3, 0, 0, 0 }, { 9, 0, 1, 0, 0, 0, 5, 0, 0 }, { 0, 3, 0, 4, 0, 0, 0, 9, 0 },
        //              { 1, 0, 8, 0, 2, 0, 0, 0, 4 }, { 4, 0, 0, 3, 0, 9, 0, 0, 1 }, { 2, 0, 0, 0, 1, 0, 6, 0, 9 },
        //              { 0, 8, 0, 0, 0, 6, 0, 2, 0 }, { 0, 0, 4, 0, 0, 0, 8, 0, 7 }, { 0, 0, 0, 7, 8, 5, 0, 1, 0 } };
        int[][] data = { { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 6, 9 }, { 0, 0, 0, 0, 0, 0, 3, 0, 7 },
                { 0, 6, 0, 0, 0, 9, 0, 0, 8 }, { 0, 0, 5, 0, 0, 4, 0, 7, 0 }, { 0, 0, 7, 1, 0, 8, 0, 4, 3 },
                { 0, 5, 0, 0, 9, 0, 0, 0, 4 }, { 0, 2, 3, 0, 0, 5, 0, 0, 0 }, { 0, 4, 1, 7, 0, 3, 6, 9, 0 } };

        SolveSudoku(data);
        System.out.println("\n" + (System.currentTimeMillis() - a) + "");
    }

}
