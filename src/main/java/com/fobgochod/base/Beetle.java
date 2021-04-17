package com.fobgochod.base;

class Insect {

    static {
        System.out.println("Insect static block");
    }

    private static int x1 = printInit("static Insect.x1 initialized");
    static int x3 = 3;
    protected int x5 = printInit("Insect.x5 initialized");
    public int x7;

    Insect() {
        System.out.println("Insect constructor");
        System.out.println("Insect.x5 = " + x5);
        System.out.println("Insect.x7 = " + x7);
        this.x7 = 7;
    }

    static int printInit(String s) {
        System.out.println(s);
        return 47;
    }
}

/**
 * The full process of initialization
 * <p>
 * 1、父类静态代码块、静态变量按文本顺序初始化
 * 2、子类静态代码块、静态变量按文本顺序初始化
 * 3、父类实例变量初始化
 * 4、父类构造函数
 * 5、子类实例变量初始化
 * 6、子类构造函数
 *
 * @author zhouxiao
 * @date 2020/8/1
 */
public class Beetle extends Insect {

    public static void main(String[] args) {
        System.out.println("********** main method begin **********");
        Beetle b = new Beetle();
    }

    private static int x2 = printInit("static Beetle.x2 initialized");
    static int x4 = 6;
    protected int x6 = printInit("Beetle.x6 initialized");
    public int x8 = 8;

    public Beetle() {
        super();
        System.out.println("Beetle constructor");
        System.out.println("Beetle.x6 = " + x6);
        System.out.println("Beetle.x8 = " + x8);
        System.out.println("Insect.x7 = " + x7);
    }

    static {
        System.out.println("Beetle static block");
    }
}
