package Sem1;

import java.util.*;
import java.util.stream.Collectors;

public class StreamAPI {
    public static void main(String[] args) {
        Random random = new Random();
        List<Integer> numbers = random.ints(1000, 1, 1000000).boxed().toList();

        System.out.println(
                "Max by List.max: " +
                        Collections.max(numbers)
        );

        System.out.println(
                "Max by stream: " +
                        numbers.stream()
                                .max(Comparator.comparingInt(o -> o))
                                .get()
        );

        System.out.println(
                "Сумма чисел больше 500000: " +
                        numbers.stream()
                                .filter(x -> x > 500000)
                                .mapToInt(x -> x * 5 - 150).sum()
        );

        System.out.println(
                "Количество чисел, квадрат которых меньше 100 000: " +
                        numbers.stream()
                                .filter(x -> (x * x) < 100000)
                                .count()
        );

    }
}
