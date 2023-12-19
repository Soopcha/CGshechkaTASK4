package com.cgvsu.math.vector;

import java.util.List;
import java.util.Objects;

public class Vector3 {
    private double x;
    private double y;
    private double z;

    public Vector3() {
    }

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
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

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    // Операция сложения векторов
    public Vector3 add(Vector3 other) {
        double newX = this.x + other.x;
        double newY = this.y + other.y;
        double newZ = this.z + other.z;
        return new Vector3(newX, newY, newZ);
    }

    // Операция вычитания векторов
    public Vector3 subtract(Vector3 other) {
        double newX = this.x - other.x;
        double newY = this.y - other.y;
        double newZ = this.z - other.z;
        return new Vector3(newX, newY, newZ);
    }

    // Операция умножения вектора на скаляр
    public Vector3 multiply(double scalar) {
        double newX = this.x * scalar;
        double newY = this.y * scalar;
        double newZ = this.z * scalar;
        return new Vector3(newX, newY, newZ);
    }

    // Операция деления вектора на скаляр
    public Vector3 divide(double scalar) {
        if (scalar == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        double newX = this.x / scalar;
        double newY = this.y / scalar;
        double newZ = this.z / scalar;
        return new Vector3(newX, newY, newZ);
    }

    // Вычисление длины вектора
    public double length() {
        return Math.sqrt(x * x + y*y + z*z);
    }

    // Нормализация вектора
    public Vector3 normalize() {
        double magnitude = length();
        return divide(magnitude);
    }

    // Скалярное произведение векторов
    public double dotProduct(Vector3 other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    // Векторное произведение векторов
    public Vector3 crossProduct(Vector3 other) {
        double newX = this.y * other.z - this.z * other.y;
        double newY = this.z * other.x - this.x * other.z;
        double newZ = this.x * other.y - this.y * other.x;
        return new Vector3(newX, newY, newZ);
    }


    //дописала по коду Артема п
    public void cross(Vector3 v1, Vector3 v2) {
        if (v1 == null || v2 == null) {
            throw new IllegalArgumentException("Vector can not be null");
        }

        double x = v1.y * v2.z - v1.z * v2.y;
        double y = v2.x * v1.z - v2.z * v1.x;

        this.z = v1.x * v2.y - v1.y * v2.x;
        this.x = x;
        this.y = y;
    }


    public static Vector3 fromTwoPoints(Vector3 vertex1, Vector3 vertex2) {
        return new Vector3(vertex2.x - vertex1.x,
                vertex2.y - vertex1.y,
                vertex2.z - vertex1.z);
    }

    public static Vector3 sum(List<Vector3> vectors) {
        final var result = new Vector3();

        vectors.forEach(result::add);

        return result;
    }

    //дописала по коду Артема п 3 метода
    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector3 vector3 = (Vector3) o;
        return Double.compare(vector3.x, x) == 0 &&
                Double.compare(vector3.y, y) == 0 &&
                Double.compare(vector3.z, z) == 0;
    }

    @Override
    public String toString() {
        return "Vector3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

}
