package com.cgvsu.render_engine;

import com.cgvsu.math.vector.Vector2;
import com.cgvsu.math.vector.Vector3;


public class Triangle {

    private final Vector3 a;
    private final Vector3 b;
    private final Vector3 c;

    private final double z1;
    private final double z2;
    private final double z3;

    private final Vector2 t1;
    private final Vector2 t2;
    private final Vector2 t3;

    private final Vector3 n1;
    private final Vector3 n2;
    private final Vector3 n3;

    public Triangle(Vector3 a, Vector3 b, Vector3 c, double z1, double z2, double z3,
                    Vector2 t1, Vector2 t2, Vector2 t3, Vector3 n1, Vector3 n2, Vector3 n3) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.z1 = z1;
        this.z2 = z2;
        this.z3 = z3;
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.n1 = n1;
        this.n2 = n2;
        this.n3 = n3;
    }
    public Vector3 getA() {
        return a;
    }

    public Vector3 getB() {
        return b;
    }

    public Vector3 getC() {
        return c;
    }

    public double getZ1() {
        return z1;
    }

    public double getZ2() {
        return z2;
    }

    public double getZ3() {
        return z3;
    }

    public Vector2 getT1() {
        return t1;
    }

    public Vector2 getT2() {
        return t2;
    }

    public Vector2 getT3() {
        return t3;
    }

    public Vector3 getN1() {
        return n1;
    }

    public Vector3 getN2() {
        return n2;
    }

    public Vector3 getN3() {
        return n3;
    }
}
