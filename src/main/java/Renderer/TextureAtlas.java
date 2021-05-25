package Renderer;

import org.lwjgl.stb.STBImage;

import java.io.File;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public final class TextureAtlas {
    private static int texture;
    private static final int[] width = new int[1];
    private static final int[] height = new int[1];
    private static final int[] channels = new int[1];

    public TextureAtlas(String atlasPath) {
        File file = new File(atlasPath);
        if (!file.exists()){
            return;
        }


        STBImage.stbi_set_flip_vertically_on_load(true);
        ByteBuffer image = STBImage.stbi_load(atlasPath, width, height, channels, 0);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width[0], height[0], 0, GL_RGB, GL_UNSIGNED_BYTE, image);
        glGenerateMipmap(GL_TEXTURE_2D);
        assert image != null;
        STBImage.stbi_image_free(image);
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void Do(){}


    public static int getWidth() {
        return width[0];
    }

    public static int getHeight() {
        return height[0];
    }

    public static int getTexture(){
        return texture;
    }
}
