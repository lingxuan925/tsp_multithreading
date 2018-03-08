package tsp;

import java.util.ArrayList;
import java.util.Random;

/*
* Toby (Lingxuan) Chang
* 5942636
* March 1st, 2018
* COSC 2p05 Assignment #3 TSP
* Helper static class - contains the shuffle and clone list method
* shuffe used for the evolutionary strategy mutation
* clonelist for deep copy 
*/

public class Helpers {
    
    //Fisher-Yates shuffle
    public static void shuffle(ArrayList<City> list) {
        int index;
        City temp;
        Random random = new Random();
        for (int i=list.size()-1; i>0; i--) {
            index = random.nextInt(i + 1);
            temp = list.get(index);
            list.set(index, list.get(i));
            list.set(i, temp);
        }
    }
    
    public static ArrayList<City> cloneList(ArrayList<City> tuple) {
        ArrayList<City> newTuple = new ArrayList<>();
        for (City city : tuple) {
            newTuple.add(city);
        }
        return newTuple;
    }
}
