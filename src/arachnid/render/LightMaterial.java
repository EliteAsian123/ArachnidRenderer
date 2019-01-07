package arachnid.render;

import arachnid.util.ColorType;
import arachnid.util.Colors;

public class LightMaterial {

    private ColorType ambientColor;
    private ColorType diffuseColor;
    private ColorType specularColor;

    public LightMaterial() {
        ambientColor = Colors.WHITE;
        diffuseColor = Colors.WHITE;
        specularColor = Colors.WHITE;
    }

    public void setAmbientColor(ColorType color) {
        ambientColor = color;
    }

    public void setDiffuseColor(ColorType color) {
        diffuseColor = color;
    }

    public void setSpecularColor(ColorType color) {
        specularColor = color;
    }

    public void setShaderUniforms(Shader shader) {
        shader.setColor3("light_ambient", ambientColor);
        shader.setColor3("light_diffuse", diffuseColor);
        shader.setColor3("light_specular", specularColor);
    }

}
