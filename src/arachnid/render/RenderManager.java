package arachnid.render;

import arachnid.util.ColorType;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class RenderManager {

    public static final int CLOCKWISE = GL_CW;
    public static final int COUNTER_CLOCKWISE = GL_CCW;

    public static List<Shader> shaders = new ArrayList<>();

    public static void clearColor(float red, float green, float blue) {
        glClearColor(red, green, blue, 1.0f);
    }

    public static void clearColor(int red, int green, int blue) {
        glClearColor((float) red / 255, (float) green / 255, (float) blue / 255, 1.0f);
    }

    public static void clearColor(ColorType color) {
        glClearColor(color.r, color.g, color.b, 1.0f);
    }

    public static void cull(int dir) {
        glFrontFace(dir);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);
    }

    public static void enableDepthTest() {
        glEnable(GL_DEPTH_TEST);
    }

    public static void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public static void wireframe(boolean arg) {
        if (arg) {
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        } else {
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        }
    }

    public static void addShader(Shader shader) {
        shaders.add(shader);
    }

    public static List<Shader> getShaders() {
        return shaders;
    }

}
