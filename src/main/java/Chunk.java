import glm_.vec3.Vec3i;

import java.util.HashMap;

public class Chunk {


    HashMap<Vec3i, Cube> Cubes = new HashMap<>(16);

    public Chunk() {

    }


    public Cube getCube(Vec3i position){
        return Cubes.get(position);
    }

    public void addCubes(Vec3i pos,Cube cube ) {
        Cubes.putIfAbsent(pos, cube);
    }

    //draw func
}
