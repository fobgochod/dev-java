package com.fobgochod.jvm;

import org.springframework.beans.factory.ObjectFactory;

public class TestBreak {

    public static void main(String[] args) {
        testContinue();
        testBreak();
        testBreak1();

        ObjectFactory<String> factory = () -> {
            System.out.println("************");
            return "***";
        };

        String object = factory.getObject();
        System.out.println(object);
    }


    public static void testContinue() {
        cmlx:
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(j + ",");
                if (j == 3) {
                    continue cmlx;
                }
            }
            System.out.println();
        }

        System.out.println("--------------------------");
    }

    public static void testBreak() {
        cmlx:
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(j + ",");
                if (j == 3) {
                    break cmlx;
                }
            }
        }

        System.out.println("--------------------------");
    }

    public static void testBreak1() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(j + ",");
                if (j == 3) {
                    break;
                }
            }
        }

        System.out.println("--------------------------");
    }

}
