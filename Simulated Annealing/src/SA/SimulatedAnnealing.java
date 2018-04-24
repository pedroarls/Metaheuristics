package SA;

import CSVUtils.CsvWriter;
import Function.Function;
import Function.Quadratica;
import Function.Rastrigin;
import HillClimbing.HillClimbingSteepestAscent;
import java.util.ArrayList;

public class SimulatedAnnealing {

    private double MAX, MIN;
    private ArrayList<Double> S;
    private int n; //quantidade de variáveis
    private Function funcaoObjetivo;
    private static CsvWriter arquivo;

    public SimulatedAnnealing(int n) {
        arquivo = new CsvWriter("SimulatedAnnealing");
        if (n < 0) {
            this.n = 1;
        } else if (n > 2) {
            this.n = 2;
        } else {
            this.n = n;
        }

        if (n == 1) {
            funcaoObjetivo = new Quadratica();
            MAX = 10;
            MIN = -10;
        } else if (n == 2) {
            funcaoObjetivo = new Rastrigin();
            MAX = 5.12;
            MIN = -5.12;
        }

        this.S = new ArrayList();

    }

    private double geraSolucaoAleatoria() {//gera uma solução aleatória com base na quantidade de variáveis
        double r = Math.random();

        return ((MAX - MIN) * r) + (MIN);
    }

    private double geraPertubacao(double x) {
        double r, percentage, pertubation;

        r = Math.random();
        percentage = 0.36;
//        pertubation = ((x * percentage) - (x * (-percentage))) * r + (x * (-percentage) / r);
        pertubation = ((x * percentage) - (x * (-percentage))) * r + (x * (-percentage));

//        double sigmoid = 1 / (1 + Math.pow(Math.E, -pertubation));
//        double rm = 2 * sigmoid - 0.5;
        if ((x + pertubation) > MAX) {
            return MAX;
        } else if ((x + pertubation) < MIN) {
            return MIN;
        }

        return x + pertubation;
    }

    private void pertubaSolucao(ArrayList<Double> solution) {
        for (int i = 0; i < this.n; i++) {
            solution.set(i, geraPertubacao(S.get(i)));//gera pertubação para cada variável
        }
    }

    public double sa_minimize(ArrayList<Double> initialSolution) {
        double T = 10000;
        double coolingRate = 0.003;
        double epsilon = Math.pow(10, -16);
        int i = 0;

        ArrayList<Double> best;
        ArrayList<Double> R;

        //S recebe a solucao inicial
        this.S = new ArrayList<>();
        S.addAll(initialSolution);

        //salva o melhor resultado encontrado ate o momento
        best = new ArrayList<>();
        best.clear();
        best.addAll(S);

        while (T > 1 && (funcaoObjetivo.calculate(best) - epsilon >= 0)) {
            //copia S para R
            R = new ArrayList<Double>();
            R.clear();
            R.addAll(S);

            //pertuba a solucao em R
            pertubaSolucao(R);

            //se a qualidade da solucao R e melhor do que a solucao S
            boolean qualityIsBetter = funcaoObjetivo.calculate(R) < funcaoObjetivo.calculate(S);

            //verifica se aceita a solução R ou não
            double r = Math.random();
            double boltzmanConstant = Math.pow(Math.E, (funcaoObjetivo.calculate(R) - funcaoObjetivo.calculate(S)) / T);
            boolean metropolisCriteria = r < boltzmanConstant;

            if (qualityIsBetter || metropolisCriteria) {
                S = new ArrayList<>();
                S.addAll(R);
                R = new ArrayList<>();
            }

            if (funcaoObjetivo.calculate(S) < funcaoObjetivo.calculate(best)) {
                best = new ArrayList<>();
                best.addAll(S);
                System.out.println(funcaoObjetivo.calculate(best));
                
            }
            T *= (1 - coolingRate);

            i++;
        }
        
        arquivo.write(best.get(0), best.get(1), funcaoObjetivo.calculate(best), i);
        return funcaoObjetivo.calculate(best);
    }

    public double sa_maximize(ArrayList<Double> Sl) {
        return 0;
    }

    public double start() {
        ArrayList<Double> Sl;

        //Gera solução inicial
        this.S = new ArrayList<>();
        for (int i = 0; i < this.n; i++) {
            double d = geraSolucaoAleatoria();
            S.add(d);
        }

        HillClimbingSteepestAscent hc = new HillClimbingSteepestAscent(this.n);

        //Busca local a partir da solução inicial
        if (this.n == 1) {
            Sl = hc.hillClimbing_maximize(S);
//            System.out.println(funcaoObjetivo.calculate(Sl));
            return sa_maximize(Sl);
        } else if (this.n == 2) {
            Sl = hc.hillClimbing_minimize(S);
//            System.out.println(funcaoObjetivo.calculate(Sl));
            return sa_minimize(Sl);
        } else {
            try {
                throw new Exception("Quantidade inválida de variáveis.");
            } catch (Exception ex) {

            }
        }

        return 0;

    }
}
