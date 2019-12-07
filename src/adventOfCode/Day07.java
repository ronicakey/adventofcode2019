package adventOfCode;

import org.paukov.combinatorics3.Generator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static adventOfCode.Day02.getNumbers;

public class Day07 {
    private static int getMaxSignal(int[] array) {
        return Generator.permutation(0, 1, 2, 3, 4)
                .simple()
                .stream()
                .map(phases -> runAmplifiers(array.clone(), phases))
                .reduce(Integer::max)
                .get();
    }

    private static int runAmplifiers(int[] array, List<Integer> phases) {
        int signal = 0;
        for (int i = 0; i < 5; i++) {
            signal = new IntCodeProcess(array, phases.get(i)).run(signal);
        }
        return signal;
    }

    private static int getMaxSignal2(int[] array) {
        return Generator.permutation(5, 6, 7, 8, 9)
                .simple()
                .stream()
                .map(phases -> runAmplifiers2(array, phases))
                .reduce(Integer::max)
                .get();
    }

    private static int runAmplifiers2(int[] array, List<Integer> phases) {
        List<IntCodeProcess> processes = IntStream.range(0, 5)
                .mapToObj(i -> new IntCodeProcess(array, phases.get(i)))
                .collect(Collectors.toList());
        int signal = 0;
        int output = 0;
        while (output != Integer.MIN_VALUE) {
            for (IntCodeProcess process : processes) {
                output = process.run(signal);
                if (output == Integer.MIN_VALUE) break;
                else signal = output;
            }
        }
        return signal;
    }



    public static void main(String[] args) {
        //part1
        System.out.println(getMaxSignal(getNumbers("day7.txt")));
        //part2
        System.out.println(getMaxSignal2(getNumbers("day7.txt")));
    }

}
