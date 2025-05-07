package com.demo.gradle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SampleTest {
  @Test
  public void testAddition() {
    System.out.println("testAddition is called");
    assertEquals(2, 1 + 1, "1 + 1 should equal 2");
  }
}
