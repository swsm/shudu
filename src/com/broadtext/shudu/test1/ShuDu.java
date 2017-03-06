package com.broadtext.shudu.test1;
import java.awt.List;
import java.util.ArrayList;
import java.util.Random;

public class ShuDu {

    /**
     * <p>
     * 打印二维数组，数独矩阵
     * </p>
     */
    public static void printArray(int a[][]) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(" " + a[i][j]);
                if (0 == ((j + 1) % 3)) {
                    System.out.print(" ");
                }
            }
            System.out.println();
            if (0 == ((i + 1) % 3)) {
                System.out.println();
            }
        }
    }

    /**
     * <p>
     * 产生一个1-9的不重复长度为9的一维数组
     * </p>
     */
    public static ArrayList<Integer> creatNineRondomArray() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        Random random = new Random();
        for (int i = 0; i < 9; i++) {
            int randomNum = random.nextInt(9) + 1;
            while (true) {
                if (!list.contains(randomNum)) {
                    list.add(randomNum);
                    break;
                }
                randomNum = random.nextInt(9) + 1;
            }

        }
        System.out.println("生成的一维数组为：");
        for (Integer integer : list) {
            System.out.print(" " + integer.toString());
        }
        System.out.println();
        return list;
    }

    /**
     * <p>
     * 通过一维数组和原数组生成随机的数独矩阵
     * </p>
     * <p>
     * 遍历二维数组里的数据，在一维数组找到当前值的位置，并把一维数组 当前位置加一处位置的值赋到当前二维数组中。目的就是将一维数组为
     * 依据，按照随机产生的顺序，将这个9个数据进行循环交换，生成一个随 机的数独矩阵。
     * </p>
     */
    public static void creatSudokuArray(int[][] seedArray, ArrayList<Integer> randomList) {
        int flag = 1;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                for (int k = 0; k < 9; k++) {
                    if (seedArray[i][j] == randomList.get(k)) {
                        seedArray[i][j] = randomList.get((k + 1) % 9);
                        break;
                    }
                }
            }
        }
        System.out.println("处理后的数组");
        ShuDu.printArray(seedArray);
    }

    //  
    public static void main(String[] args) {
        int seedArray[][] = { { 9, 7, 8, 3, 1, 2, 6, 4, 5 }, { 3, 1, 2, 6, 4, 5, 9, 7, 8 },
                { 6, 4, 5, 9, 7, 8, 3, 1, 2 }, { 7, 8, 9, 1, 2, 3, 4, 5, 6 }, { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
                { 4, 5, 6, 7, 8, 9, 1, 2, 3 }, { 8, 9, 7, 2, 3, 1, 5, 6, 4 }, { 2, 3, 1, 5, 6, 4, 8, 9, 7 },
                { 5, 6, 4, 8, 9, 7, 2, 3, 1 } };
        System.out.println("原始的二维数组:");
        ShuDu.printArray(seedArray);
        ArrayList<Integer> randomList = ShuDu.creatNineRondomArray();
        ShuDu.creatSudokuArray(seedArray, randomList);
    }
}
