package Renderer;

import Objects.Chunk;
import Objects.ChunkBuffer;
import Objects.Cube;
import Objects.CubeDistributor;
import glm_.vec3.Vec3i;
import org.lwjgl.opengl.GL;
import org.lwjgl.stb.STBImage;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Renderer {
    private static int VBO,IBO,VAO;
    private int texture;
    private final int chunkPosLoc;
    private final ChunkBuffer chunkBuffer = new ChunkBuffer();

    public Renderer(Shader shader) {
        GL.createCapabilities();
        VAO = glGenVertexArrays();
        VBO = glGenBuffers();
        IBO = glGenBuffers();
        this.chunkPosLoc = glGetUniformLocation(shader.Program, "chunkPos");
        setTexture();
    }

    private void prepareRendering(int[] vertices, int[] indices, int[] chunkPos) {

        glUniform2iv(chunkPosLoc, chunkPos);
        glBindVertexArray(VAO);



        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_DYNAMIC_DRAW);


        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, IBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);


        int SIZEOF_FLOAT = 4;
        glEnableVertexAttribArray(0);
        glVertexAttribIPointer(0, 1, GL_INT, SIZEOF_FLOAT, NULL);

        glBindVertexArray(0);
    }


    private void replaceDataInVertexBuffer(int[] vertices){
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
    }

    private void setTexture(){
        String texturePath = "D:\\3DEngine\\src\\main\\resources\\container.jpg";
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


    public void drawCube(Vec3i pos, Texture texture){
        Cube cube = new Cube();
        CubeDistributor.assignToChunk(cube, chunkBuffer);
    }


    public void drawCube(int x,int y,int z, Texture texture){
        Cube cube = new Cube(x,y,z,texture);
        CubeDistributor.assignToChunk(cube, chunkBuffer);
    }

    public void submitDrawCalls(){
        Chunk[] chunkArray = chunkBuffer.getAllChunks();
        for (Chunk chunk : chunkArray){
            int[] vertices = chunk.getVertices();
            int[] indices = chunk.getIndices();
            prepareRendering(vertices,indices, chunk.getChunkPos().getArray());
            draw(indices.length);
        }
    }

    public static void unbindBuffers(){
        glDeleteBuffers(new int[]{VAO,VBO,IBO});
    }

    private void draw(int indicesSize){
        glBindTexture(GL_TEXTURE_2D, texture);
        glBindVertexArray(VAO);
        glDrawElements(GL_TRIANGLES, indicesSize, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }


}

