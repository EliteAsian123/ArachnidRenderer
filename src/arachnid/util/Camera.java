package arachnid.util;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {

    private Matrix4f viewMatrix;
    private Matrix4f projectionMatrix;

    public Camera(Vector3f startingPos, float fov, float aspect) {
        viewMatrix = new Matrix4f();
        viewMatrix.translate(startingPos);

        projectionMatrix = new Matrix4f();
        projectionMatrix.perspective(fov, aspect, 0.1f, 100.0f);
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

}
