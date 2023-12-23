package ru.stqa.geometry;

import java.util.InputMismatchException;

public record Triangle(double a, double b, double c) {

    public Triangle(double a, double b, double c) {
        if (!(a <= 0) && !(b <= 0) && !(c <= 0)) {
            if (!(a + b > c) || !(a + c > b) || !(b + c > a)) {
                throw new InputMismatchException("Сумма двух сторон треугольника меньше или равна третьей - "
                        + "a: " + a
                        + ", b: " + b
                        + ", c: " + c);
            } else {
                this.a = a;
                this.b = b;
                this.c = c;
            }
        } else throw new InputMismatchException("Отрицательное значение! - "
                + "a: " + a
                + ", b: " + b
                + ", c: " + c);

    }

    public double heronsFormula() {
        double p = (this.a + this.b + this.c) / 2;
        return Math.sqrt(p * (p - this.a) * (p - this.b) * (p - this.c));
    }

    public double perimeter() {
        return this.a + this.b + this.c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triangle triangle = (Triangle) o;
        return (Double.compare(triangle.a, this.a) == 0 && Double.compare(triangle.b, this.b) == 0
                && Double.compare(triangle.c, this.c) == 0)
                || (Double.compare(triangle.a, this.c) == 0 && Double.compare(triangle.b, this.a) == 0
                && Double.compare(triangle.c, this.b) == 0)
                || (Double.compare(triangle.a, this.a) == 0 && Double.compare(triangle.b, this.c) == 0
                && Double.compare(triangle.c, this.b) == 0)
                || (Double.compare(triangle.a, this.b) == 0 && Double.compare(triangle.b, this.c) == 0
                && Double.compare(triangle.c, this.a) == 0)
                || (Double.compare(triangle.a, this.b) == 0 && Double.compare(triangle.b, this.a) == 0
                && Double.compare(triangle.c, this.c) == 0)
                || (Double.compare(triangle.a, this.c) == 0 && Double.compare(triangle.b, this.b) == 0
                && Double.compare(triangle.c, this.a) == 0);
    }
}

