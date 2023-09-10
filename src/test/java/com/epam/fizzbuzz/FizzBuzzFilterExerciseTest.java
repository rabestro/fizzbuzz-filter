package com.epam.fizzbuzz;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class FizzBuzzFilterExerciseTest {
    private final IntPredicate fizz = i -> i % 3 == 0;
    private final IntPredicate buzz = i -> i % 5 == 0;

    @Test
    @DisplayName("Filter out numbers that are divisible by three or five.")
    void numbers_divisible_by_three_or_five() {
        var numbers = IntStream.rangeClosed(1, 20);

        IntPredicate fizzBuzz = fizz.or(buzz);

        assertThat(numbers.filter(fizzBuzz))
                .as("Numbers divisible by three or five")
                .containsExactly(3, 5, 6, 9, 10, 12, 15, 18, 20);
    }

    @Test
    @DisplayName("Filter out numbers that are not divisible by three or five.")
    void numbers_not_divisible_by_three_or_five() {
        var numbers = IntStream.rangeClosed(1, 20);

        IntPredicate fizzBuzz = fizz.or(buzz).negate();

        assertThat(numbers.filter(fizzBuzz))
                .as("Numbers not divisible by three or five")
                .containsExactly(1, 2, 4, 7, 8, 11, 13, 14, 16, 17, 19);
    }

    @Test
    @DisplayName("Filter out numbers that are divisible by three and five.")
    void numbers_divisible_by_three_and_five() {
        var numbers = IntStream.rangeClosed(1, 20);

        IntPredicate fizzBuzz = fizz.and(buzz);

        assertThat(numbers.filter(fizzBuzz))
                .as("Numbers divisible by three and five")
                .containsExactly(15);
    }

    @Test
    @DisplayName("Filter out numbers that are divisible by either three or five.")
    void numbers_divisible_by_either_three_or_five() {
        var numbers = IntStream.rangeClosed(1, 20);

        IntPredicate fizzBuzz = i -> fizz.test(i) ^ buzz.test(i);

        assertThat(numbers.filter(fizzBuzz))
                .as("Numbers divisible by either three or five")
                .containsExactly(3, 5, 6, 9, 10, 12, 18, 20);
    }

    @Test
    @DisplayName("Filter out numbers between numbers divisible by three and by five.")
    void numbers_between_a_numbers_divisible_by_three_and_by_five() {
        var numbers = IntStream.rangeClosed(1, 20);

        // TODO: Define the predicate
        IntPredicate fizzBuzz = i -> false;

        assertThat(numbers.filter(fizzBuzz))
                .as("Numbers between numbers divisible by three and by five")
                .containsExactly(4, 7, 8, 9, 13, 14, 19);
    }
}
