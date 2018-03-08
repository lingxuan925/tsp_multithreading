package tsp;

/**
* Toby (Lingxuan) Chang
* 5942636
* March 1st, 2018
* COSC 2p05 Assignment #3 TSP
* Search Class - it is the monitor that make sure only one thread can read and/or
write to the shared resources at one time such as globalBestRoute, globalBest,
numSearchSoFar, numSearch, drawPanel, notDone in order to prevent deadlocks
*Note: output on console was used for debugging purposes, when you see threads
beginning a new search that is greater than the specified number of search, it 
is correct because the status is false which means it never entered the while
loop block to actually begin the search
*/

public class Search {
    
    private final ARoute globalBestRoute; //global best route
    private volatile double globalBest; //global best fitness
    private int numSearchSoFar; //count of searches done by threads so far
    private volatile int numSearch; //total number of searches to do
    private final DrawPanel drawPanel; //panel to do the drawing
    private final LeftPanel leftPanel; //panel with the search parameters and btn
    //true means threads have not yet completed total number of searches specified
    private boolean notDone; //false means total number of searches has been completed
    
    public Search(Cities cities, DrawPanel drawPanel, LeftPanel leftPanel, int numSearch) {
        globalBest = 0;
        this.drawPanel = drawPanel;
        this.leftPanel = leftPanel;
        this.numSearch = numSearch;
        globalBestRoute = new ARoute(cities.getSize());
        numSearchSoFar = 0;
        notDone = true;
    }
    
    /**
     * the current thread that has lock on this method block will check if its
     local best fitness is greater than the global best fitness, if it is, it 
     will update the global best fitness with its local best fitness 
     * it will also set the global best route to this thread's local best route
     * it will also set the new global best route's total distance on the left
     panel's current global best text field
     * then it will update the draw panel with this new global best route
     * globalBest is declared as volatile because it is being read and write to
     so its value can change at any time, so it needs to be refreshed each time
     a thread access it to get the most up-to-date value
     * when this method block ends, the thread will release the lock and signal
     any threads that are waiting on this lock 
     * @param t - the thread accessing and acquiring lock on this method block
     */
    public synchronized void updateGlobal(SearchThread t) {
        if (t.getLocalBest() > globalBest) {
            globalBest = t.getLocalBest();
            globalBestRoute.setRoute(Helpers.cloneList(t.getLocalBestRoute().getRoute()));
            leftPanel.setGlobalBest(globalBestRoute.getTotalRouteDist());
            System.out.println("Thread "+t.getTag()+": "+globalBestRoute.getTotalRouteDist()+" after "+t.getMutationCnter()+" mutations!");
            updatePanel();
        }
    }
    
    /**
     * this method is called in the getStatus method which is also synchronized
     * the current thread that has lock on this method block subtracts one from
     total number of searches, then it checks if total number of searches is
     less than or equal to 0, if it is, it will set notDone to false, which 
     means no more threads needs to begin new searches since total number of 
     searches has been done
     * numSearch is declared as volatile because it is both being read and 
     written to
     * @param t - the thread accessing and acquiring lock on this method block
     */
    public void updateNumSearch(SearchThread t) {
        numSearch--;
        if (numSearch <= 0) notDone = false;
        numSearchSoFar++;
        System.out.println("Thread "+t.getTag()+" began "+numSearchSoFar+"th search...");
        System.out.println("Status is "+notDone);
    }
    
    /**
     * this method is called in the updateGlobal method block which is also 
     synchronized
     * it calls the draw method in the DrawPanel class which calls repaint
     * this in turn calls the paint component which in turns calls the method 
     that will draw the route that was passed in which is the current global
     best route
     */
    private void updatePanel() {
        drawPanel.draw(globalBestRoute);
    }
    
    /**
     * returns true if total number of searches has not been reached, otherwise
     false meaning total number of searches has been reached
     * this boolean value is returned as the expression in the while loop block
     of the run method of the SearchThread class
     * @param t - the thread accessing and acquiring lock on this method block
     * @return notDone 
     */
    public synchronized boolean getStatus(SearchThread t) {       
        updateNumSearch(t);
        return notDone;
    }
    
    public ARoute getGlobalBestRoute() {
        return globalBestRoute;
    }
}
