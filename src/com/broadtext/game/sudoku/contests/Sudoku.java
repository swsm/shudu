package com.broadtext.game.sudoku.contests;

import java.util.List;

/**
 * 数独游戏接口,主要提供出题和解题2个功能
 * 
 * @author Lee
 *
 */
public interface Sudoku {
	/**
	 * 获取团队名称，唯一
	 * 可以包含队员姓名或软件版本号，建议2~6个字符
	 * 
	 * @return 团队名称
	 */
	public String getTeamName();
	
	/**
	 * 出题接口：用于获取数独题目
	 * 题目形式采用81位字符串形式，空缺位置用问号(?)占位表示；
	 * 
	 * 例如："3??8?52??71......3?94??"
	 * 
	 * @return 题目集合，可返回多个题目
	 */
	public List<String> getQuestions();
	
	/**
	 * 解题接口：用于根据传入的题目进行解题
	 * 采用补缺的形式返回答案，答案用来代替题目中的占位符(?)，题目中有多少个占位符，答案就有多少位；
	 * 
	 * 例如，题目："3??8?52??71......3?94??"
	 * 返回的答案："23964......825"
	 * 
	 * @param Question 81位字符串形式题目
	 * @return 答案集合，可返回多个答案
	 */
	public List<String> getAnswers(String Question);
	
}
