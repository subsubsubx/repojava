package ru.stqa.geometry;

public record Triangle(double a, double b, double c) {

    public double heronsFormula() {
        double p = (this.a + this.b + this.c) / 2;
        return Math.sqrt(p * (p - this.a) * (p - this.b) * (p - this.c));
    }

    public double perimeter() {
        return this.a + this.b + this.c;
    }
}

