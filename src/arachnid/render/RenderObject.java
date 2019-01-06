package arachnid.render;

import arachnid.util.ColorType;
import arachnid.util.Transform;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.*;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class RenderObject {

    private float vertices[];
    private int indices[];

    private int VBOid;
    private int VAOid;
    private int EBOid;

    private List<Integer> OBO = new ArrayList<Integer>();

    private Transform transform;

    public RenderObject(float vertices[], int indices[]) {
        this.vertices = vertices;
        this.indices = indices;

        transform = new Transform();

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

    public void addOBO(int index, int size, float[] data) {
        int id = glGenBuffers();
        OBO.add(id);

        glBindBuffer(GL_ARRAY_BUFFER, id);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

        if (index == 0) {
            System.err.println("OBO creation failed. Index 0 is taken.");
            System.exit(-1);
        }

        glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(index);
    }

    public void bindTexture(int id) {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void draw(Shader shader) {
        glUseProgram(shader.getShaderProgram());
        glBindVertexArray(VAOid);
        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
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
        glDeleteVertexArrays(VAOid);

        for (int object:OBO) {
            glDeleteBuffers(object);
        }
    }

}
