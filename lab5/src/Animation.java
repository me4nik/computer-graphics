import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.*;
import javax.vecmath.Vector3f;
import java.awt.event.*;

public class Animation extends KeyAdapter implements ActionListener {
    private TransformGroup butterflyTransformGroup;
    private Transform3D transform3D = new Transform3D();
    private static float distance = 0.02f;
    private static float angle = 0.05f;
    private float angleX;
    private float angleY;
    private float angleZ;
    private float horizontalLoc = 0;
    private float verticalLoc = 0;
    private boolean resetXRotation = false;
    private boolean resetYRotation = false;
    private boolean resetZRotation = false;
    private boolean rotatedPosY = false;
    private boolean rotatedNegY = false;
    private boolean pressedVKRight = false;
    private boolean pressedVKLeft = false;
    private boolean pressedVKUp = false;
    private boolean pressedVKDown = false;
    private boolean pressedW = false;
    private boolean pressedS = false;
    private boolean pressedA = false;
    private boolean pressedD = false;

    public Animation(Butterfly butterfly) {
        this.butterflyTransformGroup = butterfly.butterfly;
        this.butterflyTransformGroup.getTransform(this.transform3D);
        Timer timer = new Timer(20, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Move();
    }

    @Override
    public void keyPressed(KeyEvent ev) {
        switch (ev.getKeyCode()) {
            case KeyEvent.VK_UP:
                pressedVKUp = true;
                break;
            case KeyEvent.VK_DOWN:
                pressedVKDown = true;
                break;
            case 48:
                resetXRotation = true;
                resetYRotation = true;
                resetZRotation = true;
                break;
            case 87:
                pressedW = true;
                break;
            case 83:
                pressedS = true;
                break;
            case 65:
                if (!pressedA) {
                    pressedA = true;
                    rotatedNegY = true;
                }
                break;
            case 68:
                if (!pressedD) {
                    pressedD = true;
                    rotatedPosY = true;
                }
                break;
            case KeyEvent.VK_LEFT:
                pressedVKLeft = true;
                break;
            case KeyEvent.VK_RIGHT:
                pressedVKRight = true;
                break;
        }
    }

    private void Move() {
        if (pressedW) {
            verticalLoc += distance;
        }
        if (pressedS) {
            verticalLoc -= distance;
        }
        if (pressedA) {
            horizontalLoc -= distance;
        }
        if (pressedD) {
            horizontalLoc += distance;
        }
        transform3D.setTranslation(new Vector3f(horizontalLoc, verticalLoc, 0));

        if (pressedVKRight) {
            Transform3D rotation = new Transform3D();
            rotation.rotZ(angle);
            angleZ += angle;
            transform3D.mul(rotation);
        }
        if (pressedVKLeft) {
            Transform3D rotation = new Transform3D();
            rotation.rotZ(-angle);
            angleZ -= angle;
            transform3D.mul(rotation);
        }
        if (pressedVKUp) {
            Transform3D rotation = new Transform3D();
            rotation.rotX(-angle);
            angleX -= angle;
            transform3D.mul(rotation);
        }
        if (pressedVKDown) {
            Transform3D rotation = new Transform3D();
            rotation.rotX(angle);
            angleX += angle;
            transform3D.mul(rotation);
        }
        if (rotatedPosY) {
            Transform3D rotation = new Transform3D();
            rotation.rotY(degree(20));
            transform3D.mul(rotation);
            angleY += degree(20);
            rotatedPosY = false;
        }
        if (rotatedNegY) {
            Transform3D rotation = new Transform3D();
            rotation.rotY(degree(-20));
            transform3D.mul(rotation);
            angleY += degree(-20);
            rotatedNegY = false;
        }

        if (resetYRotation) {
            Transform3D rotation = new Transform3D();
            rotation.rotY(-angleY);
            transform3D.mul(rotation);

            resetYRotation = false;
            angleY = 0;
        }
        if (resetZRotation) {
            Transform3D rotation = new Transform3D();
            rotation.rotZ(-angleZ);
            transform3D.mul(rotation);
            resetZRotation = false;
            angleZ = 0;
        }
        if (resetXRotation) {
            Transform3D rotation = new Transform3D();
            rotation.rotX(-angleX);
            transform3D.mul(rotation);
            resetXRotation = false;
            angleX = 0;
        }
        butterflyTransformGroup.setTransform(transform3D);
    }

    @Override
    public void keyReleased(KeyEvent ev) {
        switch (ev.getKeyCode()) {
            case 87: // W
                pressedW = false;
                break;
            case 83: // S
                pressedS = false;
                break;
            case 65: // A
                pressedA = false;
                resetYRotation = true;
                break;
            case 68: // D
                pressedD = false;
                resetYRotation = true;
                break;
            case KeyEvent.VK_RIGHT:
                pressedVKRight = false;
                break;
            case KeyEvent.VK_LEFT:
                pressedVKLeft = false;
                break;
            case KeyEvent.VK_UP:
                pressedVKUp = false;
                break;
            case KeyEvent.VK_DOWN:
                pressedVKDown = false;
                break;
        }
    }

    private float degree(float degrees) {
        return (float) (degrees * Math.PI / 180);
    }
}
