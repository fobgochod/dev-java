package com.fobgochod.problem.od;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Code001 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] question = in.nextLine().split(",");
        String[] answer = in.nextLine().split(",");

        List<String> resList = new ArrayList<>();
        for (int i = 0; i < question.length; i++) {
            String q = question[i];
            boolean isFound = false;
            for (int j = 0; j < answer.length; j++) {
                String a = answer[j];
                if (change(q, a)) {
                    resList.add(a);
                    isFound = true;
                } else if (dist(q, a)) {
                    resList.add(a);
                    isFound = true;
                }
            }
            if (!isFound) {
                resList.add("not found");
            }
        }
        String res = "";
        for (int i = 0; i < resList.size(); i++) {
            res += resList.get(i) + ",";
        }
        System.out.println(res.substring(0, res.length() - 1));
    }

    public static boolean dist(String question, String answer) {
        List<Character> qList = new ArrayList<>();
        for (int i = 0; i < question.length(); i++) {
            char c = question.charAt(i);
            if (!qList.contains(c)) {
                qList.add(c);
            }
        }
        List<Character> aList = new ArrayList<>();
        for (int i = 0; i < answer.length(); i++) {
            char c = answer.charAt(i);
            if (!aList.contains(c)) {
                aList.add(c);
            }
        }
        if (qList.equals(aList)) {
            return true;
        }
        return false;

    }

    public static boolean change(String question, String answer) {
        String[] qStr = question.split("");
        Arrays.sort(qStr);
        String[] aStr = answer.split("");
        Arrays.sort(aStr);
        if (Arrays.equals(qStr, aStr)) {
            return true;
        }
        return false;
    }
}
