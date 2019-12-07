package adventOfCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;


public class Day02 {
    public static int[] getNumbers(String filename) {
        try (Scanner input = new Scanner(new File(filename))){
            return Arrays.stream(input.nextLine().trim().split(",")).mapToInt(Integer::parseInt).toArray();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return new int[]{};
    }

    private static int doRepair(int[] numbers) {
        for (int i = 0; i < numbers.length; i += 4) {
            switch (numbers[i]) {
                case 99:
                    return numbers[0];
                case 1:
                    numbers[numbers[i + 3]] = numbers[numbers[i + 1]] + numbers[numbers[i + 2]];
                    break;
                case 2:
                    numbers[numbers[i + 3]] = numbers[numbers[i + 1]] * numbers[numbers[i + 2]];
                    break;
            }
        }
        return numbers[0];
    }

    private static int[] changeParameters(int noun, int verb, int[] array) {
        int[] numbers = array.clone();
        numbers[1] = noun;
        numbers[2] = verb;
        return numbers;
    }

    public static void main(String[] args) {
        int[] numbers  = getNumbers("day2.txt");
        //part 1
        System.out.println(doRepair(changeParameters(12, 2, numbers)));
        //part 2
        for (int noun = 0; noun < 100; noun++) {
            for (int verb = 0; verb < 100; verb++) {
                int result = doRepair(changeParameters(noun, verb, numbers));
                if (result == 19690720) {
                    System.out.println(100 * noun + verb);
                    return;
                }
            }
        }
    }
}
