package com.fobgochod.spring.aop.aspectj.config;

import com.fobgochod.spring.aop.aspectj.aspect.AopAspect;
import com.fobgochod.spring.aop.aspectj.aspect.AopXmlAspect;

import java.util.Arrays;
import java.util.stream.Collectors;

public class AopLogUtil {

    public static String currentMethod() {
        StackTraceElement[] traceElements = Thread.currentThread().getStackTrace();
        for (StackTraceElement traceElement : traceElements) {
            if (traceElement.getClassName().equals(AopAspect.class.getName())
                    || traceElement.getClassName().equals(AopXmlAspect.class.getName())) {
                return traceElement.getMethodName();
            }
        }
        return "";
    }

    public static void annotationLog(int num, Class<?> clazz, String method, Object[] args, Object result) {
        printLog("Annotation", num, clazz, method, args, result);
    }

    public static void aopXmlLog(int num, Class<?> clazz, String method, Object[] args, Object result) {
        printLog("Xml", num, clazz, method, args, result);
    }

    public static void printLog(String type, int num, Class<?> clazz, String method, Object[] args, Object result) {
        String argsStr = Arrays.stream(args).map(Object::toString).collect(Collectors.joining(",", "(", ")"));
        System.out.printf("%-10s: %2d. %-16s aspect:%-20s method:%-10s  result:%s\n", type, num, clazz.getSimpleName(), currentMethod(), method + argsStr, result);
    }
}
