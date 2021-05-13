package Objects;

public class CubeDistributor {
    public static void assignToChunk(Cube cube, ChunkBuffer buffer){
        int x,z;
        Chunk chunk;
        x = cube.getPosition().getX() - cube.getPosition().getX() % 16;
        z = cube.getPosition().getZ() - cube.getPosition().getZ() % 16;
        chunk = buffer.containsChunk(x, z) ? buffer.getChunk(x, z) : new Chunk(x, z);
        chunk.addCube(cube);
        buffer.putChunk(chunk);

    }
}
