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

    @Test
    void triangleEquals() {
     Triangle tri1 = new Triangle(1, 2, 3);
     Triangle tri2 = new Triangle(3, 2, 1);
     Assertions.assertTrue(tri1.equals(tri2));
    }

}

