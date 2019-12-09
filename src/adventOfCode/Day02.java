package adventOfCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day02 {
    public static List<Long> getNumbers(String filename) {
        try (Scanner input = new Scanner(new File(filename))){
            return Arrays.stream(input.nextLine().trim().split(","))
                    .mapToLong(Long::parseLong).boxed()
                    .collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return new ArrayList<>();
    }

    private static long doRepair(List<Long> list, long noun, long verb) {
        List<Long> numbers = new ArrayList<>(list);
        changeParameters(numbers, noun, verb);
        for (int i = 0; i < numbers.size(); i += 4) {
            switch (numbers.get(i).intValue()) {
                case 99:
                    return numbers.get(0);
                case 1:
                    numbers.set(numbers.get(i + 3).intValue(), numbers.get(numbers.get(i + 1).intValue()) +
                            numbers.get(numbers.get(i + 2).intValue()));
                    break;
                case 2:
                    numbers.set(numbers.get(i + 3).intValue(), numbers.get(numbers.get(i + 1).intValue()) *
                            numbers.get(numbers.get(i + 2).intValue()));
                    break;
            }
        }
        return numbers.get(0);
    }

    private static void changeParameters(List<Long> numbers, long noun, long verb) {
        numbers.set(1, noun);
        numbers.set(2, verb);
    }

    private static long getAnswer(List<Long> list) {
        for (long noun = 0L; noun < 100; noun++) {
            for (long verb = 0L; verb < 100; verb++) {
                long result = doRepair(list, noun, verb);
                if (result == 19690720) {
                    return 100 * noun + verb;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        //part 1
        System.out.println(doRepair(getNumbers("day2.txt"), 12L, 2L));
        //part 2
        System.out.println(getAnswer(getNumbers("day2.txt")));
    }
}
