package net.tilialacus.adventodfcode2024;

import java.math.BigDecimal;
import java.util.Arrays;

import static net.tilialacus.adventodfcode2024.ScanInput.inputAsSingleLine;

public class Day9 {

    public static void main(String[] args) {
        var line = inputAsSingleLine("src/main/resources/input-9.txt");
        var diskSize = line.chars()
                .map(it -> it - '0')
                .sum();
        var mem = new int[diskSize];
        Arrays.fill(mem, -1); // Mark as free
        int index = 0;
        int memindex = 0;
        int fileId = 0;
        while (index < line.length()) {
            var fileSize = line.charAt(index++) - '0';
            Arrays.fill(mem, memindex, memindex+fileSize, fileId);
            memindex += fileSize;
            if (index + 1 < line.length()) {
                var free = line.charAt(index++) - '0';
                memindex += free;
            }
            fileId++;
        }

        int freePtr = nextFree(mem, 0);
        int memPtr = nextUsed(mem, mem.length -1);
        while (freePtr < memPtr) {
            mem[freePtr] = mem[memPtr];
            mem[memPtr] = -1;
            freePtr = nextFree(mem, freePtr);
            memPtr = nextUsed(mem, memPtr);
        }

        BigDecimal checksum = BigDecimal.ZERO;
        for (int i = 0; mem[i] != -1; i++) {
            System.err.println(i +" " + mem[i] + " " + mem[i] * i);
            checksum = checksum.add(BigDecimal.valueOf((long)mem[i] * i));
        }
        System.err.println(checksum);
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
}
