package net.tilialacus.adventodfcode2024;

import java.util.Arrays;

import static net.tilialacus.adventodfcode2024.ScanInput.inputAsSingleLine;

public class Day9 {

    public static void main(String[] args) {
        var line = inputAsSingleLine("src/main/resources/input-9.txt");
        int diskSize = line.chars()
                .map(it -> it - '0')
                .sum();

        var disk = new Disk(diskSize);
        disk.loadMap(line);
        compress(disk);
        System.err.println("Day 9 part 1: " + disk.checksum());
    }

    private static void compress(Disk disk) {
        int freePtr = nextFree(disk.storage, 0);
        int memPtr = nextUsed(disk.storage, disk.storage.length -1);
        while (freePtr < memPtr) {
            disk.storage[freePtr] = disk.storage[memPtr];
            disk.storage[memPtr] = -1;
            freePtr = nextFree(disk.storage, freePtr);
            memPtr = nextUsed(disk.storage, memPtr);
        }
    }

    private static int nextUsed(int[] mem, int index) {
        while (index >= 0 && mem[index] == -1) {
            index--;
        }
        return index;
    }

    private static int nextFree(int[] mem, int index) {
        while (index < mem.length && mem[index] != -1) {
            index++;
        }
        return index;
    }

    private static class Disk {
        private final int[] storage;

        public Disk(int size) {
            storage = new int[size];
            Arrays.fill(storage, -1); // Mark as free
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
    }
}
