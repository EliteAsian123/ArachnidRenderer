package arachnid.render;

import arachnid.util.ColorType;
import arachnid.util.Colors;

public class Material {

    private ColorType ambientColor;
    private ColorType diffuseColor;
    private ColorType specularColor;

    private float shininess;

    private String ambientColorName;
    private String diffuseColorName;
    private String specularColorName;

    private String shininessName;

    public Material(ColorType ambientColor, ColorType diffuseColor, ColorType specularColor, float shininess) {
        this.ambientColor = ambientColor;
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;

        this.shininess = shininess;
    }

    public Material() {
        ambientColor = Colors.BLACK;
        diffuseColor = Colors.BLACK;
        specularColor = Colors.BLACK;

        shininess = 0.0f;
    }

    public void setShaderNames(String ambientColorName, String diffuseColorName, String specularColorName, String shininessName) {
        this.ambientColorName = ambientColorName;
        this.diffuseColorName = diffuseColorName;
        this.specularColorName = specularColorName;

        this.shininessName = shininessName;
    }

    public void setShaderUniforms(Shader shader) {
        shader.setColor3(ambientColorName, ambientColor);
        shader.setColor3(diffuseColorName, diffuseColor);
        shader.setColor3(specularColorName, specularColor);

        shader.setFloat(shininessName, shininess);
    }

}
