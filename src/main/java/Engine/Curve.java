package Engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.*;

public class Curve extends Objects {
    List<Vector3f> points = new ArrayList<>();
    List<Integer> numbers;
    List<Vector3f> curve;

    public Curve(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color) {
        super(shaderModuleDataList, vertices, color);
    }

    public void CreateCurve(){
        int n = points.size() - 1;
        curve = new ArrayList<>();
        numbers = new ArrayList<>();
        pascalTriangle();

        if (points.size() > 1){
            for (double t = 0; t <= 1; t+=0.1){
                float x = 0;
                float y = 0;
                for (int i = 0; i < points.size(); i++){
                    x += (numbers.get(i) * Math.pow((1-t), n - i) * Math.pow(t, i) * points.get(i).x);
                }

                for (int i = 0; i < points.size(); i++){
                    y += (numbers.get(i) * Math.pow((1-t), n - i) * Math.pow(t, i) * points.get(i).y);
                }

                curve.add(new Vector3f(x, y, 0.0f));
            }
        }

        this.vertices = curve;
    }

    public void pascalTriangle(){
        int n = points.size() - 1;

        for (int r = 0; r <= n; r++) {
            int num = factorial(n) / (factorial(n - r) * factorial(r));
            numbers.add(num);
        }
    }

    public int factorial(int i)
    {
        if (i == 0)
            return 1;
        return i * factorial(i - 1);
    }

    public void addVertices(Vector3f newVector){
        points.add(newVector);
        CreateCurve();
        setupVAOVBO();
    }

    public void changeVerticePos(int index, Vector3f newPos){
        points.set(index, newPos);
        CreateCurve();
        setupVAOVBO();
    }
}