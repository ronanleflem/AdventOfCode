package day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SafePassword {

    public static void main(String[] args) {
        String filePath = "input/day1/input.txt";
        SafeDial dial = new SafeDial(50, 100);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                dial.applyRotation(Rotation.parse(line));
            }

            System.out.println("Mot de passe : " + dial.getZeroCount());

        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }
}

class SafeDial {

    private int position;
    private final int modulo;
    private int zeroCount = 0;

    public SafeDial(int startPosition, int modulo) {
        this.position = startPosition;
        this.modulo = modulo;
    }

    public void applyRotation(Rotation rotation) {
        if (rotation.direction() == Direction.RIGHT) {
            position = (position + rotation.value()) % modulo;
        } else {
            position = (position - rotation.value()) % modulo;
            if (position < 0) position += modulo;
        }

        if (position == 0) zeroCount++;
    }

    public int getZeroCount() {
        return zeroCount;
    }
}

record Rotation(Direction direction, int value) {

    public static Rotation parse(String line) {
        line = line.trim();
        Direction dir = Direction.fromCode(line.charAt(0));
        int val = Integer.parseInt(line.substring(1));
        return new Rotation(dir, val);
    }
}

enum Direction {
    LEFT('L'),
    RIGHT('R');

    private final char code;

    Direction(char code) {
        this.code = code;
    }

    public static Direction fromCode(char c) {
        return c == 'L' ? LEFT : RIGHT;
    }
}
