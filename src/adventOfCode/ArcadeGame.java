package adventOfCode;

import java.util.*;

import adventOfCode.HullRobot.Position;

public class ArcadeGame {
    Map<Position, Integer> map = new HashMap<>();
    private Deque<Integer> input = new ArrayDeque<>();
    private Deque<Integer> output = new ArrayDeque<>();
    private final int inputSize = 3;
    private Position scoreCommand = new Position(-1, 0);
    private int ballPositionX = 0;
    private int paddlePositionX = 0;
    private int score = 0;

    ArcadeGame() {}

    public void loadCommands(int...commands) {
        Arrays.stream(commands).forEach(input::addLast);
    }

    public boolean hasOutput() {
        return !output.isEmpty();
    }

    public int getOutput() {
        return output.pollFirst();
    }

    public int getInputSize() {
        return inputSize;
    }

    public void run() {
        while (input.size() >= inputSize) {
            Position position = getCoordinates();
            int object = getObject();
            if (position.equals(scoreCommand)) {
                score = object;
            } else {
                if (object == 3) {
                    paddlePositionX = position.getX();
                }
                else if (object == 4) {
                    ballPositionX = position.getX();
                    updateOutput();
                }
                updateMap(position, object);
            }
        }
    }

    private Position getCoordinates() {
        return new Position(input.pollFirst(), input.pollFirst());
    }

    private int getObject() {
        return input.pollFirst();
    }

    private void updateOutput() {
        output.addLast(Integer.compare(ballPositionX, paddlePositionX));
    }

    private void updateMap(Position position, int object) {
        map.put(position, object);
    }

    public long getNumBlocks() {
        return map.values().stream().filter(v -> v == 2).count();
    }

    public int getScore() {
        return score;
    }

    public void printMap() {
        for (int y = 0; y < 25; y++) {
            for (int x = 0; x < 41; x++) {
                int num = map.getOrDefault(new Position(x, y), 0);
                System.out.print(num == 0 ? "." :
                        num == 1 ? "|" :
                        num == 2 ? "#" :
                        num == 3 ? "-" :
                        "O");
            }
            System.out.println();
        }
    }
}
