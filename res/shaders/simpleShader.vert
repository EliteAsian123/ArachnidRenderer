#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texCoords;

out vec2 TexCoord;

uniform mat4 trans;
uniform mat4 view;
uniform mat4 proj;

void main() {
	gl_Position = proj * view * trans * vec4(position, 1.0);
	TexCoord = texCoords;
}