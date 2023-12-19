package com.cgvsu.math.matrix;

import com.cgvsu.math.vector.Vector4;

public class Matrix4x4 {
    private double[][] matrix;

    public Matrix4x4() {
        matrix = new double[4][4];
    }

    public Matrix4x4(double[][] matrix) {
        if (matrix.length != 4 || matrix[0].length != 4) {
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

    public Matrix4x4 add(Matrix4x4 other) {
        double[][] result = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = this.matrix[i][j] + other.matrix[i][j];
            }
        }
        return new Matrix4x4(result);
    }

    public Matrix4x4 subtract(Matrix4x4 other) {
        double[][] result = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = this.matrix[i][j] - other.matrix[i][j];
            }
        }
        return new Matrix4x4(result);
    }

    public Matrix4x4 multiply(double scalar) {
        double[][] result = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = this.matrix[i][j] * scalar;
            }
        }
        return new Matrix4x4(result);
    }

    public Vector4 multiply(Vector4 vector) {
        double[] result = new double[4];
        for (int i = 0; i < 4; i++) {
            result[i] = this.matrix[i][0] * vector.getX() +
                    this.matrix[i][1] * vector.getY() +
                    this.matrix[i][2] * vector.getZ() +
                    this.matrix[i][3] * vector.getW();
        }
        return new Vector4(result[0], result[1], result[2], result[3]);
    }

    public Matrix4x4 multiply(Matrix4x4 other) {
        double[][] result = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    result[i][j] += this.matrix[i][k] * other.matrix[k][j];
                }
            }
        }
        return new Matrix4x4(result);
    }

    public Matrix4x4 transpose() {
        double[][] result = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = this.matrix[j][i];
            }
        }
        return new Matrix4x4(result);
    }

    public static Matrix4x4 identity() {
        double[][] result = new double[4][4];
        result[0][0] = 1;
        result[1][1] = 1;
        result[2][2] = 1;
        result[3][3] = 1;
        return new Matrix4x4(result);
    }

    public static Matrix4x4 zero() {
        double[][] result = new double[4][4];
        return new Matrix4x4(result);
    }
}
