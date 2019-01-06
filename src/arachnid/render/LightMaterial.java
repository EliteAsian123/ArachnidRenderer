package arachnid.render;

import arachnid.util.ColorType;
import arachnid.util.Colors;

public class LightMaterial {

    private ColorType ambientColor;
    private ColorType diffuseColor;
    private ColorType specularColor;

    private String ambientColorName;
    private String diffuseColorName;
    private String specularColorName;

    public LightMaterial(ColorType ambientColor, ColorType diffuseColor, ColorType specularColor) {
        this.ambientColor = ambientColor;
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
    }

    public LightMaterial() {
        ambientColor = Colors.WHITE;
        diffuseColor = Colors.WHITE;
        specularColor = Colors.WHITE;
    }

    public void setShaderNames(String ambientColorName, String diffuseColorName, String specularColorName) {
        this.ambientColorName = ambientColorName;
        this.diffuseColorName = diffuseColorName;
        this.specularColorName = specularColorName;
    }

    public void setShaderUniforms(Shader shader) {
        shader.setColor3(ambientColorName, ambientColor);
        shader.setColor3(diffuseColorName, diffuseColor);
        shader.setColor3(specularColorName, specularColor);
    }

}
