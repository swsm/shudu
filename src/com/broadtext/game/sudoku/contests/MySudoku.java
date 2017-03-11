package com.broadtext.game.sudoku.contests;

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
        Game game;
        for (int i = 0; i < QUESTIONSSIZE; i ++) {
            game = new Game();
            game.LevelSudoku(LEVEL);
            questions.add(game.getQuestion());
        }
        return questions;
    }

    @Override
    public List<String> getAnswers(String Question) {
        List<String> questions;
        questions = new ArrayList<String>();
        Game game;
        game = new Game();
        game.SolveSudoku(game.ChangeStringToArray(Question));
        questions.add(game.getAnswers());
        return questions;
    }
    
}
