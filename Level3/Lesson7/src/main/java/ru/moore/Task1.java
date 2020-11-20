package ru.moore;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;

public class Task1 {

    public static void main(String[] args) {
        Class1 class1 = new Class1();

        start(class1);
    }

    public static void start(Object testClass) {
        if (!beforeAfterCorrect(testClass.getClass())) {
            throw new RuntimeException();
        }
        Method beforeM = null;
        Method afterM = null;
        ArrayList<Method> testMethods = new ArrayList<>();

        Method[] objMethods = testClass.getClass().getDeclaredMethods();
        for (Method method : objMethods) {
            if (method.getAnnotation(BeforeSuite.class) != null) {
                beforeM = method;
            } else if (method.getAnnotation(AfterSuite.class) != null) {
                afterM = method;
            } else if (method.getAnnotation(Test.class) != null) {
                testMethods.add(method);
            }
        }
        testMethods.sort(Comparator.comparingInt(o -> o.getAnnotation(Test.class).priority()));

        if (beforeM != null) {
            testMethods.add(0, beforeM);
        }

        if (afterM != null) {
            testMethods.add(afterM);
        }

        try {
            for (Method testMethod : testMethods) {
                testMethod.invoke(testClass);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static boolean beforeAfterCorrect(Class aClass) {
        int beforeCount = 0;
        int afterCount = 0;

        for (Method method : aClass.getDeclaredMethods()) {
            if (method.getAnnotation(BeforeSuite.class) != null) {
                beforeCount++;
            }
            if (method.getAnnotation(AfterSuite.class) != null) {
                afterCount++;
            }
        }

        return (beforeCount < 2) && (afterCount < 2);
    }
}
