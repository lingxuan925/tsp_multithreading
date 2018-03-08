package tsp;
/*
* Toby (Lingxuan) Chang
* 5942636
* March 1st, 2018
* COSC 2p05 Assignment #3 TSP
*/

/**
* city object:
* x and y coordinate of the city obtain from input file
* node number of the city from the input file
* function to calculate the EUC distance value between current city and the
city passed in as the parameter (pythagorean theorem)
*/
public class City {

    int nodeNum;
    double x_coor;
    double y_coor;

    public City(int node, double x, double y) {
        this.x_coor = x;
        this.y_coor = y;
        this.nodeNum = node;
    }

    public int getNodeNum() {
        return nodeNum;
    }

    public double getXCoor() {
        return this.x_coor;
    }

    public double getYCoor() {
        return this.y_coor;
    }
    //pythagorean theorem 
    public double getDist_UsingEUC(City c) {
        return Math.sqrt(Math.pow(Math.abs(this.x_coor - c.getXCoor()), 2) + Math.pow(Math.abs(this.y_coor - c.getYCoor()), 2));
    }
}
