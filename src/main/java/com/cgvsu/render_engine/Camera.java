package com.cgvsu.render_engine;
import com.cgvsu.math.matrix.Matrix4x4;
import com.cgvsu.math.vector.Vector3;
import static com.cgvsu.math.matrix.Matrix4x4.*;


public class Camera {
    private Vector3 position;
    private Vector3 target;
    private float fov;
    private float aspectRatio;
    private float nearPlane;
    private float farPlane;

    private double mousePosX;
    private double mousePosY;
    public double mouseDeltaY;

    public Camera(
            final Vector3 position,
            final Vector3 target,
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        this.position = position;
        this.target = target;
        this.fov = fov;
        this.aspectRatio = aspectRatio;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
    }

    public void setPosition(final Vector3 position) {
        this.position = position;
    }

    public void setTarget(final Vector3 target) {
        this.target = target;
    }

    public void setAspectRatio(final float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getTarget() {
        return target;
    }

    public void movePosition(final Vector3 translation) {
        this.position.add(translation);
    }

    public void moveTarget(final Vector3 translation) {
        this.target.add(target);
    }

    Matrix4x4 getViewMatrix() {
        return lookAt(position, target);
    }

    Matrix4x4 getProjectionMatrix() {
        return perspective(fov, aspectRatio, nearPlane, farPlane);
    }


    public void handleMouseInput(double x, double y, boolean isPrimaryButtonDown, boolean isSecondaryButtonDown) {

        if (isPrimaryButtonDown) {
            rotateCamera((double) (x - mousePosX), (double) (y - mousePosY));
        } else if (isSecondaryButtonDown) {
            movePosition(new Vector3((double) (x - mousePosX) * 0.1, (double) (y - mousePosY) * 0.1, 0));
        } else {
            if (mouseDeltaY > 0) {
                position.subtractThis((position.subtract(target).divide(75)));
            } else if (mouseDeltaY < 0) {
                position.addThis((position.subtract(target).divide(75)));
            }
            mouseDeltaY = 0;
        }

        mousePosX = x;
        mousePosY = y;
    }

    private void rotateCamera(double dx, double dy) {
        double rotationX = -dy * 0.2;
        double rotationY = -dx * 0.2;

        Matrix4x4 rotationMatrixX = rotate(rotationX, 1, 0, 0);
        Matrix4x4 rotationMatrixY = rotate(rotationY, 0, 1, 0);

        Matrix4x4 rotationMatrix = rotationMatrixX.multiply(rotationMatrixY);

        position = multiplyMatrix4ByVector3AndPerspectiveDivide(rotationMatrix, position);
    }
}