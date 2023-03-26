package com.fobgochod.code.code2023;// 本题为考试单行多行输入输出规范示例，无需提交，不计分。

import java.util.ArrayList;
import java.util.Scanner;

public class Code027 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        //读入数据
        int n = Integer.parseInt(in.nextLine());
        ArrayList<YuanGong> clockRecords = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String[] str = in.nextLine().split(",");
            YuanGong yuanGong = new YuanGong(Integer.parseInt(str[0]),
                    Integer.parseInt(str[1]),
                    Integer.parseInt(str[2]),
                    str[3],
                    str[4]);
            yuanGong.setError(yuanGong.isErrorData());
            clockRecords.add(yuanGong);
        }

        for (int i = 0; i < clockRecords.size(); i++) {
            for (int j = i + 1; j < clockRecords.size(); j++) {
                if (clockRecords.get(i).id == clockRecords.get(j).id) {
                    int times = clockRecords.get(i).time - clockRecords.get(j).time;
                    int diatances = clockRecords.get(i).distance - clockRecords.get(j).distance;
                    times = times > 0 ? times : times * (-1);
                    diatances = diatances > 0 ? diatances : diatances * (-1);
                    if (times < 60 && diatances > 5) {
                        clockRecords.get(i).setError(true);
                        clockRecords.get(j).setError(true);
                    }
                }
            }
        }

        String str = "";
        for (int i = 0; i < clockRecords.size(); i++) {
            if (clockRecords.get(i).error) {
                str += clockRecords.get(i).toString() + " ";
            }
        }
        if (str.length() == 0) {
            System.out.println("null");
        } else {
            System.out.println(str.trim().replace(" ", ";"));
        }

    }


    static class YuanGong {
        int id;
        int time;
        int distance;
        String actualDeviceNumber;
        String registeredDeviceNumber;
        boolean error;

        public YuanGong() {
        }

        public YuanGong(int id, int time, int distance, String actualDeviceNumber, String registeredDeviceNumber) {
            this.id = id;
            this.time = time;
            this.distance = distance;
            this.actualDeviceNumber = actualDeviceNumber;
            this.registeredDeviceNumber = registeredDeviceNumber;
            this.error = false;

        }

        public boolean isError() {
            return error;
        }

        public void setError(boolean error) {
            this.error = error;
        }

        @Override
        public String toString() {
            return "" + id + "," + time + "," + distance + "," + actualDeviceNumber + "," + registeredDeviceNumber;
        }

        public boolean isErrorData() {
            if (!actualDeviceNumber.equals(registeredDeviceNumber)) {
                return true;
            }
            return false;
        }

    }


}
