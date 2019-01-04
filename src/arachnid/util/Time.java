package arachnid.util;

public class Time {

    public static final long SECOND = 1000000000L;

    private static double deltaTime;

    private static long lastFrameTime;
    private static int frames = 0;
    private static long frameCounter = 0;

    private static int frameCount = 0;

    public static long getTime() {
        return System.nanoTime();
    }

    public static double getDelta() {
        return deltaTime;
    }

    public static int getFps() {
        return frameCount;
    }

    public static void updateTime(boolean outputFps) {
        long currentTime = getTime();
        deltaTime = (currentTime - lastFrameTime) / (float) SECOND;

        frameCounter += deltaTime * SECOND;
        if (frameCounter >= Time.SECOND) {
            frameCount = frames;

            if (outputFps) {
                System.out.println(frameCount);
            }

            frames = 0;
            frameCounter = 0;
        } else {
            frames++;
        }

        lastFrameTime = currentTime;
    }

}
