package arachnid.util;

import arachnid.render.Texture;
import org.lwjgl.system.CallbackI;
import org.lwjgl.system.MemoryStack;

import java.io.BufferedReader;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.stb.STBImage.*;

public class FileLoader {

    public static String readTextFile(String file) {
        StringBuilder fileText = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new java.io.FileReader(file));

            String line;
            while((line = reader.readLine()) != null) {
                fileText.append(line).append("//\n");
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileText.toString();
    }

    public static Texture readPNGFile(String file, boolean flip, int type) {
        ByteBuffer data = null;
        int width;
        int height;

        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            stbi_set_flip_vertically_on_load(!flip);
            if (type == Texture.RGB) {
                data = stbi_load(file, w, h, comp, 3);
            } else if (type == Texture.RGBA) {
                data = stbi_load(file, w, h, comp, 4);
            } else {
                System.err.println("Type is not allowed in image.");
            }
            if (data == null) {
                throw new RuntimeException(stbi_failure_reason());
            }

            width = w.get();
            height = h.get();
        }

        return new Texture(width, height, data, type);
    }

}
