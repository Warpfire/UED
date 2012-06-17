/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import javax.swing.JPanel;

/**
 *
 * @author Niels Visser
 */
public class CalibrationPanel extends JPanel {

    private int width;
    private int height;
    private int step;
    private static CalibrationPanel instance = null;
    private Point[] points;
    private Point[] renderAt;
    private double radius = 10;
    private CalibrationTranslator translator = null;

    private CalibrationPanel() {
        width = 1024;
        height = 1024;
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.black);
        this.setVisible(true);
        this.step = 0;
        this.points = new Point[4];
        this.renderAt = new Point[4];
        this.renderAt[0] = new Point(20, 20);
        this.renderAt[1] = new Point(1004, 20);
        this.renderAt[2] = new Point(1004, 748);
        this.renderAt[3] = new Point(20, 748);
    }

    public static CalibrationPanel getInstance() {
        if (instance == null) {
            instance = new CalibrationPanel();
        }
        return instance;
    }

    public void processTouch(Point p) {
        if(this.step > this.points.length-1)
            return;
        this.points[this.step] = p;
        this.step++;
        if(this.step > this.points.length-1) {
            this.setVisible(false);
            this.translator = new CalibrationTranslator(this.points, this.renderAt);
        }
        this.invalidate();
    }

    public boolean isDone() {
        return this.step >= 4;
    }
    
    public CalibrationTranslator getTranslator() {
        return this.translator;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(1,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
        if(this.step < this.points.length) {
            Point pos = this.renderAt[this.step];
            Shape circle = new Ellipse2D.Double(((double)pos.x)-this.radius, ((double)pos.y)-this.radius, this.radius*2d, this.radius*2d);
            g2d.setColor(Color.red);
            g2d.fill(circle);
        }
    }
}
