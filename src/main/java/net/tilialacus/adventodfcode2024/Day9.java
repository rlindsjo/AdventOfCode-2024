package net.tilialacus.adventodfcode2024;

import java.util.Arrays;

import static net.tilialacus.adventodfcode2024.ScanInput.inputAsSingleLine;

public class Day9 {

    public static void main(String[] args) {
        var line = inputAsSingleLine("src/main/resources/input-9.txt");
        var disk = new Disk(line);
        disk.loadMap(line);
        compressBlocks(disk);
        System.err.println("Day 9 part 1: " + disk.checksum());

        disk = new Disk(line);
        disk.loadMap(line);
        compressFiles(disk);
        System.err.println("Day 9 part 2: " + disk.checksum());
    }

    private static void compressBlocks(Disk disk) {
        var freeSegment = nextFree(disk.storage, 0);
        var file = nextFile(disk.storage, disk.storage.length -1);
        while (freeSegment.start < file.start) {
            disk.moveBlock(file.end, freeSegment.start);
            disk.free(file.end);
            freeSegment = nextFree(disk.storage, freeSegment.start);
            file = nextFile(disk.storage, file.end);
        }
    }

    private static void compressFiles(Disk disk) {
        var file = nextFile(disk.storage, disk.storage.length -1);
        while (file.start() > 0) {
            var freeSegment = nextFree(disk.storage, 0);
            while (freeSegment.size() < file.size() && freeSegment.before(file)) {
                freeSegment = nextFree(disk.storage, freeSegment.end + 1);
            }
            if (freeSegment.before(file)) {
                disk.move(file, freeSegment);
                disk.free(file);
            }
            file = nextFile(disk.storage, file.start - 1);
        }
    }

    private static Region nextFile(int[] mem, int index) {
        var end = index;
        while (end >= 0 && mem[end] == -1) {
            end--;
        }
        var start = end;
        while (start >= 0 && mem[start] == mem[end]) {
            --start;
        }
        return new Region(start + 1,  end);
    }

    private static Region nextFree(int[] mem, int index) {
        int start = index;
        while (start < mem.length && mem[start] != -1) {
            ++start;
        }
        int end = start;
        while (end < mem.length && mem[end] == -1) {
            ++end;
        }
        return new Region(start, end - 1);
    }

    private record Region(int start, int end) {
        public int size() {
            return end - start + 1;
        }

        public boolean before(Region region) {
            return start <= region.start;
        }
    }
    private static class Disk {
        private final int[] storage;

        public Disk(String map) {
            int diskSize = map.chars()
                    .map(it -> it - '0')
                    .sum();

            storage = new int[diskSize];
            Arrays.fill(storage, -1); // Mark as free
            loadMap(map);
        }

        public int writeFile(int fileId, int pos, int fileSize) {
            Arrays.fill(storage, pos, pos+fileSize, fileId);
            return pos+fileSize;
        }

        public long checksum() {
            long checksum = 0;
            for (int i = 0; i < storage.length; i++) {
                if (storage[i] != -1) {
                    checksum += storage[i] * i;
                }
            }
            return checksum;
        }

        public void loadMap(String map) {
            int mapIndex = 0;
            int pos = 0;
            int fileId = 0;
            while (mapIndex < map.length()) {
                var fileSize = map.charAt(mapIndex++) - '0';
                pos = writeFile(fileId, pos, fileSize);
                if (mapIndex + 1 < map.length()) {
                    var free = map.charAt(mapIndex++) - '0';
                    pos += free;
                }
                fileId++;
            }
        }

        public void moveBlock(int from, int to) {
            storage[to] = storage[from];
        }

        public void free(int pos) {
            storage[pos] = -1;
        }

        public void move(Region from, Region to) {
            System.arraycopy(storage, from.start(), storage, to.start(), from.size());
        }

        public void free(Region region) {
            Arrays.fill(storage, region.start(), region.end() + 1, -1);
        }
    }
}
