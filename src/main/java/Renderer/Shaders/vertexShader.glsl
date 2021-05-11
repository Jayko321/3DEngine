#version 460 core

layout (location = 0) in uint data;

out vec2 TexCoord;

uniform ivec2 chunkPos;
uniform mat4 viewProjection;
uniform mat4 model;

void main()
{
    vec3 position = vec3((data & 0xFFu) + chunkPos.x, (data & 0xFF00u) >> 8u, ((data & 0xFF0000u) >> 16u) + chunkPos.y);
    vec2 texCoord = vec2((data & 0x1000000u) >> 24u, (data & 0x2000000u) >> 25u);
    gl_Position = viewProjection * vec4(position, 1.0f);
    TexCoord = texCoord;
}