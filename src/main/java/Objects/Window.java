package Objects;

import Callbacks.MouseCallback;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWKeyCallbackI;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private static long window;
    private final int[] width = new int[1];
    private final int[] height = new int[1];
    private static final int[] wStatic = new int[1];
    private static final int[] hStatic = new int[1];

    public Window(){
        glfwInit();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);

        window = glfwCreateWindow(1, 1, "Engine", NULL, NULL);

        glfwMaximizeWindow(window);
        glfwGetWindowSize(window, width, height);

        glfwMakeContextCurrent(window);
    }
    public Window(int width, int height){
        glfwInit();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);

        window = glfwCreateWindow(width, height, "Engine", NULL, NULL);
        setWidth(width);
        setHeight(height);

        glfwMakeContextCurrent(window);
    }


    public void setWidth(int width){
        this.width[0] = width;
        wStatic[0] = width;
    }

    public void setHeight(int height){
        this.height[0] = height;
        hStatic[0] = height;
    }


    public int getWindowWidth(){
        return width[0];
    }

    public static int getWindowHeightByPointer(long pointer){
        if (window == pointer){
            return hStatic[0];
        }
        return 0;
    }

    public static int getWindowWidthByPointer(long pointer){
        if (window == pointer){
            return wStatic[0];
        }
        return 0;
    }

    public int getWindowHeight(){
        return height[0];
    }

    public void setInputMode(int mode, int value){
        glfwSetInputMode(window,mode,value);
    }

    public static long getWindowPointer(){
        return window;
    }

    public void setCursorPosCallback(GLFWCursorPosCallbackI callbackClass){
        glfwSetCursorPosCallback(window, callbackClass);
    }

    public void setKeyCallback(GLFWKeyCallbackI callbackClass){
        glfwSetKeyCallback(window, callbackClass);
    }
}
