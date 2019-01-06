import arachnid.render.*;
import arachnid.util.*;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;

public class TestRender {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final String TITLE = "Arachnid Renderer V0.06";

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

        RenderManager.clearColor(0, 0.4f, 0.7f);

        RenderManager.cull(RenderManager.CLOCKWISE);

        RenderManager.enableDepthTest();

        GLFWCursorPosCallback cursorPosCallback;

        glfwSetCursorPosCallback(window.getWindow(), cursorPosCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                mouseX = (float) xpos / WIDTH;
                mouseY = (float) ypos / HEIGHT;
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

        float[] normal = {
                0.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 1.0f,

                0.0f, 0.0f, -1.0f,
                0.0f, 0.0f, -1.0f,
                0.0f, 0.0f, -1.0f,
                0.0f, 0.0f, -1.0f,

                1.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f,
                1.0f, 0.0f, 0.0f,

                -1.0f, 0.0f, 0.0f,
                -1.0f, 0.0f, 0.0f,
                -1.0f, 0.0f, 0.0f,
                -1.0f, 0.0f, 0.0f,

                0.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f,

                0.0f, -1.0f, 0.0f,
                0.0f, -1.0f, 0.0f,
                0.0f, -1.0f, 0.0f,
                0.0f, -1.0f, 0.0f
        };

        float[] floorVertices = {
                1f, 0f, 1f,
                -1f, 0f, 1f,
                -1f, 0f, -1f,
                1f, 0f, -1f
        };

        int[] floorIndices = {
                0, 1, 2,
                2, 3, 0,
        };

        float[] otherFloorData = {
                0, 0,
                10, 0,
                10, 10,
                0, 10
        };

        float[] floorNormals = {
                0.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 0.0f
        };

        Shader lightShader = new Shader(FileLoader.readTextFile("res/shaders/simpleShader.vert"),
                FileLoader.readTextFile("res/shaders/simpleShader_light.frag"));

        Shader diffuseShader = new Shader(FileLoader.readTextFile("res/shaders/simpleShader_withNormal.vert"),
                FileLoader.readTextFile("res/shaders/simpleShader.frag"));

        RenderObject object = new RenderObject(vertices, indices);
        object.addOBO(1, 2, otherData);
        object.addOBO(2, 3, normal);

        object.getTransform().translate(new Vector3f(0, 0, -3));

        RenderObject light = new RenderObject(vertices, indices);

        RenderObject floor = new RenderObject(floorVertices, floorIndices);
        floor.addOBO(1, 2, otherFloorData);
        floor.addOBO(2, 3, floorNormals);

        floor.getTransform().translate(new Vector3f(0, -0.5f, 0));
        floor.getTransform().scale(10);

        Texture.textureParam(Texture.REPEAT, Texture.LINEAR, Texture.MIPMAP_LINEAR);

        Texture texture = FileLoader.readPNGFile("res/textures/error.png", true, Texture.RGB);
        Texture textureConcrete = FileLoader.readPNGFile("res/textures/concrete.png", false, Texture.RGB);

        Camera camera = new Camera(new Vector3f(0.0f, 0.0f, -3.0f), 45.0f, (float) WIDTH / HEIGHT);

        Material material = new Material(Colors.WHITE, Colors.WHITE, Colors.GRAY, 16.0f);
        material.setShaderNames("mat_ambient", "mat_diffuse", "mat_specular", "mat_shininess");

        LightMaterial lightMaterial = new LightMaterial(Colors.RGB(220, 230, 240), new ColorType(0.5f, 0.5f, 0.5f), Colors.WHITE);
        lightMaterial.setShaderNames("light_ambient", "light_diffuse", "light_specular");

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

            diffuseShader.setMatrix4("trans", object.getTransform().getMatrix());

            material.setShaderUniforms(diffuseShader);

            object.bindTexture(textureConcrete.getTextureID());
            object.draw(diffuseShader);

            diffuseShader.setMatrix4("trans", floor.getTransform().getMatrix());

            floor.bindTexture(textureConcrete.getTextureID());
            floor.draw(diffuseShader);

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
    }

}
