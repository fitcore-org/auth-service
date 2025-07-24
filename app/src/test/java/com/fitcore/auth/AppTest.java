package com.fitcore.auth;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    
    @Test
    void testBasicAssertion() {
        // Simple test to verify JUnit is working
        assertTrue(true, "Basic test should pass");
    }
    
    @Test
    void testStringComparison() {
        String expected = "FitCore Auth Service";
        String actual = "FitCore Auth Service";
        assertEquals(expected, actual, "Strings should be equal");
    }
}
