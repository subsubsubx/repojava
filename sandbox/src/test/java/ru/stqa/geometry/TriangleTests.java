package ru.stqa.geometry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TriangleTests {

    @Test
    void testArea() {
        Assertions.assertEquals(6, new Triangle(3, 4, 5).heronsFormula());
    }

    @Test
    void testPerimeter() {
        Assertions.assertEquals(12, new Triangle(3, 4, 5).perimeter());
    }
}
