import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day03 {

    static class Dot {
        private int x = 0;
        private int y = 0;
        private int steps = 0;

        Dot() {}

        Dot(Dot dot, String dir) {
            switch(dir) {
                case "U":
                    this.x = dot.x;
                    this.y = dot.y + 1;
                    this.steps = dot.steps + 1;
                    break;
                case "D":
                    this.x = dot.x;
                    this.y = dot.y - 1;
                    this.steps = dot.steps + 1;
                    break;
                case "R":
                    this.x = dot.x + 1;
                    this.y = dot.y;
                    this.steps = dot.steps + 1;
                    break;
                case "L":
                    this.x = dot.x - 1;
                    this.y = dot.y;
                    this.steps = dot.steps + 1;
                    break;
            }
        }

        public int getDistance(Dot other) {
            return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
        }

        public int getSteps() {
            return steps;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Dot) {
                Dot other = (Dot) obj;
                return this.x == other.x && this.y == other.y;
            }
            return false;
        }

        @Override
        public String toString() {
            return String.format("x: %d y: %d", x, y);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private static String[] getInput(String filename) {
        int numLines = 2;
        String[] lines = new String[numLines];
        try (Scanner input = new Scanner(new File(filename))){
            for (int i = 0; i < numLines; i++) {
                lines[i] = input.nextLine().trim();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return lines;
    }

    private static Set<Dot> getDotSet(String line) {
        Set<Dot> dots = new HashSet<>();
        Pattern pattern = Pattern.compile("([RLDU])(\\d+)");
        Matcher matcher = pattern.matcher(line);
        Dot prevDot = new Dot();
        while(matcher.find()) {
            for (int i = 1; i <= Integer.parseInt(matcher.group(2)); i++) {
                Dot dot = new Dot(prevDot, matcher.group(1));
                dots.add(dot);
                prevDot = dot;
            }
        }
        return dots;
    }

    private static Set<Dot> intersectFromSets(Set<Dot> l1, Set<Dot> l2) {
        Set<Dot> s1 = new HashSet<>(l1);
        s1.retainAll(new HashSet<>(l2));
        return s1;
    }

    private static int getMinDist(Set<Dot> s1, Set<Dot> s2) {
        Set<Dot> intersect = intersectFromSets(s1, s2);

        return intersect.stream().mapToInt(d -> d.getDistance(new Dot())).min().getAsInt();
    }

    private static int getMinSteps(Set<Dot> s1, Set<Dot> s2) {
        Set<Dot> intersect1 = intersectFromSets(s1, s2);
        Set<Dot> intersect2 = intersectFromSets(s2, s1);

        Map<Dot, Integer> map1 = setToMap(intersect1);
        Map<Dot, Integer> map2 = setToMap(intersect2);

        return map1.keySet().stream().mapToInt(dot -> map1.get(dot) + map2.get(dot)).min().getAsInt();
    }

    private static Map<Dot, Integer> setToMap(Set<Dot> set) {
        return set.stream().collect(Collectors.toMap(Function.identity(), Dot::getSteps));
    }

    public static void main(String[] args) {
        String[] lines = getInput("day3.txt");

        Set<Dot> dots1 = getDotSet(lines[0]);
        Set<Dot> dots2 = getDotSet(lines[1]);
        //part 1
        System.out.println(getMinDist(dots1, dots2));
        //part 2
        System.out.println(getMinSteps(dots1, dots2));

    }
}
