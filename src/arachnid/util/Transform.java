package arachnid.util;

import org.joml.Matrix4f;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Transform {

    private Matrix4f matrix;

    private Vector3f position;

    public Transform() {
        matrix = new Matrix4f();
        position = new Vector3f();
    }

    public void translate(Vector3f vector) {
        position.add(vector);
        matrix.identity().translate(position);
    }

    public void translate(float x, float y, float z) {
        position.add(x, y, z);
        matrix.identity().translate(position);
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
        position = new Vector3f();
    }

    public Matrix4f getMatrix() {
        return matrix;
    }

    public Vector3f getPosition() {
        return position;
    }

}
