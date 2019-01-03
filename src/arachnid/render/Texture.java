package arachnid.render;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class Texture {

    public static int REPEAT = GL_REPEAT;
    public static int MIRRORED_REPEAT = GL_MIRRORED_REPEAT;
    public static int CLAMP_TO_EDGE = GL_CLAMP_TO_EDGE;

    public static int LINEAR = GL_LINEAR;
    public static int NEAREST = GL_NEAREST;

    public static int MIPMAP_LINEAR = GL_LINEAR_MIPMAP_LINEAR;
    public static int MIPMAP_NEAREST = GL_NEAREST_MIPMAP_NEAREST;

    public static int RGB = GL_RGB;
    public static int RGBA = GL_RGBA;

    public static void textureParam(int wrapMode, int minFilter, int magFilter) {
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wrapMode);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wrapMode);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, minFilter);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, magFilter);
    }

    private int textureID;

    private int width;
    private int height;
    private int type;

    public Texture(int width, int height, ByteBuffer data, int type) {
        this.width = width;
        this.height = height;
        this.type = type;

        textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexImage2D(GL_TEXTURE_2D, 0, RGB, width, height, 0, type, GL_UNSIGNED_BYTE, data);
        glGenerateMipmap(GL_TEXTURE_2D);
    }

    public int getTextureID() {
        return textureID;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getType() {
        return type;
    }

    public void destroy() {
        glDeleteTextures(textureID);
    }

}
