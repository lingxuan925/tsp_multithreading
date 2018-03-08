package tsp;

import java.util.ArrayList;
/*
* Toby (Lingxuan) Chang
* 5942636
* March 1st, 2018
* COSC 2p05 Assignment #3 TSP
*/
/**
* ARoute object: a possible path to travel all cities and return to start
* 1st constructor creates an empty ARoute object
* 2nd constructor creates an ARoute object with an array list passed in (usually a deep copy)
* 3rd constructor creates an ARoute object and adds each city object in the
Cities object and uses the shuffle method from Helpers class to randomize
the ordering of the cities
*/
public class ARoute {
    private ArrayList<City> aRoute = new ArrayList<>();
    
    public ARoute(int size) {
        for (int i = 0; i < size; i++) aRoute.add(null);
    }

    public ARoute(ArrayList<City> aRoute) {
        this.aRoute = aRoute;
    }

    public ARoute(Cities cities) {
        for (int i=0; i<cities.getSize(); i++) aRoute.add(cities.getCity(i));
        Helpers.shuffle(aRoute);
    }
    
    public void setRoute(ArrayList<City> aRoute) {
        this.aRoute = aRoute;
    }
    
    public ArrayList<City> getRoute() {
        return aRoute;
    }

    /**
    * function to get total distance of an ARoute object by calculating the
    distance between each city using the getDist_UsingEUC function from the
    city object
    */
    public double getTotalRouteDist() {
        int totalDist = 0;
        for (int i=0; i<aRoute.size(); i++) {
            if (i+1 < aRoute.size()) totalDist += aRoute.get(i).getDist_UsingEUC(aRoute.get(i+1));
            else totalDist += aRoute.get(i).getDist_UsingEUC(aRoute.get(0));
        }
        return totalDist;
    }

    /**
    fit level is calculated as the reciprocal of the total distance, thus the
    greater the value is, the more fit the route is
    */
    public double getFitLevel() {
        return 1.0/(double)getTotalRouteDist();
    }

    public int getRouteSize() {
        return aRoute.size();
    }

    //used for mutation
    public void setC(int idx, City c) {
        aRoute.set(idx, c);
    }

    //used for mutation
    public City getC(int idx) {
        return aRoute.get(idx);
    }
}