package adventOfCode;

import static adventOfCode.Day02.getNumbers;
import static adventOfCode.Day05.runTests;

public class Day09 {
    public static void main(String[] args) {
        //part1
        runTests(getNumbers("day9.txt"), 1);
        //part2
        runTests(getNumbers("day9.txt"), 2);
    }
}
