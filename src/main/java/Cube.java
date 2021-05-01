import glm_.mat3x4.Mat3x4;
import glm_.mat4x3.Mat4x3;
import glm_.mat4x4.Mat4;
import glm_.vec3.Vec3;
import glm_.vec3.Vec3i;
import org.apache.commons.lang3.ArrayUtils;

import static glm_.glm.*;

import java.util.*;

class Cube {

    private final Vec3i position;
    HashMap<edges, Float[]> vertices = new HashMap<>();

    public Cube() {
        this.position = new Vec3i(0,0,0);
        genVertices();
    }

    public enum edges{
        RIGHT,
        LEFT,
        TOP,
        BOTTOM,
        FRONT,
        BACK
    }

    Cube(Vec3i pos){
        this.position = pos == null ? new Vec3i(0,0,0) : pos;
        genVertices();
    }

    private void genVertices() {
        int X = position.getX();
        int Y = -position.getY();
        int Z = position.getZ();
        float size = 1f;
        this.vertices.put(edges.BACK, new Float[]{//back
                0.0f + X, 0.0f + Y, 0.0f + Z, 0.0f, 0.0f,
                size + X, 0.0f + Y, 0.0f + Z, 1.0f, 0.0f,
                size + X, size + Y, 0.0f + Z, 1.0f, 1.0f,
                0.0f + X, size + Y, 0.0f + Z, 0.0f, 1.0f});
        this.vertices.put(edges.FRONT, new Float[]{//front
                0.0f + X, 0.0f + Y, size + Z, 0.0f, 0.0f,
                size + X, 0.0f + Y, size + Z, 1.0f, 0.0f,
                size + X, size + Y, size + Z, 1.0f, 1.0f,
                0.0f + X, size + Y, size + Z, 0.0f, 1.0f});
        this.vertices.put(edges.RIGHT,new Float[]{//RIGHT
                0.0f + X, size + Y, size + Z, 0.0f, 0.0f,
                0.0f + X, size + Y, 0.0f + Z, 1.0f, 0.0f,
                0.0f + X, 0.0f + Y, 0.0f + Z, 1.0f, 1.0f,
                0.0f + X, 0.0f + Y, size + Z, 0.0f, 1.0f});
        this.vertices.put(edges.LEFT, new Float[]{//left
                size + X, size + Y, size + Z, 0.0f, 0.0f,
                size + X, size + Y, 0.0f + Z, 1.0f, 0.0f,
                size + X, 0.0f + Y, 0.0f + Z, 1.0f, 1.0f,
                size + X, 0.0f + Y, size + Z, 0.0f, 1.0f});
        this.vertices.put(edges.TOP, new Float[]{//top
                0.0f + X, 0.0f + Y, 0.0f + Z, 0.0f, 0.0f,
                size + X, 0.0f + Y, 0.0f + Z, 1.0f, 0.0f,
                size + X, 0.0f + Y, size + Z, 1.0f, 1.0f,
                0.0f + X, 0.0f + Y, size + Z, 0.0f, 1.0f});
        this.vertices.put(edges.BOTTOM, new Float[]{//bottom
                0.0f + X, size + Y, 0.0f + Z, 0.0f, 0.0f,
                size + X, size + Y, 0.0f + Z, 1.0f, 0.0f,
                size + X, size + Y, size + Z, 1.0f, 1.0f,
                0.0f + X, size + Y, size + Z, 0.0f, 1.0f});

    }

    void move(Vec3i pos){
        this.position.plusAssign(pos);
        genVertices();
    }


    public void hideEdge(edges edge){
        int size = this.vertices.size();
        switch (edge){
            case BACK -> {
                if(vertices.containsKey(edges.BACK)) {
                    this.vertices.remove(edges.BACK);
                }
            }
            case FRONT -> {
                if(vertices.containsKey(edges.FRONT)) {
                    this.vertices.remove(edges.FRONT);
                }
            }
            case RIGHT -> {
                if(vertices.containsKey(edges.RIGHT)) {
                    this.vertices.remove(edges.RIGHT);
                }
            }
            case LEFT -> {
                if(vertices.containsKey(edges.LEFT)) {
                    this.vertices.remove(edges.LEFT);
                }
            }
            case TOP -> {
                if(vertices.containsKey(edges.TOP)) {
                    this.vertices.remove(edges.TOP);
                }
            }
            case BOTTOM -> {
                if(vertices.containsKey(edges.BOTTOM)) {
                    this.vertices.remove(edges.BOTTOM);
                }
            }
        }
    }

    public Vec3 getPositionAsVec3(){
        return new Vec3(position.getX(),position.getY(),position.getZ());
    }

    public Vec3i getPosition() {
        return position;
    }

    public ArrayList<Float> getVertices(){
        ArrayList<Float> v = new ArrayList<>();
        for(Map.Entry<edges, Float[]> i : vertices.entrySet()){
            v.addAll(Arrays.asList(i.getValue()));
        }
        return v;
    }
}
