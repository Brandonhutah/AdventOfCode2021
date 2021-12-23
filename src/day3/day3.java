package day3;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class day3 {
    public static void main(String[] args) {
        List<String> inputs = new ArrayList<>();

        try {
            inputs.addAll(Files.readAllLines(Paths.get(day3.class.getResource("puzzleinput.txt").toURI())));
        } catch (Exception ex) {
            System.out.println("An error occurred attempting to read your input file.");
        }

        System.out.println("The solution to part 1 is: " + calculatePowerConsumption(inputs));
        System.out.println("The solution to part 2 is: " + calculateLifeSupport(inputs));
    }

    public static long calculatePowerConsumption(List<String> inputs) {
        Map<Integer, Map<Integer, Integer>> columnToBitsCount = new HashMap<>();
        StringBuilder gammaRate = new StringBuilder();
        StringBuilder epsilonRate = new StringBuilder();

        for (String input : inputs) {
            for (int i = 0; i < input.length(); i++) {
                int bit = Integer.parseInt(String.valueOf(input.charAt(i)));

                if (!columnToBitsCount.containsKey(i)) {
                    columnToBitsCount.put(i, new HashMap<>());
                }
                if (!columnToBitsCount.get(i).containsKey(bit)) {
                    columnToBitsCount.get(i).put(bit, 0);
                }

                columnToBitsCount.get(i).put(bit, columnToBitsCount.get(i).get(bit) + 1);
            }
        }

        for (Map<Integer, Integer> bitCount : columnToBitsCount.values()) {
            gammaRate.append(bitCount.get(0) > bitCount.get(1) ? "0" : "1");
            epsilonRate.append(bitCount.get(0) > bitCount.get(1) ? "1" : "0");
        }

        return Long.parseLong(gammaRate.toString(), 2) * Long.parseLong(epsilonRate.toString(), 2);
    }

    public static long calculateLifeSupport(List<String> inputs) {
        String oxygenRating = findSingleBinaryValue(inputs, 0, true);
        String cO2Rating = findSingleBinaryValue(inputs, 0, false);

        return Long.parseLong(oxygenRating, 2) * Long.parseLong(cO2Rating, 2);
    }

    public static String findSingleBinaryValue(List<String> inputs, int column, boolean mostCommon) {
        int bit0 = 0;
        int bit1 = 0;

        for (String input : inputs) {
            int bit = Integer.parseInt(String.valueOf(input.charAt(column)));

            if (bit == 0) {
                bit0++;
            } else {
                bit1++;
            }
        }

        String mostCommonBit = bit0 > bit1 ? "0" : "1";

        int finalColumn = column;
        List<String> filteredInputs = inputs.stream().filter(input -> {
            boolean equals = String.valueOf(input.charAt(finalColumn)).equals(mostCommonBit);
            if (mostCommon) {
                return equals;
            } else {
                return !equals;
            }
        }).collect(Collectors.toList());

        if (filteredInputs.size() > 1) {
            return findSingleBinaryValue(filteredInputs, ++column, mostCommon);
        } else {
            return filteredInputs.get(0);
        }
    }
}

