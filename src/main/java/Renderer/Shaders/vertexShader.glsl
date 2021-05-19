#version 460 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texCoord;

out vec2 TexCoord;

uniform ivec2 chunkPos;
uniform mat4 viewProjection;
uniform mat4 model;

void main()
{
    gl_Position = viewProjection * vec4(position.x + chunkPos.x , position.y , position.z + chunkPos.y, 1.0f);
    TexCoord = texCoord;
}