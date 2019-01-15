package arachnid.util;

public class ColorType {

    public float r;
    public float g;
    public float b;
    public float a;

    public ColorType() {
        r = 0.0f;
        g = 0.0f;
        b = 0.0f;
        a = 1.0f;
    }

    public ColorType(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public ColorType(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
        a = 1.0f;
    }

    public String toString() {
        return "RGB: " + r + ", " + g + ", " + b;
    }

}
