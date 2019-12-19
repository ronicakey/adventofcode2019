package adventOfCode;

import java.util.*;

public abstract class Device {
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

        public void add(Position other) {
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

    public enum Direction {
        DOWN(1, 2, new Position(0, 1)),
        UP(2, 1, new Position(0, -1)),
        LEFT(3, 4, new Position(-1, 0)),
        RIGHT(4, 3, new Position(1, 0));

        private final int command;
        private final int opposite;
        private final Position vector;

        Direction(int command, int opposite, Position vector) {
            this.command = command;
            this.opposite = opposite;
            this.vector = vector;
        }

        public Position getVector() {
            return vector;
        }

        public int getCommand() {
            return command;
        }

        public int getOpposite() {
            return opposite;
        }

        public Position toPosition(Position p) {
            Position position = new Position(p);
            position.add(vector);
            return position;
        }
    }

    private Map<Position, Integer> map = new HashMap<>();
    private Deque<Integer> input = new ArrayDeque<>();
    private Deque<Integer> output = new ArrayDeque<>();
    private final int inputSize;

    Device () {
        this.inputSize = 1;
    }

    Device (int inputSize) {
        this.inputSize = inputSize;
    }

    public int getInputSize() {
        return inputSize;
    }

    public void loadCommands(int...commands) {
        Arrays.stream(commands).forEach(input::addLast);
    }

    protected boolean hasInput() {
        return input.size() >= getInputSize();
    }

    protected int getInput() {
        return input.removeFirst();
    }

    protected void updateOutput(int response) {
        output.addLast(response);
    }

    public boolean hasOutput() {
        return !output.isEmpty();
    }

    public Integer getOutput() {
        return output.removeFirst();
    }

    public abstract void run();

    protected void updateMap(Position position, int value) {
        map.put(position, value);
    }

    protected int getMapValue(Position position) {
        return map.getOrDefault(position, 0);
    }

    protected Set<Position> getMapKeys() {
        return Collections.unmodifiableSet(map.keySet());
    }

    protected Collection<Integer> getMapValues() {
        return Collections.unmodifiableCollection(map.values());
    }

    protected Position[] getMapDimensions() {
        Set<Position> positions = map.keySet();
        int xMin = positions.stream().mapToInt(Position::getX).min().getAsInt();
        int xMax = positions.stream().mapToInt(Position::getX).max().getAsInt();
        int yMin = positions.stream().mapToInt(Position::getY).min().getAsInt();
        int yMax = positions.stream().mapToInt(Position::getY).max().getAsInt();

        return new Position[]{new Position(xMin, yMin), new Position(xMax, yMax)};
    }

    public abstract void printMap();

}
