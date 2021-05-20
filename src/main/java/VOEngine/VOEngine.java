package VOEngine;/*
* TODO:
*  1)Renderer functions (cleanup,drawCube,submitDrawCalls)
*  2)Window class
*  3)Texture class
*  4)TextureAtlas Class*/



import Callbacks.KeyCallback;
import Callbacks.MouseCallback;
import VOEngine.Camera.*;
import Objects.Window;
import Renderer.*;
import org.lwjgl.opengl.GL46;

import static Callbacks.KeyCallback.getKeys;
import static Callbacks.MouseCallback.getPitch;
import static Callbacks.MouseCallback.getYaw;
import static Objects.Window.*;
import static Renderer.Renderer.unbindBuffers;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class VOEngine {
    public static Window Window;
    public static Shader Shader;
    public static Camera Camera;
    public static Renderer Renderer;
    private float lastFrame = 0, deltaTime = 0;

    public VOEngine() {
        Window = new Window(1920, 1080);
        Shader = new Shader("D:\\3DEngine\\src\\main\\java\\Renderer\\Shaders\\vertexShader.glsl",
                "D:\\3DEngine\\src\\main\\java\\Renderer\\Shaders\\fragmentShader.glsl");
        Camera = new Camera();
        Renderer  = new Renderer();
        init();
    }


    public void init(){
        Window.setInputMode(GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        Window.setCursorPosCallback(new MouseCallback());
        Window.setKeyCallback(new KeyCallback());
        Renderer.setViewport(Window.getWindowWidth(), Window.getWindowHeight());
        glEnable(GL_DEPTH_TEST);
        GL46.glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
        glfwSwapInterval(0);
    }



    public void whileLoop(){

    }

    public void start(){
        while (!glfwWindowShouldClose(getWindowPointer())) {
            float curFrame = (float) glfwGetTime();
            deltaTime = curFrame - lastFrame;
            lastFrame = curFrame;
            System.out.println("Fps is " + (1 / deltaTime) + "\nFrame Time is " + (deltaTime*1000));
            glfwPollEvents();

            //начало отрисовки
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);


            Shader.Use();
            Camera.Use();
            Camera.do_movement(getKeys(), deltaTime);
            Camera.mouse_movement(getPitch(), getYaw());
            whileLoop();
            glfwSwapBuffers(getWindowPointer());
        }
        cleanup();
    }



    public void cleanup() {
        unbindBuffers();
        glfwDestroyWindow(getWindowPointer());
        glfwMakeContextCurrent(NULL);
        glfwTerminate();
    }

    public static Window getWindow() {
        return Window;
    }

    public static Camera getCamera() {
        return Camera;
    }

    public static Renderer getRenderer() {
        return Renderer;
    }

    public static Shader getShader(){
        return Shader;
    }



    public static void main(String[] args) {
        new VOEngine().start();

    }
}