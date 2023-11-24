package jsamples;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyArgumentAggregation {

    @ParameterizedTest
    @CsvSource({
            "Jane, Doe, F, 1990-05-20",
            "John, Doe, M, 1990-10-22"
    })
    void testWithArgumentsAccessor(ArgumentsAccessor arguments){
        Person person = new Person(
                arguments.getString(0),
                arguments.getString(1),
                arguments.get(2, Person.Gender.class),
                arguments.get(3, LocalDate.class)
                );
        if (person.getFirstname().equals("Jane")){
            assertEquals(Person.Gender.F, person.getGender());
        }
        else{
            assertEquals(Person.Gender.M, person.getGender());
        }
        assertEquals("Doe", person.getLastname());
        assertEquals(1990, person.getBirthday().getYear());
    }

    @ParameterizedTest
    @CsvSource({
            "Jane, Doe, F, 1990-05-20",
            "John, Doe, M, 1990-10-22"
    })
    void testWithCustomAggregatorAnnotation(@CsvToPerson Person person){
        if (person.getFirstname().equals("Jane")){
            assertEquals(Person.Gender.F, person.getGender());
        }
        else{
            assertEquals(Person.Gender.M, person.getGender());
        }
        assertEquals("Doe", person.getLastname());
        assertEquals(1990, person.getBirthday().getYear());
    }

}
