package Sem2;

import Sem2.CustomAnnotations.AfterEach;
import Sem2.CustomAnnotations.BeforeEach;
import Sem2.CustomAnnotations.Skip;
import Sem2.CustomAnnotations.Test;

public class Main {

    public static void main(String[] args) {
        TestProcessor.runTest(MyTest.class);
    }

    static class MyTest {

        @BeforeEach
        void before1() {
            System.out.println("before1 method запущен");
        }

        @AfterEach
        void after1() {
            System.out.println("after1 method запущен");
        }

        @BeforeEach
        void before2() {
            System.out.println("before2 method запущен");
        }

        @AfterEach
        void after2() {
            System.out.println("after2 method запущен");
        }

        @Test(order = 3)
        void firstTest() {
            System.out.println("firstTest запущен");
        }

        @Test(order = 1)
        void secondTest() {
            System.out.println("secondTest запущен");
        }

        @Skip
        @Test(order = 6)
        void thirdTest() {
            System.out.println("thirdTest запущен");
        }
    }


}
