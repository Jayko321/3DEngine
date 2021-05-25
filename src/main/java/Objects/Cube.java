package Objects;

import Renderer.Texture;
import glm_.vec2.Vec2;
import glm_.vec3.Vec3;
import glm_.vec3.Vec3i;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Cube {

    private final Vec3i position;
    private Vec2[] texCoords = new Vec2[4];
    private Texture texture;
    HashMap<edges, Float[]> vertices = new HashMap<>();
    HashMap<edges, Integer[]> vert = new HashMap<>();
    public enum edges{
        RIGHT,
        LEFT,
        TOP,
        BOTTOM,
        FRONT,
        BACK
    }

    public Cube() {
        this.position = new Vec3i(0,0,0);
        genVertices();
    }

//    Cube(Vec3i pos){
//        this.position = pos == null ? new Vec3i(0,0,0) : pos;
//        genVertices();
//    }
    public Cube(Vec3i pos, Texture texture){
        this.position = pos == null ? new Vec3i(0,0,0) : pos;
        this.texture = texture;
        setTexCoords(texture.getTexCoords());
        genVertices();
    }
    public Cube(int x, int y, int z, Texture texture){
        this.position = new Vec3i(x, y, z);
        this.texture = texture;
        setTexCoords(texture.getTexCoords());
        genVertices();
    }
//    public Cube(int x, int y, int z){
//        this.position = new Vec3i(x, y, z);
//        genVertices();
//    }




    public Texture getTexture() {
        return texture;
    }

    private void setTexCoords(Vec2[] texCoords){
        if (texCoords.length != 4){return;}
        this.texCoords = texCoords;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setTextureForEdge(edges edge,Texture texture){
        Float[] vertices = this.vertices.get(edge);
        vertices[4] = texture.getTexCoords()[0].getX();
        vertices[5] = texture.getTexCoords()[0].getY();
    }


    private void genVertices() {
        int X = position.getX();
        int Y = position.getY();
        int Z = position.getZ();
        float size = 1f;

        this.vertices.put(edges.BACK, new Float[]{//back
                0.0f + X, 0.0f + Y, 0.0f + Z, texCoords[3].getX(), texCoords[3].getY(),
                size + X, 0.0f + Y, 0.0f + Z, texCoords[0].getX(), texCoords[0].getY(),
                size + X, size + Y, 0.0f + Z, texCoords[1].getX(), texCoords[1].getY(),
                0.0f + X, size + Y, 0.0f + Z, texCoords[2].getX(), texCoords[2].getY()});
        this.vertices.put(edges.FRONT, new Float[]{//front
                0.0f + X, 0.0f + Y, size + Z, texCoords[3].getX(), texCoords[3].getY(),
                size + X, 0.0f + Y, size + Z, texCoords[0].getX(), texCoords[0].getY(),
                size + X, size + Y, size + Z, texCoords[1].getX(), texCoords[1].getY(),
                0.0f + X, size + Y, size + Z, texCoords[2].getX(), texCoords[2].getY()});
        this.vertices.put(edges.RIGHT, new Float[]{//RIGHT
                0.0f + X, size + Y, size + Z, texCoords[1].getX(), texCoords[1].getY(),
                0.0f + X, size + Y, 0.0f + Z, texCoords[2].getX(), texCoords[2].getY(),
                0.0f + X, 0.0f + Y, 0.0f + Z, texCoords[3].getX(), texCoords[3].getY(),
                0.0f + X, 0.0f + Y, size + Z, texCoords[0].getX(), texCoords[0].getY()});
        this.vertices.put(edges.LEFT, new Float[]{//left
                size + X, size + Y, size + Z, texCoords[1].getX(), texCoords[1].getY(),
                size + X, size + Y, 0.0f + Z, texCoords[2].getX(), texCoords[2].getY(),
                size + X, 0.0f + Y, 0.0f + Z, texCoords[3].getX(), texCoords[3].getY(),
                size + X, 0.0f + Y, size + Z, texCoords[0].getX(), texCoords[0].getY()});
        this.vertices.put(edges.TOP, new Float[]{//top
                0.0f + X, 0.0f + Y, 0.0f + Z, texCoords[0].getX(), texCoords[0].getY(),
                size + X, 0.0f + Y, 0.0f + Z, texCoords[1].getX(), texCoords[1].getY(),
                size + X, 0.0f + Y, size + Z, texCoords[2].getX(), texCoords[2].getY(),
                0.0f + X, 0.0f + Y, size + Z, texCoords[3].getX(), texCoords[3].getY()});
        this.vertices.put(edges.BOTTOM, new Float[]{//bottom
                0.0f + X, size + Y, 0.0f + Z, texCoords[0].getX(), texCoords[0].getY(),
                size + X, size + Y, 0.0f + Z, texCoords[1].getX(), texCoords[1].getY(),
                size + X, size + Y, size + Z, texCoords[2].getX(), texCoords[2].getY(),
                0.0f + X, size + Y, size + Z, texCoords[3].getX(), texCoords[3].getY()});

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
