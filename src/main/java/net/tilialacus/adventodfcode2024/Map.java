package net.tilialacus.adventodfcode2024;

public class Map {
    private char[][] tiles;

    public Map(char[][] tiles) {
        this.tiles = tiles;
    }

    public Map(int rows, int cols, char tile) {
        tiles = new char[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                tiles[row][col] = tile;
            }
        }
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

    public void setTile(Pos pos, char tile) {
        tiles[pos.row()][pos.col()] = tile;
    }

    public boolean onMap(Pos pos) {
        return pos.row() >= 0 && pos.col() >= 0 && pos.row() < tiles.length && pos.col() < tiles[0].length;
    }

    public record Pos(int row, int col) {

        static Pos pos(int row, int col) {
            return new Pos(row, col);
        }
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

    public record Path(Path prev, Pos pos) {
        static Path start(Pos pos) {
            return new Path(null, pos);
        }

        Path to(Pos pos) {
            return new Path(this, pos);
        }

        public Path up() {
            return to(pos.up());
        }

        public Path down() {
            return to(pos.down());
        }

        public Path left() {
            return to(pos.left());
        }

        public Path right() {
            return to(pos.right());
        }

        public int length() {
            if (prev == null) {
                return 0;
            } else {
                return 1 + prev().length();
            }
        }

        @Override
        public String toString() {
            if (prev == null) {
                return pos().toString();
            } else {
                return prev + "->" + pos().toString();
            }
        }
    }
}
