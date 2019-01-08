package arachnid.render;

import arachnid.util.ColorType;
import arachnid.util.Transform;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.CallbackI;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.*;
import java.util.stream.IntStream;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class RenderObject {

    private float vertices[];
    private int indices[];

    private int VBOid;
    private int VAOid;
    private int EBOid;
    private int OBOid;

    private Transform transform;

    public RenderObject(float vertices[], int indices[]) {
        this.vertices = vertices;
        this.indices = indices;

        transform = new Transform();

        createBaseBuffers(vertices, indices);
    }

    public RenderObject(float vertices[], int indices[], float other[], int sizes[]) {
        this.vertices = vertices;
        this.indices = indices;

        transform = new Transform();

        createBaseBuffers(vertices, indices);

        OBOid = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, OBOid);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(other.length);
        buffer.put(other);
        buffer.flip();
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

        int sum = IntStream.of(sizes).sum();
        int offset = 0;

        for (int i = 0; i < sizes.length; i++) {
            glVertexAttribPointer(i + 1, sizes[i], GL_FLOAT, false, sum * 4, offset * 4);
            glEnableVertexAttribArray(i + 1);
            offset += sizes[i];
        }

    }

    private void createBaseBuffers(float vertices[], int indices[]) {
        VAOid = glGenVertexArrays();
        VBOid = glGenBuffers();
        EBOid = glGenBuffers();

        glBindVertexArray(VAOid);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBOid);
        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
        indicesBuffer.put(indices);
        indicesBuffer.flip();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, VBOid);
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
        vertexBuffer.put(vertices);
        vertexBuffer.flip();
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0);
    }

    public void bindTexture(int id, int textureID) {
        switch (textureID) {
            case 0:
                glActiveTexture(GL_TEXTURE0);
                break;
            case 1:
                glActiveTexture(GL_TEXTURE1);
                break;
            case 2:
                glActiveTexture(GL_TEXTURE2);
                break;
            case 3:
                glActiveTexture(GL_TEXTURE3);
                break;
            case 4:
                glActiveTexture(GL_TEXTURE4);
                break;
            case 5:
                glActiveTexture(GL_TEXTURE5);
                break;
            case 6:
                glActiveTexture(GL_TEXTURE6);
                break;
            case 7:
                glActiveTexture(GL_TEXTURE7);
                break;
            default:
                System.err.println("You can only use up to 8 textures. Support for more will be added in the future.");
        }
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void draw(Shader shader) {
        glUseProgram(shader.getShaderProgram());
        glBindVertexArray(VAOid);
        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
    }

    public void draw(Shader shader, Material material) {
        material.setShaderUniforms(shader, this);
        draw(shader);
    }

    public float[] getVertices() {
        return vertices;
    }

    public int[] getIndices() {
        return indices;
    }

    public int getVertexVBOid() {
        return VBOid;
    }

    public int getVAOid() {
        return VAOid;
    }

    public int getEBOid() {
        return EBOid;
    }

    public Transform getTransform() {
        return transform;
    }

    public void destroy() {
        glDeleteBuffers(VBOid);
        glDeleteBuffers(EBOid);
        glDeleteBuffers(OBOid);
        glDeleteVertexArrays(VAOid);
    }

}
