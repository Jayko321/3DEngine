import glm_.mat4x4.Mat4;
import glm_.vec3.Vec3;
import glm_.vec3.Vec3i;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL46;


import java.io.IOException;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class test {
    boolean firstMouse = true;
    float lastX = w/2f, lastY = h/2f;
    float yaw = -90f,pitch = 0f;
    private boolean[] keys = new boolean[1024];
    private float lastFrame = 0, deltaTime = 0;
    private static long window;
    public static final int w = 1920, h = 1080;
    private static int VBO, VAO, IBO, texture;

    public void start() throws IOException {
        if (!glfwInit()) {
            throw new RuntimeException("sucks");
        }

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);


        window = glfwCreateWindow(w, h, "Engine", NULL, NULL);
        if (window == NULL) {
            cleanup();
            throw new RuntimeException("sucks");
        }
//        glfwFocusWindow(window);

        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        glEnable(GL_DEBUG_OUTPUT_SYNCHRONOUS);
        Shader shader = new Shader("D:\\3DEngine\\src\\main\\java\\vertexShader.glsl",
                "D:\\3DEngine\\src\\main\\java\\fragmentShader.glsl");
        Camera camera = new Camera(window, shader, w, h);
//        error_catching();
        key_callback(camera);
        mouse_callback();

        CubeBatch cubes = new CubeBatch("D:\\3DEngine\\src\\main\\resources\\container.jpg", shader);
        CubeBatch cube1 = new CubeBatch("D:\\3DEngine\\src\\main\\resources\\container.jpg", shader);

        glViewport(0, 0, w, h);

        Vec3i[][] tmp = new Vec3i[16][16];
        Vec3i[][] tmp1 = new Vec3i[16][16];

        for (int z = 0; z < 16; z++) {
            for (int x = 0; x < 16; x++) {

                tmp[z][x] = new Vec3i(x,0,z);
                tmp1[z][x] = new Vec3i(x,1,z);
            }
        }

        for(Vec3i[] z: tmp) {
            cubes.addCube(z);

            System.out.println("aye");
        }
        for(Vec3i[] z: tmp1) {
            cubes.addCube(z);

            System.out.println("aye1");
        }

//        cubes.addCube(new Vec3i(1,1,1),new Vec3i(2,1,1), new Vec3i(1,1,0), new Vec3i(1,1,2));
//        cubes.addCube(new Vec3i(1,2,1),new Vec3i(2,2,1), new Vec3i(1,2,0), new Vec3i(1,2,2));

//        cubes.addCube(new Vec3i(0,0,-2),new Vec3i(1,0,-2),new Vec3i(0,1,-2),new Vec3i(0,0,-1),new Vec3i(0,0,-3),
//                new Vec3i(-1,0,-2), new Vec3i(0,-1,-2));
        glEnable(GL_DEPTH_TEST);
        GL46.glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
        glfwSwapInterval(0);
        cubes.func();
        int a = 0;
        while (!glfwWindowShouldClose(window)) {
            float curFrame = (float) glfwGetTime();

            deltaTime = curFrame - lastFrame;
            lastFrame = curFrame;
            System.out.println(1 / deltaTime);
            glfwPollEvents();

            //начало отрисовки
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

//            System.out.println(glGetError());
            cubes.draw();

//            if (a == 2){
//                System.exit(0);
//            }
            a++;
            shader.Use();
            camera.Use();
            camera.do_movement(keys, deltaTime);
            camera.mouse_movement(pitch, yaw);
            Mat4 value = new Mat4();

            int location = glGetUniformLocation(shader.Program, "transform");
            glUniformMatrix4fv(location, false, value.getArray());

            glfwSwapBuffers(window);
        }
        cleanup();
    }

    void key_callback(Camera camera) {

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {

            if (action == GLFW_PRESS) {
                keys[key] = true;
            }
            else if(action == GLFW_RELEASE){
                keys[key] = false;
            }
            if (keys[GLFW_KEY_ESCAPE]) {
                glfwSetWindowShouldClose(window, true);
            }
            }
        );
    }

    void error_catching(){
        glDebugMessageCallback((source, type, id, severity, length, message, userParam)->{
            String text = String.format("\"GL CALLBACK: %s type = 0x%x, severity = 0x%x, message = %s\\n\"", ( type == GL_DEBUG_TYPE_ERROR ? "** GL ERROR **" : "" ),type,severity, message);
            System.out.println(text);
        },0);
    }


    void mouse_callback(){
        glfwSetCursorPos(window, lastX, lastY);
        glfwSetCursorPosCallback(window, (window, xpos, ypos) -> {

            float xoffset = (float) (xpos - lastX);
            float yoffset = (float) (lastY - ypos);
            lastX = (float)xpos;
            lastY = (float)ypos;
            float sensitivity = 0.006f;
            xoffset *= sensitivity;
            yoffset *= sensitivity;
            yaw   += xoffset;
            pitch += yoffset;
            if(pitch > 1.5f)
                pitch =  1.5f;
            if(pitch < -1.5f)
                pitch = -1.5f;

        });
    }


    public void cleanup() {
        glDeleteVertexArrays(VAO);
        glDeleteBuffers(VBO);
        glfwDestroyWindow(window);
        glfwMakeContextCurrent(NULL);
        glfwTerminate();
    }


    public static void main(String[] args) throws IOException {
        new test().start();
    }
}