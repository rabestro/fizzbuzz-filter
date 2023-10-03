package com.epam.fizzbuzz;

import java.util.Scanner;

import static java.util.function.Predicate.not;

public class SumOfNumbers {
    public static void main(String[] args) {

        var sum = new Scanner(System.in)
                .tokens()
                .takeWhile(not("0"::equals))
                .mapToInt(Integer::parseInt)
                .sum();

        System.out.println(sum);
    }
}
