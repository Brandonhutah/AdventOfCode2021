package day1;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class driver {
    public static void main(String[] args) {
        List<Integer> inputs = new ArrayList<>();

        // get filepath for the puzzle input
        System.out.print("Please provide the filepath of the text document containing the puzzle input: ");
        String filePath = new Scanner(System.in).nextLine();

        try {
            for (String line : Files.readAllLines(Paths.get(filePath))) {
                inputs.add(Integer.parseInt(line));
            }
        } catch (Exception ex) {
            System.out.println("An error occurred attempting to read your input file.");
        }

        System.out.println("The solution to part 1 is: " + calculateIncreases(inputs));
        System.out.println("The solution to part 2 is: " + calculateSlidingWindowIncreases(inputs));
    }

    public static int calculateIncreases(List<Integer> inputs) {
        int increases = 0;
        int previousVal = 0;
        boolean firstLoop = true;

        for (int input : inputs) {
            if (!firstLoop) {
                if (input > previousVal) {
                    increases++;
                }
            }

            previousVal = input;

            firstLoop = false;
        }

        return increases;
    }

    public static int calculateSlidingWindowIncreases(List<Integer> inputs) {
        int increases = 0;
        int previousWindowSum = 0;
        boolean firstLoop = true;

        for (int i = 0; i < inputs.size(); i++) {
            if (inputs.size() > i + 2) {
                int currentSum = inputs.get(i) + inputs.get(i + 1) + inputs.get(i + 2);

                if (!firstLoop && currentSum > previousWindowSum) {
                    increases++;
                }

                firstLoop = false;
                previousWindowSum = currentSum;
            }
        }

        return increases;
    }
}
