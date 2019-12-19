package adventOfCode;

import java.util.*;
import java.util.stream.Collectors;

public class RepairDroid extends Device {
    private Position position = new Position(0, 0);
    private Deque<Position> path = new ArrayDeque<>();
    private Deque<Direction> directions = new ArrayDeque<>();
    private Position station;

    RepairDroid() {
        super(1);
        updateMap(new Position(position), 1);
        path.addLast(new Position(position));
    }

    public void run() {
        while (hasInput()) {
            updateStatus(getInput());
        }
        updateOutput();
    }

    private void updateOutput() {
        Integer output = exploreMap();
        if (output != null) {
            super.updateOutput(output);
        }
    }

    private Integer exploreMap() {
        List<Direction> neighbors = getDirections(position);
        if (neighbors.isEmpty()) {
            if (position.equals(new Position(0, 0))) {
                return null;
            } else {
                path.removeLast();
                return directions.removeLast().getOpposite();
            }
        } else {
            Direction neighbor = neighbors.get((int)(Math.random() * neighbors.size()));
            directions.addLast(neighbor);
            path.addLast(neighbor.toPosition(position));
            return neighbor.getCommand();
        }
    }

    private void updateStatus(int status) {
        switch (status) {
            case 0:
                updateMap(new Position(path.removeLast()), 0);//wall
                directions.removeLast();
                break;
            case 1:
                updateMap(new Position(path.getLast()), 1);//empty space
                position = new Position(path.getLast());
                break;
            case 2:
                updateMap(new Position(path.getLast()), 2);//oxygen station
                position = new Position(path.getLast());
                station = path.getLast();
                System.out.println("Fewest movements: " + directions.size());
                break;
            default:
                break;
        }
    }

    private List<Direction> getDirections(Position p) {
        return Arrays.stream(Direction.values())
                .filter(d -> !getMapKeys().contains(d.toPosition(p)))
                .collect(Collectors.toList());
    }

    public void getTime() {
        int time = 0;
        Set<Position> locations = new HashSet<>(Arrays.asList(station));
        while (getMapValues().contains(1)) {
            locations = locations.stream()
                    .flatMap(o -> getEmptyNeighbors(o).stream())
                    .collect(Collectors.toSet());
            locations.forEach(l -> updateMap(l, 2));
            time++;
            //printMap();
        }
        System.out.println("Filling time: " + time);
    }

    private List<Position> getEmptyNeighbors(Position p) {
        return Arrays.stream(Direction.values())
                .map(d -> d.toPosition(p))
                .filter(d -> getMapValue(d) == 1)
                .collect(Collectors.toList());
    }

    public void printMap() {
        Position[] dims = getMapDimensions();
        Position min = dims[0];
        Position max = dims[1];
        for (int y = max.getY(); y >= min.getY(); y--) {
            for (int x = min.getX(); x <= max.getX(); x++) {
                Position pos = new Position(x, y);
                if (pos.equals(position)) {
                    System.out.print("D");
                } else {
                    int num = getMapValue(pos);
                    System.out.print(num == 0 ? "#" : //unknown or wall
                                    num == 1 ? "." :
                                            "O");
                }
            }
            System.out.println();
        }
    }
}
