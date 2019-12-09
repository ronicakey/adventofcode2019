package adventOfCode;

import org.paukov.combinatorics3.Generator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static adventOfCode.Day02.getNumbers;

public class Day07 {
    private static long getMaxSignal(List<Long> list) {
        return Generator.permutation(0, 1, 2, 3, 4)
                .simple()
                .stream()
                .map(phases -> runAmplifiers(list, phases))
                .reduce(Long::max)
                .get();
    }

    private static long getMaxSignal2(List<Long> list) {
        return Generator.permutation(5, 6, 7, 8, 9)
                .simple()
                .stream()
                .map(phases -> runAmplifiers(list, phases))
                .reduce(Long::max)
                .get();
    }

    private static long runAmplifiers(List<Long> list, List<Integer> phases) {
        List<IntCodeProcess> processes = IntStream.range(0, 5)
                .mapToObj(i -> new IntCodeProcess(list, phases.get(i)))
                .collect(Collectors.toList());
        long signal = 0;
        while (true) {
            for (IntCodeProcess process : processes) {
                process.run(signal);
                if (process.hasOutput()) signal = process.getOutput();
                else return signal;
            }
        }
    }

    public static void main(String[] args) {
        //part1
        System.out.println(getMaxSignal(getNumbers("day7.txt")));
        //part2
        System.out.println(getMaxSignal2(getNumbers("day7.txt")));
    }

}
