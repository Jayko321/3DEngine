// TODO: 09.03.2021 Funcs: draw, move, setTexture,

import glm_.vec3.Vec3;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.opengl.GL;
import org.lwjgl.stb.STBImage;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL45.glCreateBuffers;

public class Cube {



    public static class data{
        float size = 1f;
        Vec3 position = new Vec3(0,0,0);

        data(Vec3 pos){

            this.position = Objects.requireNonNullElseGet(pos, () -> new Vec3(1, 1, 1));
            this.vertices = generateVertices(this.position);
            this.generateIndices();


        }
        data(){
            this.vertices = generateVertices(this.position);
            this.generateIndices();
        }








        public int[] indices = new int[]{
                0,1,2,
                2,3,0
        };

        public float[] vertices;





        float[] generateVertices(Vec3 pos){

            float X = -pos.getX();
            float Y = -pos.getY();
            float Z = pos.getZ();
            return ArrayUtils.addAll(this.vertices,
                    0.0f + X, 0.0f + Y, 0.0f + Z, 0.0f, 0.0f,
                    size + X, 0.0f + Y, 0.0f + Z, 1.0f, 0.0f,
                    size + X, size + Y, 0.0f + Z, 1.0f, 1.0f,
//                size + X, size + Y, 0.0f + Z,  1.0f, 1.0f,
                    0.0f + X, size + Y, 0.0f + Z, 0.0f, 1.0f,
//                0.0f + X, 0.0f + Y, 0.0f + Z,  0.0f, 0.0f,
//
                    0.0f + X, 0.0f + Y, size + Z, 0.0f, 0.0f,
                    size + X, 0.0f + Y, size + Z, 1.0f, 0.0f,
                    size + X, size + Y, size + Z, 1.0f, 1.0f,
//                size + X, size + Y, size + Z,  1.0f, 1.0f,
                    0.0f + X, size + Y, size + Z, 0.0f, 1.0f,
//                0.0f + X, 0.0f + Y, size + Z,  0.0f, 0.0f,
//
                    0.0f + X, size + Y, size + Z, 0.0f, 0.0f,
                    0.0f + X, size + Y, 0.0f + Z, 1.0f, 0.0f,
                    0.0f + X, 0.0f + Y, 0.0f + Z, 1.0f, 1.0f,
//                0.0f + X, 0.0f + Y, 0.0f + Z,  0.0f, 1.0f,
                    0.0f + X, 0.0f + Y, size + Z, 0.0f, 1.0f,
//                0.0f + X, size + Y, size + Z,  1.0f, 0.0f,
//
                    size + X, size + Y, size + Z, 0.0f, 0.0f,
                    size + X, size + Y, 0.0f + Z, 1.0f, 0.0f,
                    size + X, 0.0f + Y, 0.0f + Z, 1.0f, 1.0f,
//                size + X, 0.0f + Y, 0.0f + Z,  0.0f, 1.0f,
                    size + X, 0.0f + Y, size + Z, 0.0f, 1.0f,
//                size + X, size + Y, size + Z,  1.0f, 0.0f,
//
                    0.0f + X, 0.0f + Y, 0.0f + Z, 0.0f, 0.0f,
                    size + X, 0.0f + Y, 0.0f + Z, 1.0f, 0.0f,
                    size + X, 0.0f + Y, size + Z, 1.0f, 1.0f,
//                size + X, 0.0f + Y, size + Z,  1.0f, 0.0f,
                    0.0f + X, 0.0f + Y, size + Z, 0.0f, 1.0f,
//                0.0f + X, 0.0f + Y, 0.0f + Z,  0.0f, 1.0f,
//
                    0.0f + X, size + Y, 0.0f + Z, 0.0f, 0.0f,
                    size + X, size + Y, 0.0f + Z, 1.0f, 0.0f,
                    size + X, size + Y, size + Z, 1.0f, 1.0f,
//                size + X, size + Y, size + Z,  1.0f, 0.0f,
                    0.0f + X, size + Y, size + Z, 0.0f, 1.0f
//                0.0f + X, size + Y, 0.0f + Z,  0.0f, 1.0f
            );
        }

        void generateIndices(){
            for (int i = 0; i+4 < this.vertices.length/5; i+=4) {
                int last = this.indices[this.indices.length-1] + 4;
                this.indices = ArrayUtils.addAll(this.indices, last, last + 1 , last + 2, last+2 , last+3, last);
            }
        }


    }

    private int VAO,VBO,IBO;
    private int texture;
    private data Data;
    private final Shader Shader;

    public Cube(String texturePath, Shader shader){
        Shader = shader;
        GL.createCapabilities();
        this.Data = new data();
//        this.addCube(new Vec3(1,1,0));

        setTexture(texturePath);

    }

    void addCube(Vec3 Pos){
        float[] arr = Data.generateVertices(Pos);

        Data.vertices = ArrayUtils.addAll(Data.vertices, arr);
        Data.generateIndices();
    }

    void addCube(Vec3[] Pos){
        for(Vec3 pos : Pos){
            float[] arr = new data(pos).vertices;
            System.out.println("ayee");

            Data.vertices = ArrayUtils.addAll(Data.vertices, arr);
            Data.generateIndices();
        }

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
        VBO = glGenBuffers();

//        System.out.println(Arrays.toString(this.Data.indices));
//        Data.addCube(new Vec3(0, 2, 0));
        VAO = glGenVertexArrays();
        IBO = glGenBuffers();

        glBindVertexArray(VAO);

        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, this.Data.vertices, GL_DYNAMIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, IBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, this.Data.indices, GL_STATIC_DRAW);

        int SIZEOF_FLOAT = 4;
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * SIZEOF_FLOAT, 0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(2, 2, GL_FLOAT, false, 5 * SIZEOF_FLOAT, 12);
        glEnableVertexAttribArray(2);
        glBindVertexArray(0);
    }

//    public void draw(@Nullable Vec3 Pos){
//        Mat4 model = new Mat4();
//        if (Pos == null) {
//            Pos = new Vec3();
//        }
//        INSTANCE.translate(model, Pos);
//
//        int modelLoc = glGetUniformLocation(Shader.Program, "model");
//        glUniformMatrix4fv(modelLoc, false, model.getArray());
//
//        glBindTexture(GL_TEXTURE_2D, texture);
//        glBindVertexArray(VAO);
//        glDrawArrays(GL_TRIANGLES, 0, 36);
//        glBindVertexArray(0);
//    }

    public void draw(){
        prepareShape();
        glBindTexture(GL_TEXTURE_2D, texture);
        glBindVertexArray(VAO);
        int modelLoc = glGetUniformLocation(Shader.Program, "model");

//        for(Vec3 Pos: Positions){
//            Mat4 model;
//            model = INSTANCE.translate(new Mat4(), Pos);
//            glUniformMatrix4fv(modelLoc, false, model.getArray());
//            glDrawArrays(GL_TRIANGLES, 0, 72);
//        }
        glDrawElements(GL_TRIANGLES, this.Data.indices.length, GL_UNSIGNED_INT, 0);
//        glDrawArrays(GL_TRIANGLES, 0, 72);


        glBindVertexArray(0);
    }

}
