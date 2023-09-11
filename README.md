# Beyond FizzBuzz: Stream API-based Tasks Inspired by the Classic Problem

The FizzBuzz problem is a classic coding challenge often used in programming interviews. It is usually presented as follows:

>Create a program that prints numbers from 1 to n.
>- If the number is divisible by 3, print 'Fizz';
>- If the number is divisible by 5, print 'Buzz';
>- If the number is divisible by both 3 and 5, print 'FizzBuzz'.

This problem inspired me to work with Stream API by using predefined `fizz` and `buzz` predicates. Our goal is to create various filters for a stream of integers using these predicates.

We will cover solutions for four basic tasks, while a more interesting and challenging one will be left for you to solve.

First, let's define the `fizz` and `buzz` predicates:

```java
IntPredicate fizz = i -> i % 3 == 0;
IntPredicate buzz = i -> i % 5 == 0;
```

We can create a stream of integers from 1 to 20 using the `IntStream::rangeClosed` method:

```java
var numbers = IntStream.rangeClosed(1, 20);
```

The goal for all the following tasks is to filter the integer stream by creating a `fizzBuzz` predicate based on the defined `fizz` and `buzz` predicates.

### Task 1: Filtering Numbers Divisible by 3 and 5

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

### Task 2: Filtering Numbers Divisible by 3 or 5

This solution is similar to the previous one but uses the `or` method instead of `and`:

```java
IntPredicate fizzBuzz = fizz.or(buzz);

assertThat(numbers.filter(fizzBuzz))
        .as("Numbers divisible by three or five")
        .containsExactly(3, 5, 6, 9, 10, 12, 15, 18, 20);
```

### Task 3: Filtering Numbers Not Divisible by 3 and 5

In order to filter numbers that are not divisible by 3 or 5, we can use the `IntStream::negate` method. The solution looks like this:

```java
IntPredicate fizzBuzz = fizz.or(buzz).negate();

assertThat(numbers.filter(fizzBuzz))
        .as("Numbers not divisible by three or five")
        .containsExactly(1, 2, 4, 7, 8, 11, 13, 14, 16, 17, 19);
```

### Task 4: Filtering Numbers Divisible by 3 or 5 but Not Both

The next task is to filter numbers that are divisible by either 3 or 5, but not both simultaneously. While it is possible to use all the methods from the `IntStream` interface, the optimal choice would be the exclusive OR (XOR):

```java
IntPredicate fizzBuzz = i -> fizz.test(i) ^ buzz.test(i);

assertThat(numbers.filter(fizzBuzz))
        .as("Numbers divisible by either three or five")
        .containsExactly(3, 5, 6, 9, 10, 12, 18, 20);
```

### Bonus Task

As a final challenge, I invite you to test your predicate-building skills. Create a predicate for numbers within FizzBuzz sequences, excluding the boundaries. The sequence starts with a number satisfying the `fizz` predicate and concludes with a number satisfying the `buzz` predicate.

For example, for a number sequence from 1 to 20, the result should be as follows:

1, 2, (3, **4**, 5), (6, **7**, **8**, **9**, 10), 11, (12, **13**, **14**, 15), 16, 17, (18, **19**, 20)

The task is to select the numbers within the brackets, excluding the boundaries.

You can use the following code for convenience in testing:

```java
@Test
@DisplayName("Filter out numbers between integers divisible by three and by five.")
void numbers_between_integers_divisible_by_three_and_by_five() {
    var numbers = IntStream.rangeClosed(1, 20);

    // TODO: Define the predicate
    IntPredicate fizzBuzz = i -> false;

    assertThat(numbers.filter(fizzBuzz))
            .as("Numbers between interegers divisible by three and by five")
            .containsExactly(4, 7, 8, 9, 13, 14, 19);
}
```

I hope the final task is more engaging than the previous ones. Let me know in the comments how quickly you were able to solve it.
