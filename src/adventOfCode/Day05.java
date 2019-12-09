package adventOfCode;

import java.util.List;

import static adventOfCode.Day02.getNumbers;

public class Day05 {

     public static void runTests(List<Long> numbers, int input) {
        IntCodeProcess test = new IntCodeProcess(numbers, input);
        test.run();
        while (test.hasOutput()) {
            System.out.println(test.getOutput());
        }
    }

    public static void main(String[] args) {
        //part1
        runTests(getNumbers("day5.txt"), 1);
        //part2
        runTests(getNumbers("day5.txt"), 5);
    }
}
