package com.broadtext.game.sudoku.finial;

import java.awt.Point;
import java.util.Random;

public class CreateSudoku {
    //初始化生成数独(利用a生成数独然后通过交换数字位置生成新的数独再去除数字生成新数独问题)
    private int[][] a = { { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 } };
    //储存答案的数独
    private int[][] result;
    //对应难度下最小的已知格子数
    private int MINFILLED;
    //对应难度下行列已知格的底线
    private int MINKNOW;
    //存储问题占位符位置
    private Point[] tempPoint;
    
    /**
     * 
     * <p>
     * Description: 生成一个指定等级的数独，且只有唯一解
     * </p>
     * 
     * @param n 难度等级 1-5对应初级，低级，中级，高级，骨灰级
     */
    public void getSudoku(int n) {
        Random rand = new Random();
      //初始当前挖洞位置
        int p = rand.nextInt(9), q = rand.nextInt(9); 
      //保存初始挖洞位置
        int P = p, Q = q; 
      //下一个挖洞位置
        Point next = new Point(); 
      //当前已知格子数
        int filled = 81; 
      //生成的数独有解（99.7%有解）
        if (solveSudoku(a)) { 
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                  //将终盘赋值给a[][]
                    a[i][j] = result[i][j]; 
                }
            }
        } else {
            System.exit(0);
        }
      //等效变换
        a = equalChange(a); 
      //初始化MINFILLED和MINKNOW
        setLevel(n); 
        do {
          //此洞可挖
            if (isOnlyOne(p, q, a) && minKnow(p, q, a) >= MINKNOW) { 
                a[p][q] = 0;
                filled--;
            }
            next = findNext(p, q, n);
            p = next.x;
            q = next.y;
            if (n == 1 || n == 2) {
                while (p == P && q == Q) {
                    next = findNext(p, q, n);
                    p = next.x;
                    q = next.y;
                }
            }
        } while (filled > MINFILLED && (P != p || Q != q));
    }
    
    /**
     * 
     * <p>
     * Description: 判断数独b是否能解
     * </p>
     * 
     * @param b 需要求解的数独
     * @return 是否能求解
     */
    public boolean solveSudoku(int[][] b) {
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
        tempPoint = fill;
        if (test(temp) == 0) {
            return false;
        } else if (k == 0 && test(temp) == 1) { //玩家填满格子，并且正确，再来求答案
            return true;
        } else if (put(temp, 0, fill)) {
            result = output(temp);
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 
     * <p>
     * Description: 交换数独b中的数字生成新数独（等效变换的过程）
     * </p>
     * 
     * @param b 原有数独
     * @return 新数独
     */
    public int[][] equalChange(int[][] b) {
        Random rand = new Random();
        int num1 = 1 + rand.nextInt(9); //将所有的1与num1互换
        int num2 = 1 + rand.nextInt(9); //将所有的2与num2互换
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (b[i][j] == 1) {
                    b[i][j] = num1;
                } else if (b[i][j] == num1) {
                    b[i][j] = 1;
                }

                if (b[i][j] == 2) {
                    b[i][j] = num2;
                } else if (b[i][j] == num2) {
                    b[i][j] = 2;
                }
            }
        }
        return b;
    }
    
    /**
     * 
     * <p>
     * Description: 设置难度等级
     * </p>
     * 
     * @param n 1-5对应初级，低级，中级，高级，骨灰级
     */
    public void setLevel(int n) {
        Random rand = new Random();
        switch (n) { //初始化MINFILLED和MINKNOW
        case 1:
            MINFILLED = 50 + rand.nextInt(5);
            MINKNOW = 5;
            break;
        case 2:
            MINFILLED = 45 + rand.nextInt(5);
            MINKNOW = 4;
            break;
        case 3:
            MINFILLED = 31 + rand.nextInt(10);
            MINKNOW = 3;
            break;
        case 4:
            MINFILLED = 21 + rand.nextInt(10);
            MINKNOW = 2;
            break;
        case 5:
            MINFILLED = 17 + rand.nextInt(10);
            MINKNOW = 0;
            break;
        }
    }
   
    /**
     * 
     * <p>
     * Description: 判断在i,j挖去数字后是否有唯一解
     * </p>
     * 
     * @param i 当前挖洞的横坐标
     * @param j 当前挖洞的纵坐标
     * @param b 判断的数独
     * @return 判断结果 只有唯一解则返回true
     */
    public boolean isOnlyOne(int i, int j, int[][] b) {
      //待挖洞的原始数字
        int k = b[i][j]; 
        for (int num = 1; num < 10; num++) {
            b[i][j] = num;
          //除待挖的数字之外，还有其他的解，则返回false
            if (num != k && solveSudoku(b)) { 
                b[i][j] = k;
                return false;
            }
        }
        b[i][j] = k;
        return true;
    }

    /**
     * 
     * <p>
     * Description: 返回若将p q挖去后行列中已知格数的底限
     * </p>
     * 
     * @param p 当前挖洞的横坐标
     * @param q 当前挖洞的纵坐标
     * @param b 当前的数独
     * @return 已知格数的底限
     */
    public static int minKnow(int p, int q, int[][] b) {
        int temp = b[p][q];
        int minknow = 9;
        int tempknow = 9;
        b[p][q] = 0;
        for (int i = 0; i < 9; i++) { //搜索行最小已知
            for (int j = 0; j < 9; j++) {
                if (b[i][j] == 0) {
                    tempknow--;
                    if (tempknow < minknow) {
                        minknow = tempknow;
                    }
                }
            }
            tempknow = 9;
        }
        tempknow = 9;
        for (int j = 0; j < 9; j++) { //搜索列最小已知
            for (int i = 0; i < 9; i++) {
                if (b[i][j] == 0) {
                    tempknow--;
                    if (tempknow < minknow) {
                        minknow = tempknow;
                    }
                }
            }
            tempknow = 9;
        }
        b[p][q] = temp;
        return minknow;
    }
    
    /**
     * 
     * <p>
     * Description: 找到下一个挖洞的位置
     * </p>
     * 
     * @param i 当前挖洞的横坐标
     * @param j 当前挖洞的纵坐标
     * @param n 难度
     * @return 下一个要挖的洞的位置
     */
    public Point findNext(int i, int j, int n) {
        Random rand = new Random();
        Point next = new Point();
        switch (n) {
        case 1: //难度1随机
        case 2:
            next.x = rand.nextInt(9);
            next.y = rand.nextInt(9);
            break; //难度2随机
        case 3: //难度3间隔
            if (i == 8 && j == 7) {
                next.x = 0;
                next.y = 0;
            } else if (i == 8 && j == 8) {
                next.x = 0;
                next.y = 1;
            } else if ((i % 2 == 0 && j == 7) || (i % 2 == 1) && j == 0) {
                next.x = i + 1;
                next.y = j + 1;
            } else if ((i % 2 == 0 && j == 8) || (i % 2 == 1) && j == 1) {
                next.x = i + 1;
                next.y = j - 1;
            } else if (i % 2 == 0) {
                next.x = i;
                next.y = j + 2;
            } else if (i % 2 == 1) {
                next.x = i;
                next.y = j - 2;
            }
            break;
        case 4: //难度4蛇形
            if (i == 8 && j == 8) {
                next.y = 0;
            } else if (i % 2 == 0 && j < 8) { //蛇形顺序，对下个位置列的求解
                next.y = j + 1;
            } else if ((i % 2 == 0 && j == 8) || (i % 2 == 1 && j == 0)) {
                next.y = j;
            } else if (i % 2 == 1 && j > 0) {
                next.y = j - 1;
            }

            if (i == 8 && j == 8) { //蛇形顺序，对下个位置行的求解
                next.x = 0;
            } else if ((i % 2 == 0 && j == 8) || (i % 2 == 1) && j == 0) {
                next.x = i + 1;
            } else {
                next.x = i;
            }
            break;
        case 5: //难度5从左至右，自顶向下
            if (j == 8) {
                if (i == 8) {
                    next.x = 0;
                } else {
                    next.x = i + 1;
                }
                next.y = 0;
            } else {
                next.x = i;
                next.y = j + 1;
            }
            break;
        }
        return next;
    }
    
    /**
     * 
     * <p>
     * Description: 判断当前数独是否正确
     * </p>
     * 
     * @param b 当前数独
     * @return 判断结果， 1正确，0错误
     */
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
    
    /**
     * 
     * <p>
     * Description: 检验位置i,j 放入数字num后是否可行（每行每列每宫是否1-9）
     * </p>
     * 
     * @param b 数独
     * @param i 横坐标位置
     * @param j 纵坐标位置
     * @return 可行返回1
     */
    public static int test(int[][] b, int i, int j) {
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

    /**
     * 
     * <p>
     * Description: 在第n个空位子放入数字
     * </p>
     * 
     * @param b 当前数独
     * @param n 第n个空位子
     * @param fill 存放空位子的Point数组
     * @return 放入结果
     */
    public static boolean put(int b[][], int n, Point fill[]) {
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

    /**
     * 
     * <p>
     * Description: 输出结果
     * </p>
     * 
     * @param b 数独b
     * @return 结果数独
     */
    public static int[][] output(int[][] b) {
        return b;
    }


    /**
     * 
     * <p>
     * Description: 返回生成的数独
     * </p>
     * 
     * @return 生成的数独 例如："3??8?52??71......3?94??"
     */
    public String getQuestion() {
        return changeArrayToString(a);
    }
    
    /**
     * 
     * <p>
     * Description: 将数组转换为字符串
     * </p>
     * 
     * @param array 数组
     * @return 转换后的字符串
     */
    public String changeArrayToString(int[][] array) {
        String question;
        question = new String();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                question += String.valueOf(array[i][j]);
            }
        }
        return question.replaceAll("0", "?");
    }
}
