package net.tilialacus.adventodfcode2024;

import java.util.EnumSet;
import java.util.Set;

import static java.lang.Long.parseLong;
import static net.tilialacus.adventodfcode2024.Day7.Operator.*;
import static net.tilialacus.adventodfcode2024.ScanInput.mapInput;
import static net.tilialacus.adventodfcode2024.ScanInput.splitToLong;

public class Day7 {

    public static void main(String[] args) {
        var equations = mapInput("src/main/resources/input-7.txt",
                line -> {
                    var parts = line.split(":");
                    return new Equation(parseLong(parts[0]), splitToLong(parts[1].trim()));
                })
                .toList();

        System.err.println("Day 7 part 1: " + equations.stream()
                .filter(equation -> test(EnumSet.of(ADD, MULTIPLY), equation, equation.terms[0], 1))
                .mapToLong(Equation::result)
                .sum());

        System.err.println("Day 7 part 2: " + equations.stream()
                .filter(equation -> test(EnumSet.of(ADD, MULTIPLY, CONCATENATE), equation, equation.terms[0], 1))
                .mapToLong(Equation::result)
                .sum());
    }

    private static boolean test(Set<Operator> operators, Equation equation, long partial, int termIndex) {
        if (termIndex == equation.terms.length) {
            return partial == equation.result;
        } else {
            return operators.stream()
                    .anyMatch( operator -> test(
                            operators,
                            equation,
                            operator.apply(partial, equation.terms[termIndex]),
                            termIndex + 1)
                    );
        }
    }

    enum Operator {
        ADD,
        MULTIPLY,
        CONCATENATE;

        public long apply(long left, long right) {
            return switch (this) {
                case ADD -> left + right;
                case MULTIPLY -> left * right;
                case CONCATENATE -> left * (long) Math.pow(10, (long)Math.log10(right) + 1) + right;
            };
        }
    }

    record Equation(long result, long[] terms) {}
}
