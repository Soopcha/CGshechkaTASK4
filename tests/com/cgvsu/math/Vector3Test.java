package com.cgvsu.math;

import com.cgvsu.math.vector.Vector3;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Vector3Test {

    @Test
    public void testGetX() {
        Vector3 vector = new Vector3(3.0, 4.0,5);
        assertEquals(3.0, vector.getX(), 0.0001);
    }
    //delta - это погрешность допустимая

    @Test
    public void testSetX() {
        Vector3 vector = new Vector3(3.0, 4.0,6);
        vector.setX(5.0);
        assertEquals(5.0, vector.getX(), 0.0001);
    }

    @Test
    public void testGetY() {
        Vector3 vector = new Vector3(3.0, 4.0,6);
        assertEquals(4.0, vector.getY(), 0.0001);
    }

    @Test
    public void testSetY() {
        Vector3 vector = new Vector3(3.0, 4.0,6);
        vector.setY(6.0);
        assertEquals(6.0, vector.getY(), 0.0001);
    }
    @Test
    public void testGetZ() {
        Vector3 vector = new Vector3(3.0, 4.0,6);
        assertEquals(6.0, vector.getZ(), 0.0001);
    }
    @Test
    public void testSetZ() {
        Vector3 vector = new Vector3(3.0, 4.0,4);
        vector.setZ(6.0);
        assertEquals(6.0, vector.getZ(), 0.0001);
    }

    @Test
    public void testAdd() {
        Vector3 vector1 = new Vector3(1.0, 2.0, 3.0);
        Vector3 vector2 = new Vector3(4.0, 5.0, 6.0);
        Vector3 result = vector1.add(vector2);
        assertEquals(5.0, result.getX(), 0.0001);
        assertEquals(7.0, result.getY(), 0.0001);
        assertEquals(9.0, result.getZ(), 0.0001);
    }

    @Test
    public void testSubtract() {
        Vector3 vector1 = new Vector3(1.0, 2.0, 3.0);
        Vector3 vector2 = new Vector3(4.0, 5.0, 6.0);
        Vector3 result = vector1.subtract(vector2);
        assertEquals(-3.0, result.getX(), 0.0001);
        assertEquals(-3.0, result.getY(), 0.0001);
        assertEquals(-3.0, result.getZ(), 0.0001);
    }

    @Test
    public void testMultiply() {
        Vector3 vector = new Vector3(1.0, 2.0, 3.0);
        Vector3 result = vector.multiply(2.0);
        assertEquals(2.0, result.getX(), 0.0001);
        assertEquals(4.0, result.getY(), 0.0001);
        assertEquals(6.0, result.getZ(), 0.0001);
    }

    @Test
    public void testDivide() {
        Vector3 vector = new Vector3(2.0, 4.0, 6.0);
        Vector3 result = vector.divide(2.0);
        assertEquals(1.0, result.getX(), 0.0001);
        assertEquals(2.0, result.getY(), 0.0001);
        assertEquals(3.0, result.getZ(), 0.0001);
    }

    @Test
    public void testLength() {
        Vector3 vector = new Vector3(3.0, 4.0, 12.0);
        double length = vector.length();
        assertEquals(13.0, length, 0.01); // Приближенное сравнение с погрешностью 0.01
    }

    @Test
    public void testNormalize() {
        Vector3 vector = new Vector3(3.0, 4.0, 12.0);
        Vector3 normalized = vector.normalize();
        assertEquals(3.0/13.0, normalized.getX(), 0.0001);
        assertEquals(4.0/13.0, normalized.getY(), 0.0001);
        assertEquals(12.0/13.0, normalized.getZ(), 0.0001);
    }

    @Test
    public void testDotProduct() {
        Vector3 vector1 = new Vector3(1.0, 2.0, 3.0);
        Vector3 vector2 = new Vector3(4.0, 5.0, 6.0);
        double result = vector1.dotProduct(vector2);
        assertEquals(32.0, result, 0.01);
    }

    @Test
    public void testCrossProduct() {
        Vector3 vector1 = new Vector3(1.0, 0.0, 0.0);
        Vector3 vector2 = new Vector3(0.0, 1.0, 0.0);
        Vector3 result = vector1.cross(vector2);
        assertEquals(0.0, result.getX(), 0.0001);
        assertEquals(0.0, result.getY(), 0.0001);
        assertEquals(1.0, result.getZ(), 0.0001);

    }

}
