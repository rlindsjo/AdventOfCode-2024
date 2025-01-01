package net.tilialacus.adventodfcode2024;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static net.tilialacus.adventodfcode2024.ScanInput.inputAsLines;

public class Day19 {

    public static void main(String[] args) {
        var lines = inputAsLines("src/main/resources/input-19.txt");
        var inventory = Arrays.stream(lines.get(0).split(", ")).collect(Collectors.toSet());

        var possibleCount = 0;
        long count = 0;
        for (int i = 2; i < lines.size(); i++) {
            long possible = countPossible(inventory, lines.get(i), new ConcurrentHashMap<>());
            count += possible;
            if (possible > 0) {
                possibleCount++;
            }
        }
        System.err.println("Day 19 part 1 patterns possible:" + possibleCount);
        System.err.println("Day 19 part 1 pattern combinations:" + count);
    }

    private static long countPossible(Set<String> inventory, String pattern, ConcurrentHashMap<String, Long> cache) {
        if (pattern.isEmpty()) {
            return 1L; // Done
        }

        var cached = cache.get(pattern);
        if (cached != null) {
            return cached;
        }
        long sum = 0;
        for (String part : inventory) {
            if (pattern.startsWith(part)) {
                sum += countPossible(inventory, pattern.substring(part.length()), cache);
            }
        }
        cache.put(pattern, sum);

        return sum;
    }
}
