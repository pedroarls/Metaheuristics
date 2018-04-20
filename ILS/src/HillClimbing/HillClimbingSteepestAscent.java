package HillClimbing;

import Function.Function;
import Function.Quadratica;
import Function.Rastrigin;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HillClimbingSteepestAscent {

    private int n;//quantidade de variáveis
    private Function funcaoObjetivo;
    private ArrayList<Double> S;
    private double MAX, MIN;//Intervalo da função
    private final int MAX_ITERACOES = 500;
    private final int MAX_AMOSTRAS = 10;

    public HillClimbingSteepestAscent(int n) {
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

    private boolean limitCheking() {//verifica se está dentro dos limites
        for (int i = 0; i < this.n; i++) {

            if (this.S.get(i) > MAX) {
                return false;
            } else if (S.get(i) < MIN) {
                return false;
            } else {
                return true;
            }
        }

        return false;
    }

    public ArrayList<Double> hillClimbing_maximize(ArrayList<Double> solucaoInicial) {
        int i;
        ArrayList<Double> R = new ArrayList<Double>();
        ArrayList<Double> W = new ArrayList<Double>();

//        this.S = new ArrayList<>();
//        for (i = 0; i < this.n; i++) {
//            double d = geraSolucaoAleatoria();
//            S.add(d);
//        }
        this.S = solucaoInicial;//recebe solucao inicial aleatoria

        for (i = 0; (i < this.MAX_ITERACOES) && limitCheking(); i++) {
            R = new ArrayList<Double>();
            R.addAll(0, this.S); // copia S para R
            pertubaSolucao(R);

            for (int j = 0; j < MAX_AMOSTRAS; j++) {
                W = new ArrayList<Double>();
                W.addAll(0, R); // copia S para R
                pertubaSolucao(W);

                if (funcaoObjetivo.calculate(W) > funcaoObjetivo.calculate(R)) {
                    R.clear();
                    R.addAll(0, W);
                    W.clear();
                }
            }

            if (funcaoObjetivo.calculate(R) > funcaoObjetivo.calculate(S)) {
//                System.out.println("Antes: " + funcaoObjetivo.calculate(S) + "\t" + "Depois: " + funcaoObjetivo.calculate(R));
                S.clear();
                S.addAll(0, R);
                R.clear();
            }
        }

//        System.out.println("Final: " + funcaoObjetivo.calculate(S) + "\t");
//        System.out.println("Quantidade de iterações: " + i);

        return S;

    }

    public ArrayList<Double> hillClimbing_minimize(ArrayList<Double> solucaoInicial) {
        int i;
        ArrayList<Double> R = new ArrayList<Double>();
        ArrayList<Double> W = new ArrayList<Double>();

        this.S = solucaoInicial;//recebe solucao inicial aleatoria

        for (i = 0; (i < this.MAX_ITERACOES) && limitCheking(); i++) {
            R = new ArrayList<Double>();
            R.addAll(0, this.S); // copia S para R
            pertubaSolucao(R);

            for (int j = 0; j < MAX_AMOSTRAS; j++) {
                W = new ArrayList<Double>();
                W.addAll(0, R); // copia S para R
                pertubaSolucao(W);

                if (funcaoObjetivo.calculate(W) < funcaoObjetivo.calculate(R)) {
                    R.clear();
                    R.addAll(0, W);
                    W.clear();
                }
            }

            if (funcaoObjetivo.calculate(R) < funcaoObjetivo.calculate(S)) {
//                System.out.println("Antes: " + funcaoObjetivo.calculate(S) + "\t" + "Depois: " + funcaoObjetivo.calculate(R));
                S.clear();
                S.addAll(0, R);
                R.clear();
            }
        }

//        System.out.println("Final: " + funcaoObjetivo.calculate(S) + "\t");
//        System.out.println("Quantidade de iterações: " + i);
        return S;

    }

    public ArrayList<Double> start() {

        this.S = new ArrayList<>();
        for (int i = 0; i < this.n; i++) {
            double d = geraSolucaoAleatoria();
            S.add(d);
        }

        if (this.n == 1) {
            return hillClimbing_maximize(S);
        } else if (this.n == 2) {
            return hillClimbing_minimize(S);
        } else {
            try {
                throw new Exception("Quantidade inválidade de variáveis.");
            } catch (Exception ex) {

            }
        }

        return null;
    }

}
