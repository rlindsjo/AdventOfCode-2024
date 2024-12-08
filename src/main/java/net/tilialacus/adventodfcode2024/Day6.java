package net.tilialacus.adventodfcode2024;

import java.util.Arrays;

import static java.lang.Boolean.TRUE;
import static net.tilialacus.adventodfcode2024.ScanInput.inputAsArray;

public class Day6 {

    public static void main(String[] args) {
        var map = inputAsArray("src/main/resources/input-6.txt");
        var visited = new Boolean[map.length][map[0].length];

        var guard = findGuard(map);

        visited[guard.row][guard.col] = true;

        while (guard.move(map)) {
            visited[guard.row][guard.col] = true;
        }

        System.err.println("Day 6 part 1: " + Arrays.stream(visited)
                .flatMap(Arrays::stream)
                .filter(TRUE::equals)
                .count());
    }

    private static Guard findGuard(char[][] map) {
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                char c = map[row][col];
                if ("<^>v".lastIndexOf(c)> -1) {
                    return new Guard(row, col, c);
                }
            }
        }
        throw new IllegalArgumentException("No guard found");
    }

    private static class Guard {
        private int row;
        private int col;
        private char dir;

        public Guard(int row, int col, char dir) {
            this.row = row;
            this.col = col;
            this.dir = dir;
        }

        public boolean move(char[][] map) {
            return switch (dir) {
                case '>' -> {
                    if (col + 1 >= map[row].length) {
                        yield  false;
                    } else {
                        if (map[row][col + 1] != '#') {
                            col++;
                        } else {
                            dir = 'v';
                        }
                        yield true;
                    }
                }
                case 'v' -> {
                    if (row + 1 >= map.length) {
                        yield  false;
                    } else {
                        if (map[row + 1][col] != '#') {
                            row++;
                        } else {
                            dir = '<';
                        }
                        yield  true;
                    }
                }
                case '<' -> {
                    if (col - 1 < 0) {
                        yield  false;
                    } else {
                        if (map[row][col - 1] != '#') {
                            col--;
                        } else {
                            dir = '^';
                        }
                        yield true;
                    }
                }
                case '^' -> {
                    if (row - 1 < 0) {
                        yield  false;
                    } else {
                        if (map[row - 1][col] != '#') {
                            row--;
                        } else {
                            dir = '>';
                        }
                        yield  true;
                    }
                }
                default -> throw new IllegalStateException("Unexpected value: " + dir);
            };
        }
    }
}
