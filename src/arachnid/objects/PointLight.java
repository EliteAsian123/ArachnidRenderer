package arachnid.objects;

import arachnid.render.Material;
import arachnid.render.RenderManager;
import arachnid.render.Shader;

public class PointLight extends Object {

    private Material material;

    public PointLight(Material material) {
        this.material = material;
    }

    public void draw(WorldObject parent) {
        for (Shader shader : RenderManager.getShaders()) {
            shader.setVector3("light_pos", parent.getTransform().getPosition());
        }
    }

    public void destroy() {

    }

}
