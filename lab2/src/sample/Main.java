package sample;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;


public class Main extends JPanel implements ActionListener {

    private static int maxWidth;
    private static int maxHeight;
    private int border;
    Timer timer;
    private double scale = 1;
    private double delta = 0.01;
    private double tx = 0;
    private double ty = 0;

    public Main() {
        timer = new Timer(10, this);
        timer.start();
        border = 5;
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Animated butterfly");
        frame.add(new Main());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750, 750);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        Dimension size = frame.getSize();
        Insets insets = frame.getInsets();
        maxWidth = size.width - insets.left - insets.right - 1;
        maxHeight = size.height - insets.top - insets.bottom - 1;
    }


    public void paint(Graphics g) {
        double dots[][] = {{0, 0}, {-100, -16}, {-40, -62}, {0, 0}, {100, -16}, {40, -62}, {0, 0}, {100, 16}, {40, 65},
                {0, 0}, {-100, 16}, {-40, 65}, {0, 0}};
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setBackground(Color.YELLOW);
        g2d.setColor(Color.RED);
        g2d.clearRect(0, 0, maxWidth + 1, maxHeight + 1);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        BasicStroke bs1 = new BasicStroke(border, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_BEVEL);
        g2d.setStroke(bs1);
        g2d.drawRect(100, 100, maxWidth - 100 * 2, maxHeight - 100 * 2);
        g2d.setStroke(new BasicStroke());
        g2d.translate(maxWidth / 2, maxHeight / 2);
        g2d.translate(tx, ty);
        GeneralPath wings = new GeneralPath();
        wings.moveTo(dots[0][0], dots[0][1]);
        for (int k = 1; k < dots.length; k++)
            wings.lineTo(dots[k][0], dots[k][1]);
        wings.closePath();

        g2d.scale(scale, 0.99);

        GradientPaint gpBody = new GradientPaint(5, 25,
                new Color(0x00D118), 20, 2, new Color(0x001792), true);
        g2d.setPaint(gpBody);
        g2d.fill(new Ellipse2D.Double(-10, -30, 10 * 2, 30 * 2));

        GradientPaint gpWings = new GradientPaint(5, 25,
                new Color(0xC338BB), 25, 5, new Color(0x0B361A), true);
        g2d.setPaint(gpWings);

        g2d.fill(wings);
        g2d.setColor(Color.BLACK);
        g2d.drawLine(-3, -16, -10, -65);
        g2d.drawLine(3, -16, 10, -65);
    }


    @Override
    public void actionPerformed(ActionEvent event) {
        if (scale < 0.01) {
            delta = -delta;
        } else if (scale > 0.99) {
            delta = -delta;
        }
        int imageWidth = 200;
        int horizontal = (maxWidth - 2 * (100 + border)) / 2 - imageWidth /2;
        int imageHeight = 120;
        int vertical = (maxHeight - 2 * (100 + border)) / 2 - imageHeight /2;
        double dx = 2;
        double dy = 2;
        if (ty > -vertical && tx <= -horizontal) {
            ty -= dy;
        } else if (ty < vertical && tx >= horizontal) {
            ty += dy;
        } else if (tx > -horizontal && ty >= vertical) {
            tx -= dx;
        } else
            tx += dx;
        scale += delta;
        repaint();
    }

}
