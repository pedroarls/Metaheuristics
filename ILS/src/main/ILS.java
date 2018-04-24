package main;

import ILS.IteratedLocalSearch;

public class ILS {

    public static void main(String[] args) {

//        HillClimbingSteepestAscent hc = new HillClimbingSteepestAscent(2);
//        hc.start();
        for (int i = 0; i < 30; i++) {
            IteratedLocalSearch ils = new IteratedLocalSearch(2);
            ils.start();
        }

    }

}
