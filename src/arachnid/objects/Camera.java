package arachnid.objects;

import arachnid.render.RenderManager;
import arachnid.render.Shader;
import org.joml.Matrix4f;

public class Camera extends Object {

    private Matrix4f projectionMatrix;

    public Camera(float fov, float aspect) {
        projectionMatrix = new Matrix4f();
        projectionMatrix.perspective(fov, aspect, 0.1f, 100.0f);
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public void draw(WorldObject parent) {
        for (Shader shader : RenderManager.getLightEffectedShaders()) {
            shader.setMatrix4("view", parent.getTransform().getMatrix());
            shader.setMatrix4("proj", getProjectionMatrix());
        }
    }

    public void destroy() {

    }

}
