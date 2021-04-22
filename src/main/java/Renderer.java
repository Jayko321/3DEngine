import glm_.vec3.Vec3i;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.opengl.GL;
import org.lwjgl.stb.STBImage;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Renderer {
    private List<String> texturePaths;
    private final int VBO,IBO,VAO;
    private HashMap<Vec3i, Chunk> Chunks;
    int texture;

    public Renderer() {
        this.VBO = glGenBuffers();
        this.IBO = glGenBuffers();
        this.VAO = glGenVertexArrays();

        GL.createCapabilities();
    }

    public void addChunks(Chunk... chunks) {
        for (Chunk ch : chunks) {
            Chunks.put(ch.getChunkPos(), ch);
        }
    }

    private void prepareRendering(Float[] vertices) {
        glBindVertexArray(VAO);

        glBindBuffer(GL_ARRAY_BUFFER, VBO);
        glBufferData(GL_ARRAY_BUFFER, ArrayUtils.toPrimitive(vertices), GL_DYNAMIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, IBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.genIndices(Arrays.asList(vertices)), GL_STATIC_DRAW);



        int SIZEOF_FLOAT = 4;
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 5 * SIZEOF_FLOAT, 0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(2, 2, GL_FLOAT, false, 5 * SIZEOF_FLOAT, 12);
        glEnableVertexAttribArray(2);
        glBindVertexArray(0);

    }

    public void draw(){
        for(Map.Entry<Vec3i, Chunk> chunk: Chunks.entrySet()){
            //draw every chunk
            draw(32);
            draw(12);
        }
    }

    private void draw(int indicesSize){
        glBindTexture(GL_TEXTURE_2D, texture);
        glBindVertexArray(VAO);
        glDrawElements(GL_TRIANGLES, indicesSize, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }


}
