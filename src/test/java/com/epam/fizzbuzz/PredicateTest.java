package com.epam.fizzbuzz;

import org.junit.jupiter.api.Test;

import java.util.Scanner;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.function.Predicate.not;
import static org.assertj.core.api.Assertions.assertThat;

class PredicateTest {

    @Test
    void takeWhileNotZeroCount() {
        var input = "6 4 5 0 2 9";

        var count = new Scanner(input)
                .tokens()
                .takeWhile(not("0"::equals))
                .count();

        assertThat(count)
                .isEqualTo(3);
    }

    @Test
    void takeWhileNotExit() {
        var input = "create read update delete exit";

        var commands = new Scanner(input)
                .tokens()
                .map(String::toLowerCase)
                .takeWhile(isNotExit());

        assertThat(commands)
                .containsExactly("create", "read", "update", "delete");
    }

    Predicate<String> isNotExit() {
        return not(Set.of("exit", "quit")::contains);
    }
}
