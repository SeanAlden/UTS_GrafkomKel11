package Engine;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Objects extends ShaderProgram {
    List<Vector3f> vertices;
    int vao;
    int vbo;
    int vboColor;

    Vector4f color;
    List<Vector3f> verticesColor;

    UniformsMap uniformsMap;

    Matrix4f model;

    List<Objects> childObject = new ArrayList<>();

    Boolean excludeRotate = false;
    Boolean excludeScale = false;
    Boolean excludeTranslate = false;

    public Objects(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color,
                   List<Objects> childObjects) {
        super(shaderModuleDataList);
        this.vertices = vertices;
        this.color = color;
        uniformsMap = new UniformsMap(getProgramId());
        uniformsMap.createUniform(
                "uniColor"
        );
        uniformsMap.createUniform("model");
        model = new Matrix4f();
        setupVAOVBO();
        this.childObject = childObjects;
    }

    public Objects(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color) {
        super(shaderModuleDataList);
        this.vertices = vertices;
        this.color = color;
        uniformsMap = new UniformsMap(getProgramId());
        uniformsMap.createUniform(
                "uniColor"
        );
        uniformsMap.createUniform("model");
        uniformsMap.createUniform("view");
        uniformsMap.createUniform("projection");
        model = new Matrix4f();
        setupVAOVBO();
    }

    public Objects(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, List<Vector3f> verticesColor) {
        super(shaderModuleDataList);
        this.vertices = vertices;
        this.verticesColor = verticesColor;
        setupVAOVBOwithColor();
    }

    public void setupVAOVBO() {
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        glBufferData(GL_ARRAY_BUFFER, Utils.listoFloat(vertices), GL_STATIC_DRAW);
    }
    public void setupVAOVBOwithColor() {
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        glBufferData(GL_ARRAY_BUFFER, Utils.listoFloat(vertices), GL_STATIC_DRAW);

        vboColor = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboColor);
        glBufferData(GL_ARRAY_BUFFER, Utils.listoFloat(verticesColor), GL_STATIC_DRAW);
    }

    public void drawSetupWithColor() {
        bind();
        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        glEnableVertexAttribArray(1);
        glBindBuffer(GL_ARRAY_BUFFER, vboColor);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
    }

    public void drawSetup(Camera camera, Projection projection) {
        bind();
        uniformsMap.setUniform(
                "uniColor", color
        );
        uniformsMap.setUniform("model", model);
        uniformsMap.setUniform("view", camera.getViewMatrix());
        uniformsMap.setUniform("projection", projection.getProjMatrix());

        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
    }

    public void draw(Camera camera, Projection projection) {
        drawSetup(camera, projection);

        glLineWidth(10);
        glPointSize(10);
        // GL_TRIANGLES
        // GL_LINE_LOOP
        // GL_LINE_STRIP
        // GL_LINES
        // GL_POINTS
        // GL_TRIANGLE_FAN
        // GL_POLYGON
        glDrawArrays(GL_POLYGON, 0, vertices.size());
        for (Objects child:childObject){
            child.draw(camera, projection);
        }
    }
    public void drawWithColor() {
        drawSetupWithColor();

        glLineWidth(10);
        glPointSize(10);

        // GL_TRIANGLES
        // GL_LINE_LOOP
        // GL_LINE_STRIP
        // GL_LINES
        // GL_POINTS
        // GL_TRIANGLE_FAN
        // GL_POLYGON
        glDrawArrays(GL_TRIANGLES, 0, vertices.size());
    }
    public void drawLine(Camera camera, Projection projection) {
        drawSetup(camera, projection);

        glLineWidth(4.5f);
        glPointSize(10);
        // GL_TRIANGLES
        // GL_LINE_LOOP
        // GL_LINE_STRIP
        // GL_LINES
        // GL_POINTS
        // GL_TRIANGLE_FAN
        // GL_POLYGON
        glDrawArrays(GL_LINE_STRIP, 0, vertices.size());
        for (Objects child:childObject){
            child.drawLine(camera, projection);
        }
    }

    public void drawIndices(Camera camera, Projection projection) {
        drawSetup(camera, projection);

        glLineWidth(10);
        glPointSize(10);
        // GL_TRIANGLES
        // GL_LINE_LOOP
        // GL_LINE_STRIP
        // GL_LINES
        // GL_POINTS
        // GL_TRIANGLE_FAN
        // GL_POLYGON
        glDrawArrays(GL_LINE_STRIP, 0, vertices.size());
    }

    public void addVertices(Vector3f newVector){
        vertices.add(newVector);
        setupVAOVBO();
    }

    public void changeVerticePos(int index, Vector3f newPos){
        vertices.set(index, newPos);
        setupVAOVBO();
    }

    public boolean withinRectangle(Vector2f point){return false;}

    public boolean withinRadius(Vector2f point){return false;}

    public void move(Vector3f center){}

    public void translateObject(float offsetX, float offsetY, float offsetZ){
        model = new Matrix4f().translate(offsetX, offsetY, offsetZ).mul(new Matrix4f(model));
        for (Objects child:childObject){
            if (child.getExcludeTranslate() == false) {
                child.translateObject(offsetX, offsetY, offsetZ);
            }
        }
    }

    public void rotateObject(float degree, float offsetX, float offsetY, float offsetZ){
        model = new Matrix4f().rotate(degree, offsetX, offsetY, offsetZ).mul(new Matrix4f(model));
        for (Objects child:childObject){
            if (child.getExcludeRotate() == false){
                child.rotateObject(degree, offsetX, offsetY, offsetZ);
            }
        }
    }

    public void scaleObject(float offsetX, float offsetY, float offsetZ){
        model = new Matrix4f().scale(offsetX, offsetY, offsetZ).mul(new Matrix4f(model));
        for (Objects child:childObject){
            if (child.getExcludeScale() == false){
                child.scaleObject(offsetX, offsetY, offsetZ);
            }
        }
    }

    public Vector3f updateCenterPoint(){
        Vector3f centerTemp = new Vector3f();
        model.transformPosition(0.0f, 0.0f, 0.0f, centerTemp);
        return centerTemp;
    }

    public List<Objects> getChildObject() {
        return childObject;
    }

    public void setChildObject(List<Objects> childObject) {
        this.childObject = childObject;
    }

    public void addChild(Objects child){
        this.childObject.add(child);
    }

    public Boolean getExcludeRotate() {
        return excludeRotate;
    }

    public void setExcludeRotate(Boolean excludeRotate) {
        this.excludeRotate = excludeRotate;
    }

    public Boolean getExcludeScale() {
        return excludeScale;
    }

    public void setExcludeScale(Boolean excludeScale) {
        this.excludeScale = excludeScale;
    }

    public Boolean getExcludeTranslate() {
        return excludeTranslate;
    }

    public void setExcludeTranslate(Boolean excludeTranslate) {
        this.excludeTranslate = excludeTranslate;
    }
}
