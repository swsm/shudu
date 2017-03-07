package com.broadtext.shudu.test4;

public class Binary {
    public static final int MIN_NUMBER = (1 << 31), MAX_NUMBER = (-1 ^ MIN_NUMBER);

    public static int getLowestOneNumber(int n) {
        return n & (-n);
    }

    public static int getLowestZeroNumber(int n) {
        return getLowestOneNumber(~n);
    }

    public static int getLowestOneCount(int n) {
        return lastZeroBitCount(getLowestOneNumber(n));
    }

    public static int getHighestOneNumber(int n) {   
        if(n < 0) return MIN_NUMBER;   
        else if(n == 0) return -1;   
        n |= (n >>  1);         
        n |= (n >>  2);         
        n |= (n >>  4);         
        n |= (n >>  8);         
        n |= (n >> 16);          
        return n - (n >>> 1);  
    }    public static int getHighestOneCount(int n) {
        return lastZeroBitCount(getHighestOneNumber(n));
    }

    public static int getOneBitCount(int n) {
        if (n == 0)
            return 0;
        n = n - ((n >>> 1) & 0x55555555);
        n = (n & 0x33333333) + ((n >>> 2) & 0x33333333);
        n = (n + (n >>> 4)) & 0x0f0f0f0f;
        n = n + (n >>> 8);
        n = n + (n >>> 16);
        return n & 0x3f;
    }

    public static int reversalLastOneBit(int n) {
        return n & (n - 1);
    }

    public static int reversalLastZeroBit(int n) {
        return n | (n + 1);
    }

    public static int reversalFirstOneBit(int n) {
        return n ^ getHighestOneNumber(n);
    }

    public static int reversalFirstZeroBit(int n) {
        n = ~n ^ MIN_NUMBER;
        n ^= getHighestOneNumber(n);
        return ~n ^ MIN_NUMBER;
    }

    public static boolean isTwoPower(int n) {
        return (n & (n - 1)) == 0;
    }

    public static int expOfTwoPower(int n) {
        if (n <= 0 || !isTwoPower(n))
            return -1;
        return lastZeroBitCount(n);
    }

    public static int lastZeroBitCount(int n) {
        if (n == 0)
            return 32;
        int t = 31, y;
        y = n << 16;
        if (y != 0) {
            t -= 16;
            n = y;
        }
        y = n << 8;
        if (y != 0) {
            t -= 8;
            n = y;
        }
        y = n << 4;
        if (y != 0) {
            t -= 4;
            n = y;
        }
        y = n << 2;
        if (y != 0) {
            t -= 2;
            n = y;
        }
        return t - (n << 1 >>> 31);
    }

    public static int firstZeroBitCount(int n) {
        if (n == 0)
            return 32;
        int t = 1;
        if (n < 0)
            n = -n;
        if (n >> 16 == 0) {
            t += 16;
            n <<= 16;
        }
        if (n >> 24 == 0) {
            t += 8;
            n <<= 8;
        }
        if (n >> 28 == 0) {
            t += 4;
            n <<= 4;
        }
        if (n >> 30 == 0) {
            t += 2;
            n <<= 2;
        }
        return t;
    }
}
