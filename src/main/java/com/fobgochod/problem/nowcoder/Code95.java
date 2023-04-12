package com.fobgochod.problem.nowcoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 人民币转换
 * 例1
 * 输入
 * 151121.15
 * 输出
 * 人民币拾伍万壹仟壹佰贰拾壹元壹角伍分
 * <p>
 * 示例2
 * 输入
 * 1010.00
 * 输出
 * 人民币壹仟零拾元整
 */
public class Code95 {


    private static String[] chineseNum = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    private static String[] chineseUnit = {null, "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿"};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str;
        while ((str = br.readLine()) != null) {
            String zn;
            String sn = null;
            if (str.contains(".")) {
                String[] split = str.split("\\.");
                zn = split[0];
                sn = split[1];
            } else {
                zn = str;
            }
            String res = format(zn);
            StringBuilder sb = new StringBuilder(res);
            if (!"人民币".equals(res)) {
                sb.append("元");
            }
            if (sn == null || "00".equals(sn))
                sb.append("整");
            else {
                int jn = Integer.parseInt(String.valueOf(sn.charAt(0)));
                if (jn != 0) {
                    sb.append(chineseNum[jn]);
                    sb.append("角");
                }
                int fn = Integer.parseInt(String.valueOf(sn.charAt(1)));
                if (fn != 0) {
                    sb.append(chineseNum[fn]);
                    sb.append("分");
                }
            }
            System.out.println(sb.toString());
        }
    }

    public static String format(String zn) {
        StringBuilder sb = new StringBuilder("人民币");
        boolean hasZero = false;
        if ("0".equals(zn)) {
            return sb.toString();
        }
        for (int i = 0; i < zn.length(); i++) {
            int unitIndex = zn.length() - i - 1;
            int n = Integer.parseInt(String.valueOf(zn.charAt(i)));
            switch (n) {
                case 0:
                    if (!hasZero && unitIndex != 4) {
                        sb.append(chineseNum[0]);
                        hasZero = true;
                    }
                    break;
                case 1:
                    if (!"拾".equals(chineseUnit[unitIndex])) {
                        sb.append(chineseNum[1]);
                    }
                    hasZero = false;
                    break;
                default:
                    sb.append(chineseNum[n]);
                    hasZero = false;
                    break;
            }
            if (chineseUnit[unitIndex] != null) {
                if (!hasZero) sb.append(chineseUnit[unitIndex]);
            }
        }
        if (sb.charAt(sb.length() - 1) == '零')
            return sb.substring(0, sb.length() - 1);
        else
            return sb.toString();
    }
}
