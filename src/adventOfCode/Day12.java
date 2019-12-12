package adventOfCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day12 {
    static class Vector3 {
        private int x;
        private int y;
        private int z;

        Vector3 (int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        int get(int idx) {
            switch(idx) {
                case 0:
                    return x;
                case 1:
                    return y;
                case 2:
                    return z;
                default:
                    return Integer.MIN_VALUE;
            }
        }

        void add(Vector3 other) {
            this.x += other.x;
            this.y += other.y;
            this.z += other.z;
        }

        int getEnergy() {
            return Math.abs(x) + Math.abs(y) + Math.abs(z);
        }

        @Override
        public String toString() {
            return "<" + "x=" + x + ", y=" + y + ", z=" + z + ">";
        }
    }

    static class Moon {
        private Vector3 pos;
        private Vector3 vel = new Vector3(0, 0, 0);

        Moon (int x, int y, int z) {
            pos = new Vector3(x, y, z);
        }

        Vector3 getPos() {
            return pos;
        }

        Vector3 getVel() {
            return vel;
        }

        public void applyGravity(Vector3 gravity) {
            vel.add(gravity);
        }

        public void applyVelocity() {
            pos.add(vel);
        }

        public int getEnergy() {
            return pos.getEnergy() * vel.getEnergy();
        }

        @Override
        public String toString() {
            return "pos=" + pos + ", vel=" + vel;
        }
    }

    private static List<Moon> getMoons(String filename) {
        List<Moon> moons = new ArrayList<>();
        Pattern pattern = Pattern.compile("<x=(?<x>-?\\d+),\\sy=(?<y>-?\\d+),\\sz=(?<z>-?\\d+)>");
        try (Scanner input = new Scanner(new File(filename))) {
            while (input.hasNextLine()) {
                String position = input.nextLine();
                Matcher matcher = pattern.matcher(position);
                if (matcher.matches()) {
                    int x = Integer.parseInt(matcher.group("x"));
                    int y = Integer.parseInt(matcher.group("y"));
                    int z = Integer.parseInt(matcher.group("z"));
                    moons.add(new Moon(x, y, z));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return moons;
    }

    private static List<List<Integer>> getMoonPoses(List<Moon> moons) {
        return IntStream.rangeClosed(0, 2)
                .mapToObj(i -> moons.stream().map(m -> m.getPos().get(i)).collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private static List<Integer> posToGravity(List<Integer> pos) {
        return pos.stream().mapToInt(p -> pos.stream()
                .mapToInt(o -> o.compareTo(p)).sum()).boxed().collect(Collectors.toList());
    }

    private static List<List<Integer>> posesToGravity(List<List<Integer>> pos) {
        return IntStream.rangeClosed(0, 2)
                .mapToObj(i -> posToGravity(pos.get(i))).collect(Collectors.toList());
    }

    private static void updateMoons(List<Moon> moons) {
        List<List<Integer>> gravity = posesToGravity(getMoonPoses(moons));
        for (int i = 0; i < 4; i++) {
            Moon moon = moons.get(i);
            int x = gravity.get(0).get(i);
            int y = gravity.get(1).get(i);
            int z = gravity.get(2).get(i);
            moon.applyGravity(new Vector3(x, y, z));
            moon.applyVelocity();
        }
    }

    private static int getEnergy(List<Moon> moons, int numCycles) {
        IntStream.range(0, numCycles).forEach(i -> updateMoons(moons));
        return moons.stream().mapToInt(Moon::getEnergy).sum();
    }

    private static long gcd(long num1, long num2) {
        if (num1 == 0) return num2;
        return gcd(num2 % num1, num1);
    }

    private static long lcm(long num1, long num2) {
        return (num1 * num2) / gcd(num1, num2);
    }

    private static String[] getStates(List<Moon> moons) {
        return IntStream.rangeClosed(0, 2)
                .mapToObj(i -> moons.stream()
                        .map(m -> m.getPos().get(i) + String.valueOf(m.getVel().get(i)))
                        .collect(Collectors.joining("_")))
                .toArray(String[]::new);
    }

    private static long getNumCycles(List<Moon> moons) {
        long[] cycles = new long[3];
        HashSet<String>[] states = new HashSet[]{new HashSet<>(), new HashSet<>(), new HashSet<>()};
        String[] state = getStates(moons);
        for (int i = 0; i < 3; i++)
            states[i].add(state[i]);
        long cycle = 1L;
        while (cycles[0] == 0 || cycles[1] == 0 || cycles[2] == 0) {
            updateMoons(moons);
            state = getStates(moons);
            for (int i = 0; i < 3; i ++) {
                if (cycles[i] == 0) {
                    if (states[i].contains(state[i])) {
                        cycles[i] = cycle;
                    } else {
                        states[i].add(state[i]);
                    }
                }
            }
            cycle++;
        }
        return lcm(lcm(cycles[0], cycles[1]), cycles[2]);
    }

    public static void main(String[] args) {
        //part 1
        System.out.println(getEnergy(getMoons("day12.txt"), 1000));
        //part 2
        System.out.println(getNumCycles(getMoons("day12.txt")));
    }
}
