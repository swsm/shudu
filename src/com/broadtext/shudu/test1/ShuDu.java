package com.broadtext.shudu.test1;

public class ShuDu {

    public static int[][] InitialState = new int[10][10];

    //搜索第（ i , j ）位置处可以存储的数字,找到解则返回true，否则返回false
    public boolean getInitialState(int i, int j) {
        if (i > 9 || j > 9)
            return true;

        for (int k = 1; k <= 9; ++k) {
            boolean can = true;
            // can 变量用于记录数字k能否放在 ( i , j ) 处 
            for (int m = 1; m < i; ++m)
                // 检查同一列是否出现过数字k
                if (InitialState[m][j] == k) {
                    can = false;
                    break;
                }
            if (can)
                for (int n = 1; n < j; ++n)
                    if (InitialState[i][n] == k) // 检查同一行是否出现过数字k
                    {
                        can = false;
                        break;
                    }
            if (can) {
                int up1 = (i / 3) * 3 + 3; // (i,j)方格所在的3×3小方格i坐标的上限
                int up2 = (j / 3) * 3 + 3; // (i,j)方格所在的3×3小方格在j坐标的上限

                if (i % 3 == 0) //这是针对特殊情况的处理
                    up1 = i;
                if (j % 3 == 0)
                    up2 = j;

                for (int p = up1 - 2; p <= up1; ++p) /* 检查在3×3的小方格中是否出现了同一个数字 */
                {
                    if (can == false) /* 跳出外层循环 */
                        break;
                    for (int q = up2 - 2; q <= up2; ++q)
                        if (InitialState[p][q] == k) {
                            can = false;
                            break;
                        }
                }
            }
            if (can) {
                InitialState[i][j] = k;
                if (j < 9) {
                    /* 到同一行的下一位置开始搜索 */
                    if (getInitialState(i, j + 1)) {
                        return true;
                    }
                } else {
                    if (i < 9) {
                        /* 到下一行的第一个空格开始搜索 */
                        if (getInitialState(i + 1, 1)) {
                            return true;
                        }
                    } else {
                        /* i >= 9 && j >= 9 , 搜索结束 */
                        return true;
                    }

                }
                /* 关键这一步：找不到解就要回复原状，否则会对下面的搜索造成影响 */
                InitialState[i][j] = 0;
            }
        }
        /* 1到9都尝试过都不行，则返回递归的上一步 */
        return false;
    }

    void start() 
    {
        for( int i = 1  ; i <= 9 ; ++i ) {
            for( int j = 1 ; j <= 9 ; ++j ) {
                InitialState[i][j] = 0 ;
            }
        }

        for( int i = 1 ; i <= 9 ; ++i ) {
            InitialState[1][i] = i ; 
        }
        /* 第一行随机排列产生 */
        shuffle( &( InitialState[1][1]) , &( InitialState[1][10])  ) ; 

        getInitialState( 2 , 1 ) ;  /* 从第二行开始搜索 */
    }

    public static void main(String[] args) {
        ShuDu sd = new ShuDu();
        sd.start();
        for (int i = 1; i <= 9; ++i) {
            for (int j = 1; j <= 9; ++j) {
                System.out.print(InitialState[i][j] + " ");
            }
            System.out.println();
        }
    }

}
