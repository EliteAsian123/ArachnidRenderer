import arachnid.render.Shader;
import arachnid.render.RenderObject;
import arachnid.render.Texture;
import arachnid.render.Window;
import arachnid.util.Camera;
import arachnid.util.FileLoader;
import arachnid.util.Time;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class TestRender {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final String TITLE = "Arachnid Renderer V0.05";

    private static float mouseX;
    private static float mouseY;

    /*

                            CREDIT TO THE JOML FOR PROVIDING THE FIRSTPERSON SOURCE CODE.
        https://github.com/JOML-CI/joml-lwjgl3-demos/blob/master/src/org/joml/lwjgl/FirstPersonCameraDemo.java

     */

    public static void main(String[] args) {
        System.out.println("Starting Arachnid...");

        Window window = new Window(WIDTH, HEIGHT, TITLE, 0);

        GL.createCapabilities();

        glClearColor(0.1f, 0.1f, 0.1f, 0.0f);

        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);

        glEnable(GL_DEPTH_TEST);

        GLFWCursorPosCallback cursorPosCallback;

        glfwSetCursorPosCallback(window.getWindow(), cursorPosCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                mouseX = (float) xpos / WIDTH;
                mouseY = (float) ypos / HEIGHT;
            }
        });

        glfwSetInputMode(window.getWindow(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);

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

        Vector3f[] objectPositions = {
                new Vector3f(0, 0, -3),
                new Vector3f(-1, 0, -3),
                new Vector3f(1, 0 , -3),
                new Vector3f(0, -1, -3),
                new Vector3f(-1, -1, -3),
                new Vector3f(1, -1, -3),
                new Vector3f(0, 1, -3),
                new Vector3f(-1, 1, -3),
                new Vector3f(1, 1, -3),

                new Vector3f(0, 0, -4),
                new Vector3f(-1, 0, -4),
                new Vector3f(1, 0 , -4),
                new Vector3f(0, -1, -4),
                new Vector3f(-1, -1, -4),
                new Vector3f(1, -1, -4),
                new Vector3f(0, 1, -4),
                new Vector3f(-1, 1, -4),
                new Vector3f(1, 1, -4),

                new Vector3f(0, 0, -5),
                new Vector3f(-1, 0, -5),
                new Vector3f(1, 0 , -5),
                new Vector3f(0, -1, -5),
                new Vector3f(-1, -1, -5),
                new Vector3f(1, -1, -5),
                new Vector3f(0, 1, -5),
                new Vector3f(-1, 1, -5),
                new Vector3f(1, 1, -5)
        };

        Texture.textureParam(Texture.REPEAT, Texture.LINEAR, Texture.MIPMAP_LINEAR);

        Texture texture = FileLoader.readPNGFile("res/textures/error.png", true, Texture.RGB);

        Camera camera = new Camera(new Vector3f(0.0f, 0.0f, -3.0f), 45.0f, (float) WIDTH / HEIGHT);

        System.out.println("Done!");

        float movementSpeed = 2.5f;
        Vector3f position = new Vector3f();

        while(window.windowOpen()) {

            float speed = movementSpeed * (float) Time.getDelta();

            Vector3f front = new Vector3f();
            camera.getViewMatrix().positiveZ(front).negate().mul(speed);

            Vector3f right = new Vector3f();
            camera.getViewMatrix().positiveX(right).mul(speed);

            if (window.getKey(GLFW_KEY_1) == GLFW_PRESS) {
                glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
            }

            if (window.getKey(GLFW_KEY_2) == GLFW_PRESS) {
                glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
            }

            if (window.getKey(GLFW_KEY_W) == GLFW_PRESS) {
                position.add(front);
            }

            if (window.getKey(GLFW_KEY_S) == GLFW_PRESS) {
                position.sub(front);
            }

            if (window.getKey(GLFW_KEY_D) == GLFW_PRESS) {
                position.add(right);
            }

            if (window.getKey(GLFW_KEY_A) == GLFW_PRESS) {
                position.sub(right);
            }

            if (window.getKey(GLFW_KEY_Q) == GLFW_PRESS) {
                position.add(new Vector3f(0, speed, 0));
            }

            if (window.getKey(GLFW_KEY_E) == GLFW_PRESS) {
                position.sub(new Vector3f(0, speed, 0));
            }

            Time.updateTime(true);

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            camera.getViewMatrix().identity().rotateX(mouseY).rotateY(mouseX).translate(-position.x, -position.y, -position.z);


            shader.setMatrix4("view", camera.getViewMatrix());
            shader.setMatrix4("proj", camera.getProjectionMatrix());

            for (int i = 0; i < objectPositions.length; i++) {
                object.getTransform().reset();
                object.getTransform().translate(objectPositions[i]);
                shader.setMatrix4("trans", object.getTransform().getMatrix());

                object.bindTexture(texture.getTextureID());
                object.draw(shader);
            }

            window.swapBuffers();

            window.pollEvents();
        }

        object.destroy();

        window.destroy();
    }

}
