package com.demo.gradle;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SampleTest {

    @Test
    public void testAddition() {
        //这里有输出内容
        System.out.println("testAddition is called");
        assertEquals(2, 1 + 1, "1 + 1 should equal 2");
    }
}