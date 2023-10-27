# FizzBuzz and the art of filtering

#### Stream API-based Tasks Inspired by the Classic Problem

The FizzBuzz problem is a classic coding challenge often used in programming interviews. It is usually presented as follows:

> Create a program that prints numbers from 1 to n. - If the number is divisible by 3, print ‘Fizz’; - If the number is divisible by 5, print ‘Buzz’; - If the number is divisible by both 3 and 5, print ‘FizzBuzz’.
>

This problem inspired me to work with Stream API using predefined `fizz` and `buzz` predicates. We aim to create various filters for a stream of integers using these predicates.

We will cover solutions for several basic tasks, while a more interesting and challenging one will be left for you to solve.

First, let’s define the `fizz` and `buzz` predicates:

```java
IntPredicate fizz = i -> i % 3 == 0;
IntPredicate buzz = i -> i % 5 == 0;
```

We can create a stream of integers from 1 to 20 using the `IntStream::rangeClosed` method:

```java
var numbers = IntStream.rangeClosed(1, 20);
```

The goal for all the following tasks is to filter the integer stream by creating a `fizzBuzz` predicate based on the defined `fizz` and `buzz` predicates.

### Task 1: Filter out numbers divisible by 3 and 5

We need to filter numbers that are exactly divisible by both 3 and 5. There are two ways to accomplish this. For instance, we can use the logical operator `&&`:

```java
IntPredicate fizzBuzz = i -> fizz.test(i) && buzz.test(i);
```

However, a more convenient and preferable approach would be to use the `and` method from the `IntPredicate` interface:

```java
IntPredicate fizzBuzz = fizz.and(buzz);

assertThat(numbers.filter(fizzBuzz))        
		.as("Numbers divisible by three and five")        
		.containsExactly(15);
```

### Task 2: Filter out numbers divisible by 3 or 5

This solution is similar to the previous one but uses the `or` method instead of `and`:

```java
IntPredicate fizzBuzz = fizz.or(buzz);

assertThat(numbers.filter(fizzBuzz))        
		.as("Numbers divisible by three or five")        
		.containsExactly(3, 5, 6, 9, 10, 12, 15, 18, 20);
```

### Task 3: Filter out numbers not divisible by 3 and 5

In order to filter numbers that are not divisible by 3 or 5, we can use the `IntStream::negate` method. The solution looks like this:

```java
IntPredicate fizzBuzz = fizz.or(buzz).negate();

assertThat(numbers.filter(fizzBuzz))        
		.as("Numbers not divisible by three or five")        
		.containsExactly(1, 2, 4, 7, 8, 11, 13, 14, 16, 17, 19);
```

### Task 4: Filter out numbers Divisible by 3 or 5 but Not Both

The next task is to filter numbers that are divisible by either 3 or 5, but not both simultaneously. While it is possible to use all the methods from the `IntStream` interface, the optimal choice would be the exclusive OR (XOR):

```java
IntPredicate fizzBuzz = i -> fizz.test(i) ^ buzz.test(i);

assertThat(numbers.filter(fizzBuzz))        
		.as("Numbers divisible by either three or five")        
		.containsExactly(3, 5, 6, 9, 10, 12, 18, 20);
```

### Task 5: Filter out numbers down to a number divisible by 3 and 5

This problem is very similar to the first one. However, our task is now to filter the numbers down to a divisible number by 3 and 5. We should stop processing numbers as soon as we come across such a number.

We define the predicate fizzBuzz precisely the same way as in the first problem. However, we use the `takeWhile` method instead of `filter`.

```java
var fizzBuzz = fizz.and(buzz);

var result = numbers.takeWhile(fizzBuzz.negate());

assertThat(result)
		.as("Numbers down to a number divisible by three and five")
		.containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
```

### Task 6: Filtering Numbers out to a number divisible by 3 and 5

This is the opposite task to the previous one. We must throw out all the numbers up to the divisible number by three and five. All we need to solve this problem is to replace the `takeWhile` method with the `dropWhile` method.

```java
var fizzBuzz = fizz.and(buzz);

var result = numbers.dropWhile(fizzBuzz.negate());

assertThat(result)
		.as("Numbers after a number divisible by three and five")
		.containsExactly(15, 16, 17, 18, 19, 20);
```

### Bonus Task: FizzBuzz as a Flip-Flop predicate

Programming languages like Ruby or Raku have a [flip-flop operator](https://en.wikipedia.org/wiki/Flip-flop_(programming)), which is not available in Java by default. However, we can create it from scratch.

As a final task, I invite you to test your predicate-building skills. Create a method that takes two predicates, `fizz` and `buzz` as input and returns a flip-flop predicate. This predicate must filter down a series of numbers, starting with a number satisfying the predicate `fizz` and ending with a number satisfying the predicate `buzz`. The method signature looks like this:

```java
IntPredicate flipFlop(IntPredicate fizz, IntPredicate buzz) {
		// TODO: Define the predicate
}
```

To define the flip-flop predicate, we call the method as follows.

```java
var fizzBuzz = flipFlop(fizz, buzz);

assertThat(numbers.filter(fizzBuzz))
        .as("Numbers between numbers divisible by three and by five")
        .containsExactly(3, 4, 5, 6, 7, 8, 9, 10, 12, 13, 14, 15, 18, 19, 20);
```

To demonstrate the operation of this predicate, I enclosed the number sequences in square brackets.

```
1, 2, [3, 4, 5], [6, 7, 8, 9, 10], 11, [12, 13, 14, 15], 16, 17, [18, 19, 20]
```

We get a completely different sequence if we swap the predicates `fizz` and `buzz`.

```java
IntPredicate buzzFizz = flipFlop(buzz, fizz);

assertThat(numbers.filter(buzzFizz))
		.as("Numbers between numbers divisible by five and by three")
		.containsExactly(5, 6, 10, 11, 12, 15, 20);
```

Please note that in this case, the number 15 begins and ends the sequence, and the number 20 starts the series, but we don’t have its completion. At the same time, we ignore the number 3, which should end the sequence since our sequence has not yet begun.

```
1, 2, 3, 4, [5, 6], 7, 8, 9, [10, 11, 12], 13, 14, [15], 16, 17, 18, 19, [20
```

I hope you're willing to give the final problem a shot.

### Conclusion

The topic of predicates is extensive, and covering all the issues in a tiny article is impossible. Nevertheless, I wanted to show how many possibilities we have, even limited to integer predicates and elementary initial data.
