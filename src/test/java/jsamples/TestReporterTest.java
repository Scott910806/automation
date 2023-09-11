package jsamples;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

import java.util.HashMap;
import java.util.Map;

class TestReporterDemo {
    @Test
    void reportSingleValue(TestReporter reporter){
        reporter.publishEntry("a status message");
    }

    @Test
    void reportKeyValuePair(TestReporter reporter){
        reporter.publishEntry("a key" , "a value");
    }

    @Test
    void reportMultipleKeyValuePairs(TestReporter reporter){
        Map<String, String> values = new HashMap<>();
        values.put("user name", "scott");
        values.put("password", "admin123");
        reporter.publishEntry(values);
    }
}
