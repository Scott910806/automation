package jsamples;

import io.qameta.allure.Param;
import lombok.Value;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.*;
import org.junit.jupiter.params.provider.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

public class MyCsvSource {

    @ParameterizedTest
    @CsvSource({
            "apple,      1",
            "banana,     2",
            "'lemon, lime',  0xF1",
            "strawberry,     700_000"
    })
    void testWithCsvSource(String fruit, int rank){
        assertNotNull(fruit);
        assertNotEquals(0, rank);
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/java/data/test/two-column.csv", numLinesToSkip = 1)
    void testWithCsvFileSourceFromFile(String country, int reference){
        assertNotNull(country);
        assertNotEquals(0, reference);
    }

    @ParameterizedTest(name = "[{index}] : {arguments}")
    @CsvFileSource(files = "src/test/java/data/test/two-column.csv", useHeadersInDisplayName = true)
    void testWithCsvFileSourceAndHeader(String country, int reference){
        assertNotNull(country);
        assertNotEquals(0, reference);
    }

    @ParameterizedTest
    @ArgumentsSource(MyArgumentsProvider.class)
    void testWithArgumentsSource(String argument){
        assertNotNull(argument);
    }

    @ParameterizedTest
    @ValueSource(strings = {"42 cats"})
    void testWithImplicitFallbackArgumentConversion(Book book){
        assertEquals("42 cats", book.getTitle());
    }

    static class Book{
        private final String title;

        private Book(String title){
            this.title = title;
        }

        public static Book fromTitle(String title){
            return new Book(title);
        }

        public String getTitle(){
            return this.title;
        }
    }

    @ParameterizedTest
    @EnumSource(ChronoUnit.class)
    void testWithExplicitArgumentConversion(@ConvertWith(ToStringArgumentConverter.class) String argument){
        assertNotNull(ChronoUnit.valueOf(argument));
    }

    static class ToStringArgumentConverter extends SimpleArgumentConverter{

        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(String.class, targetType, "Can only convert to String");
            if (source instanceof Enum<?>){
                return ((Enum<?>) source).name();
            }
            return String.valueOf(source);
        }
    }


    @ParameterizedTest
    @ValueSource(strings = {"we", "are", "the", "best"})
    void testWithAnotherExplicitArgumentConversion(@ConvertWith(ToLengthArgumentConverter.class) Integer length){
        assertTrue(length >= 0);
    }

    static class ToLengthArgumentConverter extends TypedArgumentConverter<String, Integer>{

        protected ToLengthArgumentConverter(){
            super(String.class, Integer.class);
        }

        @Override
        protected Integer convert(String source) throws ArgumentConversionException {
            return (source != null ? source.length() : 0);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"01.01.2023", "01.05.2023"})
    void testWithExplicitJavaTimeConverter(@JavaTimeConversionPattern("dd.MM.yyyy")LocalDate argument){
        assertEquals(2023, argument.getYear());
    }

    @ParameterizedTest
    @ValueSource(strings = {"2023-01-01", "2023-05-01"})
    void testWithImplicitJavaTimeConverter(LocalDate argument){
        assertEquals(2023, argument.getYear());
    }

}

