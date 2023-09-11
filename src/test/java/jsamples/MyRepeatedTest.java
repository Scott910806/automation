package jsamples;

import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

@Slf4j
class RepeatedTestDemo {

    @BeforeEach
    void beforeEach(TestInfo testInfo, RepetitionInfo repetitionInfo){
        int currentRepetition = repetitionInfo.getCurrentRepetition();
        int totalRepetition = repetitionInfo.getTotalRepetitions();
        String methodName = testInfo.getTestMethod().get().getName();
        log.info(String.format("About to execute repetition %d of %d for %s", currentRepetition, totalRepetition, methodName));
    }

    @RepeatedTest(10)
    void repeatedTest(){}

    @RepeatedTest(5)
    void repeatedTestWithRepetitionInfo(RepetitionInfo repetitionInfo){
        assertEquals(5, repetitionInfo.getTotalRepetitions());
    }

    @RepeatedTest(value = 1, name = "{displayName} {currentRepetition}/{totalRepetitions}")
    @DisplayName("Repeat!")
    void customDisplayName(TestInfo testInfo){
        assertEquals("Repeat! 1/1", testInfo.getDisplayName());
    }

    @RepeatedTest(value = 1, name = RepeatedTest.LONG_DISPLAY_NAME)
    @DisplayName("Details...")
    void customDisplayNameWithLongPattern(TestInfo testInfo){
        assertEquals("Details... :: repetition 1 of 1", testInfo.getDisplayName());
    }
}
