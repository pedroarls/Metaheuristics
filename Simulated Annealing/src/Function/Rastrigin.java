package Function;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Rastrigin implements Function{
    private final int A = 10;
    private final int n = 2;//quantidade de variáveis
    
    public double calculate(List<Double> variaveis){
        if(variaveis==null){
            try {
                throw new Exception("Quantidade de variáveis não é compatível com a função escolhida.");
            } catch (Exception ex) {
               
            }
        }
        else{
            double x0 = variaveis.get(0);
            double x1 = variaveis.get(1);
            
            double resultado = A*n+((Math.pow(x0,2)-A*Math.cos(2*Math.PI*x0))+((Math.pow(x1,2)-A*Math.cos(2*Math.PI*x1))));
            
            return resultado;
        }
        
        try {
            throw new Exception("Impossível calcular o valor da função nesse ponto");
        } catch (Exception ex) {
            Logger.getLogger(Rastrigin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
}
