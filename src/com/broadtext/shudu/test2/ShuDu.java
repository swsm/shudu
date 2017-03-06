package com.broadtext.shudu.test2;

public class ShuDu {
    public static void main(String[] args) {
        long a = System.currentTimeMillis();
        //空位的个数
        int n = 0;
        //初始一个数独题目，0代表空位
//        int[][] data = { { 0, 6, 0, 5, 9, 3, 0, 0, 0 }, { 9, 0, 1, 0, 0, 0, 5, 0, 0 }, { 0, 3, 0, 4, 0, 0, 0, 9, 0 },
//                { 1, 0, 8, 0, 2, 0, 0, 0, 4 }, { 4, 0, 0, 3, 0, 9, 0, 0, 1 }, { 2, 0, 0, 0, 1, 0, 6, 0, 9 },
//                { 0, 8, 0, 0, 0, 6, 0, 2, 0 }, { 0, 0, 4, 0, 0, 0, 8, 0, 7 }, { 0, 0, 0, 7, 8, 5, 0, 1, 0 } };
//        int[][] data = { { 0, 0, 0, 0, 0, 0 , 8, 0, 0 }, { 4, 0, 0 , 2, 0, 8, 0, 5, 1 }, { 0, 8, 3, 9, 0, 0, 0, 0, 7 },
//                { 0, 4, 0, 5, 0, 0, 0, 8, 2 }, { 0, 0, 5, 0, 0, 0, 4, 0, 0 }, { 8, 7, 0, 0, 0, 9 , 0, 3, 0 },
//                { 2, 0, 0, 0, 0, 7,  1, 6, 0 }, { 3, 6, 0, 1, 0, 5, 0, 0, 4 }, { 0, 0, 4, 0, 0, 0, 0, 0, 0 } };
        int[][] data = { { 8, 0, 0, 0, 0, 0 , 0, 0, 0 }, { 0, 0, 3 , 6, 0, 0, 0, 0, 0 }, { 0, 7, 0, 0, 9, 0, 2, 0, 0 },
                { 0, 5, 0, 0, 0, 7, 0, 0, 0 }, { 0, 0, 0, 0, 4, 5, 7, 0, 0 }, { 0, 0, 0, 1, 0, 0 , 0, 3, 0 },
                { 0, 0, 1, 0, 0, 0,  0, 6, 8 }, { 0, 0, 8, 5, 0, 0, 0, 1, 0 }, { 0, 9, 0, 0, 0, 0, 4, 0, 0 } };
//        int[][] data = { { 0, 2, 7, 3, 8, 0 , 0, 1, 0 }, { 0, 1, 0 , 0, 0, 6, 7, 3, 5 }, { 0, 0, 0, 0, 0, 0, 0, 2, 9 },
//                { 3, 0, 5, 6, 9, 2, 0, 8, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 6, 0, 1, 7, 4 , 5, 0, 3 },
//                { 6, 4, 0, 0, 0, 0,  0, 0, 0 }, { 9, 5, 1, 8, 0, 0, 0, 7, 0 }, { 0, 8, 0, 0, 6, 5, 3, 4, 0 } };
        //输出原数独，并获取数独中空位个数
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(data[i][j] + "  ");
                if (data[i][j] == 0) {
                    n++;
                }
            }
            System.out.println();
        }
        //当前判断对象的坐标集合
        int[][] point = new int[n][2];
        //当前判断对象可填入数字集合
        int[][] chance = new int[n][9];
        //当前判断对象的可能值集合下标
        int[] onepoint = new int[n];
        check Check = new check();
        int po = 0;
        //获取所有空位的坐标
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (data[i][j] == 0) {
                    point[po][0] = i;
                    point[po][1] = j;
                    po++;
                }
            }
        }
        //用来存储具体空位的可能值的个数
        int[] chancecount = new int[n];
        //暂时通过chance固定长度数组存储可能值，1代表该下标加1可填，否则不可填
        for (int digit = 1; digit <= 9; digit++) {
            for (int i = 0; i < n; i++) {
                if (Check.boolcheck(data, digit, point[i][0], point[i][1])) {
                    chance[i][digit - 1] = 1;
                    chancecount[i]++;
                }
            }
        }
        //借助该数组存储可能值
        int[][] temp = new int[n][];
        //将chance的1和0代表的可能值转化为具体数字
        for (int i = 0; i < n; i++) {
            temp[i] = new int[chancecount[i]];
            int p = 0;
            for (int j = 0; j < 9; j++) {
                if (chance[i][j] == 1) {
                    temp[i][p] = j + 1;
                    p++;
                }
            }
        }
        //可能值的具体表示回赋给chance
        chance = temp;
        //通过循环实现回溯法
        for (int i = 0; i < n; i++) {
            //当前判断位置若退回到不可判断处，则无解
            if (i < 0) {
                System.out.println("无解！！！");
                return;
            }
            //该位置的所有可能值都无法填入该位置，则返回上一个结点
            if (onepoint[i] == chance[i].length) {
                //当前位置设为未赋值
                data[point[i][0]][point[i][1]] = 0;
                //当前结点判断位置归零，以保证下一次查找该处的时候能遍历所有可能值
                onepoint[i] = 0;
                //在上一结点存在的情况下，使上一结点的判断位置向前移
                if (i - 1 >= 0) {
                    onepoint[i - 1]++;
                }
                //使循环中的i回到前一位置，达到返回上一结点的效果
                i = i - 2;
                //跳出此次循环，进入下一次循环，i自减2，所以下次循环的时候进入上一个结点
                continue;
            }
            //该可能值可以填入当前判断位置，则给数据赋值
            if (Check.boolcheck(data, chance[i][onepoint[i]], point[i][0], point[i][1])) {
                data[point[i][0]][point[i][1]] = chance[i][onepoint[i]];
            } else {
                //不可填入时，当前可能值下标向前移动，使i自减，达到重复运行当前结节的效果，
                onepoint[i]++;
                i--;
            }
        }
        //输出结果
        for (int i = 0; i < 9; i++) {
            System.out.println();
            for (int j = 0; j < 9; j++) {
                System.out.print(data[i][j] + "  ");
            }
        }
        System.out.println("\n" + (System.currentTimeMillis() - a)  + "");
    }
}

class check {
    /**
     * 
     * <p>Description: 判断该数字能否填入该位置</p>
     * @param data
     * @param digit
     * @param x
     * @param y
     * @return
     */
    boolean boolcheck(int[][] data, int digit, int x, int y) {
        for (int i = 0; i < 9; i++) {
            //同列遍历对比
            if (data[x][i] == digit && i != y) {
                return false;
            }
            //同行遍历对比
            if (data[i][y] == digit && i != x) {
                return false;
            }
            //同宫遍历对比
            for (int n = x / 3 * 3; n < x / 3 * 3 + 3; n++) {
                for (int m = y / 3 * 3; m < y / 3 * 3 + 3; m++) {
                    if (x == n && y == m)
                        continue;
                    if (data[n][m] == digit) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
