package jsamples;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import jsamples.RandomParametersExtension.Random;

@ExtendWith(RandomParametersExtension.class)
class ParameterResolverTestDemo {

    @Test
    void injectsInteger(@Random int i, @Random int j){
        assertNotEquals(i, j);
    }

    @Test
    void injectsDouble(@Random double d){
        assertEquals(0.0, d, 1.0);
    }
}
