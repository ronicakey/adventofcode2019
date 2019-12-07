package adventOfCode;

import static adventOfCode.Day02.getNumbers;

public class Day05 {
    public static int[] getParams(int command) {
        int[] params = new int[3];
        params[0] = command % 100;
        params[1] = command / 100 % 10;
        params[2] = command / 1000 % 10;
        return params;
    }

    private static void runTests(int[] numbers, int input) {
        int i = 0;
        while (numbers[i] != 99) {
            int[] params = getParams(numbers[i]);
            switch (params[0]) {
                case 1:
                    numbers[numbers[i + 3]] = numbers[params[1] == 0 ? numbers[i + 1] : i + 1] +
                            numbers[params[2] == 0 ? numbers[i + 2] : i + 2];
                    i += 4;
                    break;
                case 2:
                    numbers[numbers[i + 3]] = numbers[params[1] == 0 ? numbers[i + 1] : i + 1] *
                            numbers[params[2] == 0 ? numbers[i + 2] : i + 2];
                    i += 4;
                    break;
                case 3:
                    numbers[numbers[i + 1]] = input;
                    i += 2;
                    break;
                case 4:
                    System.out.println(numbers[params[1] == 0 ? numbers[i + 1] : i + 1]);
                    i += 2;
                    break;
                case 5:
                    i = numbers[params[1] == 0 ? numbers[i + 1] : i + 1] != 0 ?
                            numbers[params[2] == 0 ? numbers[i + 2] : i + 2] : i + 3;
                    break;
                case 6:
                    i = numbers[params[1] == 0 ? numbers[i + 1] : i + 1] == 0 ?
                            numbers[params[2] == 0 ? numbers[i + 2] : i + 2] : i + 3;
                    break;
                case 7:
                    numbers[numbers[i + 3]] = (numbers[params[1] == 0 ? numbers[i + 1] : i + 1] <
                            numbers[params[2] == 0 ? numbers[i + 2] : i + 2]) ? 1 : 0;
                    i += 4;
                    break;
                case 8:
                    numbers[numbers[i + 3]] = (numbers[params[1] == 0 ? numbers[i + 1] : i + 1] ==
                            numbers[params[2] == 0 ? numbers[i + 2] : i + 2]) ? 1 : 0;
                    i += 4;
                    break;
                default:
                    return;
            }
        }
    }

    public static void main(String[] args) {
        //part1
        runTests(getNumbers("day5.txt"), 1);
        //part2
        runTests(getNumbers("day5.txt"), 5);
    }
}
