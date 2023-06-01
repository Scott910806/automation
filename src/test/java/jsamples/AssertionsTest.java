package jsamples;

import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class AssertionsTest {
    private final Caculator caculator = new Caculator();
    private final Person person = new Person("Jane", "Doe");

    @Test
    void standardAssertions(){
        assertEquals(2, caculator.add(1, 1));
        assertEquals(4, caculator.multiply(2, 2), "The optional failure message is now the last parameter");
        assertTrue('a' > 'b', ()->"Assertion messages can be lazily evaluated -- " +
                "to avoid constructing complex messages unnecessarily");
    }

    @Test
    void groupAssertions(){
        // In a grouped assertion all assertions are executed, and all failures will be reported together.
        assertAll("person",
                ()->assertEquals("Jane1", person.getFirstname()),
                ()->assertEquals("Doe2", person.getLastname())
        );
    }

    @Test
    void dependentAssertions(){
        // Within a code block, if an assertion fails, the subsequent code in the same block will be skipped.
        assertAll("properties",
                ()->{
                    String firstname = person.getFirstname();
                    assertNotNull(firstname);
                // Executed only if the previous assertion is valid.
                    assertAll("firstname",
                            ()->assertTrue(firstname.startsWith("J")),
                            ()->assertTrue(firstname.endsWith("e")));
                },
                ()->{
                    String lastname = person.getLastname();
                    assertNotNull(lastname);
                // Executed only if the previous assertion is valid.
                    assertAll("lastname",
                            ()->assertTrue(lastname.startsWith("D")),
                            ()->assertTrue(lastname.endsWith("e")));
                }
        );
    }

    @Test
    void exceptionTesting(){
        Exception exception = assertThrows(ArithmeticException.class, ()->caculator.divide(1,0));
        assertEquals("/ by zero", exception.getMessage());
    }

    @Test
    void timeoutNotExceeded(){
        assertTimeout(Duration.ofMinutes(2), ()->{
            // Perform task than takes less than 2 minutes
        });
    }

    @Test
    void timeoutNotExceededWithResult(){
        // The following assertion succeeds, and return the supplied an object
        String actualResult = assertTimeout(Duration.ofMinutes(2), ()->{return "a result";});
        assertEquals("a result", actualResult);
    }

    @Test
    void timeoutNotExceededWithMethod(){
        // The following assertion invokes a method reference and return an object
        String actualMethod = assertTimeout(Duration.ofMinutes(2), AssertionsDemo::greeting);
        assertEquals("hello world", actualMethod);
    }

    @Test
    void timeoutExceeded(){
        assertTimeout(Duration.ofMillis(10), ()->{
            // Simulate task that takes more than 10ms
            Thread.sleep(100);
        });
    }

    @Test
    void timeoutExceededWithPreemptiveTermination(){
        assertTimeoutPreemptively(Duration.ofMillis(10), ()->{
            // Simulate task that takes more than 10ms
            new CountDownLatch(1).await();
        });
    }

    // Third-party assertion library:Hamcrest
    @Test
    void hamcrestAssertionDemo(){
        assertThat(caculator.subtract(4, 1), is(equalTo(3)));
    }
}

class AssertionsDemo{
    static String greeting(){
        return "hello world";
    }
}