import arachnid.render.Shader;
import arachnid.render.RenderObject;
import arachnid.render.Texture;
import arachnid.render.Window;
import arachnid.util.Colors;
import arachnid.util.FileLoader;
import arachnid.util.Time;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class TestRender {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final String TITLE = "Arachnid Renderer V0.03";

    public static void main(String[] args) {
        System.out.println("Starting Arachnid...");

        Window window = new Window(WIDTH, HEIGHT, TITLE, 0);

        GL.createCapabilities();

        glClearColor(0.1f, 0.1f, 0.1f, 0.0f);

        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);

        float[] vertices = {
                -0.5f, 0.5f, 0.0f,
                0.5f, 0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f
        };

        int[] indices = {
                0, 1, 2,
                2, 3, 0
        };

        float[] otherData = {
                //TEX COORDS
                0.0f, 0.0f,
                1.0f, 0.0f,
                1.0f, 1.0f,
                0.0f, 1.0f
        };

        Shader shader = new Shader(FileLoader.readTextFile("E:/Arachnid Java/!res/shaders/simpleShader.vert"),
                FileLoader.readTextFile("E:/Arachnid Java/!res/shaders/simpleShader.frag"));

        RenderObject object = new RenderObject(vertices, indices);
        object.addOBO(1, 2, otherData);

        Texture.textureParam(Texture.REPEAT, Texture.LINEAR, Texture.MIPMAP_LINEAR);

        Texture texture = FileLoader.readPNGFile("E:/Arachnid Java/!res/textures/test.png", true, Texture.RGB);

        System.out.println("Done!");

        while(window.windowOpen()) {
            input(window);

            Time.updateTime(true);

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            object.getTransform().rotate(0.01f, new Vector3f(0.0f, 0.0f, 1.0f));

            //shader.setColor("color", Colors.PURPLE);
            shader.setMatrix4("trans", object.getTransform().getMatrix());

            object.bindTexture(texture.getTextureID());
            object.draw(shader);

            window.swapBuffers();

            window.pollEvents();
        }

        object.destroy();

        window.destroy();
    }

    private static void input(Window window) {
        if (window.getKey(GLFW_KEY_1) == GLFW_PRESS) {
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        }

        if (window.getKey(GLFW_KEY_2) == GLFW_PRESS) {
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        }
    }

}
