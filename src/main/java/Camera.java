import glm_.glm;
import glm_.mat4x4.Mat4;
import glm_.vec3.Vec3;

import java.nio.IntBuffer;


import static glm_.glm.*;
import static java.lang.Math.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

public class Camera {

    public float X = 0f, Y = 0f, Z = -3f;
    private static Mat4 proj;
    public Vec3 cameraPos = new Vec3(32*16, 0 ,32*16);
//    private Vec3 cameraTarget = new Vec3(0f, 0, 0);
    private Vec3 cameraFront = new Vec3(0,0,-1);
    private final Vec3 cameraTarget = new Vec3(0,0,0);
    private final Vec3 cameraDirection = cameraPos.minus(cameraTarget);
    private final Vec3 up = new Vec3(0, 1, 0);
    private final Vec3 cameraRight = up.cross(cameraDirection);
    private final Vec3 cameraUp = cameraDirection.cross(cameraRight);
    private final int viewLoc;
    private final int projLoc;
    private final int viewProjLoc;
    Camera(long window, Shader sh, int width, int height){
        proj = INSTANCE.perspective(glm.INSTANCE.radians(-90f), (float) width/(float) height,0.1f, 100f);
        viewProjLoc = glGetUniformLocation(sh.Program, "viewProjection");
        viewLoc = glGetUniformLocation(sh.Program, "view");
        projLoc = glGetUniformLocation(sh.Program, "projection");
    }


    void do_movement(boolean[] keys, float delta){
        float cameraSpeed = -2f * delta;
        if (keys[GLFW_KEY_W]){
            cameraPos.minusAssign(new Vec3(0, cameraSpeed, 0).cross(cameraFront).cross(up));
        }
        if (keys[GLFW_KEY_S]){
            cameraPos.plusAssign(new Vec3(0, cameraSpeed, 0).cross(cameraFront).cross(up));
        }
        if (keys[GLFW_KEY_D]){
            cameraPos.minusAssign(new Vec3(0, cameraSpeed, 0).cross(cameraFront));
        }
        if (keys[GLFW_KEY_A]){
            cameraPos.plusAssign(new Vec3(0, cameraSpeed, 0).cross(cameraFront));
        }
        if (keys[GLFW_KEY_SPACE]){
            cameraPos.plusAssign(new Vec3(0, cameraSpeed, 0));
        }
        if (keys[GLFW_KEY_LEFT_SHIFT]){
            cameraPos.minusAssign(new Vec3(0, cameraSpeed, 0));
        }
    }

    void mouse_movement(float yaw, float pitch){
        float X = ((float) -cos(pitch) * (float) cos(yaw));
        float Y = ((float) -sin(yaw));
        float Z = ((float) cos(yaw) * (float) sin(pitch));



//        front.normalizeAssign();
        cameraFront = new Vec3(X, Y, Z).normalize();
//        cameraFront.normalizeAssign();
    }




    void Use(){
        Mat4 view = INSTANCE.lookAt(cameraPos,
                                    cameraPos.plus(cameraFront),
                                    up);
        glUniformMatrix4fv(viewProjLoc, false, proj.times(view).getArray());
    }



}
