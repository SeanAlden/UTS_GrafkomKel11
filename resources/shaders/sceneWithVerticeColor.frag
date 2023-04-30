#version 330
in vec4 outColor;
out vec4 fragColor;
void main()
{
    //vec4(red, green, blue, alpha)
    //rgba -> 100/255
    //fragColor = vec4(1.0, 0.0, 0.0, 1.0);

    fragColor = outColor;
}