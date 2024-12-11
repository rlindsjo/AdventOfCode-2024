package net.tilialacus.adventodfcode2024;

import static java.lang.Math.log10;
import static net.tilialacus.adventodfcode2024.ScanInput.scanInput;

public class Day11 {
    public static void main(String[] args) {
        Stones stones = new Stones();
        scanInput("src/main/resources/input-11.txt",
                scanner -> {
                    while (scanner.hasNextLong()) {
                        stones.add(new Stone(scanner.nextLong()));
                    }
                }
        );
        for (int i = 0; i < 25; i++) {
            stones.blink();
        }
        System.err.println("Day 11 part 1: " + stones.count());
    }

    static class Stones {
        Stone first = new Stone(0);

        public void add(Stone stone) {
            var last = first;
            while (last.next != null) {
                last = last.next;
            }
            last.next = stone;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            var current = first.next;
            while (current != null) {
                sb.append(current.value).append(" ");
                current = current.next;
            }
            return sb.toString();
        }

        public void blink() {
            var current = first.next;
            while (current != null) {
                if (current.value == 0) {
                    current.value = 1;
                } else {
                    var digits = (int) log10(current.value) + 1;
                    if (digits % 2 == 0) {
                        int factor = (int) Math.pow(10, digits / 2);
                        var s = new Stone(current.value % factor);
                        s.next = current.next;
                        current.next = s;
                        current.value /= factor;
                        current = current.next;
                    } else {
                        current.value *= 2024;
                    }
                }
                current = current.next;
            }
        }

        public int count() {
            int c = 0;
            var last = first;
            while (last.next != null) {
                last = last.next;
                ++c;
            }
            return c;
        }
    }

    static class Stone {
        Stone next;
        long value;

        public Stone(long value) {
            this.value = value;
        }
    }
}
