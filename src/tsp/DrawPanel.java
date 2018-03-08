package tsp;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
* Toby (Lingxuan) Chang
* 5942636
* March 1st, 2018
* COSC 2p05 Assignment #3 TSP
* DrawPanel Class - does the drawing of every new global best route
*/

public class DrawPanel extends JPanel {
    
    private ARoute r; //the route to draw
    
    /**
     * @param route - the route to draw
     */
    public void draw(ARoute route) {
        this.r = route;
        repaint();
    }
    
    //draws both the node labels (vertices) and the edges
    private void drawPathsAndNodes_berlin(Graphics2D g2d) {
        for (int i = 0; i < r.getRouteSize(); i++) {
            g2d.fillOval((int) r.getC(i).getXCoor() / 2, (int) r.getC(i).getYCoor() / 2 + 50, 5, 5);
            g2d.drawString(String.valueOf(r.getC(i).getNodeNum()), (int) r.getC(i).getXCoor() / 2, (int) r.getC(i).getYCoor() / 2 + 50);
        }
        for (int i = 0; i < r.getRouteSize(); i++) { //wrap around connection of vertices (last city to first city)
            if (i + 1 < r.getRouteSize()) {
                g2d.drawLine((int) r.getC(i).getXCoor() / 2, (int) r.getC(i).getYCoor() / 2 + 50, (int) r.getC(i + 1).getXCoor() / 2, (int) r.getC(i + 1).getYCoor() / 2 + 50);
            } else {
                g2d.drawLine((int) r.getC(i).getXCoor() / 2, (int) r.getC(i).getYCoor() / 2 + 50, (int) r.getC(0).getXCoor() / 2, (int) r.getC(0).getYCoor() / 2 + 50);
            }
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.black);
        g2d.setStroke(new BasicStroke(1));

        if (r != null) {
            drawPathsAndNodes_berlin(g2d);
        }
    }
}
