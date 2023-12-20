package com.cgvsu.affine_transformation;


import com.cgvsu.math.matrix.Matrix4x4;
import com.cgvsu.math.vector.Vector3;

public class VectorMath {
    public static Vector3 mullMatrix4fOnVector3f(Matrix4x4 m, Vector3 v) {
        double[][] r = m.toArray();
        return new Vector3( r[0][0] * v.getX() + r[0][1] * v.getY() + r[0][2] * v.getZ() + r[0][3],
                r[1][0] * v.getX() + r[1][1] * v.getY() + r[1][2] * v.getZ() + r[1][3],
                r[2][0] * v.getX() + r[2][1] * v.getY() + r[2][2] * v.getZ() + r[2][3]);
    }
}