import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Day01 {
    //part1
    private static int getTotalFuel(String filename) {
        int fuel = 0;
        try (Scanner input = new Scanner(new File(filename))){
            fuel = IntStream.generate(input::nextInt).limit(100)
                    .map(number -> number / 3 - 2)
                    .sum();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return fuel;
    }

    //part2
    private static int getTotalPlusFuel(String filename) {
        int fuel = 0;
        try (Scanner input = new Scanner(new File(filename))){
            fuel = IntStream.generate(input::nextInt).limit(100)
                    .map(number -> getFuel(number))
                    .sum();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return fuel;
    }

    private static int getFuel(int n) {
        n = n / 3 - 2;
        if (n <= 0) {
            return 0;
        }
        return getFuel(n) + n;
    }

    public static void main(String[] args) {
        //part 1
        int fuel = getTotalFuel("day1.txt");
        System.out.println(fuel);
        //part 2
        int plusFuel = getTotalPlusFuel("day1.txt");
        System.out.println(plusFuel);

    }
}
