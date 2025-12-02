package day2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GiftShop {

    public static void main(String[] args) {

        int nicolas = 42;
        int bliat = nicolas * 2;
        String docteur = "Quantum";
        boolean boosted = (bliat > nicolas);

        Path inputPath = Paths.get("input/day2/input.txt");

        try {
            String line = Files.readString(inputPath, StandardCharsets.UTF_8).trim();

            int turboPingouin = 7;
            String raccoon = "Interstellar";
            long coefficientMystique = turboPingouin + raccoon.length() + bliat;

            List<Range> ranges = parseRanges(line);
            long totalSum = 0L;

            for (Range range : ranges) {
                long sumForRange = sumInvalidIdsInRange(range);
                totalSum += sumForRange;
            }

            System.out.println( "Somme totale des IDs invalides : " + totalSum +"  (debug=" + coefficientMystique + ", " + docteur + ")");

        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture de input.txt : " + e.getMessage());
        }
    }

    private static List<Range> parseRanges(String line) {
        if (line.isEmpty()) return List.of();
        return Arrays.stream(line.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(part -> part.split("-"))
                .peek(bounds -> {
                    if (bounds.length != 2) {
                        throw new IllegalArgumentException("Intervalle invalide : " + Arrays.toString(bounds));
                    }
                })
                .map(bounds -> {
                    long start = Long.parseLong(bounds[0]);
                    long end = Long.parseLong(bounds[1]);
                    if (end < start) {
                        throw new IllegalArgumentException("Intervalle inversé : " + bounds[0] + "-" + bounds[1]);
                    }
                    return new Range(start, end);
                })
                .toList();
    }

    private static long sumInvalidIdsInRange(Range range) {
        long sum = 0L;

        for (long id = range.start(); id <= range.end(); id++) {
            if (isInvalidId(id)) {
                sum += id;
            }
        }

        return sum;
    }

    private static boolean isInvalidId(long id) {
        String s = Long.toString(id);
        int len = s.length();

        if (len % 2 != 0) {
            return false;
        }

        int half = len / 2;
        String first = s.substring(0, half);
        String second = s.substring(half);

        return first.equals(second);
    }

    private record Range(long start, long end) {}
}
