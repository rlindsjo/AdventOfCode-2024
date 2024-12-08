package net.tilialacus.adventodfcode2024;

import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Pattern;

public class Day4 {

    private static final Pattern MUL_PATTERN = Pattern.compile("mul\\((\\d+),(\\d+)\\)");

    public static void main(String[] args) {
        var text = ScanInput.inputAsArray("src/main/resources/input-4.txt");
        System.err.println("Part 1: " + matchAnyDirection(text, "XMAS"));
        System.err.println("Part 2: " + matchX(text, "MAS"));
    }

    private static long matchAnyDirection(char[][] text, String word) {
        var hits = 0L;
        var pattern = word.toCharArray();
        for (int row = 0; row < text.length; row++) {
            for (int col = 0; col < text[row].length; col++) {
                hits += match(text, pattern, row, col, 0, 1);
                hits += match(text, pattern, row, col, 1, 1);
                hits += match(text, pattern, row, col, 1, 0);
                hits += match(text, pattern, row, col, 1, -1);
                hits += match(text, pattern, row, col, 0, -1);
                hits += match(text, pattern, row, col, -1, -1);
                hits += match(text, pattern, row, col, -1, 0);
                hits += match(text, pattern, row, col, -1, 1);
            }
        }
        return hits;
    }

    private static long matchX(char[][] text, String word) {
        var hits = 0L;
        var pattern = word.toCharArray();
        var reversePattern = new StringBuilder(word).reverse().toString().toCharArray();
        for (int row = 0; row < text.length; row++) {
            for (int col = 0; col < text[row].length - reversePattern.length + 1; col++) {
                if ( (match(text, pattern, row, col, 1, 1) > 0 || match(text, reversePattern, row, col, 1, 1) > 0)
                        && (match(text, pattern, row, col + reversePattern.length - 1, 1, -1) > 0 || match(text, reversePattern, row, col + reversePattern.length - 1, 1, -1) > 0)) {
                    hits++;
                }
            }
        }
        return hits;
    }
    private static long match(char[][] text, char[] pattern, int row, int col, int dr, int dc) {
        var rowEnd = row + (pattern.length -1) * dr;
        var colEnd = col + (pattern.length -1) * dc;
        if ( rowEnd < 0 || rowEnd >= text.length ) {
            return 0;
        } else if (colEnd < 0 || colEnd >= text[row].length ) {
            return 0;
        }
        for (int i = 0; i < pattern.length; i++) {
            if (text[row + i*dr][col + i * dc] != pattern[i]) {
                return 0;
            }
        }

        return 1;
    }
}
