import Engine.*;
import Engine.Objects;
import org.joml.*;
import org.lwjgl.opengl.GL;

import java.lang.Math;
import java.util.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL30.*;


public class Main {
    private Window window = new Window(750, 750, "Hello World");



    //Matrix4f model;
//
//    ArrayList<Objects> objects = new ArrayList<>();
    ArrayList<Objects> objectsRectangle = new ArrayList<>();
//    ArrayList<Objects> objectsCircle = new ArrayList<>();
//    ArrayList<Objects> objectsStar = new ArrayList<>();
//    ArrayList<Objects> objectsControl = new ArrayList<>();
//    ArrayList<Objects> objectsMovable = new ArrayList<>();
    ArrayList<Objects> objectsCurve = new ArrayList<>();
    ArrayList<Objects> objectsBox = new ArrayList<>();

    boolean inMovingState = false;
    int currentRectangle = 0;

    Camera camera = new Camera();
    Projection projection = new Projection(window.getWidth(), window.getHeight());

    ArrayList<Vector3f> offsets = new ArrayList<>();

    //Animation
    float MoveSatu = 1;
    float MoveDua = 1;
    float MoveTiga = 1;
    float MoveDegSatu = 0;
    float MoveDegDua = 0;
    float MoveDegTiga = 0;

    float forwardDegX = 0f;
    float forwardDegZ = 90f;
    float xDegreeOffset = 1f;
    float zDegreeOffset = -1f;
    float forwardX = 90;
    float forwardZ = 90;

    public void run() {

        init();
        loop();

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init() {
        window.init();
        GL.createCapabilities();
        camera.setPosition(0.0f, 0.0f, 5.0f);
        camera.setRotation((float) Math.toRadians(0.0f), (float) Math.toRadians(0.0f));

        //grass
        objectsRectangle.add(new Rectangles(
                Arrays.asList(
                        // shaderFile lokasi menyesuaikan objectnya
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(
                        List.of(
                                new Vector3f(-4.0f, -4.0f, 0.0f),
                                new Vector3f(4.0f, -4.0f, 0.0f),
                                new Vector3f(-4.0f, -0.45f, 0.0f),
                                new Vector3f(4.0f, -0.45f, 0.0f)
                        )
                ),
                new Vector4f(0.0f, 1.0f, 0.0f, 0.0f),
                Arrays.asList(0, 1, 2, 1, 2, 3)
        ));
        objectsRectangle.get(0).scaleObject(6, 3, 0.0f);
        objectsRectangle.get(0).translateObject(0.0f, 1.0f, -3.0f);

        //Blover
            //head
            objectsBox.add(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(144 / 255f, 238 / 255f, 144 / 255f, 1.0f),
                    0.4f,
                    0.4f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.4f,
                    36,
                    18,
                    2
            ));
            objectsBox.get(0).scaleObject(1, 1, 0.65f);
            objectsBox.get(0).translateObject(-1.9f, 0.0f, 0.0f);

            //body
            objectsCurve.add(new Curve(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    new Vector4f(0 / 255f, 128 / 255f, 0 / 255f, 1.0f)
            ));
            objectsCurve.get(0).addVertices(new Vector3f(0.0f, 0.2f, 0.0f));
            objectsCurve.get(0).addVertices(new Vector3f(-0.25f, -0.25f, 0.0f));
            objectsCurve.get(0).addVertices(new Vector3f(0.0f, -0.95f, 0.0f));
            objectsCurve.get(0).translateObject(-1.9f, -0.5f, 0.0f);

            //ground
            objectsBox.add(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(139 / 255f, 69 / 255f, 19 / 255f, 1.0f),
                    0.2f,
                    0.2f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.1f,
                    36,
                    18,
                    6
            ));
            objectsBox.get(1).translateObject(1.9f, 1.4f, 0.0f);
            objectsBox.get(1).rotateObject((float) Math.toRadians(180), 0.0f, 0.0f, 1.0f);

            //right leave
            objectsBox.get(0).addChild(
                    new Sphere(
                            Arrays.asList(
                                    new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                                    new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                            ),
                            new ArrayList<>(),
                            // color
                            new Vector4f(0 / 255f, 128 / 255f, 0 / 255f, 1.0f),
                            0.3f,
                            0.3f,
                            // center
                            new Vector3f(0.0f, 0.0f, 0.0f),
                            0.4f,
                            36,
                            18,
                            2

                    ));
            objectsBox.get(0).getChildObject().get(0).translateObject(-1.0f, 0.9f, 0.0f);
            objectsBox.get(0).getChildObject().get(0).scaleObject(0.9f, 1.5f, 0.25f);
            objectsBox.get(0).getChildObject().get(0).rotateObject((float) Math.toRadians(60), 0.0f, 0.0f, 1.0f);

            //top leave
            objectsBox.get(0).addChild(
                    new Sphere(
                            Arrays.asList(
                                    new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                                    new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                            ),
                            new ArrayList<>(),
                            // color
                            new Vector4f(0 / 255f, 128 / 255f, 0 / 255f, 1.0f),
                            0.3f,
                            0.3f,
                            // center
                            new Vector3f(0.0f, 0.0f, 0.0f),
                            0.4f,
                            36,
                            18,
                            2

                    ));
            objectsBox.get(0).getChildObject().get(1).translateObject(2.1f, -0.25f, 0.0f);
            objectsBox.get(0).getChildObject().get(1).scaleObject(0.9f, 1.5f, 0.25f);
            objectsBox.get(0).getChildObject().get(1).rotateObject((float) Math.toRadians(180), 0.0f, 0.0f, 1.0f);

            //left leave
            objectsBox.get(0).addChild(
                    new Sphere(
                            Arrays.asList(
                                    new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                                    new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                            ),
                            new ArrayList<>(),
                            // color
                            new Vector4f(0 / 255f, 128 / 255f, 0 / 255f, 1.0f),
                            0.3f,
                            0.3f,
                            // center
                            new Vector3f(0.0f, 0.0f, 0.0f),
                            0.4f,
                            36,
                            18,
                            2

                    ));
            objectsBox.get(0).getChildObject().get(2).translateObject(-1.1f, -1.3f, 0.0f);
            objectsBox.get(0).getChildObject().get(2).scaleObject(0.9f, 1.5f, 0.25f);
            objectsBox.get(0).getChildObject().get(2).rotateObject((float) Math.toRadians(-60), 0.0f, 0.0f, 1.0f);

            //left eye
            objectsBox.add(
                    new Sphere(
                            Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(0f, 0f, 0f, 1.0f),
                    0.02f,
                    0.01f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.0f,
                    36,
                    18,
                    2
            ));
            objectsBox.get(2).translateObject(-1.9f, 0.0f, 0.0f);

            objectsBox.get(2).addChild(
                    new Sphere(
                            Arrays.asList(
                                    new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                                    new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                            ),
                            new ArrayList<>(),
                            // color
                            new Vector4f(0f, 0f, 0f, 1.0f),
                            0.02f,
                            0.01f,
                            // center
                            new Vector3f(0.0f, 0.0f, 0.0f),
                            0.1f,
                            36,
                            18,
                            2
                    ));
            objectsBox.get(2).getChildObject().get(0).translateObject(-1.9f, 0.05f, 0.25f);

            //right eye
            objectsBox.add(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(0f, 0f, 0f, 1.0f),
                    0.02f,
                    0.01f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.0f,
                    36,
                    18,
                    2
            ));
            objectsBox.get(3).translateObject(-1.9f, 0.0f, 0.0f);

            objectsBox.get(3).addChild(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(0f, 0f, 0f, 1.0f),
                    0.02f,
                    0.01f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.1f,
                    36,
                    18,
                    2
            ));
            objectsBox.get(3).getChildObject().get(0).translateObject(-1.7f, 0.05f, 0.25f);

            //left pupil
            objectsBox.add(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(1.0f, 1.0f, 1.0f, 0.0f),
                    0.01f,
                    0.01f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.0f,
                    36,
                    18,
                    2
            ));
            objectsBox.get(4).translateObject(-1.9f, 0.0f, 0.0f);

            objectsBox.get(4).addChild(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(1.0f, 1.0f, 1.0f, 0.0f),
                    0.01f,
                    0.01f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.01f,
                    36,
                    18,
                    2
            ));
            objectsBox.get(4).getChildObject().get(0).translateObject(-1.9f, 0.05f, 0.25f);

            //right pupil
            objectsBox.add(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(1.0f, 1.0f, 1.0f, 0.0f),
                    0.01f,
                    0.01f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.0f,
                    36,
                    18,
                    2
            ));
            objectsBox.get(5).translateObject(-1.9f, 0.0f, 0.0f);

            objectsBox.get(5).addChild(new Sphere(
                    Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(1.0f, 1.0f, 1.0f, 0.0f),
                    0.01f,
                    0.01f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.01f,
                    36,
                    18,
                    2
            ));
            objectsBox.get(5).getChildObject().get(0).translateObject(-1.7f, 0.05f, 0.25f);

            //mouth
            objectsBox.add(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    //color
                    new Vector4f(0f, 0f, 0f, 1.0f),
                    0.03f,
                    0.01f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.0f,
                    36,
                    18,
                    2
            ));
            objectsBox.get(6).translateObject(-1.9f, 0.0f, 0.0f);

            objectsBox.get(6).addChild(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    //color
                    new Vector4f(0f, 0f, 0f, 1.0f),
                    0.03f,
                    0.01f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.03f,
                    36,
                    18,
                    2
            ));
            objectsBox.get(6).getChildObject().get(0).translateObject(-1.8f, -0.13f, 0.25f);


        //Potato-Mine
            //head
            objectsBox.add(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(222 / 255f, 184 / 222f, 135 / 255f, 1.0f),
                    0.5f,
                    0.5f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.3f,
                    36,
                    18,
                    6
            ));
            objectsBox.get(7).translateObject(0.0f, 0.0f, 0.0f);
            objectsBox.get(7).rotateObject((float) Math.toRadians(180), 0.0f, 0.0f, 1.0f);

            //ground
            objectsBox.get(7).addChild(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(139 / 255f, 69 / 255f, 19 / 255f, 1.0f),
                    0.8f,
                    0.8f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.2f,
                    36,
                    18,
                    2
            ));
            objectsBox.get(7).getChildObject().get(0).translateObject(0.0f, 0.7f, 0.0f);
            objectsBox.get(7).getChildObject().get(0).rotateObject((float) Math.toRadians(180), 0.0f, 0.0f, 1.0f);

            //left eye
            objectsBox.get(7).addChild(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(0f, 0f, 0f, 1.0f),
                    0.1f,
                    0.1f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.1f,
                    36,
                    18,
                    2
            ));
            objectsBox.get(7).getChildObject().get(1).translateObject(0.15f, -0.25f, 0.4f);

            //right eye
            objectsBox.get(7).addChild(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(0f, 0f, 0f, 1.0f),
                    0.1f,
                    0.1f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.1f,
                    36,
                    18,
                    2
            ));
            objectsBox.get(7).getChildObject().get(2).translateObject(-0.15f, -0.25f, 0.4f);

            //left pupil
            objectsBox.get(7).addChild(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(1f, 1f, 1f, 1.0f),
                    0.02f,
                    0.02f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.03f,
                    36,
                    18,
                    2
            ));
            objectsBox.get(7).getChildObject().get(3).translateObject(0.18f, -0.22f, 0.47f);

            //right pupil
            objectsBox.get(7).addChild(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(1f, 1f, 1f, 1.0f),
                    0.02f,
                    0.02f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.03f,
                    36,
                    18,
                    2
            ));
            objectsBox.get(7).getChildObject().get(4).translateObject(-0.12f, -0.22f, 0.47f);

            //teeth
            objectsBox.get(7).addChild(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(1f, 1f, 1f, 1.0f),
                    0.2f,
                    0.1f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.1f,
                    36,
                    18,
                    1
            ));
            objectsBox.get(7).getChildObject().get(5).translateObject(0.0f, -0.66f, 0.45f);
            objectsBox.get(7).getChildObject().get(5).rotateObject((float) Math.toRadians(20), -1.0f, 0.0f, 0.1f);

            // Mouth
            objectsCurve.add(new Curve(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    new Vector4f(0.255f, 0.075f, 0.0275f, 1.0f)
            ));
            objectsCurve.get(1).addVertices(new Vector3f(0.2f, -0.05f, 0.0f));
            objectsCurve.get(1).addVertices(new Vector3f(0.4f, -0.125f, 0.0f));
            objectsCurve.get(1).addVertices(new Vector3f(0.6f, -0.05f, 0.0f));
            objectsCurve.get(1).translateObject(-0.4f, -0.35f, 0.6f);

            //pipe
            objectsBox.get(7).addChild(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(1f, 1f, 1f, 1.0f),
                    0.15f,
                    0.25f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.15f,
                    36,
                    18,
                    1
            ));
            objectsBox.get(7).getChildObject().get(6).translateObject(0.0f, 0.1f, 0.0f);

            //red ball
            objectsBox.get(7).addChild(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(255f, 0f, 0f, 1.0f),
                    0.15f,
                    0.15f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.15f,
                    36,
                    18,
                    2
            ));
            objectsBox.get(7).getChildObject().get(7).translateObject(0.0f, 0.2f, 0.0f);

            //yellow ball
            objectsBox.get(7).addChild(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(255f, 255f, 0f, 1.0f),
                    0.15f,
                    0.15f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.15f,
                    36,
                    18,
                    2
            ));
            objectsBox.get(7).getChildObject().get(8).translateObject(0.0f, 0.2f, 0.0f);


        //Sun-Shroom
            // Body(rectangle)
            objectsBox.add(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(1.0f, 0.992f, 0.816f, 1.0f),
                    0.70f,
                    0.80f,
            // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.6f,
                    36,
                    18,
                    1 //
            ));
            objectsBox.get(8).translateObject(1.9f, -0.85f, 0.0f);

            // Corak -> Elipsoid
            objectsBox.get(8).addChild(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(1.0f, 0.547f, 0.0f, 1.0f),
                    0.4f,
                    0.4f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.275f,
                    36,
                    18,
                    2
            ));
            objectsBox.get(8).getChildObject().get(0).scaleObject(1.55f, 0.50f, 1.80f);
            objectsBox.get(8).getChildObject().get(0).rotateObject((float) Math.toRadians(180), 0.0f, 0.0f, 1.0f);
            objectsBox.get(8).getChildObject().get(0).translateObject(1.9f, 0.0f, 0.0250f);
            objectsBox.get(8).getChildObject().get(0).translateObject(0.0f, -0.625f, 0.0f);

            // head(elliptic paraboloid)
            objectsBox.get(8).addChild(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(1.0f, 0.780f, 0.0f, 1.0f),
                    0.48f,
                    0.48f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.275f,
                    36,
                    18,
                    6
            ));
            objectsBox.get(8).getChildObject().get(1).scaleObject(1f, 1f, 0.98f);
            objectsBox.get(8).getChildObject().get(1).translateObject(-1.9f, 0.0f, 0.0125f);
            objectsBox.get(8).getChildObject().get(1).rotateObject((float) Math.toRadians(180), 0.0f, 0.0f, 1.0f);


            // dot pertama
            objectsBox.get(8).addChild(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(0.82f, 0.41f, 0.12f, 1.0f),
                    0.1f,
                    0.1f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.1f,
                    36,
                    18,
                    2
            //untuk ellipsoid
            ));
            objectsBox.get(8).getChildObject().get(2).scaleObject(0.975f, 0.65f, 0.01f);
            objectsBox.get(8).getChildObject().get(2).rotateObject((float) Math.toRadians(80), 0.0f, 0.0f, 1.0f);
            objectsBox.get(8).getChildObject().get(2).rotateObject((float) Math.toRadians(36), -1.3f, -0.7f, 0.0f);
            objectsBox.get(8).getChildObject().get(2).translateObject(1.7f, -0.3f, 0.4666f);


            // dot kedua
            objectsBox.get(8).addChild(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(0.82f, 0.41f, 0.12f, 1.0f),
                    0.1f,
                    0.1f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.1f,
                    36,
                    18,
                    2
            ));
            objectsBox.get(8).getChildObject().get(3).scaleObject(0.85f, 0.45f, 0.01f);
            objectsBox.get(8).getChildObject().get(3).rotateObject((float) Math.toRadians(100), 0.0f, 0.0f, 1.0f);
            objectsBox.get(8).getChildObject().get(3).rotateObject((float) Math.toRadians(36), -1.0f, 0.0f, 0.0f);
            objectsBox.get(8).getChildObject().get(3).translateObject(1.85f, -0.3f, 0.5020f);


            //dot ketiga(atas)
            objectsBox.get(8).addChild(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(0.82f, 0.41f, 0.12f, 1.0f),
                    0.1f,
                    0.1f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.1f,
                    36,
                    18,
                    2
            ));
            objectsBox.get(8).getChildObject().get(4).scaleObject(0.85f, 0.45f, 0.01f);
            objectsBox.get(8).getChildObject().get(4).rotateObject((float) Math.toRadians(100), 0.0f, 0.0f, 1.0f);
            objectsBox.get(8).getChildObject().get(4).rotateObject((float) Math.toRadians(50), -1.2f, 0.0f, 0.0f);
            objectsBox.get(8).getChildObject().get(4).translateObject(1.9f, -0.1f, 0.2990f);

            //dot ketiga(bawah)
            objectsBox.get(8).addChild(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(0.82f, 0.41f, 0.12f, 1.0f),
                    0.1f,
                    0.1f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.1f,
                    36,
                    18,
                    2
            ));
            objectsBox.get(8).getChildObject().get(5).scaleObject(0.85f, 0.45f, 0.01f);
            objectsBox.get(8).getChildObject().get(5).rotateObject((float) Math.toRadians(100), 0.0f, 0.0f, 1.0f);
            objectsBox.get(8).getChildObject().get(5).rotateObject((float) Math.toRadians(36), -1.0f, 0.0f, 0.0f);
            objectsBox.get(8).getChildObject().get(5).translateObject(1.9f, -0.5f, 0.6500f);

            //dot keempat
            objectsBox.get(8).addChild(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(0.82f, 0.41f, 0.12f, 1.0f),
                    0.1f,
                    0.1f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.1f,
                    36,
                    18,
                    2
            ));
            objectsBox.get(8).getChildObject().get(6).scaleObject(0.99f, 0.48f, 0.01f);
            objectsBox.get(8).getChildObject().get(6).rotateObject((float) Math.toRadians(80), 0.0f, 0.0f, 1.0f);
            objectsBox.get(8).getChildObject().get(6).rotateObject((float) Math.toRadians(37), -1.0f, 0.50f, 0.0f);
            objectsBox.get(8).getChildObject().get(6).translateObject(2.09f, -0.4f, 0.550f);


            // Left eye
            objectsBox.get(8).addChild(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(0.0f, 0.0f, 0.0f, 1.0f),
                    0.1f,
                    0.1f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.1f,
                    36,
                    18,
                    2
            ));
            objectsBox.get(8).getChildObject().get(7).scaleObject(0.75f, 0.25f, 0.01f);
            objectsBox.get(8).getChildObject().get(7).rotateObject((float) Math.toRadians(90), 0.0f, 0.0f, 1.0f);
            objectsBox.get(8).getChildObject().get(7).rotateObject((float) Math.toRadians(22.5), -1.0f, 0.0f, 0.0f);
            objectsBox.get(8).getChildObject().get(7).translateObject(1.75f, -0.9f, 0.265f);


            // right eye
            objectsBox.get(8).addChild(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(0.0f, 0.0f, 0.0f, 1.0f),
                    0.1f,
                    0.1f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.1f,
                    36,
                    18,
                    2
            ));
            objectsBox.get(8).getChildObject().get(8).scaleObject(0.65f, 0.35f, 0.01f);
            objectsBox.get(8).getChildObject().get(8).rotateObject((float) Math.toRadians(85), 0.0f, 0.0f, 1.0f);
            objectsBox.get(8).getChildObject().get(8).rotateObject((float) Math.toRadians(22.5), -1.0f, 0.0f, 0.0f);
            objectsBox.get(8).getChildObject().get(8).translateObject(2.05f, -0.9f, 0.265f);

            // left eye's pupil
            objectsBox.get(8).addChild(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(255f, 255f, 255f, 1.0f),
                    0.1f,
                    0.1f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.1f,
                    36,
                    18,
                    2
            ));
            objectsBox.get(8).getChildObject().get(9).scaleObject(0.18f, 0.1f, 0.01f);
            objectsBox.get(8).getChildObject().get(9).rotateObject((float) Math.toRadians(85), 0.0f, 0.0f, 1.0f);
            objectsBox.get(8).getChildObject().get(9).rotateObject((float) Math.toRadians(22.5), -1.0f, 0.0f, 0.0f);
            objectsBox.get(8).getChildObject().get(9).translateObject(1.75f, -0.88f, 0.265f);

            // right eye's pupil
            objectsBox.get(8).addChild(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(255f, 255f, 255f, 1.0f),
                    0.1f,
                    0.1f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.1f,
                    36,
                    18,
                    2
            ));
            objectsBox.get(8).getChildObject().get(10).scaleObject(0.18f, 0.1f, 0.01f);
            objectsBox.get(8).getChildObject().get(10).rotateObject((float) Math.toRadians(105), 0.0f, 0.0f, 1.0f);
            objectsBox.get(8).getChildObject().get(10).rotateObject((float) Math.toRadians(20), -1.0f, 0.0f, 0.0f);
            objectsBox.get(8).getChildObject().get(10).translateObject(2.05f, -0.88f, 0.265f);

            // Mouth
            objectsCurve.add(new Curve(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    new Vector4f(0.255f, 0.075f, 0.0275f, 1.0f)
            ));
            objectsCurve.get(2).addVertices(new Vector3f(0.0f, 0.0f, 0.0f));
            objectsCurve.get(2).addVertices(new Vector3f(0.0f, 0.0f, 0.0f));
            objectsCurve.get(2).addVertices(new Vector3f(0.0f, 0.0f, 0.0f));
            objectsCurve.get(2).translateObject(1.8f, -1.025f, 0.0f);

            objectsCurve.get(2).addChild(new Curve(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    new Vector4f(0.255f, 0.075f, 0.0275f, 1.0f)
            ));
            objectsCurve.get(2).getChildObject().get(0).addVertices(new Vector3f(0.0f, 0.0f, 0.0f));
            objectsCurve.get(2).getChildObject().get(0).addVertices(new Vector3f(0.1f, -0.125f, 0.0f));
            objectsCurve.get(2).getChildObject().get(0).addVertices(new Vector3f(0.2f, 0.0f, 0.0f));
            objectsCurve.get(2).getChildObject().get(0).translateObject(1.8f, -1.025f, 0.265f);

            //moon
            objectsBox.add(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(1.0f, 1.0f, 0.0f, 1.0f),
                    0.4f,
                    0.4f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.4f,
                    36,
                    18,
                    2
            ));
            objectsBox.get(9).scaleObject(1, 1, 0.65f);
            objectsBox.get(9).translateObject(-1.9f, 2.0f, 0.0f);

            objectsBox.add(new Sphere(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                    ),
                    new ArrayList<>(),
                    // color
                    new Vector4f(0.0f, 0.0f, 1.0f, 0.0f),
                    0.4f,
                    0.4f,
                    // center
                    new Vector3f(0.0f, 0.0f, 0.0f),
                    0.4f,
                    36,
                    18,
                    2
            ));
            objectsBox.get(10).scaleObject(1.0f, 1.0f, 0.65f);
            objectsBox.get(10).translateObject(-1.5f, 1.95f, 0.2f);

    }

    public void input() {
            if (window.isKeyPressed(GLFW_KEY_W)) {
                //Fungsi tombol untuk merotasikan tanaman pada sumbu y "layar"

                //Blover viewpoint (rotate according to screen's "y")
                objectsBox.get(0).rotateObject((float) Math.toRadians(1f), 0.0f, 1.0f, 0.0f);
                objectsBox.get(1).rotateObject((float) Math.toRadians(1f), 0.0f, 1.0f, 0.0f);
                objectsCurve.get(0).rotateObject((float) Math.toRadians(1f), 0.0f, 1.0f, 0.0f);
                objectsBox.get(2).rotateObject((float) Math.toRadians(1f), 0.0f, 1.0f, 0.0f);
                objectsBox.get(3).rotateObject((float) Math.toRadians(1f), 0.0f, 1.0f, 0.0f);
                objectsBox.get(4).rotateObject((float) Math.toRadians(1f), 0.0f, 1.0f, 0.0f);
                objectsBox.get(5).rotateObject((float) Math.toRadians(1f), 0.0f, 1.0f, 0.0f);
                objectsBox.get(6).rotateObject((float) Math.toRadians(1f), 0.0f, 1.0f, 0.0f);

                //Potato mine viewpoint (rotate according to screen's "y")
                objectsBox.get(7).rotateObject((float) Math.toRadians(1f), 0.0f, 1.0f, 0.0f);
                objectsCurve.get(1).rotateObject((float) Math.toRadians(1f), 0.0f, 1.0f, 0.0f);

                //Sun-Shroom viewpoint (rotate according to screen's "y")
                objectsBox.get(8).rotateObject((float) Math.toRadians(1f), 0.0f, 1.0f, 0.0f);
                objectsCurve.get(2).rotateObject((float) Math.toRadians(1f), 0.0f, 1.0f, 0.0f);
            }

            if (window.isKeyPressed(GLFW_KEY_Y)) {
                //Fungsi tombol untuk merotasikan tanaman pada sumbu y "tanaman itu sendiri"

                //Untuk update posisi tengah setiap tanaman
                //Blover Objects
                objectsBox.get(0).setExcludeRotate(true);
                objectsBox.get(1).setExcludeRotate(true);
                objectsBox.get(2).setExcludeRotate(true);
                objectsBox.get(3).setExcludeRotate(true);
                objectsBox.get(4).setExcludeRotate(true);
                objectsBox.get(5).setExcludeRotate(true);
                objectsBox.get(6).setExcludeRotate(true);
                objectsCurve.get(0).setExcludeRotate(true);

                //Sun-Shroom Objects
                objectsBox.get(8).setExcludeRotate(true);
                objectsCurve.get(2).setExcludeRotate(true);

                //Blover Objects
                objectsBox.get(0).setExcludeRotate(false);
                objectsBox.get(1).setExcludeRotate(false);
                objectsBox.get(2).setExcludeRotate(false);
                objectsBox.get(3).setExcludeRotate(false);
                objectsBox.get(4).setExcludeRotate(false);
                objectsBox.get(5).setExcludeRotate(false);
                objectsBox.get(6).setExcludeRotate(false);
                objectsCurve.get(0).setExcludeRotate(false);

                //Sun-Shroom Objects
                objectsBox.get(8).setExcludeRotate(false);
                objectsCurve.get(2).setExcludeRotate(false);

                //Fungsi update posisi ketengahan setiap objek baik parent dan childnya
                Vector3f Objek1 = objectsBox.get(0).updateCenterPoint();
                Vector3f Objek2 = objectsBox.get(1).updateCenterPoint();
                Vector3f Objek3 = objectsBox.get(2).updateCenterPoint();
                Vector3f Objek4 = objectsBox.get(3).updateCenterPoint();
                Vector3f Objek5 = objectsBox.get(4).updateCenterPoint();
                Vector3f Objek6 = objectsBox.get(5).updateCenterPoint();
                Vector3f Objek7 = objectsBox.get(6).updateCenterPoint();
                Vector3f Objek8 = objectsCurve.get(0).updateCenterPoint();
                Vector3f objek9 = objectsBox.get(8).updateCenterPoint();
                Vector3f Objek10 = objectsCurve.get(2).updateCenterPoint();

                //Dibawah ini adalah fungsi yang digunakan untuk mengatur ketengahan dari objek yang dipilih
                //dengan mengatur translasinya agar bisa tepat setiap tanaman mau rotasi di titik yang mereka tempati

                //Blover animation (blover rotation to it's "y")
                objectsBox.get(0).translateObject(Objek1.x * -1, Objek1.y * -1, Objek1.z * -1);
                objectsBox.get(0).rotateObject((float) Math.toRadians(1f), 0.0f, 1.0f, 0.0f);
                objectsBox.get(0).translateObject(Objek1.x * +1, Objek1.y * +1, Objek1.z * +1);

                objectsBox.get(1).translateObject(Objek2.x * -1, Objek2.y * -1, Objek2.z * -1);
                objectsBox.get(1).rotateObject((float) Math.toRadians(1f), 0.0f, 1.0f, 0.0f);
                objectsBox.get(1).translateObject(Objek2.x * +1, Objek2.y * +1, Objek2.z * +1);

                objectsBox.get(2).translateObject(Objek3.x * -1, Objek3.y * -1, Objek3.z * -1);
                objectsBox.get(2).rotateObject((float) Math.toRadians(1f), 0.0f, 1.0f, 0.0f);
                objectsBox.get(2).translateObject(Objek3.x * +1, Objek3.y * +1, Objek3.z * +1);

                objectsBox.get(3).translateObject(Objek4.x * -1, Objek4.y * -1, Objek4.z * -1);
                objectsBox.get(3).rotateObject((float) Math.toRadians(1f), 0.0f, 1.0f, 0.0f);
                objectsBox.get(3).translateObject(Objek4.x * +1, Objek4.y * +1, Objek4.z * +1);

                objectsBox.get(4).translateObject(Objek5.x * -1, Objek5.y * -1, Objek5.z * -1);
                objectsBox.get(4).rotateObject((float) Math.toRadians(1f), 0.0f, 1.0f, 0.0f);
                objectsBox.get(4).translateObject(Objek5.x * +1, Objek5.y * +1, Objek5.z * +1);

                objectsBox.get(5).translateObject(Objek6.x * -1, Objek6.y * -1, Objek6.z * -1);
                objectsBox.get(5).rotateObject((float) Math.toRadians(1f), 0.0f, 1.0f, 0.0f);
                objectsBox.get(5).translateObject(Objek6.x * +1, Objek6.y * +1, Objek6.z * +1);

                objectsBox.get(6).translateObject(Objek7.x * -1, Objek7.y * -1, Objek7.z * -1);
                objectsBox.get(6).rotateObject((float) Math.toRadians(1f), 0.0f, 1.0f, 0.0f);
                objectsBox.get(6).translateObject(Objek7.x * +1, Objek7.y * +1, Objek7.z * +1);

                objectsCurve.get(0).translateObject(Objek8.x * -1, Objek8.y * -1, Objek8.z * -1);
                objectsCurve.get(0).rotateObject((float) Math.toRadians(1f), 0.0f, 1.0f, 0.0f);
                objectsCurve.get(0).translateObject(Objek8.x * +1, Objek8.y * +1, Objek8.z * +1);

                //Potato-mine animation (Potato-mine rotation to it's "y")
                objectsBox.get(7).rotateObject((float) Math.toRadians(1f), 0.0f, 1.0f, 0.0f);
                objectsCurve.get(1).rotateObject((float) Math.toRadians(1f), 0.0f, 1.0f, 0.0f);

                //Sun-shroom animation (Sun-shroom rotation to it's "y")
                objectsBox.get(8).translateObject(objek9.x * -1, objek9.y * -1, objek9.z * -1);
                objectsBox.get(8).rotateObject((float) Math.toRadians(1f), 0.0f, 1.0f, 0.0f);
                objectsBox.get(8).translateObject(objek9.x * +1, objek9.y * +1, objek9.z * +1);

                objectsCurve.get(2).translateObject(Objek10.x * -1, Objek10.y * -1, Objek10.z * -1);
                objectsCurve.get(2).rotateObject((float) Math.toRadians(1f), 0.0f, 1.0f, 0.0f);
                objectsCurve.get(2).translateObject(Objek10.x * +1, Objek10.y * +1, Objek10.z * +1);
            }

            if (window.isKeyPressed(GLFW_KEY_F)) {
                //Fungsi tombol untuk menjalankan animasi setiap tanaman

                //Potato mine animation (lamp color)
                LampAnimation();

                //Sun-Shroom animation (jamur bergoyang ke samping)
                ShroomAnimation1();

                //Blover animation (baling-baling berputar)
                BloverAnimation();
            }
            if (window.isKeyPressed(GLFW_KEY_J)){
                //Fungsi tombol tambahan untuk animasi lompat pada jamur

                //Sun-Shroom animation (jamur bergerak ke atas/bawah)
                ShroomAnimation2();
            }

        if (window.getMouseInput().isLeftButtonReleased()) {
            inMovingState = false;
        }
        if (window.getMouseInput().isLeftButtonPressed()) {
            Vector2f pos = window.getMouseInput().getCurrentPos();

            pos.x = (pos.x - (window.getWidth()) / 2.0f) / (window.getWidth() / 2.0f);
            pos.y = (pos.y - (window.getHeight()) / 2.0f) / (-window.getHeight() / 2.0f);

            if ((!(pos.x > 1 || pos.x < -0.97) && !(pos.y > 1 || pos.y < -0.97))) {

                boolean inRectangle = false;


                boolean inRadius = false;


                int rectangleToMove = 0;

                if (inRectangle == true) {
                    inMovingState = true;
                    currentRectangle = rectangleToMove;
                }

                if (inRectangle == false && inRadius == false && inMovingState == false) {
                    System.out.println("x : " + pos.x + " y : " + pos.y);
                }
            }
        }
    }

    //Animation
    //Potato-Mine Lamp Animation
    private void LampAnimation() {
        if (MoveDegSatu >= 0.1) {
            MoveSatu = -1;
        }
        if (MoveDegSatu <= -0.1) {
            MoveSatu = 1;
        }

        objectsBox.get(7).getChildObject().get(7).translateObject(0.0f, -MoveDegSatu, 0.0f);
        MoveDegSatu += MoveSatu;

    }
        private void ShroomAnimation1() {
            if (MoveDegDua >= 45f) {
                MoveDua = -1f;
            }
            if (MoveDegDua <= -45f) {
                MoveDua = 1f;
            }

            //Sun-Shroom menggerakkan badan ke samping
            objectsBox.get(8).setExcludeRotate(true);
            objectsBox.get(8).setExcludeRotate(false);
            objectsCurve.get(2).setExcludeRotate(true);
            objectsCurve.get(2).setExcludeRotate(false);

            Vector3f tengah1 = objectsBox.get(8).updateCenterPoint();
            Vector3f tengah2 = objectsCurve.get(2).updateCenterPoint();

            objectsBox.get(8).translateObject(tengah1.x * -1, tengah1.y * -1, tengah1.z * -1);
            objectsBox.get(8).rotateObject((float) Math.toRadians(MoveDua * 0.6), 0.0f, 0.0f, -1.0f);
            objectsBox.get(8).translateObject(tengah1.x * +1, tengah1.y * +1, tengah1.z * +1);

            objectsCurve.get(2).translateObject(tengah2.x * -1, tengah2.y * -1, tengah2.z * -1);
            objectsCurve.get(2).rotateObject((float) Math.toRadians(MoveDua * 0.6), 0.0f, 0.0f, -1.0f);
            objectsCurve.get(2).translateObject(tengah2.x * +1, tengah2.y * +1, tengah2.z * +1);

            //Supaya geraknya bener
            MoveDegDua += MoveDua;
        }


        private void ShroomAnimation2() {
                if (MoveDegTiga >= 0.1f) {
                    MoveTiga = -1f;
                }
                if (MoveDegTiga <= -0.1f) {
                    MoveTiga = 1f;
                }
                //Sun-Shroom menggerakkan badan ke atas/bawah
                objectsBox.get(8).setExcludeRotate(true);
                objectsBox.get(8).setExcludeRotate(false);
                objectsCurve.get(2).setExcludeRotate(true);
                objectsCurve.get(2).setExcludeRotate(false);

                Vector3f tengah1 = objectsBox.get(7).updateCenterPoint();
                Vector3f tengah2 = objectsCurve.get(2).updateCenterPoint();

                objectsBox.get(8).translateObject(tengah1.x * -1, tengah1.y * -1, tengah1.z * -1);
                objectsBox.get(8).translateObject(0.0f, MoveDegTiga, 0.0f);
                objectsBox.get(8).translateObject(tengah1.x * +1, tengah1.y * +1, tengah1.z * +1);

                objectsCurve.get(2).translateObject(tengah2.x * -1, tengah2.y * -1, tengah2.z * -1);
                objectsCurve.get(2).translateObject(0.0f, MoveDegTiga, 0.0f);
                objectsCurve.get(2).translateObject(tengah2.x * +1, tengah2.y * +1, tengah2.z * +1);

                //Supaya geraknya benar
                MoveDegTiga += MoveTiga;
            }
            private void BloverAnimation(){

                objectsBox.get(0).setExcludeRotate(true);

                Vector3f posisi = objectsBox.get(0).updateCenterPoint();

                objectsBox.get(0).translateObject(posisi.x * -1, posisi.y * -1, posisi.z * -1);

                //Blover animation (blover's leafs spinning)

                //Animasi untuk memutar baling-baling ketika blover menghadap layar
                objectsBox.get(0).rotateObject(-(float) Math.toRadians(20.0f), 0.0f, 0.0f, 1.0f);
                //Animasi untuk memutar baling-baling ketika blover menghadap pada "kanan" layar
//                objectsBox.get(0).rotateObject(-(float) Math.toRadians(20.0f), 1.0f, 0.0f, 0.0f);
                //Animasi untuk memutar baling-baling ketika blover membelakangi layar
//                objectsBox.get(0).rotateObject((float) Math.toRadians(20.0f), 0.0f, 0.0f, 1.0f);
                //Animasi untuk memutar baling-baling ketika blover menghadap pada "kiri" layar
//                objectsBox.get(0).rotateObject((float) Math.toRadians(20.0f), 1.0f, 0.0f, 0.0f);
                objectsBox.get(0).translateObject(posisi.x * +1, posisi.y * +1, posisi.z * +1);

                objectsBox.get(0).setExcludeRotate(false);
            }



    public void loop() {
        while (window.isOpen()) {
            window.update();
            glClearColor(0.0f,
                    0.0f, 1.0f,
                    0.0f);
            GL.createCapabilities();
            input();

//            for (Object2d object : objectsControl) {
//                object.drawLine();
//            }

//
//            for (Objects object : objectsMovable) {
//                object.draw();
//            }

            for (Objects object : objectsBox) {
                object.draw(camera, projection);
            }

            for (Objects object : objectsCurve) {
                object.drawLine(camera, projection);
            }

            for (Objects object : objectsRectangle) {
                object.draw(camera, projection);
            }

//            for(Object2d object: objects){
//                object.drawwithverticescolor();
//            }

            glDisableVertexAttribArray(0);
            glfwPollEvents();
        }
//        model = model.mul(new Matrix4f().rotate((float) Math.toRadians(1.0f), 1,0,0));
//        model = model.mul(new Matrix4f().rotate((float) Math.toRadians(0.5f), 0,1,0));
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
