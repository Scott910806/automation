package jsamples;

import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.junit.jupiter.api.Assertions.*;
import static jsamples.MyStringUtils.isPalindrome;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.ThrowingConsumer;

import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;


// dynamic test
@Tag("demo")
class MyFactoryTest {
    private final Caculator caculator = new Caculator();

    @TestFactory
    // This will result in a JunitException
    List<String> dynamicTestWithInvalidReturnType(){
        return Arrays.asList("hello");
    }

    @TestFactory
    Collection<DynamicTest> dynamicTestsFromCollection(){
        return Arrays.asList(
                dynamicTest("1st dynamic test", ()->assertTrue(isPalindrome("mom"))),
                dynamicTest("2st dynamic test", ()->assertEquals(4, caculator.multiply(2,2)))
        );
    }

    @TestFactory
    Iterable<DynamicTest> dynamicTestsFromIterable(){
        return Arrays.asList(
                dynamicTest("1st dynamic test", ()->assertTrue(isPalindrome("mom"))),
                dynamicTest("2st dynamic test", ()->assertEquals(4, caculator.multiply(2,2)))
        );
    }

    @TestFactory
    Iterator<DynamicTest> dynamicTestsFromIterator(){
        return Arrays.asList(
                dynamicTest("1st dynamic test", ()->assertTrue(isPalindrome("mom"))),
                dynamicTest("2st dynamic test", ()->assertEquals(4, caculator.multiply(2,2)))
        ).iterator();
    }

    @TestFactory
    DynamicTest[] dynamicTestsFromArray(){
        return new DynamicTest[]{
                dynamicTest("1st dynamic test", ()->assertTrue(isPalindrome("mom"))),
                dynamicTest("2st dynamic test", ()->assertEquals(4, caculator.multiply(2,2)))
        };
    }

    @TestFactory
    Stream<DynamicTest> dynamicTestFromStream(){
        return Stream.of("racecar", "radar", "mom", "dad").map(text -> dynamicTest(text,() -> assertTrue(isPalindrome(text))));
    }

    @TestFactory
    Stream<DynamicTest> dynamicTestFromIntStream(){
        return IntStream.iterate(0, n->n+2).limit(10).mapToObj(n -> dynamicTest("test" + n, ()->assertTrue(n % 2 == 0)));
    }

    @TestFactory
    Stream<DynamicTest> generateRandomNumberOfTestsFromIterator(){
        Iterator<Integer> inputGenerator = new Iterator<Integer>() {
            Random random = new Random();
            int current;
            @Override
            public boolean hasNext() {
                current = random.nextInt(100);
                return current % 7 != 0;
            }

            @Override
            public Integer next() {
                return current;
            }
        };
        Function<Integer, String> displayNameGenerator = (input) -> "input: " + input;
        ThrowingConsumer<Integer> testExecutor = (input) -> assertTrue(input % 7 != 0);
        return DynamicTest.stream(inputGenerator, displayNameGenerator, testExecutor);
    }

    @TestFactory
    Stream<DynamicTest> dynamicTestsFromStreamFactoryMethod(){
        Stream<String> inputStream = Stream.of("racecar", "radar", "mom", "dad");
        Function<String, String> displayNameGenerator = text -> text + " is palindrome";
        ThrowingConsumer<String> testExecutor = input -> assertTrue(isPalindrome(input));
        return DynamicTest.stream(inputStream, displayNameGenerator, testExecutor);
    }


    @TestFactory
    Stream<DynamicTest> dynamicTestsFromStreamFactoryMethodWithNames(){
        Stream<Named<String>> inputStream = Stream.of(
                Named.named("racecar is palindrome", "racecar"),
                Named.named("radar is palindarome", "radar"),
                Named.named("mom also seems to be a palindarome", "mom"),
                Named.named("dad is yet another palindarome", "dad")
        );
        return DynamicTest.stream(inputStream, input -> assertTrue(isPalindrome(input)));
    }

    @TestFactory
    Stream<DynamicNode> dynamicTestWithContainers(){
        return Stream.of("A", "B", "C").map(input -> dynamicContainer("input: " + input,
                Stream.of(dynamicTest("not null", () -> assertNotNull(input)),
                        dynamicContainer("properties", Stream.of(
                                dynamicTest("length>0", () ->assertTrue(input.length() > 0)),
                                dynamicTest("not empty", () -> assertFalse(input.isEmpty()))
                        )))
        ));
    }

    @TestFactory
    DynamicNode dynamicNodeSingleTest(){
        return dynamicTest("'pop' is a palindrome", () -> assertTrue(isPalindrome("pop")));
    }

    @TestFactory
    DynamicNode dynamicNodeSingleContainer(){
        return dynamicContainer("palindromes",
                Stream.of("racecar", "radar", "mom", "dad").map(text -> dynamicTest(text, ()-> assertTrue(isPalindrome(text))))
        );
    }
}
