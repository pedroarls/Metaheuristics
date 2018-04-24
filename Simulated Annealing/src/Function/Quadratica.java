package Function;

import java.util.List;

public class Quadratica implements Function{

    public double calculate(List<Double> variaveis) {
        return Math.pow(variaveis.get(0),2);
    }
    
}
