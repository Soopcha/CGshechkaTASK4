package com.cgvsu.math.matrix;

import com.cgvsu.math.vector.Vector3;
import com.cgvsu.math.vector.Vector4;

import javax.vecmath.Matrix4f;

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

    public Matrix4x4(
            double m00, double m01, double m02, double m03,
            double m10, double m11, double m12, double m13,
            double m20, double m21, double m22, double m23,
            double m30, double m31, double m32, double m33) {
        matrix = new double[][]{
                {m00, m01, m02, m03},
                {m10, m11, m12, m13},
                {m20, m21, m22, m23},
                {m30, m31, m32, m33}
        };
    }

    public Matrix4x4(Matrix4x4 t) {
        double [][] m = t.toArray();
        this.matrix = m;
    }

    public double[][] toArray() {
        return matrix;
    }


    public double[][] getMatrix() {
        return matrix;
    }
    public double getElem(int indX, int indY){
        return matrix[indX][indY];
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
    public static Vector3 multiplyMatrix4ByVector3(final Matrix4x4 matrix, final Vector3 vertex) {
        final double x = (double) ((vertex.get(0) * matrix.getElem(0,0)) + (vertex.get(1) * matrix.getElem(1, 0)) + (vertex.get(2) * matrix.getElem(2, 0)) + matrix.getElem(3, 0));
        final double y = (double) ((vertex.get(0) * matrix.getElem(0,1)) + (vertex.get(1) * matrix.getElem(1, 1)) + (vertex.get(2) * matrix.getElem(2, 1)) + matrix.getElem(3, 1));
        final double z = (double) ((vertex.get(0) * matrix.getElem(0,2)) + (vertex.get(1) * matrix.getElem(1, 2)) + (vertex.get(2) * matrix.getElem(2, 2)) + matrix.getElem(3, 2));
        final double w = (double) ((vertex.get(0) * matrix.getElem(0,3)) + (vertex.get(1) * matrix.getElem(1, 3)) + (vertex.get(2) * matrix.getElem(2, 3)) + matrix.getElem(3, 3));
        return new Vector3(x / w, y / w, z / w);
    }

    public static Matrix4x4 rotateScaleTranslate() {
        double[][] matrix = {
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}};
        return new Matrix4x4(matrix);
    }
    public static Matrix4x4 rotate(double angle, double axisX, double axisY, double axisZ) {
        double radians = (double) Math.toRadians(angle);
        double sin = (double) Math.sin(radians);
        double cos = (double) Math.cos(radians);
        double oneMinusCos = 1.0 - cos;

        double[][] rotationMatrix = {
                {cos + axisX * axisX * oneMinusCos, axisX * axisY * oneMinusCos - axisZ * sin, axisX * axisZ * oneMinusCos + axisY * sin, 0},
                {axisY * axisX * oneMinusCos + axisZ * sin, cos + axisY * axisY * oneMinusCos, axisY * axisZ * oneMinusCos - axisX * sin, 0},
                {axisZ * axisX * oneMinusCos - axisY * sin, axisZ * axisY * oneMinusCos + axisX * sin, cos + axisZ * axisZ * oneMinusCos, 0},
                {0, 0, 0, 1}
        };

        return new Matrix4x4(rotationMatrix);
    }

    public static Matrix4x4 lookAt(Vector3 eye, Vector3 target) {
        return lookAt(eye, target, new Vector3(0, 1.0, 0));
    }

    public static Matrix4x4 lookAt(Vector3 eye, Vector3 target, Vector3 up) {
        Vector3 resultX = new Vector3(0, 0, 0);
        Vector3  resultY = new Vector3(0, 0, 0);
        Vector3 resultZ = new Vector3(0, 0, 0);

        resultZ.subtract(target, eye);
        resultX.crossProduct(up, resultZ);
        resultY.crossProduct(resultZ, resultX);

        resultX = resultX.normalize();
        resultY = resultY.normalize();
        resultZ = resultZ.normalize();

        double[][] matrix =
                {
                        {(double) resultX.get(0), (double) resultY.get(0), (double) resultZ.get(0), 0},
                        {(double) resultX.get(1), (double) resultY.get(1), (double) resultZ.get(1), 0},
                        {(double) resultX.get(2), (double) resultY.get(2), (double) resultZ.get(2), 0},
                        {-resultX.dotProduct(eye), -resultY.dotProduct(eye), -resultZ.dotProduct(eye), 1}};
        return new Matrix4x4(matrix);

    }
    public static Matrix4x4 perspective(
            final double fov,
            final double aspectRatio,
            final double nearPlane,
            final double farPlane) {
        double[][] matrix = new double[4][4];

        double tangentMinusOnDegree = (double) (1.0 / (Math.tan(fov * 0.5)));

        matrix[0][0] = tangentMinusOnDegree / aspectRatio;
        matrix[1][1] = tangentMinusOnDegree;
        matrix[2][2] = (farPlane + nearPlane) / (farPlane - nearPlane);
        matrix[2][3] = 1.0;
        matrix[3][2] = 2 * (nearPlane * farPlane) / (nearPlane - farPlane);

        return new Matrix4x4(matrix);
    }
}
