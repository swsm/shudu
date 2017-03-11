package com.broadtext.game.sudoku.finial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DLX {
    private static final int ROW = 4096 + 50;
    private static final int COL = 1024 + 50;
    private static final int N = 4 * 9 * 9;
    private static final int m = 3;
    private static final int n = 9;

    DLXNode row[] = new DLXNode[ROW];
    DLXNode col[] = new DLXNode[COL];
    DLXNode head;

    private int num = 2;
    private int size[] = new int[COL];
    int data[][] = new int[9][9];
    List<int[][]> solutions;
    //问题空位置
    int[] emptyPosition = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1 };
    //确定列数
    int[] ensureCol = new int[324];
    
    int aa = 0;

    public DLX(int r, int c) {
        head = new DLXNode(r, c);
        head.U = head.D = head.L = head.R = head;
        for (int i = 0; i < c; ++i) {
            col[i] = new DLXNode(r, i);
            col[i].R = head;
            col[i].L = head.L;
            col[i].R.L = col[i].L.R = col[i];
            col[i].U = col[i].D = col[i];
            size[i] = 0;
        }

        for (int i = r - 1; i > -1; --i) {
            row[i] = new DLXNode(i, c);
            row[i].U = head;
            row[i].D = head.D;
            row[i].U.D = row[i].D.U = row[i];
            row[i].L = row[i].R = row[i];
        }
    }

    public void addNode(int r, int c) {
        DLXNode p = new DLXNode(r, c);
        p.R = row[r];
        p.L = row[r].L;
        p.L.R = p.R.L = p;
        p.U = col[c];
        p.D = col[c].D;
        p.U.D = p.D.U = p;
        ++size[c];
        aa++;
    }

    public void addNode(int i, int j, int k, int flag) {
        if (flag == 0) {
            //确定值
            ensureCol[i * n + k - 1] = 1;
            ensureCol[n * n + j * n + k - 1] = 1;
            ensureCol[2 * n * n + block(i, j) * n + k - 1] = 1;
            ensureCol[3 * n * n + i * n + j] = 1;
        } else {
            if (ensureCol[i * n + k - 1] == 1 || ensureCol[n * n + j * n + k - 1] == 1
                    || ensureCol[2 * n * n + block(i, j) * n + k - 1] == 1 || ensureCol[3 * n * n + i * n + j] == 1) {
                return;
            }
        }
        //计算所在行数
        int r = (i * n + j) * n + k;
        //创建每个元素只能是1-9数字的限制条件对应的node
        addNode(r, i * n + k - 1);
        //创建每行元素1-9不能重复的限制条件对应的node
        addNode(r, n * n + j * n + k - 1);
        //创建每列元素1-9不能重复的限制条件对应的node
        addNode(r, 2 * n * n + block(i, j) * n + k - 1);
        //创建每单元格元素1-9不能重复的限制条件对应的node
        addNode(r, 3 * n * n + i * n + j);
    }

    int block(int x, int y) {
        return x / m * m + y / m;
    }

    /**
     * 
     * <p>
     * Description: 删除选中列的所有元素(左右节点脱离关系)，删除所选元素所在行的所有元素的上下元素(上下节点脱离关系)
     * </p>
     * 
     * @param c
     */
    public void cover(int c) {
        if (c == N)
            return;

        col[c].delLR();
        DLXNode R, C;
        for (C = col[c].D; C != col[c]; C = C.D) {
            if (C.c == N)
                continue;
            for (R = C.L; R != C; R = R.L) {
                if (R.c == N)
                    continue;
                --size[R.c];
                R.delUD();
            }
            C.delLR();
        }
    }

    public void resume(int c) {
        if (c == N)
            return;

        DLXNode R, C;
        for (C = col[c].U; C != col[c]; C = C.U) {
            if (C.c == N)
                continue;
            C.resumeLR();
            for (R = C.R; R != C; R = R.R) {
                if (R.c == N)
                    continue;
                ++size[R.c];
                R.resumeUD();
            }
        }
        col[c].resumeLR();
    }

    public boolean solve(int depth) {
        if (head.L == head) {
            if (this.num == 1) {
                //只求1解
                solutions.add(this.data);
            } else {
                //求多解
                int solution[][] = new int[n][n];
                for (int i = 0; i < n; ++i)
                    for (int j = 0; j < n; ++j)
                        solution[i][j] = data[i][j];
                solutions.add(solution);
            }

            if (solutions.size() == num)
                return true;
            return false;
        }
        int minSize = 1 << 30;
        int c = -1;
        DLXNode p;
        //取一列中 每列对应size的最小值 
        for (p = head.L; p != head; p = p.L)
            if (size[p.c] < minSize) {
                minSize = size[p.c];
                c = p.c;
            }
        cover(c);

        for (p = col[c].D; p != col[c]; p = p.D) {
            DLXNode cell;
            p.R.L = p;
            for (cell = p.L; cell != p; cell = cell.L) {
                cover(cell.c);
            }
            p.R.L = p.L;
            int rr = p.r - 1;
            data[rr / (n * n)][rr / n % n] = rr % n + 1;
            if (solve(depth + 1))
                return true;

            p.L.R = p;
            for (cell = p.R; cell != p; cell = cell.R)
                resume(cell.c);
            p.L.R = p.R;
        }

        resume(c);
        return false;
    }

    public boolean solve(String question) {
        System.out.println("--开始设置空位点-" + System.currentTimeMillis());
        this.setEmptyPosition(question);
        System.out.println("--空位点设置完毕，开始转数组---" + System.currentTimeMillis());
        this.setDataArray(question);
        System.out.println("--数组转换完毕，开始建点---" + System.currentTimeMillis());
        init(data);
        System.out.println("--建点完毕，开始求解---" + System.currentTimeMillis());
        return solve(0);
    }

    public void setEmptyPosition(String question) {
        int j = 0;
        for (int i = -1; i <= question.lastIndexOf("?"); ++i) {
            i = question.indexOf("?", i);
            emptyPosition[j++] = i;
        }
    }

    /**
     * 
     * <p>
     * Description: 将数度字符串转换成数度数组
     * </p>
     * 
     * @param question 字符串
     * @return 数组
     */
    public void setDataArray(String question) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.data[i][j] = (question.charAt(i * 9 + j) == '?' ? 0 : Integer.parseInt(String.valueOf(question
                        .charAt(i * 9 + j))));
            }
        }
    }

    public void init(int data[][]) {
        solutions = new ArrayList<int[][]>();
        int i, j, k;
        for (i = 0; i < n; ++i)
            for (j = 0; j < n; ++j) {
                if (data[i][j] > 0) {
                    addNode(i, j, data[i][j], 0);
                } else {
                    for (k = 1; k <= n; ++k) {
                        addNode(i, j, k, 1);
                    }
                }
            }
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public List<String> getSolutions() {
        if (solutions.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> list = new ArrayList<String>();
        for (int[][] answers : solutions) {
            String answer = this.changeArrayToString(answers);
            answer = this.getOnlyAnswer(answer);
            list.add(answer);
        }
        return list;
    }

    private String getOnlyAnswer(String answer) {
        String onlyAnswer = "";
        for (int i = 0; i < 81; i++) {
            if (this.emptyPosition[i] == -1) {
                return onlyAnswer;
            }
            onlyAnswer += answer.charAt(this.emptyPosition[i]);
        }
        return onlyAnswer;
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
        String answer;
        answer = new String();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                answer += String.valueOf(array[i][j]);
            }
        }
        return answer;
    }
}

class DLXNode {
    int r, c;
    DLXNode U, D, L, R;

    DLXNode() {
        r = c = 0;
    }

    DLXNode(int r, int c) {
        this.r = r;
        this.c = c;
    }

    DLXNode(int r, int c, DLXNode U, DLXNode D, DLXNode L, DLXNode R) {
        this.r = r;
        this.c = c;
        this.U = U;
        this.D = D;
        this.L = L;
        this.R = R;
    }

    public void delLR() {
        L.R = R;
        R.L = L;
    }

    public void delUD() {
        U.D = D;
        D.U = U;
    }

    public void resumeLR() {
        L.R = R.L = this;
    }

    public void resumeUD() {
        U.D = D.U = this;
    }
}
