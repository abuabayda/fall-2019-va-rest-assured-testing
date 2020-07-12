package com.OfficeHrs;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTests {

    @Test
    public void addTest(){
        int actual = Calculator.add(1,1);
        int expected = 2;
        assertEquals(expected,actual);
        assertEquals(-1,Calculator.multiply(-1,1));
        assertEquals(0,Calculator.multiply(-1,0));
    }
    @Test
    public void minusTest(){
        assertEquals(1,Calculator.minus(2,1));
    }
    @Test
    public void divideTest(){
        assertEquals(2,Calculator.divide(10,5));
        assertEquals(0,Calculator.divide(10,0));
        assertNotEquals(10,Calculator.divide(10,0));
    }
}
