package arachnid.render;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.system.MemoryStack.*;


public class Window {

    public static final int FPS_NO_LIMIT = 0;
    public static final int FPS_NORMAL = 1;
    public static final int FPS_HALF = 2;

    private int width;
    private int height;
    private String title;

    private long window;

    public Window(int width, int height, String title, int resizable) {
        this.width = width;
        this.height = height;
        this.title = title;

        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Could not initialize GLFW.");
        }

        glfwWindowHint(GLFW_RESIZABLE, resizable);

        window = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create window.");
        }

        try (MemoryStack stack = stackPush()) {
            IntBuffer bWidth = stack.mallocInt(1);
            IntBuffer bHeight = stack.mallocInt(2);

            glfwGetWindowSize(window, bWidth, bHeight);

            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(window, (vidMode.width() - bWidth.get(0)) / 2, (vidMode.height() - bHeight.get(0)) / 2);
        }

        glfwMakeContextCurrent(window);

        //Enable V-Sync
        glfwSwapInterval(1);

        glfwShowWindow(window);
    }

    public boolean windowOpen() {
        return !glfwWindowShouldClose(window);
    }

    public void setFPSCap(int cap) {
        glfwMakeContextCurrent(window);
        glfwSwapInterval(cap);
    }

    public long getWindow() {
        return window;
    }

    public void swapBuffers() {
        glfwSwapBuffers(window);
    }

    public void pollEvents() {
        glfwPollEvents();
    }

    public void destroy() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public int getKey(int key) {
        return glfwGetKey(window, key);
    }
}
