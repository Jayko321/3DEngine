package Renderer;

import org.apache.commons.io.FileUtils;
import org.lwjgl.opengl.GL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    public int Program;


    public Shader(String vertexShaderFilePath, String fragmentShaderFilePath){
        GL.createCapabilities();
        CharSequence vertexShaderSource = readFileAsString(vertexShaderFilePath);
        CharSequence fragmentShaderSource = readFileAsString(fragmentShaderFilePath);
        int success;
        String infoLog;

        //compile vertex shader
        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, vertexShaderSource);
        glCompileShader(vertexShader);
        success = glGetShaderi(vertexShader, GL_COMPILE_STATUS);
        if (success == 0) {
            infoLog = glGetShaderInfoLog(vertexShader);
            System.out.println(infoLog);

        }

        //compile fragment shader
        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, fragmentShaderSource);
        glCompileShader(fragmentShader);
        success = glGetShaderi(fragmentShader, GL_COMPILE_STATUS);
        if (success == 0) {
            infoLog = glGetShaderInfoLog(fragmentShader);
            System.out.println(infoLog);
        }


        //build shader program
        Program = glCreateProgram();
        glAttachShader(Program, vertexShader);
        glAttachShader(Program, fragmentShader);
        glLinkProgram(Program);

        //Delete unnecessary shaders
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
    }

    public void Use() {
        glUseProgram(this.Program);
    }


    private String readFileAsString(String filePath)  {
        String content = "";
        File file = new File(filePath);
        try {
            // Read the entire contents of sample.txt
            content = FileUtils.readFileToString(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
