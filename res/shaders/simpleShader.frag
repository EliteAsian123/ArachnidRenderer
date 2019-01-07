#version 330 core

out vec4 FragColor;

in vec2 TexCoord;
in vec3 Normal;
in vec3 FragPosition;

uniform sampler2D texture_diffuse;
uniform sampler2D texture_specular;
uniform sampler2D texture_emission;

uniform vec3 lightPosition;
uniform vec3 viewPos;

uniform vec3 mat_ambient;
uniform vec3 mat_diffuse;
uniform vec3 mat_specular;
uniform vec3 mat_emission;

uniform float mat_shininess;

uniform vec3 light_ambient;
uniform vec3 light_diffuse;
uniform vec3 light_specular;

void main() {
    vec3 ambient = light_ambient * (mat_ambient * vec3(texture(texture_diffuse, TexCoord)));
  	
    vec3 norm = normalize(Normal);
    vec3 lightDir = normalize(lightPosition - FragPosition);
    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = light_diffuse * (diff * mat_diffuse) * vec3(texture(texture_diffuse, TexCoord));
	
	vec3 viewDir = normalize(viewPos - FragPosition);
	vec3 reflectDir = reflect(-lightDir, norm);
	
	float spec = pow(max(dot(viewDir, reflectDir), 0.0), mat_shininess);
	
	vec3 specular = light_specular * (spec * mat_specular) * vec3(texture(texture_specular, TexCoord));
	
	vec3 emission = mat_emission * texture(texture_emission, TexCoord).rgb;
	
    FragColor = vec4(ambient + diffuse + specular + emission, 1.0);
}