package com.broadtext.shudu.test2;

public class ShuDu {
    public static void main(String[] args) {
        int n = 0;//空位的个数
        int[][] data = {
                { 0, 6, 0, 5, 9, 3, 0, 0, 0 }, //初始一个数独题目，0代表空位
                { 9, 0, 1, 0, 0, 0, 5, 0, 0 }, { 0, 3, 0, 4, 0, 0, 0, 9, 0 }, { 1, 0, 8, 0, 2, 0, 0, 0, 4 },
                { 4, 0, 0, 3, 0, 9, 0, 0, 1 }, { 2, 0, 0, 0, 1, 0, 6, 0, 9 }, { 0, 8, 0, 0, 0, 6, 0, 2, 0 },
                { 0, 0, 4, 0, 0, 0, 8, 0, 7 }, { 0, 0, 0, 7, 8, 5, 0, 1, 0 } };
        for (int i = 0; i < 9; i++) //输出原数独，并获取数独中空位个数
        {
            for (int j = 0; j < 9; j++) {
                System.out.print(data[i][j] + " ");
                if (data[i][j] == 0) {
                    n++;
                }
            }
            System.out.println();
        }
        int[][] point = new int[n][2]; //当前判断对象的坐标集合
        int[][] chance = new int[n][9]; //当前判断对象可填入数字集合
        int[] onepoint = new int[n]; //当前判断对象的可能值集合下标
        check Check = new check();
        int po = 0;
        for (int i = 0; i < 9; i++) //获取所有空位的坐标
        {
            for (int j = 0; j < 9; j++) {
                if (data[i][j] == 0) {
                    point[po][0] = i;
                    point[po][1] = j;
                    po++;
                }
            }
        }
        int[] chancecount = new int[n]; //用来存储具体空位的可能值的个数
        for (int digit = 1; digit <= 9; digit++) //暂时通过chance固定长度数组存储可能值，1代表该下标加1可填，否则不可填
        {
            for (int i = 0; i < n; i++) {
                if (Check.boolcheck(data, digit, point[i][0], point[i][1])) {
                    chance[i][digit - 1] = 1;
                    chancecount[i]++;
                }
            }
        }
        int[][] temp = new int[n][]; //借助该数组存储可能值
        for (int i = 0; i < n; i++) //将chance的1和0代表的可能值转化为具体数字
        {
            temp[i] = new int[chancecount[i]];
            int p = 0;
            for (int j = 0; j < 9; j++) {
                if (chance[i][j] == 1) {
                    temp[i][p] = j + 1;
                    p++;
                }
            }
        }
        chance = temp; //可能值的具体表示回赋给chance
        for (int i = 0; i < n; i++) //通过循环实现回溯法
        {
            if (i < 0) //当前判断位置若退回到不可判断处，则无解
            {
                System.out.println("无解！！！");
                return;
            }
            if (onepoint[i] == chance[i].length) //该位置的所有可能值都无法填入该位置，则返回上一个结点
            {
                data[point[i][0]][point[i][1]] = 0; //当前位置设为未赋值
                onepoint[i] = 0; //当前结点判断位置归零，以保证下一次查找该处的时候能遍历所有可能值
                if (i - 1 >= 0) //在上一结点存在的情况下，使上一结点的判断位置向前移
                    onepoint[i - 1]++;
                i = i - 2; //使循环中的i回到前一位置，达到返回上一结点的效果
                continue; //跳出此次循环，进入下一次循环，i自减2，所以下次循环的时候进入上一个结点
            }
            if (Check.boolcheck(data, chance[i][onepoint[i]], point[i][0], point[i][1])) //该可能值可以填入当前判断位置，则给数据赋值
            {
                data[point[i][0]][point[i][1]] = chance[i][onepoint[i]];
            } else //不可填入时，当前可能值下标向前移动，使i自减，达到重复运行当前结节的效果，
            {
                onepoint[i]++;
                i--;
            }
        }
        for (int i = 0; i < 9; i++) //输出结果
        {
            System.out.println();
            for (int j = 0; j < 9; j++) {
                System.out.print(data[i][j] + " ");
            }
        }
    }
}

class check //判断该数字能否填入该位置
{
    boolean boolcheck(int[][] data, int digit, int x, int y) {
        for (int i = 0; i < 9; i++) {
            if (data[x][i] == digit && i != y) //同列遍历对比
                return false;
            if (data[i][y] == digit && i != x) //同行遍历对比
                return false;
            for (int n = x / 3 * 3; n < x / 3 * 3 + 3; n++) //同宫遍历对比
            {
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
