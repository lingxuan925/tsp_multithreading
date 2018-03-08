package tsp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/*
* Toby (Lingxuan) Chang
* 5942636
* March 1st, 2018
* COSC 2p05 Assignment #3 TSP
* main class - initializes the left and draw panel and adds it to the main frame
* implements action listener for the run search button
*/

public class TSP implements ActionListener {
    
    private File f;
    private Scanner sc;
    private final LeftPanel leftPanel;
    private final DrawPanel drawPanel;
    private final JButton runBtn;
    private Cities cities = null;
    private int numSearch, numIterations, numThreads;
    
    public TSP() {
        leftPanel = new LeftPanel();
        drawPanel = new DrawPanel();
        runBtn = new JButton("Run Search");
        runBtn.addActionListener(this);
        leftPanel.getTextPane().add(runBtn);
        
        MasterFrame mF = new MasterFrame(leftPanel, drawPanel);
    }
    
    /**
     * reads in file to use as input (berlin52.txt) and extract the necessary information
     */
    private Cities readFileAndInit(Cities cities) {
        try {
            f = new File(leftPanel.getFileName());
            System.out.println("You chose to open this file: " + f.getName());
            sc = new Scanner(f);
            cities = new Cities(sc.nextInt());
            for (int i=0; i<cities.getSize(); i++) {
                City newCity = new City(sc.nextInt(), sc.nextDouble(), sc.nextDouble());
                cities.addCity(newCity);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TSP.class.getName()).log(Level.SEVERE, null, ex);
        }      
        return cities;
    }   
    
    //set search parameters by reading from left panel
    private void setSearchParameters() {
        numIterations = leftPanel.getNumIterations();
        numThreads = leftPanel.getNumThreads();
        numSearch = leftPanel.getPopulation();
    }
     
    /**
     * when you click the run search button, reads file, sets parameters,
     initialize search threads and search monitor, starts the threads
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Run Search")) {
            cities = readFileAndInit(cities);
            setSearchParameters();
            SearchThread[] searchThreads = new SearchThread[numThreads];
            Search newSearch = new Search(cities, drawPanel, leftPanel, numSearch); //should probably pass in the thread here and not in mutate
            Thread[] threadPool = new Thread[numThreads];
            for (int i=0; i<numThreads; i++) {
                searchThreads[i] = new SearchThread(cities, numIterations, newSearch, i); //pass in drawPanel
                threadPool[i] = new Thread(searchThreads[i], "Thread "+i);
                System.out.println(threadPool[i].getName()+" starting...");
                threadPool[i].start();
            }
        }
        else System.exit(0);
    }
    
    public static void main(String[] args) {
        TSP tsp = new TSP();
    }
}
