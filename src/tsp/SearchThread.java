package tsp;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
* Toby (Lingxuan) Chang
* 5942636
* March 1st, 2018
* COSC 2p05 Assignment #3 TSP
* Note to self: make sure that there are absolutely no shared resources outside 
of locked/synchronized blocks (even if they are passed in by reference as 
parameters) or else it will cause issues like deadlocks
* SearchThread object - contains properties of each search thread (mainly the
* mutation)
*/

public class SearchThread implements Runnable {
    
    private final Cities cities; //the cities in this TSP problem
    private final int numIterations, tag;
    private ARoute localBestRoute; 
    private double localBest; //local best fitness
    private final Search newSearch; //monitor
    private int mutationCnter;
    
    public SearchThread(Cities cities, int numIterations, Search newSearch, int tag) { //pass in drawPanel here
        this.cities = new Cities(Helpers.cloneList(cities.getCities()));
        this.numIterations = numIterations;
        this.newSearch = newSearch;
        this.tag = tag;
    }

    public void mutate() { //does one mutation
        
        int[] originalIndex = new int[3];
        ArrayList<City> newPermutationTuple = new ArrayList(3);

        /*getting the 3 unique cities from the current aRoute object based on the
        node number of the first 3 cities in the cities arraylist (the node number
        is used as index for the aRoute object's arraylist
        */
        Helpers.shuffle(cities.getCities());
        for (int i = 0; i < 3; i++) {
            originalIndex[i] = cities.getCity(i).nodeNum - 1;
            newPermutationTuple.add(localBestRoute.getC(cities.getCity(i).nodeNum - 1));
        }
        while (!check(originalIndex, newPermutationTuple)) {
            Helpers.shuffle(newPermutationTuple);
        }
        //copy local best route and do mutation on the copy
        ARoute mutateRoute = new ARoute(Helpers.cloneList(localBestRoute.getRoute())); 

        //where mutation occurs
        for (int i = 0; i < originalIndex.length; i++) mutateRoute.setC(originalIndex[i], newPermutationTuple.get(i));  
        mutationCnter += 1; //for debugging
        
        if (isShorter(mutateRoute.getFitLevel())) {
            localBest = mutateRoute.getFitLevel();
            localBestRoute.setRoute(mutateRoute.getRoute());
            newSearch.updateGlobal(this);
            try { 
                Thread.sleep(30); //delay for drawing, change speed of animation
            } catch (InterruptedException ex) {
                Logger.getLogger(SearchThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //return ID of thread
    public int getTag() {
        return tag;
    }
    
    //return mutation counter (used for debugging)
    public int getMutationCnter() {
        return mutationCnter;
    }
    
    //return local best route
    public ARoute getLocalBestRoute() {
        return localBestRoute;
    }
    
    //return locat best fitness
    public double getLocalBest() {
        return localBest;
    }
    
    /**
     * checks if mutated route's fitness is greater than current local best 
     route's fitness, if it is, means mutated route has shorter distance
     * @param fitness
     * @return 
     */
    private boolean isShorter(double fitness) {
        return fitness > localBest;
    }
    
    /**makes sure shuffle does not give back same permutation 
     * originalIndex contains the index of the 3 cities that was added to the
    newPermutationTuple arraylist, after shuffling this arraylist, if I get
    the node number of the city at the original index in the aRoute arraylist
    and compare it with the node number of the city in the newPermutationTuple
    arraylist and they are equal for all 3 city comparison, then the shuffle
    did not change the original permutation
    */
    private boolean check(int[] originalIndex, ArrayList<City> newPerm) {
        for (int i=0; i<originalIndex.length; i++) if (localBestRoute.getC(originalIndex[i]).nodeNum != newPerm.get(i).nodeNum) return true;
        return false;
    }

    /**
     * thread will only go inside while loop and begin a new search if status
     is true
     * initializes a new starting route and initialize the local best fitness to
     that route's fitness
     * reset mutation counter to 0 (used for debugging)
     * starts the mutation
     */
    @Override
    public void run() {
        while (newSearch.getStatus(this)) {
            System.out.println("Thread "+getTag()+" start.");
            localBestRoute = new ARoute(cities);
            localBest = localBestRoute.getFitLevel();
            mutationCnter = 0;
            System.out.println("Thread "+getTag() + " start mutation");
            for (int i=0; i<numIterations; i++) mutate();
            System.out.println("Thread "+getTag()+" end.");
        }
        System.out.println(getTag()+" done!");
    } 
}
