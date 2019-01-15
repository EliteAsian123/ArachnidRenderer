package arachnid.render;

import arachnid.objects.ModelRenderer;
import arachnid.util.ColorType;
import arachnid.util.Colors;
import arachnid.util.FileLoader;

public class Material {

    private Shader shader;

    private ColorType ambientColor;
    private ColorType diffuseColor;
    private ColorType specularColor;
    private ColorType emissionColor;

    private float shininess;

    private Texture diffuseTexture;
    private Texture specularTexture;
    private Texture emissionTexture;

    public Material(Shader shader) {
        this.shader = shader;

        ambientColor = Colors.WHITE;
        diffuseColor = Colors.WHITE;
        specularColor = Colors.WHITE;
        emissionColor = Colors.WHITE;

        diffuseTexture = FileLoader.readPNGFile("res/textures/error.png", false, Texture.RGB);
        specularTexture = FileLoader.readPNGFile("res/textures/white.png", false, Texture.RGB);
        emissionTexture = FileLoader.readPNGFile("res/textures/black.png", false, Texture.RGB);

        shininess = 16.0f;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }

    public void setAmbientColor(ColorType color) {
        ambientColor = color;
    }

    public void setDiffuseColor(ColorType color) {
        diffuseColor = color;
    }

    public void calculateLightDiffuse() {
        diffuseColor = Colors.sub(ambientColor, Colors.div(specularColor, 4));
    }

    public void setDiffuseTexture(Texture texture) {
        diffuseTexture = texture;
    }

    public void setSpecularColor(ColorType color) {
        specularColor = color;
    }

    public void setSpecularTexture(Texture texture) {
        specularTexture = texture;
    }

    public void setEmissionColor(ColorType color) {
        emissionColor = color;
    }

    public void setEmissionTexture(Texture texture) {
        emissionTexture = texture;
    }

    public Shader getShader() {
        return shader;
    }

    public void setShaderUniforms(Shader shader, ModelRenderer object) {
        shader.setColor3("mat_ambient", ambientColor);
        shader.setColor3("mat_diffuse", diffuseColor);
        shader.setColor3("mat_specular", specularColor);
        shader.setColor3("mat_emission", emissionColor);

        shader.setFloat("mat_shininess", shininess);

        shader.setInt("texture_diffuse", 0);
        shader.setInt("texture_specular", 1);
        shader.setInt("texture_emission", 2);

        object.bindTexture(diffuseTexture.getTextureID(), 0);
        object.bindTexture(specularTexture.getTextureID(), 1);
        object.bindTexture(emissionTexture.getTextureID(), 2);
    }

    public void setLightShaderUniforms(Shader shader) {
        shader.setColor3("light_ambient", ambientColor);
        shader.setColor3("light_diffuse", diffuseColor);
        shader.setColor3("light_specular", specularColor);
    }

}
