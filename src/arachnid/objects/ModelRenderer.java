package arachnid.objects;

import arachnid.render.Material;
import arachnid.util.Transform;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.stream.IntStream;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class ModelRenderer extends Object {

    private float[] vertices;
    private int[] indices;

    private int VBOid;
    private int VAOid;
    private int EBOid;
    private int OBOid;

    private Material material;

    private WorldObject object;

    public ModelRenderer(float[] vertices, int[] indices, Material material) {
        this.vertices = vertices;
        this.indices = indices;
        this.material = material;


        createBuffers(vertices, indices);
    }

    public ModelRenderer(float[] vertices, int[] indices, float[] otherData, int[] sizes, Material material) {
        this.vertices = vertices;
        this.indices = indices;
        this.material = material;


        createBuffers(vertices, indices);

        OBOid = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, OBOid);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(otherData.length);
        buffer.put(otherData);
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

    private void createBuffers(float[] vertices, int[] indices) {
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

    public float[] getVertices() {
        return vertices;
    }

    public int[] getIndices() {
        return indices;
    }

    public int getVBOid() {
        return VBOid;
    }

    public int getVAOid() {
        return VAOid;
    }

    public int getEBOid() {
        return EBOid;
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

    public void draw(WorldObject parent) {
        material.setShaderUniforms(material.getShader(), this);

        material.getShader().setMatrix4("trans", parent.getTransform().getMatrix());

        glUseProgram(material.getShader().getShaderProgram());
        glBindVertexArray(VAOid);
        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
    }

    public void destroy() {
        glDeleteBuffers(VBOid);
        glDeleteBuffers(EBOid);
        glDeleteBuffers(OBOid);
        glDeleteVertexArrays(VAOid);
    }

}
