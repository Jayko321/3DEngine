package Renderer;

import Objects.Chunk;
import Objects.ChunkBuffer;
import Objects.Cube;
import Objects.CubeDistributor;
import glm_.vec3.Vec3i;
import org.lwjgl.opengl.GL;
import org.lwjgl.stb.STBImage;

import java.nio.ByteBuffer;

import static Renderer.TextureAtlas.getTexture;
import static Util.Util.SIZEOF_FLOAT;
import static VOEngine.VOEngine.getShader;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Renderer {
    private static int VBO,IBO,VAO;

    private final int chunkPosLoc;
    private final ChunkBuffer chunkBuffer = new ChunkBuffer();

    public Renderer() {
        GL.createCapabilities();
        VAO = glGenVertexArrays();
        VBO = glGenBuffers();
        IBO = glGenBuffers();
        this.chunkPosLoc = glGetUniformLocation(getShader().Program, "chunkPos");

        preAlloc();
    }

    private void preAlloc(){
        glBindVertexArray(VAO);
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, IBO);

        glBufferData(GL_ARRAY_BUFFER, 16L*16*256*SIZEOF_FLOAT, GL_DYNAMIC_DRAW);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, 16L*16*256*SIZEOF_FLOAT / 5, GL_DYNAMIC_DRAW);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(0, 3, GL_FLOAT,false,5 * SIZEOF_FLOAT, 0);
        glVertexAttribPointer(1, 2, GL_FLOAT,false,5 * SIZEOF_FLOAT, 12);
        glBindVertexArray(0);
    }


    private void prepareRendering(float[] vertices, int[] indices, int[] chunkPos) {
        glBindVertexArray(VAO);




        glUniform2iv(chunkPosLoc, chunkPos);
        glBindVertexArray(0);
    }


    private void replaceDataInVertexBuffer(float[] vertices, int[] indices,int[] chunkPos){
        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, IBO);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER , 0, indices);
        glUniform2iv(chunkPosLoc, chunkPos);
    }



    public void drawCube(Vec3i pos, Texture texture){
        Cube cube = new Cube(pos, texture);
        CubeDistributor.assignToChunk(cube, chunkBuffer);
    }


    public void drawCube(int x,int y,int z, Texture texture){
        Cube cube = new Cube(x,y,z,texture);
        CubeDistributor.assignToChunk(cube, chunkBuffer);
    }

    public void setViewport(int width, int height){
        glViewport(0,0, width, height);
    }

    public void submitDrawCalls(){
        Chunk[] chunkArray = chunkBuffer.getAllChunks();
        for (Chunk chunk : chunkArray){
            {
                float[] vertices = chunk.getVertices();
                int[] indices = chunk.getIndices();
                replaceDataInVertexBuffer(vertices,indices,chunk.getChunkPos());
                draw(chunk.getIndicesSize());
            }
        }
    }

    public static void unbindBuffers(){
        glDeleteBuffers(new int[]{VAO,VBO,IBO});
    }

    private void draw(int indicesSize){
        glBindTexture(GL_TEXTURE_2D, getTexture());
        glBindVertexArray(VAO);
        glDrawElements(GL_TRIANGLES, indicesSize, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }


}

