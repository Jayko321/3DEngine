import glm_.vec2.Vec2i;
import glm_.vec3.Vec3i;
import org.lwjgl.opengl.GL;
import org.lwjgl.stb.STBImage;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Renderer {
    private List<String> texturePaths;
    private final int VBO,IBO,VAO;
    private final HashMap<Vec2i, Chunk> Chunks = new HashMap<>();
    private int texture;
    private final int chunkPosLoc;

    public Renderer(Shader shader) {
        GL.createCapabilities();
        this.VAO = glGenVertexArrays();
        this.VBO = glGenBuffers();
        this.IBO = glGenBuffers();
        this.chunkPosLoc = glGetUniformLocation(shader.Program, "chunkPos");
        setTexture();
    }

    public void addChunks(Chunk... chunks) {
        for (Chunk chunk : chunks) {
            Chunks.putIfAbsent(chunk.getChunkPos(), chunk);
//            prepareRendering(chunk.getVertices(), chunk.getIndices(), chunk.getChunkPos().getArray());
        }

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
        glVertexAttribIPointer(0, 1, GL_UNSIGNED_INT, SIZEOF_FLOAT, NULL);

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

    public void replaceChunk(Vec2i oldChunkPos, Chunk newChunk){
        if(Chunks.containsValue(newChunk)){return;}
        replaceDataInVertexBuffer(newChunk.getVertices());
        Chunks.remove(oldChunkPos);
        Chunks.put(newChunk.getChunkPos(), newChunk);
    }


    public void draw(){
        for(Map.Entry<Vec2i, Chunk> chunk: Chunks.entrySet()){
            int a = 0;
            Chunk Chunk = chunk.getValue();
            //draw every chunk
            for (int v : Chunk.getVertices()){
                if(v >> 26 > 1){a++;}
            }
            if(a > 0){continue;}
            prepareRendering(Chunk.getVertices(), Chunk.getIndices(), Chunk.getChunkPos().getArray());
            draw(Chunk.getIndicesSize());
        }
    }

    private void draw(int indicesSize){
        glBindTexture(GL_TEXTURE_2D, texture);
        glBindVertexArray(VAO);
        glDrawElements(GL_TRIANGLES, indicesSize, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }


}
