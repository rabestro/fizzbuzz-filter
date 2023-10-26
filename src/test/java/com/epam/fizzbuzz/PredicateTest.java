package com.epam.fizzbuzz;

import org.junit.jupiter.api.DisplayName;
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
