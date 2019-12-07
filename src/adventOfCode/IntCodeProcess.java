package adventOfCode;

import java.util.ArrayDeque;
import java.util.Deque;

import static adventOfCode.Day05.getParams;

public class IntCodeProcess {
    private int[] numbers;
    private int idx = 0;
    private Deque<Integer> input = new ArrayDeque<>();

    IntCodeProcess(int[] numbers, Integer phase) {
        this.numbers = numbers.clone();
        updateInput(phase);
    }

    private void updateInput(int signal) {
        input.addLast(signal);
    }

    public int run(int signal) {
        updateInput(signal);
        while (numbers[idx] != 99) {
            int[] params = getParams(numbers[idx]);
            switch (params[0]) {
                case 1:
                    numbers[numbers[idx + 3]] = numbers[params[1] == 0 ? numbers[idx + 1] : idx + 1] +
                            numbers[params[2] == 0 ? numbers[idx + 2] : idx + 2];
                    idx += 4;
                    break;
                case 2:
                    numbers[numbers[idx + 3]] = numbers[params[1] == 0 ? numbers[idx + 1] : idx + 1] *
                            numbers[params[2] == 0 ? numbers[idx + 2] : idx + 2];
                    idx += 4;
                    break;
                case 3:
                    numbers[numbers[idx + 1]] = input.pollFirst();
                    idx += 2;
                    break;
                case 4:
                    int output = numbers[params[1] == 0 ? numbers[idx + 1] : idx + 1];
                    idx += 2;
                    return output;
                case 5:
                    idx = numbers[params[1] == 0 ? numbers[idx + 1] : idx + 1] != 0 ?
                            numbers[params[2] == 0 ? numbers[idx + 2] : idx + 2] : idx + 3;
                    break;
                case 6:
                    idx = numbers[params[1] == 0 ? numbers[idx + 1] : idx + 1] == 0 ?
                            numbers[params[2] == 0 ? numbers[idx + 2] : idx + 2] : idx + 3;
                    break;
                case 7:
                    numbers[numbers[idx + 3]] = (numbers[params[1] == 0 ? numbers[idx + 1] : idx + 1] <
                            numbers[params[2] == 0 ? numbers[idx + 2] : idx + 2]) ? 1 : 0;
                    idx += 4;
                    break;
                case 8:
                    numbers[numbers[idx + 3]] = (numbers[params[1] == 0 ? numbers[idx + 1] : idx + 1] ==
                            numbers[params[2] == 0 ? numbers[idx + 2] : idx + 2]) ? 1 : 0;
                    idx += 4;
                    break;
            }
        }
        return Integer.MIN_VALUE;
    }

}
