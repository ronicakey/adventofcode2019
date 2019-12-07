package adventOfCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day06 {
    private static Map<String, List<String>> getSatellites(String filename) {
        Map<String, List<String>> objects = new HashMap<>();
        Pattern pattern = Pattern.compile("(\\w+)\\)(\\w+)");
        try (Scanner input = new Scanner(new File(filename))){
            while (input.hasNextLine()) {
                Matcher matcher = pattern.matcher(input.nextLine());
                while(matcher.find()) {
                    String center = matcher.group(1);
                    String satellite = matcher.group(2);
                    List<String> satellites = objects.getOrDefault(center, new ArrayList<>());
                    satellites.add(satellite);
                    objects.put(center, satellites);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return objects;
    }

    private static String getCenter(Map<String, List<String>> objects) {
        Set<String> satellites = objects.values().stream().flatMap(list -> list.stream())
                .collect(Collectors.toSet());
        return objects.keySet().stream().filter(k -> !satellites.contains(k)).findFirst().get();
    }

    private static Map<String, String> getOrbits(String filename) {
        Map<String, String> objects = new HashMap<>();
        Pattern pattern = Pattern.compile("(\\w+)\\)(\\w+)");
        try (Scanner input = new Scanner(new File(filename))){
            while (input.hasNextLine()) {
                Matcher matcher = pattern.matcher(input.nextLine());
                while(matcher.find()) {
                    String center = matcher.group(1);
                    String satellite = matcher.group(2);
                    objects.put(satellite, center);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return objects;
    }

    private static int getTotalPathLength(String filename) {
        Map<String, List<String>> objects = getSatellites(filename);
        String center = getCenter(objects);
        Map<String, Integer> pathLengths = new HashMap<>();
        pathLengths.put(center, 0);
        Deque<String> queue = new ArrayDeque<>();
        queue.addLast(center);
        while (!queue.isEmpty()) {
            String object = queue.pollFirst();
            if (objects.containsKey(object)) {
                for (String satellite : objects.get(object)) {
                    pathLengths.put(satellite, pathLengths.get(object) + 1);
                    queue.add(satellite);
                }
            }
        }
        return pathLengths.values().stream().reduce(Integer::sum).get();
    }

    private static int getPathLength(String filename) {
        Map<String, String> orbits = getOrbits(filename);
        List<Set<String>> paths = new ArrayList<>();
        String[] objects = {"YOU", "SAN"};
        for (String object : objects) {
            Set<String> path = new HashSet<>();
            object = orbits.get(object);
            while (object != null) {
                path.add(object);
                object = orbits.get(object);
            }
            paths.add(path);
        }

        Set<String> path = new HashSet<>(paths.get(0));
        path.addAll(paths.get(1));
        paths.get(0).retainAll(paths.get(1));
        path.removeAll(paths.get(0));
        return path.size();
    }

    public static void main(String[] args) {
        //part 1
        System.out.println(getTotalPathLength("day6.txt"));
        //part 2
        System.out.println(getPathLength("day6.txt"));
    }
}
