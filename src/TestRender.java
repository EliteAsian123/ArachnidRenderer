import arachnid.render.*;
import arachnid.render.Window;
import arachnid.util.*;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;

public class TestRender {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final String TITLE = "Arachnid Renderer V0.071";

    private static float mouseX;
    private static float mouseY;

    private static boolean mouseDisabled = false;

    /*
                            CREDIT TO THE JOML FOR PROVIDING THE FIRSTPERSON SOURCE CODE.
        https://github.com/JOML-CI/joml-lwjgl3-demos/blob/master/src/org/joml/lwjgl/FirstPersonCameraDemo.java
     */

    public static void main(String[] args) {
        System.out.println("Starting Arachnid...");

        Window window = new Window(WIDTH, HEIGHT, TITLE, 0);

        GL.createCapabilities();

        RenderManager.clearColor(0.1f, 0.1f, 0.1f);

        RenderManager.cull(RenderManager.CLOCKWISE);

        RenderManager.enableDepthTest();

        GLFWCursorPosCallback cursorPosCallback;

        glfwSetCursorPosCallback(window.getWindow(), cursorPosCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                if (!mouseDisabled) {
                    mouseX = (float) xpos / WIDTH;
                    mouseY = (float) ypos / HEIGHT;
                }
            }
        });

        GLFWMouseButtonCallback mouseButtonCallback;

        glfwSetMouseButtonCallback(window.getWindow(), mouseButtonCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS) {
                    if (mouseDisabled) {
                        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
                        mouseDisabled = false;
                    }
                }
            }
        });

        glfwSetInputMode(window.getWindow(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        FileLoader.setErrorTexturePath("res/textures/error.png");

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
                //TEX COORDS   //NORMALS
                0, 0,          0, 0, 1,
                1, 0,          0, 0, 1,
                1, 1,          0, 0, 1,
                0, 1,          0, 0, 1,

                0, 0,          0, 0, -1,
                1, 0,          0, 0, -1,
                1, 1,          0, 0, -1,
                0, 1,          0, 0, -1,

                0, 0,          1, 0, 0,
                1, 0,          1, 0, 0,
                1, 1,          1, 0, 0,
                0, 1,          1, 0, 0,

                0, 0,          -1, 0, 0,
                1, 0,          -1, 0, 0,
                1, 1,          -1, 0, 0,
                0, 1,          -1, 0, 0,

                0, 0,          0, 1, 0,
                1, 0,          0, 1, 0,
                1, 1,          0, 1, 0,
                0, 1,          0, 1, 0,

                0, 0,          0, -1, 0,
                1, 0,          0, -1, 0,
                1, 1,          0, -1, 0,
                0, 1,          0, -1, 0
        };

        Shader lightShader = new Shader(FileLoader.readTextFile("res/shaders/simpleShader.vert"),
                FileLoader.readTextFile("res/shaders/simpleShader_light.frag"));

        Shader diffuseShader = new Shader(FileLoader.readTextFile("res/shaders/simpleShader_withNormal.vert"),
                FileLoader.readTextFile("res/shaders/simpleShader.frag"));

        RenderObject object = new RenderObject(vertices, indices, otherData, new int[]{2, 3});

        RenderObject light = new RenderObject(vertices, indices);

        Texture.textureParam(Texture.REPEAT, Texture.LINEAR, Texture.MIPMAP_LINEAR);

        Texture textureCrate = FileLoader.readPNGFile("res/textures/crate.png", true, Texture.RGB);
        Texture textureCrateSpecular = FileLoader.readPNGFile("res/textures/crate_specular.png", true, Texture.RGB);
        Texture texture = FileLoader.readPNGFile("res/textures/test.png", true, Texture.RGB);

        Camera camera = new Camera(new Vector3f(0.0f, 0.0f, -3.0f), 45.0f, (float) WIDTH / HEIGHT);

        Material material = new Material();
        material.setShininess(32.0f);
        material.setAmbientColor(Colors.RGB(128, 128, 128));
        material.setDiffuseColor(Colors.RGB(82, 82, 82));
        //material.setEmissionTexture(texture);
        material.setDiffuseTexture(textureCrate);
        material.setSpecularTexture(textureCrateSpecular);

        LightMaterial lightMaterial = new LightMaterial();
        lightMaterial.setAmbientColor(Colors.RGB(128, 128, 128));
        lightMaterial.setDiffuseColor(Colors.RGB(64, 64, 64));
        lightMaterial.setSpecularColor(Colors.WHITE);

        System.out.println("Done!");

        float movementSpeed = 2.5f;
        Vector3f position = new Vector3f();

        float i = 0;

        while(window.windowOpen()) {

            float speed = movementSpeed * (float) Time.getDelta();

            Vector3f front = new Vector3f();
            camera.getViewMatrix().positiveZ(front).negate().mul(speed);

            Vector3f right = new Vector3f();
            camera.getViewMatrix().positiveX(right).mul(speed);

            i += 1.0 * Time.getDelta();

            if (window.getKey(GLFW_KEY_1) == GLFW_PRESS) {
                RenderManager.wireframe(false);
            }

            if (window.getKey(GLFW_KEY_2) == GLFW_PRESS) {
                RenderManager.wireframe(true);
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

            if (window.getKey(GLFW_KEY_ESCAPE) == GLFW_PRESS) {
                glfwSetInputMode(window.getWindow(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
                mouseDisabled = true;
            }

            Time.updateTime(true);

            RenderManager.clear();

            camera.getViewMatrix().identity().rotateX(mouseY).rotateY(mouseX).translate(-position.x, -position.y, -position.z);

            lightShader.setMatrix4("view", camera.getViewMatrix());
            lightShader.setMatrix4("proj", camera.getProjectionMatrix());

            diffuseShader.setMatrix4("view", camera.getViewMatrix());
            diffuseShader.setMatrix4("proj", camera.getProjectionMatrix());

            lightMaterial.setShaderUniforms(diffuseShader);
            lightMaterial.setShaderUniforms(lightShader);

            diffuseShader.setVector3("lightPosition", light.getTransform().getPosition());

            diffuseShader.setVector3("viewPos", position);

            object.getTransform().reset();
            object.getTransform().translate(-0.75f, 0, -3);
            diffuseShader.setMatrix4("trans", object.getTransform().getMatrix());
            object.draw(diffuseShader, material);

            object.getTransform().reset();
            object.getTransform().translate(0.75f, 0, -3);
            diffuseShader.setMatrix4("trans", object.getTransform().getMatrix());
            object.draw(diffuseShader, material);

            light.getTransform().reset();
            light.getTransform().translate((float) Math.sin(i) * 2, 0, (float) Math.cos(i) * 2 - 3);
            light.getTransform().scale(0.2f);

            lightShader.setMatrix4("trans", light.getTransform().getMatrix());
            light.draw(lightShader);

            window.swapBuffers();

            window.pollEvents();
        }

        object.destroy();

        window.destroy();

        System.exit(0);
    }

}
