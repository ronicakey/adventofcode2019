package adventOfCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day08 {

    private static String[] getLayers(String filename, int layerSize) {
        try (Scanner input = new Scanner(new File(filename))){
            return input.next().split("(?<=\\G.{" + layerSize + "})");
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return new String[]{};
    }

    private static long getNumDigits(String layer, int unicode) {
        return layer.chars().filter(i -> i == unicode).count();
    }

    private static String getMinZeroLayer(String[] layers) {
        return Arrays.stream(layers).min(Comparator.comparingLong(layer -> getNumDigits(layer, 48))).get();
    }

    private static long getAnswer(String[] layers) {
        String layer = getMinZeroLayer(layers);
        return getNumDigits(layer, 49) * getNumDigits(layer, 50);
    }

    private static String getPixelColor(String[] layers, int layerSize) {
        return IntStream.range(0, layerSize)
                .mapToObj(p -> Arrays.stream(layers)
                        .map(layer -> layer.charAt(p))
                        .filter(i -> (i == '0' | i == '1'))
                        .findFirst().get().toString())
                .collect(Collectors.joining());
    }

    private static void getMessage(String[] layers, int width, int height) {
        String pixels = getPixelColor(layers, width * height);
        String[] rows = pixels
                .replaceAll("0", " ") //easier to read
                .split("(?<=\\G.{" + width + "})");
        Arrays.stream(rows).forEach(System.out::println);
    }

    public static void main(String[] args) {
        int width = 25;
        int height = 6;
        String[] layers = getLayers("day8.txt", width * height);

        //part 1
        System.out.println(getAnswer(layers));
        //part2
        getMessage(layers, width, height);
    }
}
