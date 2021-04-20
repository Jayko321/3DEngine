// TODO: 09.03.2021 Funcs: draw, move, setTexture,

import glm_.mat4x4.Mat4;
import glm_.vec3.Vec3;
import glm_.vec3.Vec3i;
import glm_.vec4.Vec4;
import glm_.vec4.Vec4i;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.opengl.GL;
import org.lwjgl.stb.STBImage;

import java.nio.ByteBuffer;
import java.util.*;

import static glm_.glm.*;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL45.glCreateBuffers;

public class CubeBatch extends Cube{
    private int VAO,VBO,IBO;
    private final ArrayList<Float> verticesBatch = new ArrayList<Float>();
    private final ArrayList<Integer> indicesBatch = new ArrayList<Integer>();
    private int texture;
    private final Map<Vec3i,Cube> Cubes = new HashMap<>();


    public CubeBatch(String texturePath, Shader shader){
        super();
        GL.createCapabilities();
        setTexture(texturePath);
    }

    List<Integer> genIndices(List<Float> vertices){
        ArrayList<Integer> indices = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 2, 3, 0));
        for (int i = 0; i+4 <= vertices.size()/5; i+=4) {
            int last = indices.get(indices.size() - 1) + 4;
            indices.addAll(Arrays.asList(last, last + 1 , last + 2, last+2 , last+3, last));
        }
        return indices;
    }

    void addCube(Vec3i Pos){
        Cubes.put(Pos ,new Cube(Pos));
    }

    void addCube(Vec3i... Pos){
        for(Vec3i pos : Pos){
            Cubes.put(pos, new Cube(pos));
        }

    }

    void func(){
        HideNotNeededEdges();
        genMeshes();
        prepareShape();
    }


    void moveCube(Cube Cube,Vec3i pos){
        if(Cubes.containsValue(Cube)){
            replaceDataInVertexArray(ArrayUtils.toPrimitive(Cube.getVertices().toArray(new Float[0])));
            Cubes.remove(Cube.getPosition());
            Cube.move(pos);

            Cubes.putIfAbsent(Cube.getPosition(), Cube);
        }
        else {
            System.out.println("Cube not found");
        }

    }




    void replaceDataInVertexArray(float[] vertices){
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
    }


//
//    void rotateCube(Cube Cube, float angle, Vec3 Axis){
//        if(Cubes.containsValue(Cube)){
//            Mat4 a = new Mat4();
//            a = INSTANCE.rotate(a, INSTANCE.radians(angle), Axis);
//            Vec4 b = a.times(new Vec4(Cube.getPositionAsVec3(), 1));
//            Cube.move(new Vec3i((int)(b.getX() + 0), (int)(b.getY() + 0),(int)(b.getZ() + 0)));
//        }
//    }

    Cube getCubeByPos(Vec3i Pos){
        return Cubes.get(Pos);
    }


    void HideNotNeededEdges(){
        for (Map.Entry<Vec3i, Cube> HashedCubes : Cubes.entrySet()) {
            Cube Cube = HashedCubes.getValue();
            if(Cubes.get(new Vec3i(Cube.getPosition().getX() - 1,Cube.getPosition().getY() + 0, Cube.getPosition().getZ() + 0)) != null){
                Cubes.get(new Vec3i(Cube.getPosition().getX() - 1,Cube.getPosition().getY() + 0, Cube.getPosition().getZ() + 0)).hideEdge(edges.LEFT);
                Cube.hideEdge(edges.RIGHT);
            }
            if(Cubes.get(new Vec3i(Cube.getPosition().getX() + 1,Cube.getPosition().getY() + 0, Cube.getPosition().getZ() + 0)) != null){
                Cubes.get(new Vec3i(Cube.getPosition().getX() + 1,Cube.getPosition().getY() + 0, Cube.getPosition().getZ() + 0)).hideEdge(edges.RIGHT);
                Cube.hideEdge(edges.LEFT);
            }
            if(Cubes.get(new Vec3i(Cube.getPosition().getX() + 0,Cube.getPosition().getY() + 1, Cube.getPosition().getZ() + 0)) != null){
                Cubes.get(new Vec3i(Cube.getPosition().getX() + 0,Cube.getPosition().getY() + 1, Cube.getPosition().getZ() + 0)).hideEdge(edges.BOTTOM);
                Cube.hideEdge(edges.TOP);
            }
            if(Cubes.get(new Vec3i(Cube.getPosition().getX() + 0,Cube.getPosition().getY() - 1, Cube.getPosition().getZ() + 0)) != null){
                Cubes.get(new Vec3i(Cube.getPosition().getX() + 0,Cube.getPosition().getY() - 1, Cube.getPosition().getZ() + 0)).hideEdge(edges.TOP);
                Cube.hideEdge(edges.BOTTOM);
            }

            if(Cubes.get(new Vec3i(Cube.getPosition().getX() + 0,Cube.getPosition().getY() + 0, Cube.getPosition().getZ() + 1)) != null){
                Cubes.get(new Vec3i(Cube.getPosition().getX() + 0,Cube.getPosition().getY() + 0, Cube.getPosition().getZ() + 1)).hideEdge(edges.BACK);
                Cube.hideEdge(edges.FRONT);
            }
            if(Cubes.get(new Vec3i(Cube.getPosition().getX() + 0,Cube.getPosition().getY() + 0, Cube.getPosition().getZ() - 1)) != null){
                Cubes.get(new Vec3i(Cube.getPosition().getX() + 0,Cube.getPosition().getY() + 0, Cube.getPosition().getZ() - 1)).hideEdge(edges.FRONT);
                Cube.hideEdge(edges.BACK);
            }
        }
    }


    void genMeshes(){
        for (Map.Entry<Vec3i, Cube> HashedCubes : Cubes.entrySet()) {
            Cube Cube = HashedCubes.getValue();
            if (Cube == null){
                break;
            }
            verticesBatch.addAll(Cube.getVertices());
        }
        indicesBatch.addAll(genIndices(verticesBatch));
    }

    private void setTexture(String texturePath){
        int[] width = new int[1], height = new int[1], channels = new int[1];
        ByteBuffer image = STBImage.stbi_load(texturePath, width, height, channels, 0);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width[0], height[0], 0, GL_RGB, GL_UNSIGNED_BYTE, image);
        glGenerateMipmap(GL_TEXTURE_2D);
        assert image != null;
        STBImage.stbi_image_free(image);
        glBindTexture(GL_TEXTURE_2D, 0);
    }


    private void prepareShape() {
        if (VBO == 0){
            VBO = glGenBuffers();
        }
        if (IBO == 0){
            IBO = glGenBuffers();
        }
        if (VAO == 0){
            VAO = glGenVertexArrays();
        }

        glBindVertexArray(VAO);

        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, ArrayUtils.toPrimitive(verticesBatch.toArray(new Float[0])), GL_DYNAMIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, IBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, ArrayUtils.toPrimitive(indicesBatch.toArray(new Integer[0])), GL_STATIC_DRAW);



        int SIZEOF_FLOAT = 4;
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * SIZEOF_FLOAT, 0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(2, 2, GL_FLOAT, false, 5 * SIZEOF_FLOAT, 12);
        glEnableVertexAttribArray(2);
        glBindVertexArray(0);

    }

    public void draw(){

        glBindTexture(GL_TEXTURE_2D, texture);
        glBindVertexArray(VAO);
        glDrawElements(GL_TRIANGLES, indicesBatch.size(), GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);

    }

}
