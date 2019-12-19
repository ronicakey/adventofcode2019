package adventOfCode;

import java.util.List;

import static adventOfCode.Day02.getNumbers;

public class Day17 {

    private static int getSum(List<Long> numbers) {
        VacuumRobot robot = new VacuumRobot();
        IntCodeProcess program = new IntCodeProcess(numbers);
        program.run();
        while(program.hasOutput()) {
            robot.loadCommands(program.getOutput().intValue());
            robot.run();
            program.run();
        }
        //robot.printMap();
        return robot.getSum();
    }

    private static int getDust(List<Long> numbers) {
        VacuumRobot robot = new VacuumRobot();
        IntCodeProcess program = new IntCodeProcess(numbers);
        program.setOpCode(0, 2);
        robot.getMovementRoutines();
        while(robot.hasOutput()) {
            program.run(robot.getOutput());
        }
        while (program.hasOutput()) {
            robot.loadCommands(program.getOutput().intValue());
            robot.run();
            program.run();
        }
        //robot.printMap();
        return robot.getDust();
    }

    public static void main(String[] args) {
        // part 1
        System.out.println(getSum(getNumbers("day17.txt")));
        //part 2
        System.out.println(getDust(getNumbers("day17.txt")));
    }

}
