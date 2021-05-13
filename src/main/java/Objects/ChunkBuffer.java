package Objects;

import glm_.vec2.Vec2i;

import java.util.HashMap;
import java.util.Map;

public class ChunkBuffer {
    private final HashMap<Vec2i, Chunk> chunkBuffer;
    public ChunkBuffer() {
        chunkBuffer = new HashMap<>();
    }

    public HashMap<Vec2i, Chunk> getChunkBuffer() {
        return chunkBuffer;
    }

    public int[][] getVertices(){
        int[][] vertices = new int[chunkBuffer.size()][];
        int a = 0;
        for (Map.Entry<Vec2i, Chunk> chunks: chunkBuffer.entrySet()){
            int[] vert = chunks.getValue().getVertices();
            vertices[a] = vert;
            a++;
        }
        return vertices;
    }

    public int[][] getIndices(){
        int[][] indices = new int[chunkBuffer.size()][];
        int a = 0;
        for (Map.Entry<Vec2i, Chunk> chunks: chunkBuffer.entrySet()) {
            int[] ind = chunks.getValue().getIndices();
            indices[a] = ind;
            a++;
        }

        return indices;
    }


    public Chunk getChunk(int x,int z){
        return chunkBuffer.get(new Vec2i(x, z));
    }

    public boolean containsChunk(Chunk chunk){
        return chunkBuffer.containsValue(chunk);
    }

    public boolean containsChunk(Vec2i pos){
        return chunkBuffer.containsKey(pos);
    }

    public boolean containsChunk(int x, int z){
        return chunkBuffer.containsKey(new Vec2i(x,z));
    }

    public void putChunk(Chunk chunk){
        chunkBuffer.put(chunk.getChunkPos(), chunk);
    }

    public void deleteChunk(Chunk chunk){
        chunkBuffer.remove(chunk.getChunkPos());
    }

}
