package adventOfCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Day14 {

    private static long getMinOre(Map<String, Map<String, Integer>> ingredients, Map<String, Integer> amounts, long fuelRequired) {
        String fuel = "FUEL";
        String ore = "ORE";
        Map<String, Long> resources = new HashMap<>();
        resources.put(fuel, fuelRequired);
        while (true) {
            String chemical = resources.keySet().stream().filter(i -> !i.equals(ore) && resources.get(i) > 0)
                    .findFirst().orElse("");
            if (chemical.isEmpty()) break;
            long amount = (long) Math.ceil(resources.get(chemical) / (double)amounts.get(chemical));
            resources.put(chemical, resources.get(chemical) - amounts.get(chemical) * amount);
            for (Map.Entry<String, Integer> ing : ingredients.get(chemical).entrySet()) {
                resources.put(ing.getKey(), resources.getOrDefault(ing.getKey(), 0L) + ing.getValue() * amount);
            }
        }
        return resources.get(ore);
    }

    /*private static long getMaxFuelBinary(Map<String, Map<String, Integer>> ingredients, Map<String, Integer> amounts) {
        long oreAvailable = 1_000_000_000_000L;
        long min = 0;
        long max = oreAvailable;
        while (min < max) {
            long mid = (min + max) / 2;
            long amount = getMinOre(ingredients, amounts, mid);
            if (amount > oreAvailable) {
                max = mid - 1;
            }
            else if (amount < oreAvailable) {
                min = mid;
            }
        }
        return min;
    }*/

    private static long getMaxFuel(Map<String, Map<String, Integer>> ingredients, Map<String, Integer> amounts) {
        long fuel = 1;
        long oreAvailable = 1_000_000_000_000L;
        while (true) {
            long amount = getMinOre(ingredients, amounts, fuel + 1);
            if (amount > oreAvailable) {
                return fuel;
            } else {
                fuel = Math.max(fuel + 1, (long) Math.floor((fuel + 1) * oreAvailable / (double) amount));
            }
        }
    }

    public static void main(String[] args) {
        Map<String, Map<String, Integer>> ingredients = new HashMap<>();
        Map<String, Integer> amounts = new HashMap<>();
        try (Scanner input = new Scanner(new File("day14.txt"))){
            while (input.hasNext()) {
                String[] reaction = input.nextLine().trim().split("\\s=>\\s");
                String[] element = reaction[1].split(" ");
                amounts.put(element[1], Integer.parseInt(element[0]));
                Map<String, Integer> resources = Arrays.stream(reaction[0].split(", "))
                        .map(r -> r.split(" "))
                        .collect(Collectors.toMap(r -> r[1], r -> Integer.parseInt(r[0])));
                ingredients.put(element[1], resources);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        //part 1
        System.out.println(getMinOre(ingredients, amounts, 1));
        //part 2
        System.out.println(getMaxFuel(ingredients, amounts));
    }
}
