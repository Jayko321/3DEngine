import glm_.vec3.Vec3;
import glm_.vec3.Vec3i;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Cube {

    private final Vec3i position;
    HashMap<edges, Float[]> vertices = new HashMap<>();
    HashMap<edges, Integer[]> vert = new HashMap<>();

    public Cube() {
        this.position = new Vec3i(0,0,0);
        genVertices();
    }

    public enum edges{
        RIGHT,
        LEFT,
        TOP,
        BOTTOM,
        FRONT,
        BACK
    }

    Cube(Vec3i pos){
        this.position = pos == null ? new Vec3i(0,0,0) : pos;
        genVertices();
    }

    private void genVertices() {
        int X = position.getX();
        int Y = -position.getY();
        int Z = position.getZ();
        float size = 1f;
        int iSize = (int) size;

        {
            int a1;
            int a2;
            int a3;
            int a4;
            a1 = X | Y << 8 | Z << 16;
            a2 = iSize + X | Y << 8 | Z << 16 | 1 << 24;
            a3 = iSize + X | iSize + Y << 8 | Z << 16 | 1 << 24 | 1 << 25;
            a4 = X | iSize + Y << 8 | Z << 16 | 1 << 25;
            this.vert.put(edges.BACK, new Integer[]{a1, a2, a3, a4});
        }

//        for(Integer a : this.vert.get(edges.BACK)){
//            System.out.println("X = " + (a & 0xFF) + "\n" +
//                    "Y = " + ((a & 0xFF00) >> 8) + "\n" +
//                    "Z = " + ((a & 0xFF0000) >> 16 ) + "\n" +
//                    "texCoord1 = " + ((a & 0x1000000) >> 24) +
//                    "\ntexCoord2 = " + ((a & 0x2000000) >> 25)
//            );
//        }

        {
            int a1;
            int a2;
            int a3;
            int a4;
            a1 = X | Y << 8 | iSize + Z << 16;
            a2 = iSize + X | Y << 8 | iSize + Z << 16 | 1 << 24;
            a3 = iSize + X | iSize + Y << 8 | iSize + Z << 16 | 1 << 24 | 1 << 25;
            a4 = X | iSize + Y << 8 | iSize + Z << 16 | 1 << 25;
            this.vert.put(edges.FRONT, new Integer[]{a1, a2, a3, a4});
        }

        {
            int a1;
            int a2;
            int a3;
            int a4;
            a1 = X | iSize + Y << 8 | iSize + Z << 16;
            a2 = X | iSize + Y << 8 | Z << 16 | 1 << 24;
            a3 = X | Y << 8 | Z << 16 | 1 << 24 | 1 << 25;
            a4 = X | Y << 8 | iSize + Z << 16 | 1 << 25;
            this.vert.put(edges.RIGHT, new Integer[]{a1, a2, a3, a4});
        }

        {
            int a1;
            int a2;
            int a3;
            int a4;
            a1 = iSize + X | iSize + Y << 8 | iSize + Z << 16;
            a2 = iSize + X | iSize + Y << 8 | Z << 16 | 1 << 24;
            a3 = iSize + X | Y << 8 | Z << 16 | 1 << 24 | 1 << 25;
            a4 = iSize + X | Y << 8 | iSize + Z << 16 | 1 << 25;
            this.vert.put(edges.LEFT, new Integer[]{a1, a2, a3, a4});
        }

        {
            int a1;
            int a2;
            int a3;
            int a4;
            a1 = X | Y << 8 | Z << 16;
            a2 = iSize + X | Y << 8 | Z << 16 | 1 << 24;
            a3 = iSize + X | Y << 8 | iSize + Z << 16 | 1 << 24 | 1 << 25;
            a4 = X | Y << 8 | iSize + Z << 16 | 1 << 25;
            this.vert.put(edges.TOP, new Integer[]{a1, a2, a3, a4});
        }

        {
            int a1;
            int a2;
            int a3;
            int a4;
            a1 = X | iSize + Y << 8 | Z << 16;
            a2 = iSize + X | iSize + Y << 8 | Z << 16 | 1 << 24;
            a3 = iSize + X | iSize + Y << 8 | iSize + Z << 16 | 1 << 24 | 1 << 25;
            a4 = X | iSize + Y << 8 | iSize + Z << 16 | 1 << 25;
            this.vert.put(edges.BOTTOM, new Integer[]{a1, a2, a3, a4});
        }

    }

    void move(Vec3i pos){
        this.position.plusAssign(pos);
        genVertices();
    }


    public void hideEdge(edges edge){
        int size = this.vertices.size();
        switch (edge){
            case BACK -> {
                if(vertices.containsKey(edges.BACK)) {
                    this.vertices.remove(edges.BACK);
                }
            }
            case FRONT -> {
                if(vertices.containsKey(edges.FRONT)) {
                    this.vertices.remove(edges.FRONT);
                }
            }
            case RIGHT -> {
                if(vertices.containsKey(edges.RIGHT)) {
                    this.vertices.remove(edges.RIGHT);
                }
            }
            case LEFT -> {
                if(vertices.containsKey(edges.LEFT)) {
                    this.vertices.remove(edges.LEFT);
                }
            }
            case TOP -> {
                if(vertices.containsKey(edges.TOP)) {
                    this.vertices.remove(edges.TOP);
                }
            }
            case BOTTOM -> {
                if(vertices.containsKey(edges.BOTTOM)) {
                    this.vertices.remove(edges.BOTTOM);
                }
            }
        }
    }

    public Vec3 getPositionAsVec3(){
        return new Vec3(position.getX(),position.getY(),position.getZ());
    }

    public Vec3i getPosition() {
        return position;
    }

    public ArrayList<Float> getVert(){
        ArrayList<Float> v = new ArrayList<>();
        for(Map.Entry<edges, Float[]> i : vertices.entrySet()){
            v.addAll(Arrays.asList(i.getValue()));
        }
        return v;
    }

    public ArrayList<Integer> getVertices(){
        ArrayList<Integer> v = new ArrayList<>();
        for(Map.Entry<edges, Integer[]> i : vert.entrySet()){
            v.addAll(Arrays.asList(i.getValue()));
        }
        return v;
    }
}
