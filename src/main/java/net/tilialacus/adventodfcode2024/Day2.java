package net.tilialacus.adventodfcode2024;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.abs;
import static net.tilialacus.adventodfcode2024.ScanInput.inputAsLines;
import static net.tilialacus.adventodfcode2024.ScanInput.splitToLong;

public class Day2 {

    public static void main(String[] args) {
        var reports = new ArrayList<Report>();
        inputAsLines("src/main/resources/input-2.txt",
                line -> {
                    reports.add(new Report(splitToLong(line)));
                }
        );

        System.err.println("Part 1: " + reports.stream().filter(Report::isSafe).count());
        System.err.println("Part 2: " + reports.stream().filter(Report::isDampenerSafe).count());
    }

    private static class Report {
        private long[] levels;

        public Report(long[] levels) {
            this.levels = levels;
        }

        public boolean isSafe() {
            return isSafe(levels);
        }

        public boolean isDampenerSafe() {
            if (isSafe(levels)) {
                return true;
            } else {
                for (int i = 0; i < levels.length; i++) {
                    var n = Arrays.copyOf(levels, levels.length -1);
                    System.arraycopy(levels, 0, n, 0, i);
                    System.arraycopy(levels, i+1, n, i, levels.length - i-1);

                    if (isSafe(n)) {
                        return true;
                    };
                }
                return false;
            }
        }

        private boolean isSafe(long[] input) {
            // Assume a lot
            int increasing = 0;
            int decreasing = 0;
            for (int i = 1; i < input.length; i++) {
                if (input[i] > input[i - 1]) {
                    increasing++;
                };
                if (input[i] < input[i - 1]) {
                    decreasing++;
                };
                long diff = abs(input[i] - input[i - 1]);
                if ((increasing < i && decreasing < i) || (diff < 1 || diff > 3)) {
                    return false;
                };
            }
            return true;
        }
    }
}
