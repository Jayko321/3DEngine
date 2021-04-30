#version 460 core

layout (location = 0) in vec3 position;
layout (location = 2) in vec2 texCoord;

out vec2 TexCoord;


uniform mat4 viewProjection;
uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;


void main()
{
    gl_Position = viewProjection * vec4(position, 1.0f);
    TexCoord = texCoord;
}