package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppTest {
    @BeforeEach
    public void setup() {
    }

    @Test
    public void testInit() {
        Assertions.assertEquals(5, 2 + 3);
    }
}
