package day5;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class day5 {
    public static void main(String[] args) {
        List<String> inputs = new ArrayList<>();

        try {
            inputs.addAll(Files.readAllLines(Paths.get(day5.class.getResource("puzzleinput.txt").toURI())));
        } catch (Exception ex) {
            System.out.println("An error occurred attempting to read your input file.");
        }

        System.out.println("The solution to part 1 is: " + calculateStraightLineOverlaps(inputs));
        System.out.println("The solution to part 2 is: " + calculateAllLineOverlaps(inputs));
    }

    public static int calculateStraightLineOverlaps(List<String> inputs) {
        Map<String, Integer> pointToOverlapsMap = new HashMap<>();

        inputs.forEach(input -> {
            String[] points = input.split(" -> ");
            int[] point1 = Arrays.stream(points[0].split(",")).mapToInt(Integer::parseInt).toArray();
            int[] point2 = Arrays.stream(points[1].split(",")).mapToInt(Integer::parseInt).toArray();

            if (point1[0] == point2[0]) {
                int smallerY = Math.min(point1[1], point2[1]);
                int biggerY = Math.max(point1[1], point2[1]);

                for (int i = smallerY; i <= biggerY; i++) {
                    if (!pointToOverlapsMap.containsKey(point1[0] + "," + i)) {
                        pointToOverlapsMap.put(point1[0] + "," + i, 0);
                    }

                    pointToOverlapsMap.put(point1[0] + "," + i, pointToOverlapsMap.get(point1[0] + "," + i) + 1);
                }
            } else if (point1[1] == point2[1]) {
                int smallerX = Math.min(point1[0], point2[0]);
                int biggerX = Math.max(point1[0], point2[0]);

                for (int i = smallerX; i <= biggerX; i++) {
                    if (!pointToOverlapsMap.containsKey(i + "," + point1[1])) {
                        pointToOverlapsMap.put(i + "," + point1[1], 0);
                    }

                    pointToOverlapsMap.put(i + "," + point1[1], pointToOverlapsMap.get(i + "," + point1[1]) + 1);
                }
            }
        });

        return (int) pointToOverlapsMap.values().stream().filter(val -> val > 1).count();
    }

    public static int calculateAllLineOverlaps(List<String> inputs) {
        Map<String, Integer> pointToOverlapsMap = new HashMap<>();

        inputs.forEach(input -> {
            String[] points = input.split(" -> ");
            int[] point1 = Arrays.stream(points[0].split(",")).mapToInt(Integer::parseInt).toArray();
            int[] point2 = Arrays.stream(points[1].split(",")).mapToInt(Integer::parseInt).toArray();

            if (point1[0] == point2[0]) {
                int smallerY = Math.min(point1[1], point2[1]);
                int biggerY = Math.max(point1[1], point2[1]);

                for (int i = smallerY; i <= biggerY; i++) {
                    if (!pointToOverlapsMap.containsKey(point1[0] + "," + i)) {
                        pointToOverlapsMap.put(point1[0] + "," + i, 0);
                    }

                    pointToOverlapsMap.put(point1[0] + "," + i, pointToOverlapsMap.get(point1[0] + "," + i) + 1);
                }
            } else if (point1[1] == point2[1]) {
                int smallerX = Math.min(point1[0], point2[0]);
                int biggerX = Math.max(point1[0], point2[0]);

                for (int i = smallerX; i <= biggerX; i++) {
                    if (!pointToOverlapsMap.containsKey(i + "," + point1[1])) {
                        pointToOverlapsMap.put(i + "," + point1[1], 0);
                    }

                    pointToOverlapsMap.put(i + "," + point1[1], pointToOverlapsMap.get(i + "," + point1[1]) + 1);
                }
            } else {
                int dir;
                int dist = Math.max(point1[0], point2[0]) - Math.min(point1[0], point2[0]);

                if (point1[0] < point2[0]) {
                    if (point1[1] < point2[1]) {
                        dir = 0;
                    } else {
                        dir = 1;
                    }
                } else {
                    if (point1[1] < point2[1]) {
                        dir = 2;
                    } else {
                        dir = 3;
                    }
                }

                for (int i = 0; i <= dist; i++) {
                    String key = "";

                    switch (dir) {
                        case 0:
                            key = (point1[0] + i) + "," + (point1[1] + i);
                            break;
                        case 1:
                            key = (point1[0] + i) + "," + (point1[1] - i);
                            break;
                        case 2:
                            key = (point1[0] - i) + "," + (point1[1] + i);
                            break;
                        case 3:
                            key = (point1[0] - i) + "," + (point1[1] - i);
                            break;
                    }

                    if (!pointToOverlapsMap.containsKey(key)) {
                        pointToOverlapsMap.put(key, 0);
                    }

                    pointToOverlapsMap.put(key, pointToOverlapsMap.get(key) + 1);
                }
            }
        });

        return (int) pointToOverlapsMap.values().stream().filter(val -> val > 1).count();
    }
}