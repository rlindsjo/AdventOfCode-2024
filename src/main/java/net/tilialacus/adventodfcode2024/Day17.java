package net.tilialacus.adventodfcode2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day17 {

    public static void main(String[] args) {
        var input = ScanInput.inputAsLines("src/main/resources/input-17.txt");
        var computer = new Computer();
        computer.a = Integer.parseInt(input.get(0).substring(12));
        computer.b = Integer.parseInt(input.get(1).substring(12));
        computer.c = Integer.parseInt(input.get(2).substring(12));
        computer.mem = Arrays.stream(input.get(4).substring(9).split(",")).mapToInt(Integer::parseInt).toArray();

        while (computer.pc < computer.mem.length) {
            computer.step();
        }
        System.err.println("IC: " + computer.ic + " A: " + computer.a + " B: " + computer.b + " C: " + computer.c);
        System.err.println(computer.out.stream().map(String::valueOf).collect(Collectors.joining(",")));
    }

    static class Computer {
        int a;
        int b;
        int c;
        int pc;
        int ic = 0;
        int[] mem;
        List<Integer> out = new ArrayList<>();

        void step() {
            ic ++;
            var opcode = mem[pc];

            var literal = mem[pc + 1];
            var combo = switch (literal) {
                case 0, 1, 2, 3 -> literal;
                case 4 -> a;
                case 5 -> b;
                case 6 -> c;
                default -> -1;
            };
            pc += 2;

            switch (opcode) {
                case 0: a >>= combo; break;
                case 1: b = b ^ literal ; break;
                case 2: b = combo & 7; break;
                case 3: if (a != 0) pc = literal; break;
                case 4: b = b ^ c; break;
                case 5: out.add(combo & 7); break;
                case 6: b = a >> combo; break;
                case 7: c = a >> combo; break;
                default : throw new IllegalStateException("Unexpected value: " + opcode);
            }
        }
    }
}
