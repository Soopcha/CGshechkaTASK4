package com.cgvsu.math;

import com.cgvsu.math.vector.Vector4;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Vector4Test {

    @Test
    public void testGetX() {
        Vector4 vector = new Vector4(3.0, 4.0,4,5);
        assertEquals(3.0, vector.getX(), 0.0001);
    }
    //delta - это погрешность допустимая

    @Test
    public void testSetX() {
        Vector4 vector = new Vector4(3.0, 4.0,4,5);
        vector.setX(5.0);
        assertEquals(5.0, vector.getX(), 0.0001);
    }

    @Test
    public void testGetY() {
        Vector4 vector = new Vector4(3.0, 4.0,4,5);
        assertEquals(4.0, vector.getY(), 0.0001);
    }

    @Test
    public void testSetY() {
        Vector4 vector = new Vector4(3.0, 4.0,4,5);
        vector.setY(6.0);
        assertEquals(6.0, vector.getY(), 0.0001);
    }
    @Test
    public void testGetZ() {
        Vector4 vector = new Vector4(3.0, 4.0,4,5);
        assertEquals(4.0, vector.getZ(), 0.0001);
    }
    @Test
    public void testSetZ() {
        Vector4 vector = new Vector4(3.0, 4.0,4,5);
        vector.setZ(6.0);
        assertEquals(6.0, vector.getZ(), 0.0001);
    }

    @Test
    public void testGetW() {
        Vector4 vector = new Vector4(3.0, 4.0,4,5);
        assertEquals(5.0, vector.getW(), 0.0001);
    }
    @Test
    public void testSetW() {
        Vector4 vector = new Vector4(3.0, 4.0,4,5);
        vector.setW(6.0);
        assertEquals(6.0, vector.getW(), 0.0001);
    }
    @Test
    public void testAdd() {
        Vector4 vector1 = new Vector4(1, 2, 3, 4);
        Vector4 vector2 = new Vector4(2, 3, 4, 5);
        Vector4 result = vector1.add(vector2);
        assertEquals(3, result.getX());
        assertEquals(5, result.getY());
        assertEquals(7, result.getZ());
        assertEquals(9, result.getW());
    }

    @Test
    public void testSubtract() {
        Vector4 vector1 = new Vector4(2, 4, 6, 8);
        Vector4 vector2 = new Vector4(1, 2, 3, 4);
        Vector4 result = vector1.subtract(vector2);
        assertEquals(1, result.getX());
        assertEquals(2, result.getY());
        assertEquals(3, result.getZ());
        assertEquals(4, result.getW());
    }

    @Test
    public void testMultiply() {
        Vector4 vector = new Vector4(2, 3, 4, 5);
        Vector4 result = vector.multiply(2);
        assertEquals(4, result.getX());
        assertEquals(6, result.getY());
        assertEquals(8, result.getZ());
        assertEquals(10, result.getW());
    }

    @Test
    public void testDivide() {
        Vector4 vector = new Vector4(2, 4, 6, 8);
        Vector4 result = vector.divide(2);
        assertEquals(1, result.getX());
        assertEquals(2, result.getY());
        assertEquals(3, result.getZ());
        assertEquals(4, result.getW());
    }

    @Test
    public void testLength() {
        Vector4 vector = new Vector4(3, 4, 5, 6);
        double length = vector.length();
        assertEquals(9.2736, length, 0.0001);
    }

    @Test
    public void testNormalize() {
        Vector4 vector = new Vector4(3, 4, 5, 6);
        Vector4 result = vector.normalize();
        assertEquals(0.3238, result.getX(), 0.001);
        assertEquals(0.4317, result.getY(), 0.001);
        assertEquals(0.5396, result.getZ(), 0.001);
        assertEquals(0.6476, result.getW(), 0.001);
    }

    @Test
    public void testDotProduct() {
        Vector4 vector1 = new Vector4(1, 2, 3, 4);
        Vector4 vector2 = new Vector4(2, 3, 4, 5);
        double result = vector1.dotProduct(vector2);
        assertEquals(40, result, 0.0001);
    }
}

