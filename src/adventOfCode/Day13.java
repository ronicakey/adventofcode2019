package adventOfCode;

import java.util.List;
import java.util.stream.IntStream;

import static adventOfCode.Day02.getNumbers;

public class Day13 {
    public static long getNumBlocks(List<Long> numbers) {
        ArcadeGame game = new ArcadeGame();
        int inputSize = game.getInputSize();
        IntCodeProcess program = new IntCodeProcess(numbers);
        while (true) {
            program.run();
            if (program.hasOutput()) {
                IntStream.range(0, inputSize)
                        .forEach(i -> game.loadCommands(program.getOutput().intValue()));
                game.run();
            } else
                return game.getNumBlocks();
        }
    }

    public static int playGame(List<Long> numbers) {
        ArcadeGame game = new ArcadeGame();
        int inputSize = game.getInputSize();
        IntCodeProcess program = new IntCodeProcess(numbers);
        program.setOpCode(0, 2);
        while (true) {
            if (game.hasOutput()) {
                //game.printMap();
                program.run(game.getOutput());
            } else {
                program.run();
            }

            if (program.hasOutput()) {
                IntStream.range(0, inputSize)
                        .forEach(i -> game.loadCommands(program.getOutput().intValue()));
                game.run();
            } else {
                //game.printMap();
                return game.getScore();
            }
        }
    }

    public static void main(String[] args) {
        //part 1
        System.out.println(getNumBlocks(getNumbers("day13.txt")));
        //part 2
        System.out.println(playGame(getNumbers("day13.txt")));

    }
}
