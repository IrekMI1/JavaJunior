package Sem3;

import java.io.Serializable;

public class Main {
    public static void main(String[] args) {
        SomeClass someClass = new SomeClass("First instance", 20);
        Serializer.serializeToFile(someClass);

        SomeClass fromFileClass = (SomeClass) Serializer.deserializeFromFile("Sem3.SomeClass_8d13d40e-d21f-4049-9b77-fd30e40fe7f0");
        System.out.println(fromFileClass);
    }
}

class SomeClass implements Serializable {
    int num;
    String str;

    SomeClass(String str, int num) {
        this.num = num;
        this.str = str;
    }

    @Override
    public String toString() {
        return "SomeClass{" +
                "num=" + num +
                ", str='" + str + '\'' +
                '}';
    }
}
