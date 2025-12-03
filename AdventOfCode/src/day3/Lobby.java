package day3;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Lobby {

    public static void main(String[] args) {
        Path inputPath = Paths.get("input/day3/input.txt");

        try (Stream<String> lines = Files.lines(inputPath, StandardCharsets.UTF_8)) {
            long totalOutputJoltage = lines
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(BatteryBank::new)
                    .mapToInt(BatteryBank::maxJoltage)
                    .sum();

            System.out.println("Total output joltage : " + totalOutputJoltage);
        } catch (IOException e) {
            System.err.println("Erreur de lecture de l'input : " + e.getMessage());
        }
    }
}

record BatteryBank(String digits) {

    int maxJoltage() {
        int n = digits.length();

        return IntStream.range(0, n - 1)
                .flatMap(i -> IntStream.range(i + 1, n)
                        .map(j -> (digits.charAt(i) - '0') * 10 + (digits.charAt(j) - '0')))
                .max()
                .orElse(0);
    }
}
