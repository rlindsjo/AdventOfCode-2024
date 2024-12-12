package net.tilialacus.adventodfcode2024;

import java.util.ArrayList;

import static net.tilialacus.adventodfcode2024.ScanInput.inputAsArray;

public class Day12 {

    private static char[][] map;
    private static boolean[][] visited;

    public static void main(String[] args) {
        map = inputAsArray("src/main/resources/input-12.txt");
        visited = new boolean[map.length][map[0].length];

        var fields = new ArrayList<Field>();
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                var field = scan(row, col);
                if (field != null) {
                    fields.add(field);
                }
            }
        }
        System.err.println("Day 12 part 1: " + fields.stream()
                .mapToInt(it -> it.area() * it.perimeter()).sum());
    }

    private static Field scan(int row, int col) {
        if (visited[row][col]) return null;
        visited[row][col] = true;
        var crop = map[row][col];
        return Field.start()
                .add(scanSurrounding(row - 1, col, crop))
                .add(scanSurrounding(row + 1, col, crop))
                .add(scanSurrounding(row, col - 1, crop))
                .add(scanSurrounding(row, col + 1, crop));
    }

    private static Field scanSurrounding(int row, int col, char crop) {
        if (row < 0 || row >= map.length || col < 0 || col >= map[row].length) {
            return Field.fence();
        } else if (map[row][col] != crop) {
            return Field.fence();
        } else {
            return scan(row, col);
        }
    }

    record Field(int area, int perimeter) {
        public static Field fence() {
            return new Field(0, 1);
        }

        public static Field start() {
            return new Field(1, 0);
        }

        Field add(Field other) {
            if (other == null) return this;
            return new Field(area + other.area, perimeter + other.perimeter);
        }
    };
}
