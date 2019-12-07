package adventOfCode;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day04 {
    private static boolean isNonDecreasing(String number) {
        return IntStream.range(1, number.length())
                .allMatch(i -> number.charAt(i) >= number.charAt(i - 1));
    }

    private static boolean hasDoubles1(String number) {
        return IntStream.range(1, number.length())
                .anyMatch(i -> number.charAt(i) == number.charAt(i - 1));
    }

    private static boolean hasDoubles2(String number) {
        return number.chars().mapToObj(n -> (char) n)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .containsValue(2L);
    }

    //part 1
    private static long getPasswordCount1(int start, int end) {
        return IntStream.rangeClosed(start, end).mapToObj(String::valueOf)
                .filter(n -> isNonDecreasing(n) && hasDoubles1(n)).count();
    }

    //part2
    private static long getPasswordCount2(int start, int end) {
        return IntStream.rangeClosed(start, end).mapToObj(String::valueOf)
                .filter(n -> isNonDecreasing(n) && hasDoubles2(n)).count();
    }

    public static void main(String[] args) {
        int start = 158126;
        int end = 624574;

        System.out.println(getPasswordCount1(start, end));
        System.out.println(getPasswordCount2(start, end));
    }
}
