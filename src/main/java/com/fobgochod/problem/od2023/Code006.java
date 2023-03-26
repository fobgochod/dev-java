package com.fobgochod.problem.od2023;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Code006 {

    /**
     * 7
     * IN 1 1
     * IN 1 2
     * IN 1 3
     * IN 2 1
     * OUT 1
     * OUT 2
     * OUT 2
     */
    public static void main(String[] args) {
        List<List<File>> m = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            m.add(new ArrayList<>());
        }
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int flag = 0;
        for (int i = 0; i <= n; i++) {
            String s = in.nextLine();
            String[] s1 = s.split(" ");
            String type = s1[0];

            if ("IN".equals(type)) {
                int p = Integer.parseInt(s1[1]);
                int num = Integer.parseInt(s1[2]);
                flag++;
                File file = new File(flag, num);
                List<File> files = m.get(p - 1);
                files.add(file);
            } else if ("OUT".equals(type)) {
                int p = Integer.parseInt(s1[1]);
                List<File> files = m.get(p - 1);
                if (files != null && files.size() > 0) {
                    files.sort((a, b) -> {
                        return b.getWeight() - a.getWeight();
                    });
                    File file = files.get(0);
                    System.out.println(file.getIndex());
                    files.remove(0);
                } else {
                    System.out.println("NULL");
                }
            }

        }


    }

    private static class File {
        private int index;
        private int weight;

        public File(int index, int weight) {
            this.index = index;
            this.weight = weight;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
    }

}

