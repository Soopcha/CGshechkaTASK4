package com.cgvsu.render_engine;
import com.cgvsu.math.vector.Vector3;

import javax.vecmath.*;

public class GraphicConveyor {

    public static Matrix4f rotateScaleTranslate() {
        float[] matrix = new float[]{
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1};
        return new Matrix4f(matrix);
    }

    public static Matrix4f lookAt(Vector3f eye, Vector3f target) {
        return lookAt(eye, target, new Vector3f(0F, 1.0F, 0F));
    }

    public static Matrix4f lookAt(Vector3f eye, Vector3f target, Vector3f up) {
        Vector3f resultX = new Vector3f();
        Vector3f resultY = new Vector3f();
        Vector3f resultZ = new Vector3f();

        resultZ.sub(target, eye);
        resultX.cross(up, resultZ);
        resultY.cross(resultZ, resultX);

        resultX.normalize();
        resultY.normalize();
        resultZ.normalize();

        float[] matrix = new float[]{
                resultX.x, resultY.x, resultZ.x, 0,
                resultX.y, resultY.y, resultZ.y, 0,
                resultX.z, resultY.z, resultZ.z, 0,
                -resultX.dot(eye), -resultY.dot(eye), -resultZ.dot(eye), 1};
        return new Matrix4f(matrix);
    }

    public static Matrix4f perspective(
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        Matrix4f result = new Matrix4f();
        float tangentMinusOnDegree = (float) (1.0F / (Math.tan(fov * 0.5F)));
        result.m00 = tangentMinusOnDegree / aspectRatio;
        result.m11 = tangentMinusOnDegree;
        result.m22 = (farPlane + nearPlane) / (farPlane - nearPlane);
        result.m23 = 1.0F;
        result.m32 = 2 * (nearPlane * farPlane) / (nearPlane - farPlane);
        return result;
    }

    public static Vector3 multiplyMatrix4ByVector3(final Matrix4f matrix, final Vector3 vertex) {
        final double x = (vertex.getX() * matrix.m00) + (vertex.getY() * matrix.m10) + (vertex.getZ() * matrix.m20) + matrix.m30;
        final double y = (vertex.getX() * matrix.m01) + (vertex.getY() * matrix.m11) + (vertex.getZ() * matrix.m21) + matrix.m31;
        final double z = (vertex.getX() * matrix.m02) + (vertex.getY() * matrix.m12) + (vertex.getZ() * matrix.m22) + matrix.m32;
        final double w = (vertex.getX() * matrix.m03) + (vertex.getY() * matrix.m13) + (vertex.getZ() * matrix.m23) + matrix.m33;
        return new Vector3(x / w, y / w, z / w);
    }

    public static Point2d vertexToPoint(final Vector3 vertex, final int width, final int height) {
        return new Point2d(vertex.getX() * width + width / 2.0F, -vertex.getY() * height + height / 2.0F);
    }
}
