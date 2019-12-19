package adventOfCode;

import java.util.List;

import static adventOfCode.Day02.getNumbers;

public class Day19 {

    private static long getBeamCoverage(List<Long> numbers) {
        IntCodeProcess program;
        long coverage = 0;
        for (long y = 0; y < 50; y++) {
            for (long x = 0; x < 50; x++)  {
                program = new IntCodeProcess(numbers);
                program.run(x, y);
                long output = program.getOutput();
                System.out.print(output == 0 ? "." : "#");
                coverage += output;
            }
            System.out.println();
        }
        return coverage;
    }

    private static long getClosestPoint(List<Long> numbers) {
        long x = 600;
        long y = 600;
        while (true) {
            if (isPulled(numbers, x, y)) {
                if (isPulled(numbers, x + 99, y - 99)) {
                    return 10000 * x + y - 99;
                } else {
                    y++;
                }
            } else {
                x++;
            }
        }
    }

    private static boolean isPulled(List<Long> numbers, long x, long y) {
        IntCodeProcess program = new IntCodeProcess(numbers);
        program.run(x, y);
        return program.getOutput() == 1;
    }

    public static void main(String[] args) {
        //part 1
        System.out.println(getBeamCoverage(getNumbers("day19.txt")));
        //part 2
        System.out.println(getClosestPoint(getNumbers("day19.txt")));
    }
}
