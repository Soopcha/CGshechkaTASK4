package com.cgvsu.math;

import com.cgvsu.math.matrix.Matrix3x3;
import com.cgvsu.math.vector.Vector3;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Matrix3x3Test {

    @Test
    public void testAdd() {
        double[][] data1 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        double[][] data2 = {
                {9, 8, 7},
                {6, 5, 4},
                {3, 2, 1}
        };
        Matrix3x3 matrix1 = new Matrix3x3(data1);
        Matrix3x3 matrix2 = new Matrix3x3(data2);
        Matrix3x3 result = matrix1.add(matrix2);
        double[][] expectedData = {
                {10, 10, 10},
                {10, 10, 10},
                {10, 10, 10}
        };
        Matrix3x3 expected = new Matrix3x3(expectedData);
        for (int i = 0; i < 3; i++){
            for (int j = 0; i < 3; i++) {
                assertEquals(expected.getMatrix()[i][j], result.getMatrix()[i][j], 0.001);
            }
        }
    }

    @Test
    public void testSubtract() {
        double[][] data1 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        double[][] data2 = {
                {9, 8, 7},
                {6, 5, 4},
                {3, 2, 1}
        };
        Matrix3x3 matrix1 = new Matrix3x3(data1);
        Matrix3x3 matrix2 = new Matrix3x3(data2);
        Matrix3x3 result = matrix1.subtract(matrix2);
        double[][] expectedData = {
                {-8, -6, -4},
                {-2, 0, 2},
                {4, 6, 8}
        };
        Matrix3x3 expected = new Matrix3x3(expectedData);
        for (int i = 0; i < 3; i++){
            for (int j = 0; i < 3; i++) {
                assertEquals(expected.getMatrix()[i][j], result.getMatrix()[i][j], 0.001);
            }
        }
    }

    @Test
    public void testMultiplyScalar() {
        double[][] data = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix3x3 matrix = new Matrix3x3(data);
        double scalar = 2.5;
        Matrix3x3 result = matrix.multiply(scalar);
        double[][] expectedData = {
                {2.5, 5, 7.5},
                {10, 12.5, 15},
                {17.5, 20, 22.5}
        };
        Matrix3x3 expected = new Matrix3x3(expectedData);
        for (int i = 0; i < 3; i++){
            for (int j = 0; i < 3; i++) {
                assertEquals(expected.getMatrix()[i][j], result.getMatrix()[i][j], 0.001);
            }
        }
    }

    @Test
    public void testMultiplyVector() {
        double[][] data = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix3x3 matrix = new Matrix3x3(data);
        Vector3 vector = new Vector3(2, 3, 4);
        Vector3 result = matrix.multiply(vector);
        Vector3 expected = new Vector3(20, 47, 74);
        assertEquals(expected.getX(), result.getX(), 0.001);
        assertEquals(expected.getY(), result.getY(), 0.001);
        assertEquals(expected.getZ(), result.getZ(), 0.001);

    }

    @Test
    public void testMultiplyMatrix() {
        double[][] data1 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        double[][] data2 = {
                {9, 8, 7},
                {6, 5, 4},
                {3, 2, 1}
        };
        Matrix3x3 matrix1 = new Matrix3x3(data1);
        Matrix3x3 matrix2 = new Matrix3x3(data2);
        Matrix3x3 result = matrix1.multiply(matrix2);
        double[][] expectedData = {
                {30, 24, 18},
                {84, 69, 54},
                {138, 114, 90}
        };

        Matrix3x3 expected = new Matrix3x3(expectedData);
        for (int i = 0; i < 3; i++){
            for (int j = 0; i < 3; i++) {
                assertEquals(expected.getMatrix()[i][j], result.getMatrix()[i][j], 0.001);
            }
        }
    }

    @Test
    public void testTranspose() {
        double[][] data = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix3x3 matrix = new Matrix3x3(data);
        Matrix3x3 result = matrix.transpose();
        double[][] expectedData = {
                {1, 4, 7},
                {2, 5, 8},
                {3, 6, 9}
        };
        Matrix3x3 expected = new Matrix3x3(expectedData);
        for (int i = 0; i < 3; i++){
            for (int j = 0; i < 3; i++) {
                assertEquals(expected.getMatrix()[i][j], result.getMatrix()[i][j], 0.001);
            }
        }
    }

    @Test
    public void testIdentity() {
        Matrix3x3 identity = Matrix3x3.identity();
        double[][] expectedData = {
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        };
        Matrix3x3 expected = new Matrix3x3(expectedData);
        for (int i = 0; i < 3; i++){
            for (int j = 0; i < 3; i++) {
                assertEquals(expected.getMatrix()[i][j], identity.getMatrix()[i][j], 0.001);
            }
        }
    }

    @Test
    public void testZero() {
        Matrix3x3 zero = Matrix3x3.zero();
        double[][] expectedData = {
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };
        Matrix3x3 expected = new Matrix3x3(expectedData);
        for (int i = 0; i < 3; i++){
            for (int j = 0; i < 3; i++) {
                assertEquals(expected.getMatrix()[i][j], zero.getMatrix()[i][j], 0.001);
            }
        }
    }
}

