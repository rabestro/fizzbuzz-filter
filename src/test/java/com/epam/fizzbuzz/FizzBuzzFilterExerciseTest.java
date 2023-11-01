package com.epam.fizzbuzz;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.IntPredicate;

import static java.util.stream.IntStream.rangeClosed;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("FizzBuzz and the art of filtering")
class FizzBuzzFilterExerciseTest {
    private final IntPredicate fizz = i -> i % 3 == 0;
    private final IntPredicate buzz = i -> i % 5 == 0;


    @Test
    @DisplayName("Task 1: Filter out numbers divisible by 3 and 5.")
    void numbers_divisible_by_three_and_five() {
        var numbers = rangeClosed(1, 20);

        var fizzBuzz = fizz.and(buzz);

        assertThat(numbers.filter(fizzBuzz))
                .as("Numbers divisible by three and five")
                .containsExactly(15);
    }

    @Test
    @DisplayName("Task 2: Filter out numbers divisible by 3 or 5")
    void numbers_divisible_by_three_or_five() {
        var numbers = rangeClosed(1, 20);

        var fizzBuzz = fizz.or(buzz);

        assertThat(numbers.filter(fizzBuzz))
                .as("Numbers divisible by three or five")
                .containsExactly(3, 5, 6, 9, 10, 12, 15, 18, 20);
    }

    @Test
    @DisplayName("Task 3: Filter out numbers not divisible by 3 and 5")
    void task3_numbers_not_divisible_by_three_or_five() {
        var numbers = rangeClosed(1, 20);

        var fizzBuzz = fizz.or(buzz).negate();

        assertThat(numbers.filter(fizzBuzz))
                .as("Numbers not divisible by three or five")
                .containsExactly(1, 2, 4, 7, 8, 11, 13, 14, 16, 17, 19);
    }

    @Test
    @DisplayName("Task 4: Filter out numbers that are divisible by either three or five.")
    void task4_numbers_divisible_by_either_three_or_five() {
        var numbers = rangeClosed(1, 20);

        IntPredicate fizzBuzz = i -> fizz.test(i) ^ buzz.test(i);

        assertThat(numbers.filter(fizzBuzz))
                .as("Numbers divisible by either three or five")
                .containsExactly(3, 5, 6, 9, 10, 12, 18, 20);
    }

    @Test
    @DisplayName("Task 5: Filter out numbers down to a number divisible by 3 and 5")
    void task5_filtering_numbers_down_to_a_number_divisible_by_three_and_five() {
        var numbers = rangeClosed(1, 20);

        var fizzBuzz = fizz.and(buzz);
        var result = numbers.takeWhile(fizzBuzz.negate());

        assertThat(result)
                .as("Numbers down to a number divisible by three and five")
                .containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
    }

    @Test
    @DisplayName("Task 6: Filter out numbers out to a number divisible by 3 and 5.")
    void task5_filtering_numbers_out_to_a_number_divisible_by_three_and_five() {
        var numbers = rangeClosed(1, 20);

        var fizzBuzz = fizz.and(buzz);
        var result = numbers.dropWhile(fizzBuzz.negate());

        assertThat(result)
                .as("Numbers after a number divisible by three and five")
                .containsExactly(15, 16, 17, 18, 19, 20);
    }


    @Test
    @DisplayName("Bonus Task: Filter out numbers between numbers divisible by three and by five.")
    void numbers_between_a_numbers_divisible_by_three_and_by_five() {
        var numbers = rangeClosed(1, 20);

        var fizzBuzz = flipFlop(fizz, buzz);

        assertThat(numbers.filter(fizzBuzz))
                .as("Numbers between numbers divisible by three and by five")
                .containsExactly(3, 4, 5, 6, 7, 8, 9, 10, 12, 13, 14, 15, 18, 19, 20);
    }

    @Test
    @DisplayName("Bonus Task: Filter out numbers between numbers divisible by five and by three.")
    void numbers_between_a_numbers_divisible_by_five_and_three() {
        var numbers = rangeClosed(1, 20);

        var buzzFizz = flipFlop(buzz, fizz);

        assertThat(numbers.filter(buzzFizz))
                .as("Numbers between numbers divisible by five and by three")
                .containsExactly(5, 6, 10, 11, 12, 15, 20);
    }


    /**
     * Returns a new IntPredicate that represents a flip-flop sequence.
     * The flip-flop sequence determines whether to include a value based on the specified predicates.
     *
     * @param fizz the IntPredicate for the start condition
     * @param buzz the IntPredicate for the end condition
     * @return a new IntPredicate representing the flip-flop sequence
     */
    IntPredicate flipFlop(IntPredicate fizz, IntPredicate buzz) {
        return new IntPredicate() {
            boolean state;

            @Override
            public boolean test(int value) {
                var result = state || fizz.test(value);
                state = result && !buzz.test(value);
                return result;
            }
        };
    }
}
