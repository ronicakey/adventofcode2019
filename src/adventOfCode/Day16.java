package adventOfCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day16 {
    private static String getInput(String filename) {
        try (Scanner input = new Scanner(new File(filename))){
            return input.next();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return "-1";
    }

    private static String getAnswer1(String input) {
        List<Integer> values = input.chars().map(Character::getNumericValue).boxed().collect(Collectors.toList());
        int length = values.size();
        IntStream.range(0, 100).forEach(cycle -> {
            for (int idx = 0; idx < length; idx++) {
                int sum = 0;
                int phase = 1;
                for (int col = idx; col < length; col += 2 * idx + 2) {
                    sum += values.subList(col, Math.min(col + idx + 1, length)).stream()
                            .mapToInt(Integer::intValue).sum() * phase;
                    phase *= -1;
                }
                values.set(idx, Math.abs(sum) % 10);
            }
        });
        return values.subList(0, 8).stream().map(String::valueOf).collect(Collectors.joining());
    }

    private static String getAnswer2(String input) {
        int offset = Integer.parseInt(input.substring(0, 7));
        input = input.repeat(10_000);
        input = input.substring(offset);
        List<Integer> values = input.chars().map(Character::getNumericValue).boxed().collect(Collectors.toList());
        int length = values.size();
        IntStream.range(0, 100).forEach(cycle -> {
            int sum = 0;
            for (int idx = length - 1; idx >= 0; idx--) {
                sum = (sum + values.get(idx)) % 10;
                values.set(idx, sum);
            }
        });
        return values.subList(0, 8).stream().map(String::valueOf).collect(Collectors.joining());
    }

    public static void main(String[] args) {
        //part 1
        System.out.println(getAnswer1(getInput("day16.txt")));
        //part 2
        System.out.println(getAnswer2(getInput("day16.txt")));
    }
}
