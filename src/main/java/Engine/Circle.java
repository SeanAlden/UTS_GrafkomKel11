package Engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.*;

import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glPointSize;
import static org.lwjgl.opengl.GL11C.*;

public class Circle extends Objects {

    float radiusX;
    float radiusY;
    float x;
    float y;

    List<Vector3f> circle;

    Vector3f center;

    public Circle(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color,
                  float radiusX, float radiusY, Vector3f center) {
        super(shaderModuleDataList, vertices, color);
        this.radiusX = radiusX;
        this.radiusY = radiusY;
        this.center = center;
        CreateCircle2D();
        this.vertices = circle;
        setupVAOVBO();
    }

    public void CreateCircle2D(){

        circle = new ArrayList<>();
        circle.add(new Vector3f(center.x, center.y, center.z));


        float circleLine = 0.01f;
        float triangleLine = 120f;
        float squareLine = 90f;

        for (float i = 0; i <= 360; i += circleLine){
            double rad = Math.toRadians(i);

            x = (float)(radiusX * Math.cos(rad) + center.x);
            y = (float)(radiusY * Math.sin(rad) + center.y);

            circle.add(new Vector3f(x, y, 0.0f));
        }
    }
}