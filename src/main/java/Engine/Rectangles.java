package Engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;

import static org.lwjgl.opengl.GL15C.*;


public class Rectangles extends Objects {

    List<Integer> index;
    int ibo;

    public Rectangles(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color, List<Integer> index) {
        super(shaderModuleDataList, vertices, color);
        this.index = index;
        ibo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Utils.listoInt(index), GL_STATIC_DRAW);
    }

    public void draw(Camera camera, Projection projection){
        drawSetup(camera, projection);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glDrawElements(GL_TRIANGLE_FAN, index.size(), GL_UNSIGNED_INT, 0);
    }

}
