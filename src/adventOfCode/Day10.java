package adventOfCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Day10 {

    static class Asteroid {
        private int x;
        private int y;

        Asteroid(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public double getDistance(Asteroid other) {
            return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
        }

        public Asteroid getClosest(List<Asteroid> asteroids) {
            return asteroids.stream().min(Comparator.comparing(this::getDistance)).get();
        }

        public double getAngle(Asteroid other) {
            return (2 * Math.PI - Math.atan2(this.x - other.x, this.y - other.y)) % (2 * Math.PI);
        }

        public int getBet() {
            return x * 100 + y;
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
            Asteroid asteroid = (Asteroid) other;
            if (x != asteroid.x) return false;
            return y == asteroid.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }

    private static List<Asteroid> getAsteroids(String filename) {
        List<Asteroid> asteroids = new ArrayList<>();
        try (Scanner input = new Scanner(new File(filename))) {
            int y = 0;
            while (input.hasNext()) {
                char[] row = input.next().trim().toCharArray();
                for (int x = 0; x < row.length; x++) {
                    if (row[x] == '#')
                        asteroids.add(new Asteroid(x, y));
                }
                y++;
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return asteroids;
    }

    private static int getNumVisible(Asteroid a1, List<Asteroid> asteroids) {
        return asteroids.stream().filter(a -> !a.equals(a1)).map(a1::getAngle).collect(Collectors.toSet()).size();
    }

    private static int getMostVisible(List<Asteroid> asteroids) {
        return asteroids.stream().mapToInt(a -> getNumVisible(a, asteroids)).max().getAsInt();
    }

    private static Asteroid getBestPosition(List<Asteroid> asteroids) {
        return asteroids.stream().max(Comparator.comparingDouble(a -> getNumVisible(a, asteroids))).get();
    }

    private static int getBet(List<Asteroid> asteroids) {
        Asteroid station = getBestPosition(asteroids);
        asteroids.remove(station);
        Map<Double, List<Asteroid>> order = asteroids.stream().collect(Collectors.groupingBy(station::getAngle));
        List<Double> angles = new ArrayList<>(order.keySet());
        Collections.sort(angles);
        //ugly shit :(
        int i = 1;
        while (true) {
            for (Double angle : angles) {
                List<Asteroid> targets = order.get(angle);
                if (!targets.isEmpty()) {
                    Asteroid asteroid = station.getClosest(targets);
                    if (i == 200) return asteroid.getBet();
                    targets.remove(asteroid);
                    order.put(angle, targets);
                    i++;
                }
            }
        }
    }

    public static void main(String[] args) {
        //part 1
        System.out.println(getMostVisible(getAsteroids("day10.txt")));
        //part 2
        System.out.println(getBet(getAsteroids("day10.txt")));
    }
}
