package arachnid.util;

import org.joml.Matrix4f;
import org.joml.Quaternionfc;
import org.joml.Vector3f;

public class Transform {

    private Matrix4f matrix;

    public Transform() {
        matrix = new Matrix4f();
    }

    public void translate(Vector3f vector) {
        matrix.translate(vector);
    }

    public void translate(float x, float y, float z) {
        matrix.translate(x, y, z);
    }

    public void rotate(Quaternionfc quaternion) {
        matrix.rotate(quaternion);
    }

    public void rotate(float angle, Vector3f axis) {
        matrix.rotate(angle, axis);
    }

    public void rotate(float angle, float x, float y, float z) {
        matrix.rotate(angle, x, y, z);
    }

    public void scale(float xyz) {
        matrix.scale(xyz);
    }

    public void scale(float x, float y, float z) {
        matrix.scale(x, y, z);
    }

    public void scale(Vector3f vector) {
        matrix.scale(vector);
    }

    public void reset() {
        matrix.identity();
    }

    public Matrix4f getMatrix() {
        return matrix;
    }

}
