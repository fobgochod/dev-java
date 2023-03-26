package com.fobgochod.code.code2023;

import java.util.Scanner;

public class Code020 {

    /**
     * 输入：
     * 3
     * head add 1
     * remove
     * tail add 2
     * head add 3
     * remove
     * remove
     * 输出：
     * 1
     */
    public static void main(String[] args) {

        System.out.println(f1());


    }

    // 如果前一个数是头插入，那么后面的添加操作只能是 尾端插入 如果出现 头插入就要调整一次
    // 如果之前的是 尾端插入， 那么后面的操作只能是 尾端插入  如果出现 头插入 就要调整一次
    // 无论前面的是头插入 还是 尾端插入， 只要出现了头插入的都要调整一次
    // 如果 remove的命令已经把之前的所有的插入的数据都移除了 就要重新开始计算了
    public static int f1() {
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());
        int cnt = 0;
        int unRemoveNum = 0;
        boolean isResver = false;
        for (int i = 0; i < (n << 1); i++) {
            String[] str = in.nextLine().split(" ");
            if (str.length == 3) {
                if (unRemoveNum != 0) {
                    if (str[0].equals("head") && !isResver) {
                        isResver = true;
                    }
                }
                unRemoveNum++;
            }
            if (str.length == 1) {
                unRemoveNum--;
                cnt += isResver ? 1 : 0;
                isResver = false;
            }
        }
        return cnt;
    }

}
