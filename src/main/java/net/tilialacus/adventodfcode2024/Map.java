package net.tilialacus.adventodfcode2024;

public class Map {
    private char[][] tiles;

    public Map(char[][] tiles) {
        this.tiles = tiles;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (char[] row : tiles) {
            for (char tile : row) {
                sb.append(tile);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public Pos find(char target) {
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {
                if (tiles[row][col] == target) {
                    return new Pos(row, col);
                }
            }
        }
        return null;
    }

    public char tileAt(Pos pos) {
        return tiles[pos.row][pos.col];
    }

    public record Pos(int row, int col) {
        Pos right() {
            return new Pos(row, col + 1);
        }

        Pos left() {
            return new Pos(row, col - 1);
        }

        Pos up() {
            return new Pos(row - 1, col);
        }
        Pos down() {
            return new Pos(row + 1, col);
        }
    }
}
