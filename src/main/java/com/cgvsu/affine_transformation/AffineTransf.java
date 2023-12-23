package com.cgvsu.affine_transformation;


import com.cgvsu.math.matrix.Matrix4x4;
import com.cgvsu.math.vector.Vector3;
import com.cgvsu.model.Model;

import java.util.ArrayList;

public class AffineTransf {

    //Перечисление отвечающее за порядок поворотов в каждой из плоскостей
    private OrderRotation or = OrderRotation.ZYX;
    //Параметры масштабирования
    private double Sx = 1;
    private double Sy = 1;
    private double Sz = 1;
    //Параметры поворота
    //УГЛЫ ПОВОРОТА ЗАДАЮТСЯ ПО ЧАСОВОЙ СТРЕЛКЕ В ГРАДУСАХ
    private double Rx = 0;
    private double Ry = 0;
    private double Rz = 0;
    //Параметры переноса
    private double Tx = 0;
    private double Ty = 0;
    private double Tz = 0;
    // конструктор матрицы изменила мб по дргому сделать лучше
    private Matrix4x4 R = new Matrix4x4(1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1);
    private Matrix4x4 S;
    private Matrix4x4 T;
    private Matrix4x4 A = new Matrix4x4(1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1);

    public AffineTransf() {
    }

    public AffineTransf(OrderRotation or, double sx, double sy, double sz, double rx, double ry, double rz, double tx, double ty, double tz) {
        this.or = or;
        Sx = sx;
        Sy = sy;
        Sz = sz;
        Rx = rx;
        Ry = ry;
        Rz = rz;
        Tx = tx;
        Ty = ty;
        Tz = tz;

        calculateA();
    }

    private void calculateA() {
        //Матрица поворота задается единичной
        R = new Matrix4x4(1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1);

        //Вычисление матрицы переноса
        T = new Matrix4x4(1, 0, 0, Tx,
                0, 1, 0, Ty,
                0, 0, 1, Tz,
                0, 0, 0, 1);
        //Вычисление матрицы масштабирования
        S = new Matrix4x4(Sx, 0, 0, 0,
                0, Sy, 0, 0,
                0, 0, Sz, 0,
                0, 0, 0, 1);
        //Вычисление тригонометрических функций
        double sinA = (double) Math.sin(Rx * Math.PI / 180);
        double cosA = (double) Math.cos(Rx * Math.PI / 180);

        double sinB = (double) Math.sin(Ry * Math.PI / 180);
        double cosB = (double) Math.cos(Ry * Math.PI / 180);

        double sinY = (double) Math.sin(Rz * Math.PI / 180);
        double cosY = (double) Math.cos(Rz * Math.PI / 180);

        //Матрицы поворота в каждой из плоскостей
        Matrix4x4 Z = new Matrix4x4(cosY, sinY, 0, 0,
                -sinY, cosY, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1);


        Matrix4x4 Y = new Matrix4x4(cosB, 0, sinB, 0,
                0, 1, 0, 0,
                -sinB, 0, cosB, 0,
                0, 0, 0, 1);

        Matrix4x4 X = new Matrix4x4(1, 0, 0, 0,
                0, cosA, sinA, 0,
                0, -sinA, cosA, 0,
                0, 0, 0, 1);

        //Матрица афинных преобразований принимается равной единице
//        A = new Matrix4x4(T);
        A = new Matrix4x4(T);


        //Перемножение матриц поворота согласно их порядку
        switch (or) {
            case ZYX -> {
//                R.multiply(X);
//                R.multiply(Y);
//                R.multiply(Z);
                R = R.multiply(X);
                R = R.multiply(Y);
                R = R.multiply(Z);
            }
            case ZXY -> {
//                R.multiply(Y);
//                R.multiply(X);
//                R.multiply(Z);
                R = R.multiply(Y);
                R = R.multiply(X);
                R = R.multiply(Z);
            }
            case YZX -> {
//                R.multiply(X);
//                R.multiply(Z);
//                R.multiply(Y);
                R = R.multiply(X);
                R = R.multiply(Z);
                R = R.multiply(Y);
            }
            case YXZ -> {
//                R.multiply(Z);
//                R.multiply(X);
//                R.multiply(Y);
                R = R.multiply(Z);
                R = R.multiply(X);
                R = R.multiply(Y);
            }
            case XZY -> {
//                R.multiply(Y);
//                R.multiply(Z);
//                R.multiply(X);
                R = R.multiply(Y);
                R = R.multiply(Z);
                R = R.multiply(X);
            }
            case XYZ -> {
//                R.multiply(Z);
//                R.multiply(Y);
//                R.multiply(X);
                R = R.multiply(Z);
                R = R.multiply(Y);
                R = R.multiply(X);
            }
            default -> R.multiply(1);
        }
        //Вычисление матрицы афинных преобразований
        A = A.multiply(R);
        A = A.multiply(S);

    }

    public Vector3 transformVertex(Vector3 v) {
        return VectorMath.mullMatrix4fOnVector3f(A, v);
    }

    public Model transformModel(Model m) {
        Model rez = new Model();
        rez.polygons = new ArrayList<>(m.polygons);
        rez.textureVertices = new ArrayList<>(m.textureVertices);
        //Полигоны и текстурные вершины не изменяются

        rez.vertices = new ArrayList<>();
        for (Vector3 v : m.vertices) {
            rez.vertices.add(transformVertex(v));
        }

        for (Vector3 v : m.normals) {
            rez.normals.add(VectorMath.mullMatrix4fOnVector3f(R,v));
            //На преобразование нормалей влимяет только матрица поворота
        }

        return rez;
    }


    public OrderRotation getOr() {
        return or;
    }

    public void setOr(OrderRotation or) {
        this.or = or;
        calculateA();
    }

    public double getSx() {
        return Sx;
    }

    public void setSx(double sx) {
        Sx = sx;
        calculateA();
    }

    public double getSy() {
        return Sy;
    }

    public void setSy(double sy) {
        Sy = sy;
        calculateA();
    }

    public double getSz() {
        return Sz;
    }

    public void setSz(double sz) {
        Sz = sz;
        calculateA();
    }

    public double getRx() {
        return Rx;
    }

    public void setRx(double rx) {
        Rx = rx;
        calculateA();
    }

    public double getRy() {
        return Ry;
    }

    public void setRy(double ry) {
        Ry = ry;
        calculateA();
    }

    public double getRz() {
        return Rz;
    }

    public void setRz(double rz) {
        Rz = rz;
        calculateA();
    }

    public double getTx() {
        return Tx;
    }

    public void setTx(double tx) {
        Tx = tx;
        calculateA();
    }

    public double getTy() {
        return Ty;
    }

    public void setTy(double ty) {
        Ty = ty;
        calculateA();
    }

    public double getTz() {
        return Tz;
    }

    public void setTz(double tz) {
        Tz = tz;
        calculateA();
    }

    public Matrix4x4 getR() {
        return R;
    }

    public Matrix4x4 getS() {
        return S;
    }

    public Matrix4x4 getT() {
        return T;
    }

    public Matrix4x4 getA() {
        return A;
    }

}
