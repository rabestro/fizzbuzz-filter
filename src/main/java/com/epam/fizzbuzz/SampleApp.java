package com.epam.fizzbuzz;

import java.util.Scanner;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

public class SampleApp {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        Stream.generate(scanner::nextLine)
                .takeWhile(not("exit"::equals))
                .forEach(SampleApp::process);
    }

    private static void process(String command) {
        System.out.println("Processing command: " + command);
    }
}
