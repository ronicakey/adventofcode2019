package adventOfCode;


public class ArcadeGame extends Device {
    private int ballPositionX = 0;
    private int paddlePositionX = 0;
    private int score = 0;

    ArcadeGame() {
        super(3);
    }

    public void run() {
        while (hasInput()) {
            int x = getInput();
            int y = getInput();
            int object = getInput();
            if (x == -1) {
                score = object;
            } else {
                if (object == 3) {
                    paddlePositionX = x;
                }
                else if (object == 4) {
                    ballPositionX = x;
                    updateOutput(Integer.compare(ballPositionX, paddlePositionX));
                }
                updateMap(new Position(x, y), object);
            }
        }
    }

    public long getNumBlocks() {
        return getMapValues().stream().filter(v -> v == 2).count();
    }

    public int getScore() {
        return score;
    }

    public void printMap() {
        for (int y = 0; y < 25; y++) {
            for (int x = 0; x < 41; x++) {
                int num = getMapValue(new Position(x, y));
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
