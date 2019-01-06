package arachnid.util;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

    private Transform viewMatrix;
    private Matrix4f projectionMatrix;

    public Camera(Vector3f startingPos, float fov, float aspect) {
        viewMatrix = new Transform();
        viewMatrix.translate(startingPos);

        projectionMatrix = new Matrix4f();
        projectionMatrix.perspective(fov, aspect, 0.1f, 100.0f);
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix.getMatrix();
    }

    public Transform getTransform() {
        return viewMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

}
