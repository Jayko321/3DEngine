import Objects.Cube;
import Renderer.Texture;
import Renderer.TextureAtlas;
import VOEngine.VOEngine;


public class test extends VOEngine {
    TextureAtlas atlas = new TextureAtlas("D:\\3DEngine\\src\\main\\resources\\atlases\\1.jpg");
    private final Cube cube;
    public test() {
        super();
        cube = new Cube(0, 0, 0, new Texture(256,4));
    }

    @Override
    public void whileLoopFunc(){
        enableFpsCounter();
        Renderer.drawCube(cube);
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
