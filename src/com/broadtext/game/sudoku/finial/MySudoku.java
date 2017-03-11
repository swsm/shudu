package com.broadtext.game.sudoku.finial;

import java.util.ArrayList;
import java.util.List;

public class MySudoku implements Sudoku{
    public static String TEAMNAME = "cooool";
     
    public static int QUESTIONSSIZE = 4;

    public static int LEVEL = 5;
    
    @Override
    public String getTeamName() {
        return TEAMNAME;
    }

    @Override
    public List<String> getQuestions() {
        List<String> questions;
        questions = new ArrayList<String>();
        CreateSudoku createSudoKu = new CreateSudoku();
        for (int i = 0; i < QUESTIONSSIZE; i ++) {
            createSudoKu.getSudoku(LEVEL);
            questions.add(createSudoKu.getQuestion());
        }
        return questions;
    }

    @Override
    public List<String> getAnswers(String question) {
        List<String> solutions;
        solutions = new ArrayList<String>();
        DLX dlx = new DLX(9 * 9 * 9 + 1, 4 * 9 * 9);
        dlx.setNum(1);
        dlx.solve(question);
        solutions = dlx.getSolutions();
        return solutions;
    }
    
}
