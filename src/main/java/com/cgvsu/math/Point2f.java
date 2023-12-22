package com.cgvsu.math;

import com.cgvsu.math.vector.Vector3;

public final class Point2f {
    public double x;
    public double y;

    public Point2f(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Point2f vertexToPoint(final Vector3 vertex, final int width, final int height) {
        return new Point2f(vertex.getX() * width + width / 2.0F, -vertex.getY() * height + height / 2.0F);
    }

    @Override
    public String toString() {
        return "Point2f{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}