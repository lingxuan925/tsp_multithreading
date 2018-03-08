package tsp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
* Toby (Lingxuan) Chang
* 5942636
* March 1st, 2018
* COSC 2p05 Assignment #3 TSP
*/

public class LeftPanel extends JPanel {
    
    private final JLabel fileL, globalBestL, numThreadL, numSearchL, numIteraionsL;
    private final JTextField fileTF, globalBestTF, numThreadTF, numSearchTF, numIterationsTF;
    private final JPanel textPane, labelPane;
    
    public LeftPanel() {
        super(new BorderLayout());
        
        labelPane = new JPanel(new GridLayout(15,1));
        fileL = new JLabel("File Path");
        globalBestL = new JLabel("Current Best");
        numThreadL = new JLabel("Number of Threads");
        numSearchL = new JLabel("Number of Searches");
        numIteraionsL = new JLabel("Number of Interations");
        labelPane.add(fileL);
        labelPane.add(globalBestL);
        labelPane.add(numThreadL);
        labelPane.add(numSearchL);
        labelPane.add(numIteraionsL);
        
        textPane = new JPanel(new GridLayout(15,1));
        fileTF = new JTextField(15);
        globalBestTF = new JTextField(15);
        globalBestTF.setEditable(false);
        numThreadTF = new JTextField(15);
        numSearchTF = new JTextField(15);
        numIterationsTF = new JTextField(15);
        textPane.add(fileTF);
        textPane.add(globalBestTF);
        textPane.add(numThreadTF);
        textPane.add(numSearchTF);
        textPane.add(numIterationsTF);
  
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(textPane, BorderLayout.EAST);
        add(labelPane, BorderLayout.WEST);
    }
    
    public JPanel getTextPane() {
        return textPane;
    }
    
    public String getFileName() {
        return fileTF.getText();
    }
    
    public int getPopulation() {
        return Integer.parseInt(numSearchTF.getText());
    }
    
    public int getNumThreads() {
        return Integer.parseInt(numThreadTF.getText());
    }
    
    public int getNumIterations() {
        return Integer.parseInt(numIterationsTF.getText());
    }
    
    public void setGlobalBest(double best) {
        globalBestTF.setText(String.valueOf(best));
    }
}
