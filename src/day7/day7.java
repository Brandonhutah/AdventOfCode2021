package day7;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class day7 {
    public static void main(String[] args) {
        List<Integer> inputs = new ArrayList<>();

        try {
            inputs = Arrays.stream(Files.readAllLines(Paths.get(day7.class.getResource("puzzleinput.txt").toURI())).get(0).split(",")).map(input -> Integer.parseInt(input)).collect(Collectors.toList());
        } catch (Exception ex) {
            System.out.println("An error occurred attempting to read your input file.");
        }

        System.out.println("The solution to part 1 is: " + calculateMinFuelRequired(inputs));
        System.out.println("The solution to part 2 is: " + calculateMinFuelRequiredExponential(inputs));
    }

    public static int calculateMinFuelRequired(List<Integer> inputs) {
        Collections.sort(inputs);
        int maxValue = inputs.get(inputs.size() - 1);
        int minFuel = 0;

        for (int i = 0; i < maxValue; i++) {
            int fuelRequired = 0;
            for (int position : inputs) {
                if (position < i) {
                    fuelRequired += i - position;
                } else {
                    fuelRequired += position - i;
                }
            }

            if (i == 0) {
                minFuel = fuelRequired;
            } else if (fuelRequired < minFuel) {
                minFuel = fuelRequired;
            }
        }

        return minFuel;
    }

    public static int calculateMinFuelRequiredExponential(List<Integer> inputs) {
        Collections.sort(inputs);
        int maxValue = inputs.get(inputs.size() - 1);
        int minFuel = 0;

        for (int i = 0; i < maxValue; i++) {
            int fuelRequired = 0;
            for (int position : inputs) {
                int steps;
                if (position < i) {
                    steps = i - position;
                } else {
                    steps = position - i;
                }

                do {
                    fuelRequired += steps;
                    steps--;
                } while (steps > 0);
            }

            if (i == 0) {
                minFuel = fuelRequired;
            } else if (fuelRequired < minFuel) {
                minFuel = fuelRequired;
            }
        }

        return minFuel;
    }
}
