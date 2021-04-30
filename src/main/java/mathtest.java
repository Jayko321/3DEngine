import java.io.IOException;



public class mathtest {
    mathtest(){

    }

    void test(){
        float a =  (15 | 255 << 4 | 15 << 12 | 1 << 19);




        System.out.println("X = " + ((int)a & 0xF) + "\n" +
                           "Y = " + (((int)a & 0xFF0) >> 4) + "\n" +
                           "Z = " + (((int)a & 0xF000) >> 12 ) + "\n" +
                           "texCoord1 = " + (((int)a & 0xC0000) >> 19)
                            );
        System.out.println(Integer.toBinaryString((int)a));
    }

    public static void main(String[] args) throws IOException {
        new mathtest().test();
    }
}
