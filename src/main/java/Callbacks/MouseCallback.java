package Callbacks;

import org.lwjgl.glfw.GLFW;

import static Objects.Window.getWindowWidthByPointer;
import static Objects.Window.getWindowHeightByPointer;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;

import static Objects.Window.getWindowPointer;

public class MouseCallback implements GLFWCursorPosCallbackI {
    private static float yaw = -90f;
    private static float pitch = 0f;
    float lastX = 1920/2f;
    float lastY = 1080/2f;


    @Override
    public void invoke(long window, double xpos, double ypos) {
        float xOffset = (float) (xpos - lastX);
        float yOffset = (float) (lastY - ypos);
        lastX = (float)xpos;
        lastY = (float)ypos;
        float sensitivity = 0.006f;
        xOffset *= sensitivity;
        yOffset *= sensitivity;
        yaw += xOffset;
        pitch += yOffset;
        if(pitch > 1.5f)
            pitch =  1.5f;
        if(pitch < -1.5f)
            pitch = -1.5f;
    }


    public static float getYaw() {
        return yaw;
    }

    public static float getPitch() {
        return pitch;
    }
}
