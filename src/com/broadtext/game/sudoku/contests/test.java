package com.broadtext.game.sudoku.contests;

import java.util.List;

public class test {
    public static void main(String[] args) {
        List<String>  list;
        MySudoku sudu;
        sudu = new MySudoku();
        list = sudu.getQuestions();
        long a = System.currentTimeMillis();
        for (String s : list) {
            System.out.println(s);
            sudu.getAnswers(s);
        }
        //sudu.getAnswers("8??????????36??????7??9?2???5???7???????457?????1???3???1????68??85???1??9????4??");
        System.out.println("******" + (System.currentTimeMillis() - a));
    }
    
}
