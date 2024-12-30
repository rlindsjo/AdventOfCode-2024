package net.tilialacus.adventodfcode2024;

import java.util.*;
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

        System.err.println(computer.out.stream().map(String::valueOf).collect(Collectors.joining(",")));

        // Try to match end and then trying one octet at a time
        // Each octet generates one output
        // Skipping 0 as starting as that can not grow
        Queue<Long> candidates = new LinkedList<>();
        for (long i = 1; i < 8; i++) {
            candidates.add(i);
        }
        while (!candidates.isEmpty()) {
            var c = candidates.remove() * 8;
            loop: for (long a = c; a < c + 8; a++) {
                computer.reset();
                computer.a = a;
                while (computer.pc < computer.mem.length) {
                    computer.step();
                }
                for (int i = 0; i < computer.out.size(); i++) {
                    if (!computer.out.get(i).equals(computer.mem[computer.mem.length + i - computer.out.size()])) {
                        continue loop;
                    }
                }
                if (computer.out.size() == computer.mem.length) {
                    System.err.println("Day 17 part 2 possible seed " + a);
                } else {
                    candidates.add(a);
                }
            }
        }
    }

    private static String reverse(String text) {
        return new StringBuilder(text).reverse().toString();
    }


    static class Computer {
        long a;
        long b;
        long c;
        int pc;
        int[] mem;
        List<Integer> out = new ArrayList<>();

        void reset() {
            a = 0;
            b = 0;
            c = 0;
            pc = 0;
            out.clear();
        }

        void step() {
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
                case 5: out.add((int) (combo & 7)); break;
                case 6: b = a >> combo; break;
                case 7: c = a >> combo; break;
                default : throw new IllegalStateException("Unexpected value: " + opcode);
            }
        }
    }
}
