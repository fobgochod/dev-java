package com.fobgochod.problem.solution.backtracking;

import java.util.*;

/**
 * 电话号码的字母组合
 * 例1
 * 输入
 * 23
 * 输出
 * ["ad","ae","af","bd","be","bf","cd","ce","cf"]
 * <p>
 * 示例2
 * 输入
 * 输出
 */
public class LeetCode017 {

    static Map<Character, String> phoneMap = new HashMap<Character, String>() {{
        put('2', "abc");
        put('3', "def");
        put('4', "ghi");
        put('5', "jkl");
        put('6', "mno");
        put('7', "pqrs");
        put('8', "tuv");
        put('9', "wxyz");
    }};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String digits = scanner.nextLine();

        List<String> combinations = new ArrayList<>();
        if (digits.length() > 0) {
            backtrack(combinations, digits, 0, new StringBuffer());
        }
        System.out.println(combinations);
    }

    public static void backtrack(List<String> combinations, String digits, int index, StringBuffer combination) {
        if (index == digits.length()) {
            combinations.add(combination.toString());
        } else {
            char digit = digits.charAt(index);
            String letters = phoneMap.get(digit);
            int lettersCount = letters.length();
            for (int i = 0; i < lettersCount; i++) {
                combination.append(letters.charAt(i));
                backtrack(combinations, digits, index + 1, combination);
                combination.deleteCharAt(index);
            }
        }
    }
}
