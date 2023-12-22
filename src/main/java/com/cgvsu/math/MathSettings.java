package com.cgvsu.math;

public final class MathSettings {
    public static final double EPS = 1e-5f;

    public static boolean isEqual(double x, double y){
        return Math.abs(x-y) < EPS;
    }
}
