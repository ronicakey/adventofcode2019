package adventOfCode;

import java.util.List;

import static adventOfCode.Day02.getNumbers;

public class Day11 {

    public static int runTests(List<Long> numbers) {
        HullRobot robot = new HullRobot(0);
        IntCodeProcess program = new IntCodeProcess(numbers);
        while (true) {
            program.run(robot.getOutput());
            if (program.hasOutput()) {
                robot.loadCommands(program.getOutput().intValue());
                robot.loadCommands(program.getOutput().intValue());
                robot.run();
            } else
                return robot.getNumPaintedTiles();
        }
    }

    public static void printId(List<Long> numbers) {
        HullRobot robot = new HullRobot(1);
        IntCodeProcess program = new IntCodeProcess(numbers);
        while (true) {
            program.run(robot.getOutput());
            if (program.hasOutput()) {
                robot.loadCommands(program.getOutput().intValue());
                robot.loadCommands(program.getOutput().intValue());
                robot.run();
            } else {
                robot.printId();
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
