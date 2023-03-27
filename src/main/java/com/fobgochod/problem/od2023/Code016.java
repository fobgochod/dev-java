package com.fobgochod.problem.od2023;

import java.util.Scanner;

/**
 * 网上商城优惠活动
 * 例1
 * 输入
 * 3 2 5
 * 3
 * 100
 * 200
 * 400
 * 输出
 * 65 6
 * 155 7
 * 338 4
 * <p>
 * 示例2
 * 输入
 * 输出
 */
public class Code016 {

    public static int manjian;
    public static int dazhe;
    public static int wumenkan;
    //券的最小使用量
    public static int mincountQuan;
    //最少价格
    public static int minCount;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        manjian = sc.nextInt();
        dazhe = sc.nextInt();
        wumenkan = sc.nextInt();

        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {

            double money = sc.nextInt();

            //首先使用满减的张数
            int quanMJ = money / 100 > manjian ? manjian : (int) (money / 100);

            //使用无门槛的券的张数
            int quanWMK;
            //券的最小使用量
            mincountQuan = Integer.MAX_VALUE;
            //最少价格
            minCount = Integer.MAX_VALUE;

            if (dazhe > 0) {  //有打折券的情况

                //先满减后打折
                int MJafterDZ = (int) Math.floor(Manjian(money) * 0.92);
                flush(MJafterDZ, quanMJ + 1);

                //先打折后满减
                int DZafterMJ = (int) Math.floor(Manjian(money * 0.92));
                //先打折后满减的满减券
                int quanDZafterMJ = (int) (money * 0.92 / 100 > manjian ? manjian : money * 0.92 / 100);
                flush(DZafterMJ, quanDZafterMJ + 1);

                //先打折后无门槛
                double dazhe = money * 0.92;
                //无门槛需要的张数
                quanWMK = wumenkan(dazhe);
                int dazheWMK;
                if (dazhe <= quanWMK * 5) {
                    //打折后的价格小于等于无门槛的全部价格（可以0元购）
                    dazheWMK = 0;
                } else {
                    dazheWMK = (int) Math.floor(dazhe - quanWMK * 5);
                }
                flush(dazheWMK, quanWMK + 1);

                //先无门槛后打折
                int wmkDZ;
                //无门槛需要的张数
                quanWMK = wumenkan(money);
                if (money <= quanWMK * 5) {
                    //价格小于等于无门槛的全部价格（可以0元购）
                    flush(0, quanWMK);
                } else {
                    wmkDZ = (int) Math.floor((money - quanWMK * 5) * 0.92);
                    flush(wmkDZ, quanWMK + 1);
                }
            }

            //先满减后无门槛
            int mjWMK;
            double manjian = Manjian(money);
            //无门槛需要的张数
            quanWMK = wumenkan(manjian);
            if (manjian <= quanWMK * 5) {
                //满减后的价格小于等于无门槛的全部价格（可以0元购）
                mjWMK = 0;
            } else {
                mjWMK = (int) Math.floor(manjian - quanWMK * 5);
            }
            flush(mjWMK, quanWMK + quanMJ);

            System.out.println(minCount + " " + mincountQuan);

        }
    }

    /**
     * 刷新最少价格和最少使用券
     *
     * @param count
     * @param quanCount
     */
    public static void flush(int count, int quanCount) {
        if (count < minCount) {
            minCount = count;
            mincountQuan = quanCount;
        } else if (count == minCount) {
            mincountQuan = Math.min(quanCount, mincountQuan);
        }
    }

    /**
     * 求出需要无门槛优惠券的张数
     *
     * @param money
     * @return
     */
    public static int wumenkan(double money) {

        for (int i = 1; i <= wumenkan; i++) {
            if (money <= 5 * i) {
                return i;
            }
        }

        return wumenkan;
    }

    /**
     * 满减后的价格
     *
     * @param money
     * @return
     */
    public static double Manjian(double money) {

        if (money / 100 >= manjian) {
            return money - manjian * 10;
        } else {
            return money - ((int) money / 100) * 10;
        }
    }

}
