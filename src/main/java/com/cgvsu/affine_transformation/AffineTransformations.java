package com.cgvsu.affine_transformation;


import com.cgvsu.math.VectorMath;
import com.cgvsu.model.Model;
import com.cgvsu.math.matrix.Matrix4x4;
import com.cgvsu.math.vector.Vector3;

import java.util.ArrayList;

import static com.cgvsu.math.VectorMath.multiplyMatrix4x4OnVector3;

public class AffineTransformations {

    private RotationOrder rotationOrder = RotationOrder.XYZ;

    private Vector3 translation = new Vector3(0,0,0);
    private Vector3 rotation = new Vector3(1, 1,1 );
    private Vector3 scale = new Vector3(1, 1,1);

    private Matrix4x4 RotationMatrix = new Matrix4x4(1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1);
    private Matrix4x4 ScaleMatrix;
    private Matrix4x4 TranslationMatrix;
    private Matrix4x4 AffineTransformMatrix = new Matrix4x4(1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1);

    public AffineTransformations() {
    }

    public AffineTransformations(RotationOrder rotationOrder, double scaleX, double scaleY, double scaleZ, double rotateX, double rotateY, double rotateZ, double translateX, double translateY, double translateZ) {
        this.rotationOrder = rotationOrder;
        scale.setAll(scaleX, scaleY, scaleZ);
        rotation.setAll(rotateX, rotateY, rotateZ);
        translation.setAll(translateX, translateY, translateZ);

        calculateAffineTransformMatrix();
    }

    private void calculateAffineTransformMatrix() {
        //Матрица поворота задается единичной
        RotationMatrix = new Matrix4x4(1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1);

        //Вычисление матрицы переноса
        TranslationMatrix = new Matrix4x4(1, 0, 0, translation.get(0),
                0, 1, 0, translation.get(1),
                0, 0, 1, translation.get(2),
                0, 0, 0, 1);
        //Вычисление матрицы масштабирования
        ScaleMatrix = new Matrix4x4(scale.get(0), 0, 0, 0,
                0, scale.get(1), 0, 0,
                0, 0, scale.get(2), 0,
                0, 0, 0, 1);
        //Вычисление тригонометрических функций
        float sinA = (float) Math.sin(rotation.get(0) * Math.PI / 180);
        float cosA = (float) Math.cos(rotation.get(0) * Math.PI / 180);

        float sinB = (float) Math.sin(rotation.get(1) * Math.PI / 180);
        float cosB = (float) Math.cos(rotation.get(1) * Math.PI / 180);

        float sinY = (float) Math.sin(rotation.get(2) * Math.PI / 180);
        float cosY = (float) Math.cos(rotation.get(2) * Math.PI / 180);

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

        //Матрица аффинных преобразований принимается равной единице
        AffineTransformMatrix = new Matrix4x4(TranslationMatrix);

        //Перемножение матриц поворота согласно их порядку
        switch (rotationOrder) {
            case ZYX -> {
                RotationMatrix = RotationMatrix.multiply(X);
                RotationMatrix = RotationMatrix.multiply(Y);
                RotationMatrix = RotationMatrix.multiply(Z);
            }
            case ZXY -> {
                RotationMatrix = RotationMatrix.multiply(Y);
                RotationMatrix = RotationMatrix.multiply(X);
                RotationMatrix = RotationMatrix.multiply(Z);
            }
            case YZX -> {
                RotationMatrix = RotationMatrix.multiply(X);
                RotationMatrix = RotationMatrix.multiply(Z);
                RotationMatrix = RotationMatrix.multiply(Y);
            }
            case YXZ -> {
                RotationMatrix = RotationMatrix.multiply(Z);
                RotationMatrix = RotationMatrix.multiply(X);
                RotationMatrix = RotationMatrix.multiply(Y);
            }
            case XZY -> {
                RotationMatrix = RotationMatrix.multiply(Y);
                RotationMatrix = RotationMatrix.multiply(Z);
                RotationMatrix = RotationMatrix.multiply(X);
            }
            case XYZ -> {
                RotationMatrix = RotationMatrix.multiply(Z);
                RotationMatrix = RotationMatrix.multiply(Y);
                RotationMatrix = RotationMatrix.multiply(X);
            }
            default -> RotationMatrix = RotationMatrix.multiply(1);
        }
        //Вычисление матрицы аффинных преобразований
        AffineTransformMatrix = AffineTransformMatrix.multiply(RotationMatrix);
        AffineTransformMatrix = AffineTransformMatrix.multiply(ScaleMatrix);
    }

    public Vector3 transformVertex(Vector3 v) {
        return multiplyMatrix4x4OnVector3(AffineTransformMatrix, v);
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
            Vector3 rotatedNormal = VectorMath.multiplyMatrix4x4OnVector3(RotationMatrix, v).normalize();
            rez.normals.add(rotatedNormal);
            //На преобразование нормалей влимяет только матрица поворота
        }

        return rez;
    }

    public RotationOrder getRotationOrder() {
        return rotationOrder;
    }

    public void setRotationOrder(RotationOrder rotationOrder) {
        this.rotationOrder = rotationOrder;
        calculateAffineTransformMatrix();
    }

    public double getScaleX() {
        return scale.get(0);
    }

    public void setScaleX(double scaleX) {
        scale.setX(scaleX);
        calculateAffineTransformMatrix();
    }

    public double getScaleY() {
        return scale.get(1);
    }

    public void setScaleY(double scaleY) {
        scale.setY(scaleY);
        calculateAffineTransformMatrix();
    }

    public double getScaleZ() {
        return scale.get(2);
    }

    public void setScaleZ(double scaleZ) {
        scale.setZ(scaleZ);
        calculateAffineTransformMatrix();
    }
    public void setScale(double scaleX, double scaleY, double scaleZ){
        scale.setAll(scaleX, scaleY, scaleZ);
        calculateAffineTransformMatrix();
    }

    public double getRotationX() {
        return rotation.get(0);
    }

    public void setRotationX(double rotationX) {
        rotation.setX(rotationX);
        calculateAffineTransformMatrix();
    }

    public double getRotationY() {
        return rotation.get(1);
    }

    public void setRotationY(double rotationY) {
        rotation.setY(rotationY);
        calculateAffineTransformMatrix();
    }

    public double getRotationZ() {
        return rotation.get(2);
    }

    public void setRotationZ(double rotationZ) {
        rotation.setZ(rotationZ);
        calculateAffineTransformMatrix();
    }

    public void setRotation(double rotationX, double rotationY, double rotationZ){
        scale.setAll(rotationX, rotationY, rotationZ);
        calculateAffineTransformMatrix();
    }

    public double getTranslationX() {
        return translation.get(0);
    }

    public void setTranslationX(double translationX) {
        translation.setX(translationX);
        calculateAffineTransformMatrix();
    }

    public double getTranslationY() {
        return translation.get(1);
    }

    public void setTranslationY(double translationY) {
        translation.setY(translationY);
        calculateAffineTransformMatrix();
    }

    public double getTranslationZ() {
        return translation.get(2);
    }

    public void setTranslationZ(double translationZ) {
        translation.setZ(translationZ);
        calculateAffineTransformMatrix();
    }

    public void setTranslation(double translationX, double translationY, double translationZ){
        scale.setAll(translationX, translationY, translationZ);
        calculateAffineTransformMatrix();
    }

    public Matrix4x4 getRotationMatrix() {
        return RotationMatrix;
    }

    public Matrix4x4 getScaleMatrix() {
        return ScaleMatrix;
    }

    public Matrix4x4 getTranslationMatrix() {
        return TranslationMatrix;
    }

    public Matrix4x4 getAffineTransformMatrix() {
        return AffineTransformMatrix;
    }
}