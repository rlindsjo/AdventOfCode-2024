package net.tilialacus.adventodfcode2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static java.lang.Boolean.TRUE;
import static net.tilialacus.adventodfcode2024.ScanInput.inputAsArray;

public class Day8 {
    public static void main(String[] args) {
        var map = inputAsArray("src/main/resources/input-8.txt");
        var groupedAntennas = new HashMap<Character, List<Antenna>>();
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map.length; col++) {
                var f = map[row][col];
                if (f >= 'a' && f <= 'z' || f >= 'A' && f <= 'Z' || f >= '0' && f <= '9') {
                    groupedAntennas.computeIfAbsent(f, k -> new ArrayList<>()).add(new Antenna(f, row, col));
                }
            }
        }

        System.err.println("Day 8 part 1: " + Arrays.stream(calculateAntinodes(map, groupedAntennas))
                .flatMap(Arrays::stream)
                .filter(TRUE::equals)
                .count());

        System.err.println("Day 8 part 2: " + Arrays.stream(calculateAntinodesWithHarmonics(map, groupedAntennas))
                .flatMap(Arrays::stream)
                .filter(TRUE::equals)
                .count());

    }

    private static Boolean[][] calculateAntinodes(char[][] map, HashMap<Character, List<Antenna>> groupedAntennas) {
        var antinodes = new Boolean[map.length][map[0].length];
        for (List<Antenna> antennas : groupedAntennas.values()) {
            for (Antenna antenna : antennas) {
                for (Antenna current : antennas) {
                    if (current == antenna) {
                        continue;
                    }
                    Antenna a = current.offset(current.row - antenna.row, current.col - antenna.col);
                    if (onMap(map, a)) {
                        antinodes[a.row][a.col] = true;
                    }
                }
            }
        }
        return antinodes;
    }

    private static Boolean[][] calculateAntinodesWithHarmonics(char[][] map, HashMap<Character, List<Antenna>> groupedAntennas) {
        var antinodes = new Boolean[map.length][map[0].length];
        for (List<Antenna> antennas : groupedAntennas.values()) {
            for (Antenna antenna : antennas) {
                for (Antenna current : antennas) {
                    if (current == antenna) {
                        continue;
                    }

                    var dRow = current.row - antenna.row;
                    var dCol = current.col - antenna.col;

                    for (Antenna a = current.offset(dRow, dCol); onMap(map, a); a = a.offset(dRow, dCol)) {
                        antinodes[a.row][a.col] = true;
                    }
                    for (Antenna a = current.offset(-dRow, -dCol); onMap(map, a); a = a.offset(-dRow, -dCol)) {
                        antinodes[a.row][a.col] = true;
                    }
                }
            }
        }
        return antinodes;
    }

    private static boolean onMap(char[][] map, Antenna a) {
        return a.row >= 0 && a.col >= 0 && a.row < map.length && a.col < map[0].length;
    }

    private record Antenna(char frequency, int row, int col) {
        Antenna offset(int drow, int dcol) {
            return new Antenna(frequency, row + drow, col + dcol);
        }
    }
}
