import javax.media.j3d.*;
import javax.imageio.ImageIO;
import javax.vecmath.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.behaviors.vp.*;
import javax.swing.JFrame;
import com.sun.j3d.loaders.*;
import com.sun.j3d.loaders.objectfile.*;

public class Bug extends JFrame{
    private Hashtable roachNamedObjects;
    private Canvas3D canvas;

    public static void main(String[] args){
        new Bug();
    }

    public Bug(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        SimpleUniverse simpleUniverse = new SimpleUniverse(canvas);
        simpleUniverse.getViewingPlatform().setNominalViewingTransform();
        createSceneGraph(simpleUniverse);
        addLight(simpleUniverse);
        OrbitBehavior ob = new OrbitBehavior(canvas);
        ob.setSchedulingBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE));
        simpleUniverse.getViewingPlatform().setViewPlatformBehavior(ob);
        setTitle("Bug animation");
        setSize(1000,700);
        getContentPane().add("Center", canvas);
        setVisible(true);
    }

    private void setBackground(String path, BranchGroup branchGroup) {
        TextureLoader t = new TextureLoader(path, canvas);
        Background background = new Background(t.getImage());
        background.setImageScaleMode(Background.SCALE_FIT_ALL);
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        background.setApplicationBounds(bounds);
        branchGroup.addChild(background);
    }

    private void createSceneGraph(SimpleUniverse su){
        ObjectFile f = new ObjectFile(ObjectFile.RESIZE);
        Scene bugScene = null;
        try {
            bugScene = f.load("src/ladybug.obj");
        }
        catch (Exception e){
            System.out.println("File loading failed:" + e);
        }
        Transform3D scaling = new Transform3D();
        scaling.setScale(1.0 / 6);
        Transform3D transform = new Transform3D();
        transform.rotX(-3 * Math.PI / 2);
        transform.rotY(-3 * Math.PI / 2);
        transform.mul(scaling);
        TransformGroup transformGroupBug = new TransformGroup(transform);
        TransformGroup sceneGroup = new TransformGroup();
        roachNamedObjects = bugScene.getNamedObjects();
        BranchGroup scene = new BranchGroup();
        TransformGroup transformGroupBody = new TransformGroup();
        Shape3D body_bug = (Shape3D) roachNamedObjects.get("ladybug");
        body_bug.setAppearance(loadTexture("src/img/ladybug.jpg", true));
        transformGroupBody.addChild(body_bug.cloneTree());
        int noRotHour = 800;
        int timeRotationHour = 200;
        BoundingSphere boundingSphere = new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE);
        Transform3D legRotAxis = new Transform3D();
        legRotAxis.rotZ(Math.PI/2);
        Transform3D leg2RotAxis = new Transform3D();
        sceneGroup.addChild(animateLeg("leg1", noRotHour, timeRotationHour, legRotAxis, boundingSphere, (float) Math.PI/8, 100));
        sceneGroup.addChild(animateLeg("leg2", noRotHour, timeRotationHour, legRotAxis, boundingSphere, (float) Math.PI/8, 200));
        sceneGroup.addChild(animateLeg("leg3", noRotHour, timeRotationHour, legRotAxis, boundingSphere, (float) Math.PI/8, 300));
        sceneGroup.addChild(animateLeg("leg4", noRotHour, timeRotationHour, leg2RotAxis, boundingSphere, -(float) Math.PI/8, 100));
        sceneGroup.addChild(animateLeg("leg5", noRotHour, timeRotationHour, leg2RotAxis, boundingSphere, -(float) Math.PI/8, 200));
        sceneGroup.addChild(animateLeg("leg6", noRotHour, timeRotationHour, leg2RotAxis, boundingSphere, -(float) Math.PI/8, 300));
        sceneGroup.addChild(transformGroupBody.cloneTree());
        Transform3D transformCrawl = new Transform3D();
        transformCrawl.rotY(-Math.PI/2);
        long crawlTime = 10000;
        Alpha crawl = new Alpha(1, Alpha.INCREASING_ENABLE, 0, 0, crawlTime,0,0,0,0,0);
        float crawlDistance = 6.0f;
        PositionInterpolator posICrawl = new PositionInterpolator(crawl, sceneGroup, transformCrawl, -6.0f, crawlDistance);

        posICrawl.setSchedulingBounds(boundingSphere);
        sceneGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        sceneGroup.addChild(posICrawl);
        transformGroupBug.addChild(sceneGroup);
        scene.addChild(transformGroupBug);
        setBackground("src/img/il_570xN.1657383853_gf6h.jpg", scene);
        scene.compile();
        su.addBranchGraph(scene);
    }

    private TransformGroup animateLeg(String elementName, int noRotHour, long timeRotationHour, Transform3D legRotAxis, Bounds bs, float v, int l){
        Alpha rotateLeg = new Alpha(noRotHour, Alpha.INCREASING_ENABLE, l,0, timeRotationHour,
                0,0,0,0,0);
        Shape3D leg = (Shape3D) roachNamedObjects.get(elementName);
        TransformGroup transformGroup = new TransformGroup();
        transformGroup.addChild(leg.cloneTree());
        RotationInterpolator legRotation = new RotationInterpolator(rotateLeg, transformGroup, legRotAxis, v,0.0f);
        legRotation.setSchedulingBounds(bs);
        transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        transformGroup.addChild(legRotation);
        return transformGroup;
    }

    private void addLight(SimpleUniverse su){
        BranchGroup bgLight = new BranchGroup();
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
        Color3f lightColour = new Color3f(1.0f,1.0f,1.0f);
        Vector3f lightDir = new Vector3f(-1.0f,0.0f,-0.5f);
        DirectionalLight light = new DirectionalLight(lightColour, lightDir);
        light.setInfluencingBounds(bounds);
        bgLight.addChild(light);
        su.addBranchGraph(bgLight);
    }

    private Image loadImage(String fileName) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return img;
    }

    private Appearance loadTexture(String fileName, boolean emit) {
        Appearance app = new Appearance();

        TextureLoader loader = new TextureLoader(loadImage(fileName), null);
        Texture2D texture = (Texture2D)loader.getTexture();
        texture.setMinFilter(texture.BASE_LEVEL_LINEAR);
        texture.setMagFilter(texture.BASE_LEVEL_LINEAR);

        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);
        app.setTextureAttributes(texAttr);
        app.setTexture(texture);
        Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
        Color3f black = new Color3f(0f, 0f, 0f);
        if (emit)
            app.setMaterial(new Material(black, white, black, black, 4.0f));
        else
            app.setMaterial(new Material(white, black, white, white, 4.0f));
        return app;
    }
}
