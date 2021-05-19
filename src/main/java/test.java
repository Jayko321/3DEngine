import Renderer.Texture;

import java.io.IOException;

public class test extends VOEngine{
    public test() throws IOException {
    }

    @Override
    public void whileLoop(){
        Renderer.drawCube(0,0,0);
        Renderer.drawCube(1, 1, 1);
        Renderer.drawCube(17,0,17);
        Renderer.drawCube(18, 0, 18);
        Renderer.drawCube(1,0,1);
        Renderer.submitDrawCalls();
    }

    public static void main(String[] args) throws IOException {
        new test().start();
    }

}
