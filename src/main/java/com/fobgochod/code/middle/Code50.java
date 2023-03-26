package com.fobgochod.code.middle;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.Scanner;
import java.util.Stack;

public class Code50 {

    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        input = input.replace("[", "(");
        input = input.replace("{", "(");
        input = input.replace("}", ")");
        input = input.replace("]", ")");
        ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");
        System.out.println(scriptEngine.eval(input));
    }
}
