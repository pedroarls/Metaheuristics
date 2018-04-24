package principal;

import SA.SimulatedAnnealing;
import java.io.File;

public class SA {

    public static void main(String[] args) {
   
//        File file = new File(fileName);
//        if (file.exists()) {
//            file.delete();
//        }
        
        
        for (int i = 0; i < 30; i++) {
            SimulatedAnnealing sa = new SimulatedAnnealing(2);
            System.out.println(sa.start());
        }

    }

}
