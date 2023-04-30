package Engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_POLYGON;

public class Sphere extends Circle{
    float radiusZ;
    int stackCount;
    int sectorCount;

    public Sphere(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color,
                  float radiusX, float radiusY, Vector3f center, float radiusZ, int sectorCount, int stackCount, int shapes) {
        super(shaderModuleDataList, vertices, color, radiusX, radiusY, center);
        this.radiusZ = radiusZ;
        this.sectorCount = sectorCount;
        this.stackCount = stackCount;

        if (shapes == 1){
            CreateBox();
        } else if (shapes == 2){
            CreateElipsoid();
        } else if (shapes == 3){
            CreateHyperoloid1();
        } else if (shapes == 4){
            CreateHyperoloid2();
        } else if (shapes == 5){
            CreateElipticCone();
        } else if (shapes == 6){
            CreateElipticParaboloid();
        } else if (shapes == 7){
            CreateHyperboloidParaboloid();
        }

        setupVAOVBO();
    }

    public void CreateBox(){
        vertices.clear();
        Vector3f box = new Vector3f();
        ArrayList<Vector3f> boxVertices = new ArrayList<>();

        box.x = center.x - radiusX / 2;
        box.y = center.y + radiusY / 2;
        box.z = center.z - radiusZ / 2;
        boxVertices.add(box);

        box = new Vector3f();
        box.x = center.x - radiusX / 2;
        box.y = center.y - radiusY / 2;
        box.z = center.z - radiusZ / 2;
        boxVertices.add(box);

        box = new Vector3f();
        box.x = center.x - radiusX / 2;
        box.y = center.y + radiusY / 2;
        box.z = center.z + radiusZ / 2;
        boxVertices.add(box);

        box = new Vector3f();
        box.x = center.x - radiusX / 2;
        box.y = center.y - radiusY / 2;
        box.z = center.z + radiusZ / 2;
        boxVertices.add(box);

        box = new Vector3f();
        box.x = center.x + radiusX / 2;
        box.y = center.y + radiusY / 2;
        box.z = center.z - radiusZ / 2;
        boxVertices.add(box);

        box = new Vector3f();
        box.x = center.x + radiusX / 2;
        box.y = center.y - radiusY / 2;
        box.z = center.z - radiusZ / 2;
        boxVertices.add(box);

        box = new Vector3f();
        box.x = center.x + radiusX / 2;
        box.y = center.y + radiusY / 2;
        box.z = center.z + radiusZ / 2;
        boxVertices.add(box);

        box = new Vector3f();
        box.x = center.x + radiusX / 2;
        box.y = center.y - radiusY / 2;
        box.z = center.z + radiusZ / 2;
        boxVertices.add(box);

        vertices.add(boxVertices.get(0));
        vertices.add(boxVertices.get(1));
        vertices.add(boxVertices.get(5));

        vertices.add(boxVertices.get(5));
        vertices.add(boxVertices.get(4));
        vertices.add(boxVertices.get(0));

        vertices.add(boxVertices.get(2));
        vertices.add(boxVertices.get(3));
        vertices.add(boxVertices.get(7));

        vertices.add(boxVertices.get(7));
        vertices.add(boxVertices.get(6));
        vertices.add(boxVertices.get(2));

        vertices.add(boxVertices.get(0));
        vertices.add(boxVertices.get(1));
        vertices.add(boxVertices.get(3));

        vertices.add(boxVertices.get(3));
        vertices.add(boxVertices.get(2));
        vertices.add(boxVertices.get(0));

        vertices.add(boxVertices.get(4));
        vertices.add(boxVertices.get(5));
        vertices.add(boxVertices.get(7));

        vertices.add(boxVertices.get(7));
        vertices.add(boxVertices.get(6));
        vertices.add(boxVertices.get(4));

        vertices.add(boxVertices.get(0));
        vertices.add(boxVertices.get(4));
        vertices.add(boxVertices.get(6));

        vertices.add(boxVertices.get(6));
        vertices.add(boxVertices.get(2));
        vertices.add(boxVertices.get(0));

        vertices.add(boxVertices.get(1));
        vertices.add(boxVertices.get(5));
        vertices.add(boxVertices.get(7));

        vertices.add(boxVertices.get(7));
        vertices.add(boxVertices.get(3));
        vertices.add(boxVertices.get(1));

    }

    public void CreateSphere(){
        vertices.clear();
        float pi = (float)Math.PI;

        float sectorStep = 2 * (float)Math.PI / sectorCount;
        float stackStep = (float)Math.PI / stackCount;
        float sectorAngle, StackAngle, x, y, z;

        for (int i = 0; i <= stackCount; ++i)
        {
            StackAngle = pi / 2 - i * stackStep;
            x = radiusX * (float)Math.cos(StackAngle);
            y = radiusY * (float)Math.cos(StackAngle);
            z = radiusZ * (float)Math.sin(StackAngle);

            for (int j = 0; j <= sectorCount; ++j)
            {
                sectorAngle = j * sectorStep;
                Vector3f temp_vector = new Vector3f();
                temp_vector.x = center.x + x * (float)Math.cos(sectorAngle);
                temp_vector.y = center.y + y * (float)Math.sin(sectorAngle);
                temp_vector.z = center.z + z;
                vertices.add(temp_vector);
            }
        }
    }

    public void CreateElipsoid(){
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/60){
                float x = radiusX * (float)(Math.cos(v) * Math.cos(u));
                float y = radiusY * (float)(Math.cos(v) * Math.sin(u));
                float z = radiusZ * (float)(Math.sin(v));
                temp.add(new Vector3f(x,z,y));
            }
        }
        vertices = temp;

    }

    public void CreateHyperoloid1(){
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/60){
                float z = radiusX * (float)(1/Math.cos(v) * Math.cos(u));
                float y = radiusY * (float)(1/Math.cos(v) * Math.sin(u));
                float x = radiusZ * (float)(Math.tan(v));
                temp.add(new Vector3f(x,z,y));
            }
        }
        vertices = temp;
    }

    public void CreateHyperoloid2(){
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI/2; u<= Math.PI/2; u+=Math.PI/60){
                float x = radiusX * (float)(Math.tan(v) * Math.cos(u));
                float y = radiusY * (float)(Math.tan(v) * Math.sin(u));
                float z = radiusZ * (float)(1.0/Math.cos(v));
                temp.add(new Vector3f(x,z,y));
            }

            for(double u = Math.PI/2; u<= 3 * Math.PI / 2; u+=Math.PI/60){
                float x = radiusX * (float)(Math.tan(v) * Math.cos(u));
                float y = radiusY * (float)(Math.tan(v) * Math.sin(u));
                float z = radiusZ * (float)(1.0/Math.cos(v));
                temp.add(new Vector3f(x,-z,y));
            }
        }
        vertices = temp;
    }

    public void CreateElipticCone(){
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/60){
                float x = radiusX * (float)(v * Math.cos(u));
                float y = radiusY * (float)(v * Math.sin(u));
                float z = radiusZ * (float)(v);
                temp.add(new Vector3f(x,z,y));
            }
        }
        vertices = temp;
    }

    public void CreateElipticParaboloid(){
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = 0; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/60){
                float x = radiusX * (float)(v * Math.cos(u));
                float y = radiusY * (float)(v * Math.sin(u));
                float z = radiusZ * (float)(Math.pow(v,2));
                temp.add(new Vector3f(x,z,y));
            }
        }
        vertices = temp;
    }

    public void CreateHyperboloidParaboloid(){
        vertices.clear();
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = 0; v<= 1; v+=Math.PI/60){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/60){
                float x = radiusX * (float)(v * Math.tan(u));
                float y = radiusY * (float)(v * (1.0f/Math.cos(u)));
                float z = radiusZ * (float)(Math.pow(v,2));
                temp.add(new Vector3f(x,z,y));
            }
        }
        vertices = temp;
    }

    public void drawIndices(Camera camera, Projection projection){
        drawSetup(camera, projection);
        glLineWidth(10);
        glPointSize(10);
        glDrawArrays(GL_LINE_STRIP, 0, vertices.size());
    }
}
