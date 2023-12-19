package com.cgvsu.math.vector;

public class Vector2 {
    private double x;
    private double y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    // Операция сложения векторов
    public Vector2 add(Vector2 other) {
        double newX = this.x + other.x;
        double newY = this.y + other.y;
        return new Vector2(newX, newY);
    }

    // Операция вычитания векторов
    public Vector2 subtract(Vector2 other) {
        double newX = this.x - other.x;
        double newY = this.y - other.y;
        return new Vector2(newX, newY);
    }

    // Операция умножения вектора на скаляр
    public Vector2 multiply(double scalar) {
        double newX = this.x * scalar;
        double newY = this.y * scalar;
        return new Vector2(newX, newY);
    }

    // Операция деления вектора на скаляр
    public Vector2 divide(double scalar) {
        double newX = this.x / scalar;
        double newY = this.y / scalar;
        return new Vector2(newX, newY);
    }

    // Вычисление длины вектора
    public double length() {
        return Math.sqrt(x*x + y*y);
    }

    // Нормализация вектора
    public Vector2 normalize() {
        double magnitude = length();
        return divide(magnitude);
    }

    // Скалярное произведение векторов
    public double dotProduct(Vector2 other) {
        return this.x * other.x + this.y * other.y;
    }

    // Векторное произведение векторов (для векторов размерности 3)
    public Vector2 crossProduct(Vector2 other) {
        // Для вектора размерности 2 векторное произведение не определено
        throw new UnsupportedOperationException("Cross product is only defined for vectors of dimension 3");
    }

}
