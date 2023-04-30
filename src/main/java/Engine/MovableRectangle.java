package Engine;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.*;

import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glPointSize;
import static org.lwjgl.opengl.GL11C.*;

public class MovableRectangle extends Objects {

    float radius;
    float x;
    float y;

    List<Vector3f> rectangle;

    Vector3f center;

    public MovableRectangle(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color,
                  float radius, Vector3f center) {
        super(shaderModuleDataList, vertices, color);

        this.radius = radius;
        this.center = center;
        CreateRectangle();
        setupVAOVBO();
    }

    public void CreateRectangle(){
        rectangle = new ArrayList<>();
        rectangle.add(new Vector3f(center.x, center.y, center.z));

        float circleLine = 0.01f;
        float triangleLine = 120f;
        float squareLine = 90f;

        for (float i = 45; i <= 405; i += squareLine){
            double rad = Math.toRadians(i);

            x = (float)(radius * Math.cos(rad) + center.x);
            y = (float)(radius * Math.sin(rad) + center.y);

            rectangle.add(new Vector3f(x, y, 0.0f));
        }
        vertices = rectangle;
    }

    public void move(Vector3f center){
        this.center = center;
        CreateRectangle();
        setupVAOVBO();
    }

    public void draw(Camera camera, Projection projection){
        drawSetup(camera, projection);
        glLineWidth(10);
        glPointSize(10);
        glDrawArrays(GL_TRIANGLE_FAN, 0, vertices.size());
    }

    public boolean withinRectangle(Vector2f point){
        float maxWidth = center.x + radius;
        float minWidth = center.x - radius;
        float maxHeight = center.y + radius;
        float minHeight = center.y - radius;

        if (point.x >= minWidth && point.x <= maxWidth){
            if (point.y >= minHeight && point.y <= maxHeight){
                return true;
            }
        }
        return false;
    }

    public boolean withinRadius(Vector2f point){
        float maxWidth = center.x + radius * 2;
        float minWidth = center.x - radius * 2;
        float maxHeight = center.y + radius * 2;
        float minHeight = center.y - radius * 2;

        if (point.x >= minWidth && point.x <= maxWidth){
            if (point.y >= minHeight && point.y <= maxHeight){
                return true;
            }
        }
        return false;
    }
}