package Sem2;

import Sem2.CustomAnnotations.AfterEach;
import Sem2.CustomAnnotations.BeforeEach;
import Sem2.CustomAnnotations.Skip;
import Sem2.CustomAnnotations.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestProcessor {

    /**
     * Данный метод находит все void методы без аргументов в классе, и запускеет их.
     * <p>
     * Для запуска создается тестовый объект с помощью конструткора без аргументов.
     */
    public static void runTest(Class<?> testClass) {
        final Constructor<?> declaredConstructor;
        try {
            declaredConstructor = testClass.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Для класса \"" + testClass.getName() + "\" не найден конструктор без аргументов");
        }

        final Object testObj;
        try {
            testObj = declaredConstructor.newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Не удалось создать объект класса \"" + testClass.getName() + "\"");
        }

        List<Method> beforeMethods = new ArrayList<>();
        List<Method> afterMethods = new ArrayList<>();
        List<Method> methods = new ArrayList<>();
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                checkTestMethod(method);
                methods.add(method);
            } else if (method.isAnnotationPresent(BeforeEach.class)) {
                checkTestMethod(method);
                beforeMethods.add(method);
            } else if (method.isAnnotationPresent(AfterEach.class)) {
                checkTestMethod(method);
                afterMethods.add(method);
            }
        }

        methods.sort(Comparator.comparingInt(o -> o.getDeclaredAnnotation(Test.class).order()));
        beforeMethods.forEach(it -> runBefore(it, testObj));
        methods.forEach(it -> runTest(it, testObj));
        afterMethods.forEach(it -> runAfter(it, testObj));
    }

    private static void checkTestMethod(Method method) {
        if (!method.getReturnType().isAssignableFrom(void.class) || method.getParameterCount() != 0) {
            throw new IllegalArgumentException("Метод \"" + method.getName() + "\" должен быть void и не иметь аргументов");
        }
    }

    private static void runTest(Method testMethod, Object testObj) {
        try {
            if (!testMethod.isAnnotationPresent(Skip.class))
                testMethod.invoke(testObj);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException("Не удалось запустить тестовый метод \"" + testMethod.getName() + "\"");
        }
    }

    private static void runBefore(Method beforeMethod, Object obj) {
        try {
            beforeMethod.invoke(obj);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException("Не удалось запустить перед тестами метод \"" + beforeMethod.getName() + "\"");
        }
    }

    private static void runAfter(Method afterMethod, Object obj) {
        try {
            afterMethod.invoke(obj);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException("Не удалось запустить после тестов метод \"" + afterMethod.getName() + "\"");
        }
    }
}
