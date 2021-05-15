#version 460 core

layout (location = 0) in int data;

out vec2 TexCoord;

uniform ivec2 chunkPos;
uniform mat4 viewProjection;
uniform mat4 model;

void main()
{
    vec3 position = vec3((data & 0xFF) + chunkPos.x, (data & 0xFF00) >> 8, ((data & 0xFF0000) >> 16) + chunkPos.y);
    vec2 texCoord = vec2((data & 0x1000000) >> 24, (data & 0x2000000) >> 25);
    gl_Position = viewProjection * vec4(position, 1.0f);
    TexCoord = texCoord;
}