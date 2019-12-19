package adventOfCode;


public class HullRobot extends Device {
    private Direction direction = Direction.UP;
    private Position position = new Position(0, 0);

    HullRobot(int color) {
        super(2);
        //set the color of the first tile
        updateOutput(color);
    }

    public void run() {
        while (hasInput()) {
            paintTile();
            moveAround();
            updateOutput(getMapValue(position));
        }
    }

    private void paintTile() {
        updateMap(new Position(position), getInput());
    }

    private void moveAround() {
        switch(getInput()) {
            case 0:
                turnLeft();
                break;
            case 1:
                turnRight();
                break;
            default:
                break;
        }
        position.add(direction.getVector());
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
        return getMapKeys().size();
    }

    public void printMap() {
        Position[] dims = getMapDimensions();
        Position min = dims[0];
        Position max = dims[1];
        for (int y = min.getY(); y <= max.getY(); y++) {
            for (int x = min.getX(); x <= max.getX(); x++) {
                int num = getMapValue(new Position(x, y));
                System.out.print(num == 0 ? " " : "|");
            }
            System.out.println();
        }
    }
}
