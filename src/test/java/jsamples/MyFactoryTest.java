package jsamples;

import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.junit.jupiter.api.Assertions.*;
import static jsamples.MyStringUtils.isPalindrome;

import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.List;
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
