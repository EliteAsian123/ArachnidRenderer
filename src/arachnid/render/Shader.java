package arachnid.render;

import arachnid.util.ColorType;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public class Shader {

    private int shaderProgram;

    public Shader(String vertexShaderSource, String fragmentShaderSource) {
        int vertexShader = glCreateShader(GL_VERTEX_SHADER);

        glShaderSource(vertexShader, vertexShaderSource);
        glCompileShader(vertexShader);

        if (glGetShaderi(vertexShader, GL_COMPILE_STATUS) == GL_FALSE) {
            System.out.println(glGetShaderInfoLog(vertexShader, 512));
            System.err.println("Vertex shader compile was not successful.");
        }

        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);

        glShaderSource(fragmentShader, fragmentShaderSource);
        glCompileShader(fragmentShader);

        if (glGetShaderi(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE) {
            System.out.println(glGetShaderInfoLog(fragmentShader, 512));
            System.err.println("Fragment shader compile was not successful.");
        }

        shaderProgram = glCreateProgram();

        glAttachShader(shaderProgram, vertexShader);
        glAttachShader(shaderProgram, fragmentShader);
        glLinkProgram(shaderProgram);

        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
    }

    public int getShaderProgram() {
        return shaderProgram;
    }

    public void setInt(String name, int val) {
        glUniform1i(glGetUniformLocation(shaderProgram, name), val);
    }

    public void setFloat(String name, float val) {
        glUniform1f(glGetUniformLocation(shaderProgram, name), val);
    }

    public void setVector2(String name, float x, float y) {
        glUniform2f(glGetUniformLocation(shaderProgram, name), x, y);
    }

    public void setVector3(String name, float x, float y, float z) {
        glUniform3f(glGetUniformLocation(shaderProgram, name), x, y, z);
    }

    public void setVector4(String name, float x, float y, float z, float w) {
        glUniform4f(glGetUniformLocation(shaderProgram, name), x, y, z, w);
    }

    public void setColor(String name, ColorType color) {
        glUniform4f(glGetUniformLocation(shaderProgram, name), color.r, color.g, color.b, color.a);
    }

    public void setMatrix4(String name, Matrix4f matrix) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        matrix.get(buffer);
        glUniformMatrix4fv(glGetUniformLocation(shaderProgram, name), false, buffer);
    }

}
