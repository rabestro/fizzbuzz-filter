# FizzBuzz и искусство фильтрации: Stream API и предикаты

Проблема FizzBuzz - это классическая задача, которая часто встречается на собеседованиях для программистов. Обычно она формулируется так:

> Создайте программу, которая выводит числа от 1 до n.
> - Если число делится на 3, выведите 'Fizz'.
> - Если число делится на 5, выведите 'Buzz'.
> - Если число делится и на 3 и на 5, выведите 'FizzBuzz'.

Это упражнение подтолкнуло меня к идее поработать над задачами с использованием Stream API, используя заранее определенные предикаты `fizz` и `buzz`. Наша цель – создать различные фильтры для потока чисел используя эти предикаты.

Мы разберем решения четырех простых задач, а более сложная и интересная останется вам.

Сначала определим предикаты `fizz` и `buzz`:

```java
IntPredicate fizz = i -> i % 3 == 0;
IntPredicate buzz = i -> i % 5 == 0;
```

Поток целых чисел от 1 до 20 создадим с помощью метода `IntStream::rangeClosed`:

```java
var numbers = IntStream.rangeClosed(1, 20);
```

Цель всех задача – отфильтровать поток чисел создав предикат `fizzBuzz`, на основе уже определённых предикатов.

#### Задача №1. Фильтрация чисел, кратных 3 и 5

Необходимо отфильтровать числа, которые делятся нацело на 3 и 5. 
В этом случае есть два способа решить задачу. 
Например, мы можем использовать логический оператор `&&`:

```java
IntPredicate fizzBuzz = i -> fizz.test(i) && buzz.test(i);
```

Но более предпочтительный и удобный вариант – использовать метод `and`, принадлежащий интерфейсу `IntPredicate`:

```java
IntPredicate fizzBuzz = fizz.and(buzz);

assertThat(numbers.filter(fizzBuzz))
        .as("Numbers divisible by three and five")
        .containsExactly(15);
```

#### Задача №2. Фильтрация чисел, кратных 3 или 5

Здесь решение аналогично предыдущей задаче, но мы используем метод `or` вместо `and`:

```java
IntPredicate fizzBuzz = fizz.or(buzz);

assertThat(numbers.filter(fizzBuzz))
        .as("Numbers divisible by three or five")
        .containsExactly(3, 5, 6, 9, 10, 12, 15, 18, 20);
```

#### Задача №3. Фильтрация чисел, не кратных 3 и 5

Для того чтобы отфильтровать числа, которые не делятся ни на 3, ни на 5 можно использовать метод `IntStream::negate`. Решение выглядит следующим образом:

```java
IntPredicate fizzBuzz = fizz.or(buzz).negate();

assertThat(numbers.filter(fizzBuzz))
        .as("Numbers not divisible by three or five")
        .containsExactly(1, 2, 4, 7, 8, 11, 13, 14, 16, 17, 19);
```

#### Задача №4. Фильтрация чисел, кратных 3 или 5, но не обоим

Следующая задача – отфильтровать числа, которые делятся на 3 или на 5, но не делятся нацело на оба числа одновременно. Здесь можно использовать все методы интерфейса `IntStream`, однако лучший вариант – исключающее ИЛИ:

```java
IntPredicate fizzBuzz = i -> fizz.test(i) ^ buzz.test(i);

assertThat(numbers.filter(fizzBuzz))
        .as("Numbers divisible by either three or five")
        .containsExactly(3, 5, 6, 9, 10, 12, 18, 20);
```

#### Бонусная задача

В заключение предлагаю вам проверить ваш навык составления предикатов. Составьте предикат для чисел внутри последовательностей _FizzBuzz_ исключая границы. Последовательность начинается числом, удовлетворяющим предикату `fizz`, и заканчивается числом, удовлетворяющим предикату `buzz`.

Например, для последовательности чисел от 1 до 20, результат должен выглядеть следующим образом:

1, 2, (3, **4**, 5), (6, **7**, **8**, **9**, 10), 11, (12, **13**, **14**, 15), 16, 17, (18, **19**, 20)

Задача выбрать числа внутри скобок не включая границы.

Для удобства можете использовать следующий код для теста:

```java
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
```

В качестве альтернативы я предлагаю следующую задачу которая аналогична предыдущей.

```java 
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
    Predicate<String> fizzBuzz = i -> false;

    assertThat(markdown.lines().filter(fizzBuzz))
            .as("Java code snippets")
            .containsExactly("""
                    System.out.println("Hello, World!");""");
}
```
Надеюсь, что последняя задачка более интересная чем предыдущие. 
Напишите в комментариях, насколько быстро вам удалось её решить.
