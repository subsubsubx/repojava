package ru.stqa.geometry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.InputMismatchException;

public class TriangleTests {

    @Test
    void testArea() {
        Assertions.assertEquals(6, new Triangle(3, 4, 5).heronsFormula());
    }

    @Test
    void testPerimeter() {
        Assertions.assertEquals(12, new Triangle(3, 4, 5).perimeter());
    }

    @Test
    void triangleEquals() {
        Triangle tri1 = new Triangle(7, 10, 5);
        Triangle tri2 = new Triangle(5, 7, 10);
        Assertions.assertEquals(tri1, tri2);
    }

    @Test
    void testNegativeValue() {
        Assertions.assertThrows(InputMismatchException.class, () -> new Triangle(-1, 2, 3));
        Assertions.assertThrows(InputMismatchException.class, () -> new Triangle(1, -2, 3));
        Assertions.assertThrows(InputMismatchException.class, () -> new Triangle(1, 2, -3));
    }

    @Test
    void testValidThrow() {
        Assertions.assertDoesNotThrow(() -> new Triangle(7, 10, 5));
    }

    @Test
    void testInvalidThrow() {
        Assertions.assertThrows(InputMismatchException.class, () -> new Triangle(1, 10, 13));
    }
}

