import glm_.vec3.Vec3i;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chunk {
    private final HashMap<Vec3i, Cube> Cubes = new HashMap<>(16);
    private final List<Float> vertices = new ArrayList<>(16*120);
    private int[] indices = new int[]{};
    private final Vec3i ChunkPos;

    public Chunk(Vec3i ChunkPos) {
        this.ChunkPos = ChunkPos;
    }

    public Vec3i getChunkPos() {
        return ChunkPos;
    }


    public Cube getCube(Vec3i position){
        return Cubes.get(position);
    }

    public void addCube(Vec3i pos) {
        Cubes.putIfAbsent(pos, new Cube(pos));
    }

    void genData(){
        for (Map.Entry<Vec3i, Cube> HashedCubes : Cubes.entrySet()) {
            Cube Cube = HashedCubes.getValue();
            if (Cube == null){
                break;
            }
            vertices.addAll(Cube.getVertices());
        }
        indices = Util.genIndices(vertices);
    }

    public int[] getIndices() {
        return indices;
    }
    public int getIndicesSize() {
        return indices.length;
    }

    public float[] getVertices() {
        return ArrayUtils.toPrimitive(vertices.toArray(new Float[0]));
    }

    //draw func
}
