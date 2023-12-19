package com.cgvsu.math.vector;

public class Vector4 {
        private double x;
        private double y;
        private double z;
        private double w;

        public Vector4(double x, double y, double z, double w) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.w = w;
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

    public double getW() {
        return w;
    }

    public void setW(double w) {
        this.w = w;
    }

    // Операция сложения векторов
        public Vector4 add(Vector4 other) {
            double newX = this.x + other.x;
            double newY = this.y + other.y;
            double newZ = this.z + other.z;
            double newW = this.w + other.w;
            return new Vector4(newX, newY, newZ, newW);
        }

        // Операция вычитания векторов
        public Vector4 subtract(Vector4 other) {
            double newX = this.x - other.x;
            double newY = this.y - other.y;
            double newZ = this.z - other.z;
            double newW = this.w - other.w;
            return new Vector4(newX, newY, newZ, newW);
        }

        // Операция умножения вектора на скаляр
        public Vector4 multiply(double scalar) {
            double newX = this.x * scalar;
            double newY = this.y * scalar;
            double newZ = this.z * scalar;
            double newW = this.w * scalar;
            return new Vector4(newX, newY, newZ, newW);
        }

        // Операция деления вектора на скаляр
        public Vector4 divide(double scalar) {
            if (scalar == 0) {
                throw new IllegalArgumentException("Cannot divide by zero");
            }
            double newX = this.x / scalar;
            double newY = this.y / scalar;
            double newZ = this.z / scalar;
            double newW = this.w / scalar;
            return new Vector4(newX, newY, newZ, newW);
        }

        // Вычисление длины вектора
        public double length() {
            return Math.sqrt(x*x + y*y + z*z + w*w);
        }

        // Нормализация вектора
        public Vector4 normalize() {
            double magnitude = length();
            return divide(magnitude);
        }

        // Скалярное произведение векторов
        public double dotProduct(Vector4 other) {
            return this.x * other.x + this.y * other.y + this.z * other.z + this.w * other.w;
        }

        // Векторное произведение векторов (для векторов размерности 3)
        public Vector4 crossProduct(Vector4 other) {
            throw new UnsupportedOperationException("Cross product is only defined for vectors of dimension 3");
        }
    }
