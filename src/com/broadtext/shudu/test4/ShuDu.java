package com.broadtext.shudu.test4;

/**
 * <p>ClassName: ShuDu</p>
 * <p>Description: 位运算求数独</p>
 * <p>Author: Administrator</p>
 * <p>Date: 2017-3-7</p>
 */
public class ShuDu {  
    
    public static void main (String[] args) {
        long s = System.currentTimeMillis();   
        //String str =  "000000000000000000000000000000000000000000000000000000000000000000000000000000000";
        String str =  "800000000003600000070090200050007000000045700000100030001000068008500010090000400";
        //String str =  "800000000007500009030000180060001050009040000000750000002070004000003610000000800";
        load(str);   
        System.out.println(System.currentTimeMillis() - s);   //System.gc();  
    }   
    private static int[] row = new int[9], col = new int[9];  
    private static int[][] square = new int[3][3], cell = new int[9][9];  
    private static int[] x = new int[81], y = new int[81];  
    private static final int[] trailZero = new int[257]; 
    private static int n = 0;  
    private static final int max = 0x1FF;  
    static {   
        //储存1到256每一个数的二进制最后有多少个0
        for(int i = 1; i < 257; i++) {
            trailZero[i] = Binary.lastZeroBitCount(i) + 1;  
        }
    }
    public static void load(String input) {
       int[][] _cell = new int[9][9];
       for(int i = 0; i < input.length(); i++) {
           char c = input.charAt(i);
           _cell[i/9][i%9] = (c&Integer.MAX_VALUE) - 48;
       }
       load(_cell);
    }
    public static void load(int[][]input) {
        java.util.Arrays.fill(x, -1);
        java.util.Arrays.fill(y, -1);
        int t[] = new int[81], i, j;
        for(i=0;i<9;i++) {
            for(j=0;j<9;j++) {
                System.out.print(input[i][j]+"");
                cell[i][j]=input[i][j];
                if(input[i][j]==0) {
                    //记录空格
                    x[n]=i;
                    y[n++]=j;
                    continue;
             }
             //非空方格
             int tmp = 1<<input[i][j]>>1;
             //这一格的二进制表示数
             row[i] |= tmp;
             //储存可能值的数组添加已经填好的方格
             col[j] |= tmp;
             square[i/3][j/3] |= tmp;
             }
             System.out.println();
        }
        //得到与这一个空格有联系的行、列和九宫格的非空数字的个数
        for(i = 0; i < n; i++) {
            t[i] = Binary.getOneBitCount(row[x[i]]) + Binary.getOneBitCount(col[y[i]]) + Binary.getOneBitCount(square[x[i] / 3][y[i] / 3]);
        }
        //排序，使得这个空格的行、列和九宫格的非空数字的个数大的优先计算
        for(i = 0; i < n; i++) {
            for(j = i + 1; j < n; j++) {
                if (t[i] < t[j]) {      
                    int s = t[i]; 
                    t[i] = t[j]; 
                    t[j] = s;       
                    s = x[i]; 
                    x[i] = x[j]; 
                    x[j] = s;       
                    s = y[i]; 
                    y[i] = y[j]; 
                    y[j] = s;     
                }   
                System.out.println();   
                boolean jud = calc(0); 
                //calculate the cross word   i
                //have no answer 
                if (!jud)  {
                    System.out.print("No Answer");
                }
            }
        }
    }
    
    /**   
     *  这个程序的主要思想是用位来储存填过的数字。   
     *  如row[4] = 39 = 二进制的000100111，代表第5行填过了6、3、2和1，这几个数是不能填的   
     *  哪一位（从右向左）是1，就代表哪个数已经填过了，不能再填。   
     *  则row[x] | col[y] | square[x / 3][y / 3]其实就是综合哪些位是1，也就是哪些   
     *  数不能填。getLowestOneNumber()是得到最后一个1所在位置，如12 = 二进制的1100  
     *  则getLowestOneNumber(12)返回二进制的100，也就是4.   
     *  lastZeroBitCount()是返回这一个数的最后有多少个0位.lastZeroBitCount(12) = 2   *
     **/  
     private static boolean calc(int pos) {   
         if(pos == n){
             //穷举到极限，得到答案    
             for(int[] i : cell) {
                 //output     
                 for (int j : i) {
                     System.out.print(j + " ");     
                 }
                 System.out.println();    
             }    
             return true;
             //true means have the answer now   
             }   
         int a = x[pos], b = y[pos], a1 = a / 3, b1 = b / 3;
         //获取下标   
         int and = row[a] | col[b] | square[a1][b1];
         //得到所有已经填过的数字   
         if(and == max) {return false;}
         //当and到max时，也就是在这一行，这一列以及这一个九宫格中1到9的数字都填过了，则返回0   
         boolean state = false;//储存下层状态，false代表没有结果，true代表找到结果，不用再运算了   //^运算有一个特性：(a^b)^b=a，用^运算可以赋值递归计算后再还原   
             while(and < max) {
                 //穷举    
                 int tmp = Binary.getLowestOneNumber(~and);
                 //得到可能值    
                 row[a] ^= tmp;
                 //赋值储存可能值的数组为当前可能值    
                 col[b] ^= tmp;    
                 square[a1][b1] ^= tmp;    
                 int past = cell[a][b];
                 //得到cell数组的原来值，为了递归计算之后还原以便下次重新计算    
                 cell[a][b] = trailZero[tmp];
                 //将cell[a][b]赋值为可能值    
                 state = calc(pos + 1);
                 //得到下一层计算的状态   
                 if(state) break;//得到结果，跳出循环    
                 cell[a][b] = past;//还原cell数组    
                 row[a] ^= tmp;//还原储存可能值的数组    
                 col[b] ^= tmp;
                 square[a1][b1] ^= tmp;    
                 and |= tmp;//and加上可能值，为了下一次的搜索不重复   
             }    
             return state;//返回此层状态回上一层
     }
    
}  
