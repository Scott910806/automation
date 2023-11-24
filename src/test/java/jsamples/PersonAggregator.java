package jsamples;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;

import java.time.LocalDate;

public class PersonAggregator implements ArgumentsAggregator {
    @Override
    public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
        return new Person(
                accessor.getString(0),
                accessor.getString(1),
                accessor.get(2, Person.Gender.class),
                accessor.get(3, LocalDate.class)
        );
    }
}
