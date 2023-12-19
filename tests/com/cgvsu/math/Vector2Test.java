package com.cgvsu.math;

import com.cgvsu.math.vector.Vector2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;



public class Vector2Test {

    @Test
    public void testGetX() {
        Vector2 vector = new Vector2(3.0, 4.0);
        assertEquals(3.0, vector.getX(), 0.0001);
    }
    //delta - это погрешность допустимая

    @Test
    public void testSetX() {
        Vector2 vector = new Vector2(3.0, 4.0);
        vector.setX(5.0);
        assertEquals(5.0, vector.getX(), 0.0001);
    }

    @Test
    public void testGetY() {
        Vector2 vector = new Vector2(3.0, 4.0);
        assertEquals(4.0, vector.getY(), 0.0001);
    }

    @Test
    public void testSetY() {
        Vector2 vector = new Vector2(3.0, 4.0);
        vector.setY(6.0);
        assertEquals(6.0, vector.getY(), 0.0001);
    }

    @Test
    public void testAdd() { //тест сложения
        Vector2 vector1 = new Vector2(3.0, 4.0);
        Vector2 vector2 = new Vector2(5.0, 6.0);
        Vector2 result = vector1.add(vector2);
        assertEquals(8.0, result.getX(), 0.0001);
        assertEquals(10.0, result.getY(), 0.0001);
    }

    @Test
    public void testSubtract() {
        Vector2 vector1 = new Vector2(3.0, 4.0);
        Vector2 vector2 = new Vector2(5.0, 6.0);
        Vector2 result = vector1.subtract(vector2);
        assertEquals(-2.0, result.getX(), 0.0001);
        assertEquals(-2.0, result.getY(), 0.0001);
    }

    @Test
    public void testMultiply() {
        Vector2 vector = new Vector2(3.0, 4.0);
        Vector2 result = vector.multiply(3.0);
        assertEquals(9.0, result.getX(), 0.0001);
        assertEquals(12.0, result.getY(), 0.0001);
    }

    @Test
    public void testDivide() {
        Vector2 vector = new Vector2(12.0, 8.0);
        Vector2 result = vector.divide(2.0);
        assertEquals(6.0, result.getX(), 0.0001);
        assertEquals(4.0, result.getY(), 0.0001);
    }

    @Test
    public void testLength() {
        Vector2 vector = new Vector2(3.0, 4.0);
        assertEquals(5.0, vector.length(), 0.0001);
    }

    @Test
    public void testNormalize() {
        Vector2 vector = new Vector2(3.0, 4.0);
        Vector2 result = vector.normalize();
        assertEquals(0.6, result.getX(), 0.0001);
        assertEquals(0.8, result.getY(), 0.0001);
    }

    @Test
    public void testDotProduct() {
        Vector2 vector1 = new Vector2(3.0, 4.0);
        Vector2 vector2 = new Vector2(5.0, 6.0);
        double result = vector1.dotProduct(vector2);
        assertEquals(39.0, result, 0.0001);
    }

//    @Test(expected = UnsupportedOperationException.class)
//    public void testCrossProduct() {
//        Vector2 vector1 = new Vector2(3.0, 4.0);
//        Vector2 vector2 = new Vector2(5.0, 6.0);
//        vector1.crossProduct(vector2);
//    }
}
