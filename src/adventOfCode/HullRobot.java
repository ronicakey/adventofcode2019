package adventOfCode;

import java.util.*;

public class HullRobot {
    private enum Direction {
        UP(new Position(0, -1)),
        DOWN(new Position(0, 1)),
        RIGHT(new Position(1, 0)),
        LEFT(new Position(-1, 0));

        private final Position vector;

        Direction(Position vector) {
            this.vector = vector;
        }

        public Position getVector() {
            return vector;
        }
    }

    public static class Position {
        private int x;
        private int y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Position(Position position) {
            this(position.x, position.y);
        }

        public int getX() {
            return x;
        }

        public  int getY() {
            return  y;
        }

        public void move(Position other) {
            this.x += other.x;
            this.y += other.y;
        }

        @Override
        public String toString() {
            return String.format("(%d, %d)", x, y);
        }

        //template stuff
        @Override
        public boolean equals(Object other) {
            if (this == other) return true;
            if (other == null || getClass() != other.getClass()) return false;
            Position position = (Position) other;
            if (x != position.x) return false;
            return y == position.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }

    private Direction direction = Direction.UP;
    private Position position = new Position(0, 0);
    private Map<Position, Integer> tiles = new HashMap<>();
    private Deque<Integer> input = new ArrayDeque<>();
    private Deque<Integer> output = new ArrayDeque<>();

    HullRobot(int color) {
        //set the color of the first tile
        output.addLast(color);
    }

    public void loadCommands(int...commands) {
        Arrays.stream(commands).forEach(input::addLast);
    }

    public Integer getOutput() {
        return output.pollFirst();
    }

    public void run() {
        while (input.size() >= 2) {
            paintTile();
            moveAround();
            updateOutput();
        }
    }

    private void paintTile() {
        tiles.put(new Position(position), input.pollFirst());
    }

    private void moveAround() {
        switch(input.pollFirst()) {
            case 0:
                turnLeft();
                break;
            case 1:
                turnRight();
                break;
            default:
                break;
        }
        position.move(direction.getVector());
    }

    private void updateOutput() {
        output.addLast(tiles.getOrDefault(position, 0));
    }

    private void turnLeft() {
        switch(direction) {
            case UP:
                direction = Direction.LEFT;
                break;
            case DOWN:
                direction = Direction.RIGHT;
                break;
            case RIGHT:
                direction = Direction.UP;
                break;
            case LEFT:
                direction = Direction.DOWN;
                break;
        }
    }

    private void turnRight() {
        switch(direction) {
            case UP:
                direction = Direction.RIGHT;
                break;
            case DOWN:
                direction = Direction.LEFT;
                break;
            case RIGHT:
                direction = Direction.DOWN;
                break;
            case LEFT:
                direction = Direction.UP;
                break;
        }
    }

    public int getNumPaintedTiles() {
        return tiles.keySet().size();
    }

    public void printId() {
        Set<Position> positions = tiles.keySet();
        int xMin = positions.stream().mapToInt(Position::getX).min().getAsInt();
        int xMax = positions.stream().mapToInt(Position::getX).max().getAsInt();
        int yMin = positions.stream().mapToInt(Position::getY).min().getAsInt();
        int yMax = positions.stream().mapToInt(Position::getY).max().getAsInt();

        for (int y = yMin; y <= yMax; y++) {
            for (int x = xMin; x <= xMax; x++) {
                int num = tiles.getOrDefault(new Position(x, y), 0);
                System.out.print(num == 0 ? " " : "|");
            }
            System.out.println();
        }
    }
}
