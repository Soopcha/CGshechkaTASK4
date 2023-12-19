package com.cgvsu.math.matrix;

import com.cgvsu.math.vector.Vector3;

public class Matrix3x3 {
    private double[][] matrix;

    public Matrix3x3() {
        matrix = new double[3][3];
    }

    public Matrix3x3(double[][] matrix) {
        if (matrix.length != 3 || matrix[0].length != 3) {
            throw new IllegalArgumentException("Invalid matrix dimensions");
        }
        this.matrix = matrix;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public Matrix3x3 add(Matrix3x3 other) {
        double[][] result = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = this.matrix[i][j] + other.matrix[i][j];
            }
        }
        return new Matrix3x3(result);
    }

    public Matrix3x3 subtract(Matrix3x3 other) {
        double[][] result = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = this.matrix[i][j] - other.matrix[i][j];
            }
        }
        return new Matrix3x3(result);
    }

    public Matrix3x3 multiply(double scalar) {
        double[][] result = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = this.matrix[i][j] * scalar;
            }
        }
        return new Matrix3x3(result);
    }



    public Vector3 multiply(Vector3 vector) {
        if (vector == null) {
            throw new NullPointerException("Вектор не может быть нулевым");
        }
        double[] result = new double[3];
        for (int i = 0; i < 3; i++) {
            result[i] = this.matrix[i][0] * vector.getX() +
                    this.matrix[i][1] * vector.getY() +
                    this.matrix[i][2] * vector.getZ();
        }
        return new Vector3(result[0], result[1], result[2]);
    }

    public Matrix3x3 multiply(Matrix3x3 other) {
        double[][] result = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    result[i][j] += this.matrix[i][k] * other.matrix[k][j];
                }
            }
        }
        return new Matrix3x3(result);
    }

    public Matrix3x3 transpose() {
        double[][] result = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = this.matrix[j][i];
            }
        }
        return new Matrix3x3(result);
    }

    public static Matrix3x3 identity() {
        double[][] result = new double[3][3];
        result[0][0] = 1;
        result[1][1] = 1;
        result[2][2] = 1;
        return new Matrix3x3(result);
    }

    public static Matrix3x3 zero() {
        double[][] result = new double[3][3];
        return new Matrix3x3(result);
    }
}
