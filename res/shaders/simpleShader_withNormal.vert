#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texCoords;
layout (location = 2) in vec3 normals;

out vec2 TexCoord;
out vec3 Normal;
out vec3 FragPosition;

uniform mat4 trans;
uniform mat4 view;
uniform mat4 proj;

void main() {
	FragPosition = vec3(trans * vec4(position, 1.0));
	TexCoord = texCoords;
	Normal = mat3(transpose(inverse(trans))) * normals;  
	
	gl_Position = proj * view * vec4(FragPosition, 1.0);
}