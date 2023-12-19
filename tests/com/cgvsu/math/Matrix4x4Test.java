package com.cgvsu.math;

import com.cgvsu.math.matrix.Matrix4x4;
import com.cgvsu.math.vector.Vector4;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Matrix4x4Test {

    @Test
    public void testAdd() {
        double[][] data1 = {
                {2, 4, 6, 8},
                {10, 12, 14, 16},
                {18, 20, 22, 24},
                {26, 28, 30, 32}
        };
        double[][] data2 = {
                {1, 3, 5, 7},
                {9, 11, 13, 15},
                {17, 19, 21, 23},
                {25, 27, 29, 31}
        };
        Matrix4x4 matrix1 = new Matrix4x4(data1);
        Matrix4x4 matrix2 = new Matrix4x4(data2);
        Matrix4x4 result = matrix1.add(matrix2);
        double[][] expectedData = {
                {3, 7, 11, 15},
                {19, 23, 27, 31},
                {35, 39, 43, 47},
                {51, 55, 59, 63}
        };
        Matrix4x4 expected = new Matrix4x4(expectedData);
        for (int i = 0; i < 4; i++){
            for (int j = 0; i < 4; i++) {
                assertEquals(expected.getMatrix()[i][j], result.getMatrix()[i][j], 0.001);
            }
        }
    }

    @Test
    public void testSubtract() {
        double[][] data1 = {
                {2, 4, 6, 8},
                {10, 12, 14, 16},
                {18, 20, 22, 24},
                {26, 28, 30, 32}
        };
        double[][] data2 = {
                {1, 3, 5, 7},
                {9, 11, 13, 15},
                {17, 19, 21, 23},
                {25, 27, 29, 31}
        };
        Matrix4x4 matrix1 = new Matrix4x4(data1);
        Matrix4x4 matrix2 = new Matrix4x4(data2);
        Matrix4x4 result = matrix1.subtract(matrix2);
        double[][] expectedData = {
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1},
                {1, 1, 1, 1}
        };
        Matrix4x4 expected = new Matrix4x4(expectedData);
        for (int i = 0; i < 4; i++){
            for (int j = 0; i < 4; i++) {
                assertEquals(expected.getMatrix()[i][j], result.getMatrix()[i][j], 0.001);
            }
        }
    }

    @Test
    public void testMultiplyScalar() {
        double[][] data = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        Matrix4x4 matrix = new Matrix4x4(data);
        double scalar = 2.5;
        Matrix4x4 result = matrix.multiply(scalar);
        double[][] expectedData = {
                {2.5, 5, 7.5, 10},
                {12.5, 15, 17.5, 20},
                {22.5, 25, 27.5, 30},
                {32.5, 35, 37.5, 40}
        };
        Matrix4x4 expected = new Matrix4x4(expectedData);
        for (int i = 0; i < 4; i++){
            for (int j = 0; i < 4; i++) {
                assertEquals(expected.getMatrix()[i][j], result.getMatrix()[i][j], 0.001);
            }
        }
    }

    @Test
    public void testMultiplyVector() {
        double[][] data = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        Matrix4x4 matrix = new Matrix4x4(data);
        Vector4 vector = new Vector4(2, 3, 4, 5);
        Vector4 result = matrix.multiply(vector);
        Vector4 expected = new Vector4(40, 96, 152, 208);
        assertEquals(expected.getX(), result.getX(), 0.001);
        assertEquals(expected.getY(), result.getY(), 0.001);
        assertEquals(expected.getZ(), result.getZ(), 0.001);
        assertEquals(expected.getW(), result.getW(), 0.001);
    }

    @Test
    public void testMultiplyMatrix() {
        double[][] data1 = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        double[][] data2 = {
                {16, 15, 14, 13},
                {12, 11, 10, 9},
                {8, 7, 6, 5},
                {4, 3, 2, 1}
        };
        Matrix4x4 matrix1 = new Matrix4x4(data1);
        Matrix4x4 matrix2 = new Matrix4x4(data2);
        Matrix4x4 result = matrix1.multiply(matrix2);
        double[][] expectedData = {
                {80, 70, 60, 50},
                {240, 214, 188, 162},
                {400, 358, 316, 274},
                {560, 502, 444, 386}
        };
        Matrix4x4 expected = new Matrix4x4(expectedData);
        for (int i = 0; i < 4; i++){
            for (int j = 0; i < 4; i++) {
                assertEquals(expected.getMatrix()[i][j], result.getMatrix()[i][j], 0.001);
            }
        }
    }

    @Test
    public void testTranspose() {
        double[][] data = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        Matrix4x4 matrix = new Matrix4x4(data);
        Matrix4x4 result = matrix.transpose();
        double[][] expectedData = {
                {1, 5, 9, 13},
                {2, 6, 10, 14},
                {3, 7, 11, 15},
                {4, 8, 12, 16}
        };
        Matrix4x4 expected = new Matrix4x4(expectedData);
        for (int i = 0; i < 4; i++){
            for (int j = 0; i < 4; i++) {
                assertEquals(expected.getMatrix()[i][j], result.getMatrix()[i][j], 0.001);
            }
        }
    }

    @Test
    public void testIdentity() {
        Matrix4x4 identity = Matrix4x4.identity();
        double[][] expectedData = {
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };
        Matrix4x4 expected = new Matrix4x4(expectedData);
        for (int i = 0; i < 4; i++){
            for (int j = 0; i < 4; i++) {
                assertEquals(expected.getMatrix()[i][j], identity.getMatrix()[i][j], 0.001);
            }
        }
    }

    @Test
    public void testZero() {
        Matrix4x4 zero = Matrix4x4.zero();
        double[][] expectedData = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        Matrix4x4 expected = new Matrix4x4(expectedData);
        for (int i = 0; i < 4; i++){
            for (int j = 0; i < 4; i++) {
                assertEquals(expected.getMatrix()[i][j], zero.getMatrix()[i][j], 0.001);
            }
        }
    }
}

