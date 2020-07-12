package com.cbt.tests.day1_intro;

import org.junit.jupiter.api.*;

public class Junit5Intro {
  @BeforeAll
  public static void runBeforeEveryThingInCLass() {
    System.out.println("runs once before everything");
  }

  @BeforeEach
  public void runBeforeEachTest() {
    System.out.println("runs before every test");
  }

  @Test
  public void test1() {

    Assertions.assertEquals(1, 1);
  }

  @Test
  public void test2() {

    Assertions.assertTrue(true);

    Assertions.assertAll(
         () -> Assertions.assertTrue(true),
         () -> Assertions.assertEquals(1, 2),
         () -> Assertions.assertTrue(true),
         () -> Assertions.assertEquals(33, 1)
    );
  }

  @AfterEach
  public void runAfterEachTest() {
    System.out.println("runs after every test");
  }
}
