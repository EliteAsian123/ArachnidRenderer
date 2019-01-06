package arachnid.render;

import arachnid.util.ColorType;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
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
        glUseProgram(shaderProgram);
        glUniform1i(glGetUniformLocation(shaderProgram, name), val);
    }

    public void setFloat(String name, float val) {
        glUseProgram(shaderProgram);
        glUniform1f(glGetUniformLocation(shaderProgram, name), val);
    }

    public void setVector2(String name, float x, float y) {
        glUseProgram(shaderProgram);
        glUniform2f(glGetUniformLocation(shaderProgram, name), x, y);
    }

    public void setVector2(String name, Vector2f vector) {
        glUseProgram(shaderProgram);
        glUniform2f(glGetUniformLocation(shaderProgram, name), vector.x, vector.y);
    }

    public void setVector3(String name, float x, float y, float z) {
        glUseProgram(shaderProgram);
        glUniform3f(glGetUniformLocation(shaderProgram, name), x, y, z);
    }

    public void setVector3(String name, Vector3f vector) {
        glUseProgram(shaderProgram);
        glUniform3f(glGetUniformLocation(shaderProgram, name), vector.x, vector.y, vector.z);
    }

    public void setVector4(String name, float x, float y, float z, float w) {
        glUseProgram(shaderProgram);
        glUniform4f(glGetUniformLocation(shaderProgram, name), x, y, z, w);
    }

    public void setVector4(String name, Vector4f vector) {
        glUseProgram(shaderProgram);
        glUniform4f(glGetUniformLocation(shaderProgram, name), vector.x, vector.y, vector.z, vector.w);
    }

    public void setColor4(String name, ColorType color) {
        glUseProgram(shaderProgram);
        glUniform4f(glGetUniformLocation(shaderProgram, name), color.r, color.g, color.b, color.a);
    }

    public void setColor3(String name, ColorType color) {
        glUseProgram(shaderProgram);
        glUniform3f(glGetUniformLocation(shaderProgram, name), color.r, color.g, color.b);
    }

    public void setMatrix4(String name, Matrix4f matrix) {
        glUseProgram(shaderProgram);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        matrix.get(buffer);
        glUniformMatrix4fv(glGetUniformLocation(shaderProgram, name), false, buffer);
    }

}
