package Renderer;

import glm_.vec2.Vec2;

import static Renderer.TextureAtlas.getHeight;
import static Renderer.TextureAtlas.getWidth;

public class Texture {
    //256
    private final float x1,y1,x2,y2;

    public Texture(int size, int index) {
        float width = (float)getWidth();
        float height = (float)getHeight();
        int yIndex = (index / 32) + 1;

        x1 = ((index - 1) * size) / width;
        y1 = (((height / size) - yIndex) * size) / height;
        x2 = (index * size) / width;
        y2 = (((height / size) - (yIndex - 1)) * size) / height;
        float a = 1;
    }

    public Vec2[] getTexCoords(){
        Vec2[] texCoords = new Vec2[4];
        texCoords[0] = new Vec2(x1,y1);
        texCoords[1] = new Vec2(x1,y2);
        texCoords[2] = new Vec2(x2,y2);
        texCoords[3] = new Vec2(x2,y1);
        return texCoords;
    }

}
