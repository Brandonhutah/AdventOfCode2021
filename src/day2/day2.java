package day2;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class day2 {
    public static void main(String[] args) {
        List<String> inputs = new ArrayList<>();

        try {
            inputs.addAll(Files.readAllLines(Paths.get(day2.class.getResource("puzzleinput.txt").toURI())));
        } catch (Exception ex) {
            System.out.println("An error occurred attempting to read your input file.");
        }

        System.out.println("The solution to part 1 is: " + calculatePositionMultiplier(inputs));
        System.out.println("The solution to part 2 is: " + calculatePositionMultiplierWithAim(inputs));
    }

    public static int calculatePositionMultiplier(List<String> inputs) {
        int horizontalPosition = 0;
        int depth = 0;

        for (String input : inputs) {
            String[] command = input.split(" ");

            switch (command[0]) {
                case "forward":
                    horizontalPosition += Integer.parseInt(command[1]);
                    break;
                case "up":
                    depth -= Integer.parseInt(command[1]);
                    break;
                case "down":
                    depth += Integer.parseInt(command[1]);
                    break;
            }
        }

        return horizontalPosition * depth;
    }

    public static int calculatePositionMultiplierWithAim(List<String> inputs) {
        int horizontalPosition = 0;
        int depth = 0;
        int aim = 0;

        for (String input : inputs) {
            String[] command = input.split(" ");

            switch (command[0]) {
                case "forward":
                    horizontalPosition += Integer.parseInt(command[1]);
                    depth += aim * Integer.parseInt(command[1]);
                    break;
                case "up":
                    aim -= Integer.parseInt(command[1]);
                    break;
                case "down":
                    aim += Integer.parseInt(command[1]);
                    break;
            }
        }

        return horizontalPosition * depth;
    }
}
