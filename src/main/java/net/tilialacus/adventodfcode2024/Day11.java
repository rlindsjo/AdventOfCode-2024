package net.tilialacus.adventodfcode2024;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.log10;
import static net.tilialacus.adventodfcode2024.ScanInput.scanInput;

public class Day11 {
    public static void main(String[] args) {
        var stones = buildInput();
        for (int i = 0; i < 25; i++) {
            stones = blink(stones);
        }
        System.err.println("Day 11 part 1: " + count(stones));

        for (int i = 25; i < 75; i++) {
            stones = blink(stones);
        }
        System.err.println("Day 11 part 2: " + count(stones));
    }

    private static long count(HashMap<Long, Long> stones) {
        return stones.values().stream().mapToLong(Long::longValue).sum();
    }

    private static HashMap<Long, Long> blink(HashMap<Long, Long> stones) {
        var newStone = new HashMap<Long, Long>();
        for (Map.Entry<Long, Long> entry : stones.entrySet()) {
            Long value = entry.getKey();
            if (value == 0) {
                newStone.merge(1L, entry.getValue(), Long::sum);
            } else {
                var digits = (int) log10(value) + 1;
                if (digits % 2 == 0) {
                    int factor = (int) Math.pow(10, digits / 2);
                    long right = value % factor;
                    newStone.merge(right, entry.getValue(), Long::sum);
                    long left = value / factor;
                    newStone.merge(left, entry.getValue(), Long::sum);
                } else {
                    newStone.merge(value * 2024, entry.getValue(), Long::sum);
                }
            }
        }
        stones = newStone;
        return stones;
    }

    private static HashMap<Long, Long> buildInput() {
        var stones = new HashMap<Long, Long>();
        scanInput("src/main/resources/input-11.txt",
                scanner -> {
                    while (scanner.hasNextLong()) {
                        stones.merge(scanner.nextLong(), 1L, Long::sum);
                    }
                }
        );
        return stones;
    }
}
