package adventOfCode;

import java.util.List;
import java.util.stream.IntStream;

import static adventOfCode.Day02.getNumbers;

public class Day11 {

    public static int runTests(List<Long> numbers) {
        HullRobot robot = new HullRobot(0);
        int inputSize = robot.getInputSize();
        IntCodeProcess program = new IntCodeProcess(numbers);
        while (true) {
            program.run(robot.getOutput());
            if (program.hasOutput()) {
                IntStream.range(0, inputSize)
                        .forEach(i -> robot.loadCommands(program.getOutput().intValue()));
                robot.run();
            } else
                return robot.getNumPaintedTiles();
        }
    }

    public static void printId(List<Long> numbers) {
        HullRobot robot = new HullRobot(1);
        int inputSize = robot.getInputSize();
        IntCodeProcess program = new IntCodeProcess(numbers);
        while (true) {
            program.run(robot.getOutput());
            if (program.hasOutput()) {
                IntStream.range(0, inputSize)
                        .forEach(i -> robot.loadCommands(program.getOutput().intValue()));
                robot.run();
            } else {
                robot.printMap();
                return;
            }
        }
    }

    public static void main(String[] args) {
        //part 1
        System.out.println(runTests(getNumbers("day11.txt")));
        //part 2
        printId(getNumbers("day11.txt"));
    }
}
