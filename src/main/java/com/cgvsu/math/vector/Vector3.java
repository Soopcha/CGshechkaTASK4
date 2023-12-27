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
    public final void addThis(Vector3 other1) {
        this.x += other1.x;
        this.y += other1.y;
        this.z += other1.z;
    }

    // Операция вычитания векторов
    public Vector3 subtract(Vector3 other) {
        double newX = this.x - other.x;
        double newY = this.y - other.y;
        double newZ = this.z - other.z;
        return new Vector3(newX, newY, newZ);
    }
    public final void subtractThis(Vector3 other1) {
        this.x -= other1.x;
        this.y -= other1.y;
        this.z -= other1.z;
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
    public Vector3 cross(Vector3 other) {
        double newX = this.y * other.z - this.z * other.y;
        double newY = this.z * other.x - this.x * other.z;
        double newZ = this.x * other.y - this.y * other.x;
        return new Vector3(newX, newY, newZ);
    }
    public final void crossProduct(Vector3 other1, Vector3 other2) {
        double v1 = other1.y * other2.z - other1.z * other2.y;
        double v2 = other1.z * other2.x - other1.x * other2.z;
        this.z = other1.x * other2.y - other1.y * other2.x;
        this.x = v1;
        this.y = v2;
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
        if (vectors == null || vectors.isEmpty()) {
            throw new IllegalArgumentException("List of vectors is null or empty");
        }

        double sumX = 0.0;
        double sumY = 0.0;
        double sumZ = 0.0;

        for (Vector3 vector : vectors) {
            sumX += vector.getX();
            sumY += vector.getY();
            sumZ += vector.getZ();
        }

        return new Vector3(sumX, sumY, sumZ);
    }

    //дописала по коду Артема п 3 метода
    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }


    /* @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector3 vector3 = (Vector3) o;
        return Double.compare(vector3.getX(), x) == 0 &&
                Double.compare(vector3.getY(), y) == 0 &&
                Double.compare(vector3.getZ(), z) == 0;
    }

     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector3 vector3 = (Vector3) o;
        return Math.abs(vector3.getX() - x) < EPSILON &&
                Math.abs(vector3.getY() - y) < EPSILON &&
                Math.abs(vector3.getZ() - z) < EPSILON;
    }

    private static final double EPSILON = 1e-10;  // или любое другое подходящее значение погрешности

    @Override
    public String toString() {
        return "Vector3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
    public double get(int index) {
        switch (index) {
            case 0:
                return x;
            case 1:
                return y;
            case 2:
                return z;
        }
        throw new IllegalArgumentException("Индекс выходит за границы");
    }

    //    public static Vector2 vertexToPoint1(final Vector3 vertex, final int width, final int height) {
//        return new Vector2((float) vertex.get(0) * width + width / 2.0F, (float) -vertex.get(1) * height + height / 2.0F);
//    }
    public final void subtract(Vector3 other1, Vector3 other2) {
        this.x = other1.x - other2.x;
        this.y = other1.y - other2.y;
        this.z = other1.z - other2.z;
    }
    public static Vector2 vertexToPoint(final Vector3 vertex, final int width, final int height) {
        return new Vector2((double) vertex.get(0) * width + width / 2.0F, (double) -vertex.get(1) * height + height / 2.0F);
    }

}
