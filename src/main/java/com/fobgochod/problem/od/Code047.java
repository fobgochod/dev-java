package com.fobgochod.problem.od;

import java.util.*;

/**
 * 字符串重新排序
 * <p>
 * 例1
 * 输入
 * This is an apple
 * 输出
 * an is This aelpp
 * 例2
 * 输入
 * My sister is in the house not in the yard
 * 输出
 * in in eht eht My is not adry ehosu eirsst
 */
public class Code047 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String[] strings = sc.nextLine().split(" ");
        Map<String, Integer> map = new HashMap<>();

        for (int i = 0; i < strings.length; i++) {
            String temp = chuli(strings[i]);
            map.put(temp, map.getOrDefault(temp, 0) + 1);
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());

        list.sort((a, b) -> {
            if (b.getValue() < a.getValue()) {
                return -1;
            } else if (b.getValue() == a.getValue()) {
                if (b.getKey().length() > a.getKey().length()) {
                    return -1;
                } else if (b.getKey().length() == a.getKey().length()) {
                    return a.getKey().hashCode() - b.getKey().hashCode();
                }
            }
            return 1;
        });

        String res = "";
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).getValue(); j++) {

                res += list.get(i).getKey() + " ";
            }
        }

        System.out.println(res.substring(0, res.length() - 1));
    }

    public static String chuli(String s) {

        List<Character> list = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            list.add(s.charAt(i));
        }
        Collections.sort(list);
        String res = "";
        for (int i = 0; i < list.size(); i++) {
            res += list.get(i);
        }

        return res;

    }

}
