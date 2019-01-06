#version 330 core

out vec4 FragColor;

in vec2 TexCoord;

uniform vec3 light_ambient;
uniform vec3 light_diffuse;
uniform vec3 light_specular;

void main() {
	FragColor = vec4(light_specular, 1.0);
}