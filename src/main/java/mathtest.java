import java.io.IOException;



public class mathtest {
    mathtest(){

    }

    void test(){
        float a =  (63 | 255 << 6 | 63 << 14 | 1 << 21);




        System.out.println("X = " + ((int)a & 0x3F) + "\n" +
                           "Y = " + (((int)a & 0x3FC0) >> 6) + "\n" +
                           "Z = " + ((((int)a & 0xFC000) >> 14 )) + "\n" +
                           "texCoord1 = " + (((int)a & 0x300000) >> 21)
                            );
        System.out.println(Integer.toBinaryString((int)a));
    }

    public static void main(String[] args) throws IOException {
        new mathtest().test();
    }
}
