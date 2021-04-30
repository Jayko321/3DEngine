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
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

public class Renderer {
    private List<String> texturePaths;
    private final int VBO,IBO,VAO;
    private final HashMap<Vec3i, Chunk> Chunks = new HashMap<>();
    private int texture;

    public Renderer() {
        GL.createCapabilities();
        this.VAO = glGenVertexArrays();
        this.VBO = glGenBuffers();
        this.IBO = glGenBuffers();

        setTexture();
    }

    public void addChunks(Chunk... chunks) {
        for (Chunk ch : chunks) {
            Chunks.putIfAbsent(ch.getChunkPos(), ch);
            prepareRendering(ch.getVertices());
            System.out.println(ch.getCube(new Vec3i(0,0,0)).getVertices());
        }

    }



    private void preAllocMem(){
        glBindVertexArray(VAO);

        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, new int[0], GL_DYNAMIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, IBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, new int[0], GL_STATIC_DRAW);
    }

    private void prepareRendering(float[] vertices) {
        preAllocMem();


        int SIZEOF_FLOAT = 4;
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * SIZEOF_FLOAT, 0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(2, 2, GL_FLOAT, false, 5 * SIZEOF_FLOAT, 12);
        glEnableVertexAttribArray(2);
        glBindVertexArray(0);
    }


    private void replaceDataInVertexBuffer(float[] vertices){
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

    public void replaceChunk(Vec3i oldChunkPos, Chunk newChunk){
        if(Chunks.containsValue(newChunk)){return;}
        replaceDataInVertexBuffer(newChunk.getVertices());
        Chunks.remove(oldChunkPos);
        Chunks.put(newChunk.getChunkPos(), newChunk);
    }


    public void draw(){
        for(Map.Entry<Vec3i, Chunk> chunk: Chunks.entrySet()){
            Chunk Chunk = chunk.getValue();
            //draw every chunk
            prepareRendering(Chunk.getVertices());
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
