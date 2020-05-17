import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.*;
import java.awt.*;
import java.awt.event.*;

public class BuildAcorn extends JFrame implements ActionListener {
    private TransformGroup TransformGroupView;
    private Transform3D TransformView = new Transform3D();
    private TransformGroup transformGroup;
    private Transform3D transform3d = new Transform3D();
    private float z;
    private static float uViewLimit = 2.0f;
    private static float lViewLimit = -10.0f;
    private static float fViewLimit = 10.0f;
    private static float nViewLimit = 2.0f;
    private static float cAngle = 0.06f;
    private static float cDistance = 1f;
    private float viewHeight = uViewLimit;
    private float viewDistance = fViewLimit;
    private DirectionalLight leftTopLight;
    private DirectionalLight rightBottomLight;
    private AmbientLight ambientLight;
    private BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);


    public static void main(String[] args) {
        BuildAcorn buildAcorn = new BuildAcorn();
        buildAcorn.setVisible(true);
    }

    public BuildAcorn() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setResizable(false);
        init();
        setTitle("3D Acorn");
        Timer timer = new Timer(11, this);
        timer.start();

    }

    private void init() {
        Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        canvas3D.setFocusable(true);
        canvas3D.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_RIGHT:
                        z += cAngle;
                        break;
                    case KeyEvent.VK_LEFT:
                        z -= cAngle;
                        break;
                    case KeyEvent.VK_UP:
                        if (viewHeight < uViewLimit) {
                            viewHeight += cDistance;
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (viewHeight > lViewLimit) {
                            viewHeight -= cDistance;
                        }
                        break;
                    case 87:
                        if (viewDistance > nViewLimit) {
                            viewDistance -= cDistance;
                        }
                        break;
                    case 83:
                        if (viewDistance < fViewLimit) {
                            viewDistance += cDistance;
                        }
                        break;
                    default:
                        break;

                }
            }
        });

        add(canvas3D, BorderLayout.CENTER);
        SimpleUniverse universe = new SimpleUniverse(canvas3D);
        universe.addBranchGraph(createSceneGraph());
        TransformGroupView = universe.getViewingPlatform().getViewPlatformTransform();
    }

    private BranchGroup createSceneGraph() {
        Background background = new Background(new Color3f(1.0f, 1.0f, 1.0f));
        BoundingSphere sphere = new BoundingSphere(new Point3d(0,0,0), 100000);
        background.setApplicationBounds(sphere);
        BranchGroup objRoot = new BranchGroup();
        transformGroup = new TransformGroup();
        transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        buildAcorn();
        TransformGroup tg = new TransformGroup();
        Transform3D t = new Transform3D();
        t.setScale(new Vector3d(1.0f, 1.0f, 1.0f));
        tg.setTransform(t);
        tg.addChild(transformGroup);
        objRoot.addChild(tg);
        objRoot.addChild(background);
        setLight(objRoot);
        return objRoot;
    }

    private void setLight(BranchGroup objRoot) {
        Color3f color1 = new Color3f(1.0f, 1.0f, 1.0f);
        Vector3f direction1 = new Vector3f(4.0f, -7.0f, -12.0f);
        leftTopLight = new DirectionalLight(color1, direction1);
        leftTopLight.setCapability(DirectionalLight.ALLOW_INFLUENCING_BOUNDS_WRITE);
        leftTopLight.setInfluencingBounds(bounds);
        objRoot.addChild(leftTopLight);
        Color3f color2 = new Color3f(1f, 1f, 1f);
        Vector3f direction2 = new Vector3f(4.0f, 7.0f, 12.0f);
        rightBottomLight = new DirectionalLight(color2, direction2);
        rightBottomLight.setCapability(DirectionalLight.ALLOW_INFLUENCING_BOUNDS_WRITE);
        rightBottomLight.setInfluencingBounds(bounds);
        objRoot.addChild(rightBottomLight);
        Color3f ambientColor = new Color3f(1.0f, 1.0f, 1.0f);
        ambientLight = new AmbientLight(ambientColor);
        ambientLight.setCapability(AmbientLight.ALLOW_INFLUENCING_BOUNDS_WRITE);
        ambientLight.setInfluencingBounds(bounds);
        objRoot.addChild(ambientLight);
    }

    public static Appearance getAppearance(Color color, String texturePath) {
        Appearance ap = new Appearance();

        Color3f emissive = new Color3f(Color.BLACK);
        Color3f ambient = new Color3f(color);
        Color3f diffuse = new Color3f(color);
        Color3f specular = new Color3f(color);

        ap.setMaterial(new Material(ambient, emissive, diffuse, specular, 64.0f));

        if (texturePath != null && !texturePath.isEmpty()) {
            TextureLoader loader = new TextureLoader(texturePath, "LUMINANCE", new Container());
            Texture texture = loader.getTexture();

            texture.setBoundaryModeS(Texture.WRAP);
            texture.setBoundaryModeT(Texture.WRAP);
            texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 1.0f, 0.0f));
            TextureAttributes texAttr = new TextureAttributes();
            texAttr.setTextureMode(TextureAttributes.MODULATE);
            ap.setTexture(texture);
            ap.setTextureAttributes(texAttr);
        }

        return ap;
    }

    private void buildAcorn() {
        ShapeTransform shapeTransform = new ShapeTransform();
        int flags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;

        TransformGroup acornBody = shapeTransform.setShape(new Sphere(
                0.7f,
                flags,
                100,
                getAppearance(new Color(144, 69, 7), null)))
                .setScale(1.5f, 1.5f, 2.5f)
                .getTransformGroup();

        TransformGroup bottom = shapeTransform.setShape(new Cone(
                1f,
                2f,
                flags,
                getAppearance(new Color(42, 4, 4), null)))
                .rotX(-90)
                .setTranslation(0f, 0f, -1f)
                .getTransformGroup();

        TransformGroup roof = shapeTransform.setShape(new Sphere(0.7f, flags, 60,
                getAppearance(new Color(127, 85, 51), "sample\\img\\37015025-abstract-acorn-pattern-seamless-background-wood-texture.jpg")))
                .setTranslation(0f, 0f, 1.2f)
                .setScale(2f, 2f, 0.8f)
                .getTransformGroup();

        TransformGroup baseStick = shapeTransform.setShape(new Box(0.1f, 0.1f, 0.27f, flags,
                getAppearance(new Color(184, 131, 99), "sample\\img\\wood-tiles-dark-diffuse-600x600.jpg")))
                .rotX(-5)
                .setTranslation(0f, 0.1f, 2f)
                .getTransformGroup();

        TransformGroup mainStick = shapeTransform.setShape(new Box(0.1f, 0.1f, 0.8f, flags,
                getAppearance(new Color(184, 131, 99), "sample\\img\\wood-tiles-dark-diffuse-600x600.jpg")))
                .rotX(-20)
                .setTranslation(0, 0.18f, 2.4f)
                .getTransformGroup();

        transformGroup.addChild(bottom);
        transformGroup.addChild(acornBody);
        transformGroup.addChild(roof);
        transformGroup.addChild(baseStick);
        transformGroup.addChild(mainStick);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        transform3d.rotZ(z);
        transformGroup.setTransform(transform3d);
        TransformView.lookAt(new Point3d(viewDistance, .0f, viewHeight), new Point3d(.0f, .0f, .0f),
                new Vector3d(.0f, .0f, 1.0f));
        TransformView.invert();
        TransformGroupView.setTransform(TransformView);
    }
}

class ShapeTransform {
    private Primitive shape;
    private Transform3D transform3D;
    private TransformGroup transformGroup;
    public ShapeTransform() {
        init();
    }

    private void init() {
        shape = null;
        transformGroup = new TransformGroup();
        transform3D = new Transform3D();
    }

    public ShapeTransform setShape(Primitive shape) {
        this.shape = shape;
        return this;
    }

    public TransformGroup getTransformGroup() {
        if (shape == null) {
            throw new IllegalStateException("The shape has not yet been generated!");
        }
        transformGroup.setTransform(transform3D);
        transformGroup.addChild(shape);

        TransformGroup transformGroupToReturn = transformGroup;

        init();

        return transformGroupToReturn;
    }

    public ShapeTransform setTranslation(float x, float y, float z) {
        transform3D.setTranslation(new Vector3f(x, y, z));
        return this;
    }

    public ShapeTransform rotX(float degrees) {
        transform3D.rotX(degrees*Math.PI/180.0);
        return this;
    }

    public ShapeTransform setScale(double x, double y, double z) {
        transform3D.setScale(new Vector3d(x, y, z));
        return this;
    }
}
