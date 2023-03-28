package com.fobgochod.problem.solution.dynamic_programming;

public class DivideApple {

    public static void main(String[] args) {

        int i = calStep(7, 3);
        System.out.println(i);
    }

    /**
     * @param m 苹果
     * @param n 盘子
     * @return
     */
    public static int calStep(int m, int n) {
        // 盘子大于苹果 多余的盘子没用
        if (n > m) {
            return calStep(m, m);
        }
        //盘子为1 或者苹果0
        if (m == 0 || n == 1) {
            return 1;
        }
        // 苹果>盘子
        // 有一个空盘子 n-1
        // 每个盘子先放一个  m-n
        return calStep(m, n - 1) + calStep(m - n, n);
    }
}
