package adventOfCode;

import java.util.List;
import java.util.stream.IntStream;

import static adventOfCode.Day02.getNumbers;

public class Day15 {

    public static void main(String[] args) {
        List<Long> numbers = getNumbers("day15.txt");
        RepairDroid droid = new RepairDroid();
        int inputSize = droid.getInputSize();
        IntCodeProcess program = new IntCodeProcess(numbers);
        droid.run();
        while (true) {
            if (droid.hasOutput()) {
                program.run(droid.getOutput());
                IntStream.range(0, inputSize)
                        .forEach(i -> droid.loadCommands(program.getOutput().intValue()));
                droid.run();
            } else {
                droid.printMap();
                droid.getTime(); //part 2
                droid.printMap();
                return;
            }
        }
    }
}
