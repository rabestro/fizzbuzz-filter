package com.epam.fizzbuzz;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Scanner;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static java.util.function.Predicate.not;
import static java.util.stream.IntStream.rangeClosed;
import static org.assertj.core.api.Assertions.assertThat;

class FizzBuzzFilterExerciseTest {
    private final IntPredicate fizz = i -> i % 3 == 0;
    private final IntPredicate buzz = i -> i % 5 == 0;


    @Test
    @DisplayName("Filter out numbers that are divisible by three and five.")
    void numbers_divisible_by_three_and_five() {
        var numbers = rangeClosed(1, 20);

        var fizzBuzz = fizz.and(buzz);

        assertThat(numbers.filter(fizzBuzz))
                .as("Numbers divisible by three and five")
                .containsExactly(15);
    }

    @Test
    @DisplayName("Filter out numbers that are divisible by three or five.")
    void numbers_divisible_by_three_or_five() {
        var numbers = rangeClosed(1, 20);

        var fizzBuzz = fizz.or(buzz);

        assertThat(numbers.filter(fizzBuzz))
                .as("Numbers divisible by three or five")
                .containsExactly(3, 5, 6, 9, 10, 12, 15, 18, 20);
    }

    @Test
    @DisplayName("Filter out numbers that are not divisible by three or five.")
    void task3_numbers_not_divisible_by_three_or_five() {
        var numbers = rangeClosed(1, 20);

        var fizzBuzz = fizz.or(buzz).negate();

        assertThat(numbers.filter(fizzBuzz))
                .as("Numbers not divisible by three or five")
                .containsExactly(1, 2, 4, 7, 8, 11, 13, 14, 16, 17, 19);
    }

    @Test
    @DisplayName("Filter out numbers that are divisible by either three or five.")
    void task4_numbers_divisible_by_either_three_or_five() {
        var numbers = rangeClosed(1, 20);

        IntPredicate fizzBuzz = i -> fizz.test(i) ^ buzz.test(i);

        assertThat(numbers.filter(fizzBuzz))
                .as("Numbers divisible by either three or five")
                .containsExactly(3, 5, 6, 9, 10, 12, 18, 20);
    }

    @Test
    @DisplayName("Filter out numbers down to a number divisible by three and five.")
    void task5_filtering_numbers_down_to_a_number_divisible_by_three_and_five() {
        var numbers = rangeClosed(1, 20);

        var fizzBuzz = fizz.and(buzz);
        var result = numbers.takeWhile(fizzBuzz.negate());

        assertThat(result)
                .as("Numbers down to a number divisible by three and five")
                .containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
    }

    @Test
    @DisplayName("Filter out numbers out to a number divisible by three and five.")
    void task5_filtering_numbers_out_to_a_number_divisible_by_three_and_five() {
        var numbers = rangeClosed(1, 20);

        var fizzBuzz = fizz.and(buzz);
        var result = numbers.dropWhile(fizzBuzz.negate());

        assertThat(result)
                .as("Numbers after a number divisible by three and five")
                .containsExactly(15, 16, 17, 18, 19, 20);
    }

    @Test
    void the_length_of_the_sequence() {
        var input = "1 7 9 0 5";

        var count = new Scanner(input)
                .tokens()
                .takeWhile(not("0"::equals))
                .count();

        assertThat(count)
                .as("The length of the sequence")
                .isEqualTo(3);
    }

    @Test
    @DisplayName("Filter out numbers between numbers divisible by three and by five.")
    void numbers_between_a_numbers_divisible_by_three_and_by_five() {
        var numbers = rangeClosed(1, 20);

        IntPredicate fizzBuzz = flipFlop(fizz, buzz);

        assertThat(numbers.filter(fizzBuzz))
                .as("Numbers between numbers divisible by three and by five")
                .containsExactly(3, 4, 5, 6, 7, 8, 9, 10, 12, 13, 14, 15, 18, 19, 20);
    }

    @Test
    @DisplayName("Filter out numbers between numbers divisible by five and by three.")
    void numbers_between_a_numbers_divisible_by_five_and_three() {
        var numbers = rangeClosed(1, 20);

        IntPredicate buzzFizz = flipFlop(buzz, fizz);

        assertThat(numbers.filter(buzzFizz))
                .as("Numbers between numbers divisible by five and by three")
                .containsExactly(5, 6, 10, 11, 12, 15, 20);
    }

    IntPredicate flipFlop(IntPredicate fizz, IntPredicate buzz) {
        // TODO: Define the predicate
        return new IntPredicate() {
            boolean state;

            @Override
            public boolean test(int value){
                var result = state || fizz.test(value);
                state = result && !buzz.test(value);
                return result;
            };
        };
    }

    @Test
    @DisplayName("Filter out lines between [```java] and [```].")
    void extract_all_java_code_snippets_from_markdown_document() {
        var markdown = """
                # Hello, World!
                The following code snippet is written in Java:
                ```java
                System.out.println("Hello, World!");
                ```
                The following code snippet is written in Kotlin:
                ```kotlin
                println("Hello, World!")
                ```
                """;

        Predicate<String> fizz = "```java"::equals;
        Predicate<String> buzz = "```"::equals;

        // TODO: Define the predicate
        Predicate<String> fizzBuzz = s -> false;

        assertThat(markdown.lines().filter(fizzBuzz))
                .as("Java code snippets")
                .containsExactly("""
                        System.out.println("Hello, World!");""");
    }
}
