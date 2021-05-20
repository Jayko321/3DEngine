import Renderer.Texture;
import Renderer.TextureAtlas;
import VOEngine.VOEngine;


public class test extends VOEngine {
    TextureAtlas atlas = new TextureAtlas("D:\\3DEngine\\src\\main\\resources\\1.jpg");
    public test() {
        super();

    }

    @Override
    public void whileLoop(){
        Renderer.drawCube(0,0,0, new Texture(256,2));
        Renderer.drawCube(1, 1, 1, new Texture(256,2));
        Renderer.drawCube(17,0,17, new Texture(256,2));
        Renderer.drawCube(18, 0, 18, new Texture(256,2));
        Renderer.drawCube(1,0,1, new Texture(256,2));
        Renderer.submitDrawCalls();
    }

    public static void main(String[] args) {

        new test().start();
    }

}
