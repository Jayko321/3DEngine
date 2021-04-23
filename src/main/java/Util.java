import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {







    boolean shouldChunkBeRendered(Chunk Chunk){
        return true;
    }



    static int[] genIndices(float[] vertices){
        ArrayList<Integer> indices = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 2, 3, 0));
        for (int i = 0; i+4 <= vertices.length/5; i+=4) {
            int last = indices.get(indices.size() - 1) + 4;
            indices.addAll(Arrays.asList(last, last + 1 , last + 2, last+2 , last+3, last));
        }
        return ArrayUtils.toPrimitive(indices.toArray(new Integer[0]));
    }

    static int[] genIndices(List<Float> vertices){
        ArrayList<Integer> indices = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 2, 3, 0));
        for (int i = 0; i+4 <= vertices.size()/5; i+=4) {
            int last = indices.get(indices.size() - 1) + 4;
            indices.addAll(Arrays.asList(last, last + 1 , last + 2, last+2 , last+3, last));
        }
        return ArrayUtils.toPrimitive(indices.toArray(new Integer[0]));
    }
}
