package adventOfCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class VacuumRobot extends Device {
    private Position position = new Position(0, 0);

    VacuumRobot() {}

    public void run() {
        while(hasInput()) {
            updateMap(getInput());
        }
    }

    private void updateMap(int input) {
        if (input == 10) {
            position = new Position(0, position.getY() + 1);
        } else {
            updateMap(new Position(position), input);
            position = new Position(position.getX() + 1, position.getY());
        }
    }

    public int getSum() {
        return getIntersections().stream().mapToInt(i -> i.getX() * i.getY()).sum();
    }

    public int getDust() {
        return getMapValue(new Position(0, getMapDimensions()[1].getY()));
    }

    private List<Position> getIntersections() {
        return getMapKeys().stream()
                .filter(k -> getMapValue(k) == 35 && isIntersection(k))
                .collect(Collectors.toList());
    }

    private boolean isIntersection(Position p) {
        return Arrays.stream(Direction.values())
                .map(d -> d.toPosition(p))
                .filter(d -> getMapValue(d) == 35)
                .count() == 4L;
    }

    public void getMovementRoutines() {
        uploadMovementFunctions(getMovementFunctions());
    }

    private void uploadMovementFunctions(List<String> functions) {
        functions.stream().flatMapToInt(f -> (f + "\n").chars()).boxed().forEach(this::updateOutput);
    }

    //todo: getting path from the map

    private List<String> getMovementFunctions() {
        String path = "L,6,R,12,R,8,R,8,R,12,L,12,R,8,R,12,L,12,L,6,R,12,R,8,R,12,L,12,L,4,L,4,L,6,R,12,R,8," +
                "R,12,L,12,L,4,L,4,L,6,R,12,R,8,R,12,L,12,L,4,L,4,R,8,R,12,L,12,";
        Pattern pattern = Pattern.compile("^(.{1,21})\\1*(.{1,21})(?:\\1|\\2)*(.{1,21})(?:\\1|\\2|\\3)*$");
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            String a = matcher.group(1);
            String b = matcher.group(2);
            String c = matcher.group(3);
            a = a.substring(0, a.length() - 1);
            b = b.substring(0, b.length() - 1);
            c = c.substring(0, c.length() - 1);
            String routine = path.substring(0, path.length() - 1)
                    .replaceAll(a, "A")
                    .replaceAll(b, "B")
                    .replaceAll(c, "C");
            return Arrays.asList(routine, a, b, c, "n");
        }
        return new ArrayList<>();
    }

    public void printMap() {
        Position[] dims = getMapDimensions();
        Position min = dims[0];
        Position max = dims[1];
        for (int y = min.getY(); y <= max.getY(); y++) {
            for (int x = min.getX(); x <= max.getX(); x++) {
                int num = getMapValue(new Position(x, y));
                System.out.print((char) num);
            }
            System.out.println();
        }
    }
}
