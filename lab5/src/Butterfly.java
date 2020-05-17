import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.*;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class Butterfly extends JFrame {
    private BranchGroup root;
    private TransformGroup air = new TransformGroup();
    public TransformGroup butterfly;
    private Canvas3D canvas;
    private SimpleUniverse universe;
    private Map<String, Shape3D> map;
    public Butterfly() throws IOException {
        setWindow();
        configureCanvas();
        configureUniverse();
        root = new BranchGroup();
        root.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
        setBackground("src/img/blue-butterfly-wallpaper-iphone-2020-02-08-11-00-45.jpg");
        addLightToUniverse();
        ChangeViewAngle();
        butterfly = getButterflyGroup();
        air.addChild(butterfly);
        root.addChild(air);
        addAppearance();
        Animation animation = new Animation(this);
        canvas.addKeyListener(animation);
        root.compile();
        universe.addBranchGraph(root);
    }

    public static void main(String[] args) {
        try {
            Butterfly window = new Butterfly();
            window.setVisible(true);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void setWindow() {
        setTitle("Butterfly Monarch");
        setSize(3840, 2160);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void configureCanvas() {
        canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        canvas.setDoubleBufferEnable(true);
        canvas.setFocusable(true);
        add(canvas, BorderLayout.CENTER);
    }

    private void configureUniverse() {
        universe = new SimpleUniverse(canvas);
        universe.getViewingPlatform().setNominalViewingTransform();
    }

    private void setBackground(String path) {
        TextureLoader t = new TextureLoader(path, canvas);
        Background bg = new Background(t.getImage());
        bg.setImageScaleMode(Background.SCALE_FIT_ALL);
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        bg.setApplicationBounds(bounds);
        root.addChild(bg);
    }

    private void addLightToUniverse() {
        BoundingSphere bounds = new BoundingSphere();
        bounds.setRadius(2000);
        DirectionalLight directionalLight = new DirectionalLight(new Color3f(new Color(255, 255, 255)), new Vector3f(0, -0.5f, -0.5f));
        directionalLight.setInfluencingBounds(bounds);
        AmbientLight ambientLight = new AmbientLight(new Color3f(new Color(255, 255, 245)));
        ambientLight.setInfluencingBounds(bounds);
        root.addChild(directionalLight);
        root.addChild(ambientLight);
    }

    private TransformGroup getButterflyGroup() throws IOException {
        Transform3D transform3D = new Transform3D();
        transform3D.setTranslation(new Vector3d(1, 0, 0));
        TransformGroup group = getModelGroup("src/MONARCH.obj");
        group.setTransform(transform3D);
        return group;
    }

    private TransformGroup getModelGroup(String path) throws IOException {
        Scene scene = getSceneFromFile(path);
        map = scene.getNamedObjects();
        TransformGroup transformGroup = new TransformGroup();
        for (String s : map.keySet()) {
            Shape3D shape = map.get(s);
            scene.getSceneGroup().removeChild(shape);
            transformGroup.addChild(shape);
        }
        transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        return transformGroup;
    }


    private void addAppearance() {
        Appearance wings = new Appearance();
        wings.setMaterial(getMaterial(
                Color.BLACK,
                new Color(0x01234a)));
        map.get("mon_wing").setAppearance(wings);

        Appearance butterflyBody = new Appearance();
        butterflyBody.setMaterial(getMaterial(
                Color.BLACK,
                new Color(0x181300)));
        map.get("mon_body").setAppearance(butterflyBody);
    }

    Material getMaterial(
            Color emissiveColor,
            Color defaultColor) {
        Material material = new Material();
        material.setEmissiveColor(new Color3f(emissiveColor));
        material.setAmbientColor(new Color3f(defaultColor));
        material.setDiffuseColor(new Color3f(defaultColor));
        material.setSpecularColor(new Color3f(defaultColor));
        material.setShininess(64);
        material.setLightingEnable(true);
        return material;
    }

    private void ChangeViewAngle() {
        ViewingPlatform vp = universe.getViewingPlatform();
        TransformGroup vpGroup = vp.getMultiTransformGroup().getTransformGroup(0);
        Transform3D vpTranslation = new Transform3D();
        vpTranslation.setTranslation(new Vector3f(0, 0, 6));
        vpGroup.setTransform(vpTranslation);

    }

    public static Scene getSceneFromFile(String location) throws IOException {
        ObjectFile file = new ObjectFile(ObjectFile.RESIZE);
        file.setFlags(ObjectFile.RESIZE | ObjectFile.TRIANGULATE | ObjectFile.STRIPIFY);
        return file.load(new FileReader(location));
    }
}
