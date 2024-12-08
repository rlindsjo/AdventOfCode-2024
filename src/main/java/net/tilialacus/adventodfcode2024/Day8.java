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

    }

    private static Boolean[][] calculateAntinodes(char[][] map, HashMap<Character, List<Antenna>> groupedAntennas) {
        var antinodes = new Boolean[map.length][map[0].length];
        for (List<Antenna> antennas : groupedAntennas.values()) {
            for (Antenna antenna : antennas) {
                for (Antenna current : antennas) {
                    if (current == antenna) {
                        continue;
                    }
                    var aRow = current.row - (antenna.row - current.row);
                    var aCol = current.col - (antenna.col - current.col);
                    if (aRow >= 0 && aCol >= 0 && aRow < map.length && aCol < map[0].length) {
                        antinodes[aRow][aCol] = true;
                    }
                }
            }
        }
        return antinodes;
    }

    private record Antenna(char frequwency, int row, int col) {}
}
