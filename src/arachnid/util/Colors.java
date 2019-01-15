package arachnid.util;

public class Colors {

    public static final ColorType CLEAR = new ColorType(0.0f, 0.0f, 0.0f, 0.0f);

    public static final ColorType BLACK = new ColorType();
    public static final ColorType WHITE = new ColorType(1.0f, 1.0f, 1.0f);
    public static final ColorType RED = new ColorType(1.0f, 0.0f, 0.0f);
    public static final ColorType GREEN = new ColorType(0.0f, 1.0f, 0.0f);
    public static final ColorType BLUE = new ColorType(0.0f, 0.0f, 1.0f);
    public static final ColorType YELLOW = new ColorType(1.0f, 1.0f, 0.0f);
    public static final ColorType MAGENTA = new ColorType(1.0f, 0.0f, 1.0f);
    public static final ColorType AQUA = new ColorType(0.0f, 1.0f, 1.0f);
    public static final ColorType CYAN = AQUA;
    public static final ColorType GRAY = new ColorType(0.5f, 0.5f, 0.5f);
    public static final ColorType GREY = GRAY;
    public static final ColorType ORANGE = new ColorType(1.0f, 0.5f, 0.0f);
    public static final ColorType PINK = new ColorType(1.0f, 0.5f, 1.0f);
    public static final ColorType PURPLE = new ColorType(0.5f, 0.0f, 0.75f);


    public static ColorType RGB(int r, int g, int b) {
        return new ColorType((float) r / 255, (float) g / 255, (float) b / 255);
    }

    public static ColorType RGBA(int r, int g, int b, int a) {
        return new ColorType((float) r / 255, (float) g / 255, (float) b / 255, (float) a / 255);
    }

    public static ColorType add(ColorType a, ColorType b) {
        ColorType out = new ColorType(a.r + b.r, a.g + b.g, a.b + b.b);

        if (out.r > 1) {
            out.r = 1;
        }

        if (out.g > 1) {
            out.g = 1;
        }

        if (out.b > 1) {
            out.b = 1;
        }

        return out;
    }

    public static ColorType sub(ColorType a, ColorType b) {
        ColorType out = new ColorType(a.r - b.r, a.g - b.g, a.b - b.b);

        if (out.r < 0) {
            out.r = 0;
        }

        if (out.g < 0) {
            out.g = 0;
        }

        if (out.b < 0) {
            out.b = 0;
        }

        return out;
    }

    public static ColorType div(ColorType color, float num) {
        return new ColorType(color.r / num, color.g / num, color.b / num);
    }

}
