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
        if(ChunkPos.getX() % 16 != 0){System.err.println("Wrong chunk position");}
        if(ChunkPos.getY() % 16 != 0){System.err.println("Wrong chunk position");}
        if(ChunkPos.getZ() % 16 != 0){System.err.println("Wrong chunk position");}
        this.ChunkPos = ChunkPos;
    }

    public Vec3i getChunkPos() {
        return ChunkPos;
    }

    public Cube getCube(Vec3i position){
        return Cubes.get(position);
    }

    public void addCube(Vec3i pos) {
        if (pos.getX() >= 16) {return;}
        if (pos.getY() >= 256){return;}
        if (pos.getZ() >= 16) {return;}
        Cubes.putIfAbsent(pos, new Cube(new Vec3i(
                pos.getX() + (ChunkPos.getX()),
                pos.getY() + 0,
                pos.getZ() + (ChunkPos.getZ())
        )));
    }

    void genData(){
        for (Map.Entry<Vec3i, Cube> HashedCubes : Cubes.entrySet()) {
            Cube Cube = HashedCubes.getValue();

            vertices.addAll(Cube.getVertices());
        }
        indices = Util.genIndices(vertices);
    }

    public int[] getIndices() {
        if(indices.length == 0){
            genData();
        }
        return indices;
    }
    public int getIndicesSize() {
        if(indices.length == 0){
            genData();
        }
        return indices.length;
    }

    public float[] getVertices() {
        if(vertices.size() == 0){
            genData();
        }
        return ArrayUtils.toPrimitive(vertices.toArray(new Float[0]));
    }

    //draw func
}
