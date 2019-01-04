import arachnid.render.Shader;
import arachnid.render.RenderObject;
import arachnid.render.Texture;
import arachnid.render.Window;
import arachnid.util.Camera;
import arachnid.util.FileLoader;
import arachnid.util.Time;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class TestRender {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final String TITLE = "Arachnid Renderer V0.04";

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
                //FRONT FACE
                -0.5f, 0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
                -0.5f, -0.5f, 0.5f,

                //BACK FACE
                -0.5f, 0.5f, -0.5f,
                0.5f, 0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                -0.5f, -0.5f, -0.5f,

                //RIGHT FACE
                0.5f, 0.5f, 0.5f,
                0.5f, 0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, 0.5f,

                //LEFT FACE
                -0.5f, 0.5f, 0.5f,
                -0.5f, 0.5f, -0.5f,
                -0.5f, -0.5f, -0.5f,
                -0.5f, -0.5f, 0.5f,

                //TOP FACE
                0.5f, 0.5f, 0.5f,
                -0.5f, 0.5f, 0.5f,
                -0.5f, 0.5f, -0.5f,
                0.5f, 0.5f, -0.5f,

                //BOTTOM FACE
                0.5f, -0.5f, 0.5f,
                -0.5f, -0.5f, 0.5f,
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f
        };

        int[] indices = {
                0, 1, 2,
                2, 3, 0,

                5, 4, 7,
                7, 6, 5,

                8, 9, 10,
                10, 11, 8,

                14, 13, 12,
                12, 15, 14,

                16, 17, 18,
                18, 19, 16,

                22, 21, 20,
                20, 23, 22
        };

        float[] otherData = {
                //TEX COORDS
                0, 0,
                1, 0,
                1, 1,
                0, 1,

                0, 0,
                1, 0,
                1, 1,
                0, 1,

                0, 0,
                1, 0,
                1, 1,
                0, 1,

                0, 0,
                1, 0,
                1, 1,
                0, 1,

                0, 0,
                1, 0,
                1, 1,
                0, 1,

                0, 0,
                1, 0,
                1, 1,
                0, 1

        };

        Shader shader = new Shader(FileLoader.readTextFile("res/shaders/simpleShader.vert"),
                FileLoader.readTextFile("res/shaders/simpleShader.frag"));

        RenderObject object = new RenderObject(vertices, indices);
        object.addOBO(1, 2, otherData);

        Texture.textureParam(Texture.REPEAT, Texture.LINEAR, Texture.MIPMAP_LINEAR);

        Texture texture = FileLoader.readPNGFile("res/textures/test.png", true, Texture.RGB);

        Camera camera = new Camera(new Vector3f(0.0f, 0.0f, -3.0f), 45.0f, (float) WIDTH / HEIGHT);

        System.out.println("Done!");

        while(window.windowOpen()) {
            input(window);

            Time.updateTime(true);

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            object.getTransform().rotate(0.01f, new Vector3f(0.5f, 1.0f, 0.0f));

            shader.setMatrix4("trans", object.getTransform().getMatrix());
            shader.setMatrix4("view", camera.getViewMatrix());
            shader.setMatrix4("proj", camera.getProjectionMatrix());

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
