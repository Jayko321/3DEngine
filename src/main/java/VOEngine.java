/*
* TODO:
*  1)Renderer functions (cleanup,drawCube,submitDrawCalls)
*  2)Window class
*  3)Texture class
*  4)TextureAtlas Class*/



import Callbacks.KeyCallback;
import Callbacks.MouseCallback;
import Camera.*;
import Objects.Window;
import Renderer.*;
import org.lwjgl.opengl.GL46;

import java.io.IOException;

import static Callbacks.KeyCallback.getKeys;
import static Callbacks.MouseCallback.getPitch;
import static Callbacks.MouseCallback.getYaw;
import static Objects.Window.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class VOEngine {
    public Window Window = new Window(1920,1080);
    public Shader Shader = new Shader("D:\\3DEngine\\src\\main\\java\\Renderer\\Shaders\\vertexShader.glsl",
            "D:\\3DEngine\\src\\main\\java\\Renderer\\Shaders\\fragmentShader.glsl");
    public Camera Camera = new Camera(Shader, Window.getWindowWidth(), Window.getWindowHeight());
    public Renderer Renderer = new Renderer(Shader);
    private float lastFrame = 0, deltaTime = 0;

    public VOEngine() throws IOException {
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
        Renderer.unbindBuffers();
        glfwDestroyWindow(getWindowPointer());
        glfwMakeContextCurrent(NULL);
        glfwTerminate();
    }


    public static void main(String[] args) throws IOException {
        new VOEngine().start();

    }
}