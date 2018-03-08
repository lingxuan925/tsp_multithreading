package tsp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;

/*
* Toby (Lingxuan) Chang
* 5942636
* March 1st, 2018
* COSC 2p05 Assignment #3 TSP
*/

public class MasterFrame extends JFrame {

    public MasterFrame(LeftPanel leftPanel, DrawPanel drawPanel) {
        setLayout(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(380, 300));
        drawPanel.setPreferredSize(new Dimension(900, 700));
        Container container = getContentPane();
        container.add(leftPanel, BorderLayout.WEST);
        container.add(drawPanel, BorderLayout.EAST);
        setResizable(false);
        pack();
        setTitle("TSP Solver");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
 
}
