package jsamples;

import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


public class MyParameterizedTest {
    @ParameterizedTest
    @ValueSource(strings = {"racecar", "radar", "able was I ere I saw elba"})
    void palindromes(String candidate){
        assertTrue(MyStringUtils.isPalindrome(candidate));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void testWithValueSource(int argument){
        assertTrue(argument >0 && argument < 4);
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    @ValueSource(strings = {" ", "    ", "\t", "\n"})
    void nullEmptyAndBlankStrings(String text){
        assertTrue(text == null || text.trim().isEmpty());
    }

    @ParameterizedTest
    @EnumSource(ChronoUnit.class)
    void testWithEnumSource(TemporalUnit unit){
        assertNotNull(unit);
    }

    @ParameterizedTest
    @EnumSource
    void testWithEnumSourceAutoDetection(Season season){
        assertTrue(season.equals(Season.SUMMER));
    }

    @ParameterizedTest
    @EnumSource(names = {"DAYS", "HOURS"})
    void testWithEnumSourceInclude(ChronoUnit unit){
        assertTrue(EnumSet.of(ChronoUnit.DAYS, ChronoUnit.HOURS).contains(unit));
    }

    @ParameterizedTest
    @EnumSource(mode = EnumSource.Mode.EXCLUDE, names = {"ERAS", "FOREVER"})
    void testWithEnumSourceExclude(ChronoUnit unit){
        assertFalse(EnumSet.of(ChronoUnit.ERAS, ChronoUnit.FOREVER).contains(unit));
    }

    @ParameterizedTest
    @EnumSource(mode = EnumSource.Mode.MATCH_ALL, names = "^.*DAYS$")
    void testWithEnumSourceRegex(ChronoUnit unit){
        assertTrue(unit.name().endsWith("DAYS"));
    }

    @ParameterizedTest
    @MethodSource("stringProvider")
    void testWithExplicitLocalMethodSource(String argument){
        assertNotNull(argument);
    }

    static Stream<String> stringProvider(){
        return Stream.of("apple", "banana");
    }

    @ParameterizedTest
    @MethodSource
    void testWithDefaultLocalMethodSource(int argument){
        assertNotEquals(9, argument);
    }

    static IntStream range(){ return IntStream.range(0, 20).skip(10); }

    @ParameterizedTest
    @MethodSource("stringIntAndListProvider")
    void testWithMultiArgMethodSource(String str, int num, List<String> list){
        assertEquals(5, str.length());
        assertTrue(num >=1 && num<=2);
        assertEquals(2, list.size());
    }

    static Stream<Arguments> stringIntAndListProvider(){
        return Stream.of(
                Arguments.of("apple", 1, Arrays.asList("a", "b")),
                Arguments.of("lemon", 2, Arrays.asList("x", "y"))
        );
    }

    @ParameterizedTest
    @MethodSource("jsamples.StringProviders#tinyStrings")
    void testWithExternalMethodSource(String tinyStrings){
        assertNotNull(tinyStrings);
    }

    @RegisterExtension
    static final IntegerResolver integerResolver = new IntegerResolver();

    @ParameterizedTest
    @MethodSource("factoryMethodWithArgument")
    void testWithFactoryMethodWithArguments(String argument){
        assertTrue(argument.startsWith("2"));
    }

    static Stream<Arguments> factoryMethodWithArgument(int quantity){
        return Stream.of(
                Arguments.arguments(quantity + "apples"),
                Arguments.arguments(quantity + "lemons")
        );
    }

    static class IntegerResolver implements ParameterResolver{

        @Override
        public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
            return parameterContext.getParameter().getType() == int.class;
        }

        @Override
        public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
            return 2;
        }
    }
}


enum Season{
    SPRING,SUMMER,AUTUMN,WINTER
}

class StringProviders{
    static Stream<String> tinyStrings(){
        return Stream.of(".", "oo", "000");
    }
}
