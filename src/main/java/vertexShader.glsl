#version 460 core

layout (location = 0) in vec3 position;
layout (location = 2) in vec2 texCoord;
layout (location = 3) in vec3 ChunkPosition;

uniform vec2 offset;


out vec3 ourColor;
out vec2 TexCoord;

uniform mat4 transform;
uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;


void main()
{
    float x = float((12u & 0xFu) >> 6u);
    x += ChunkPosition.x;
    uint index = (1 & 0xF) >> 21u;

    gl_Position = projection * view * transform * vec4(position, 1.0f);
    TexCoord = texCoord;
}