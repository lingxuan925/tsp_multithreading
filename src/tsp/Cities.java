package tsp;

import java.util.ArrayList;
/*
* Toby (Lingxuan) Chang
* 5942636
* March 1st, 2018
* COSC 2p05 Assignment #3 TSP
*/

/**
* cities object:
* holds all the city object created based on the information read of input file
*/
public class Cities {

    private final ArrayList<City> cities;
    private final int numOfCities;

    public Cities(int num) {
        cities = new ArrayList<>(num);
        numOfCities = num;
    }
    
    public Cities(ArrayList<City> copy) {
        cities = copy;
        numOfCities = copy.size();
    }
    
    public ArrayList<City> getCities() {
        return cities;
    }

    public int getSize() {
        return numOfCities;
    }

    public void addCity(City c) {
        cities.add(c);
    }

    public City getCity(int idx) {
        return cities.get(idx);
    }
}
