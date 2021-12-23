package day6;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class day6 {
    public static void main(String[] args) {
        List<String> inputs = new ArrayList<>();

        try {
            inputs.addAll(Files.readAllLines(Paths.get(day6.class.getResource("puzzleinput.txt").toURI())));
        } catch (Exception ex) {
            System.out.println("An error occurred attempting to read your input file.");
        }

        System.out.println("The solution to part 1 is: " + calculateLanternFish(inputs, 80));
        System.out.println("The solution to part 2 is: " + calculateLanternFish(inputs, 256));
    }

    public static BigInteger calculateLanternFish(List<String> inputs, int days) {
        Map<Integer, BigInteger> daysToFishMap = new HashMap<>();
        daysToFishMap.put(0, BigInteger.ZERO);

        Arrays.stream(inputs.get(0).split(",")).map(input -> Integer.parseInt(input)).forEach(fish -> {
            if (!daysToFishMap.containsKey(fish)) {
                daysToFishMap.put(fish, BigInteger.ZERO);
            }

            daysToFishMap.put(fish, daysToFishMap.get(fish).add(BigInteger.ONE));
        });

        for (int k = 0; k < days; k++) {
            BigInteger newFish = daysToFishMap.get(0);

            for (int i = 1; i <= 8; i++) {
                daysToFishMap.put(i - 1, daysToFishMap.getOrDefault(i, BigInteger.ZERO));
            }

            daysToFishMap.put(8, newFish);
            daysToFishMap.put(6, daysToFishMap.getOrDefault(6, BigInteger.ZERO).add(newFish));
        }

        BigInteger totalFish = BigInteger.ZERO;
        for (BigInteger daysTotal : daysToFishMap.values()) {
            totalFish = totalFish.add(daysTotal);
        }

        return totalFish;
    }
}
