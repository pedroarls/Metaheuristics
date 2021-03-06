package ILS;

import CSVUtils.CsvWriter;
import Function.Function;
import Function.Quadratica;
import Function.Rastrigin;
import HillClimbing.HillClimbingSteepestAscent;
import java.util.ArrayList;

public class IteratedLocalSearch {

    private int n;//quantidade de variáveis
    private Function funcaoObjetivo;
    private ArrayList<Double> S;
    private double MAX, MIN;//Intervalo da função
    private final int MAX_ITERACOES = 10000;
    private final int MAX_AMOSTRAS = 10;
    private static CsvWriter arquivo;

    static {
        arquivo = new CsvWriter("ILS");
    }

    public IteratedLocalSearch(int n) {
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
        pertubation = ((x * percentage) - (x * (-percentage))) * r + (x * (-percentage)) / r;

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

    private double iteratedLocalSearch_minimize(ArrayList<Double> solucaoLocal) {
        ArrayList<Double> R = new ArrayList<>();
        ArrayList<Double> W = new ArrayList<>();
        HillClimbingSteepestAscent hc = new HillClimbingSteepestAscent(this.n);
        double epsilon = Math.pow(10, -15);
        int i;

        this.S = solucaoLocal;

        System.out.println("----------------------------------------\n");

        System.out.println("INÍCIO\n");
        System.out.println("Ponto inicial : (" + S.get(0) + ", " + S.get(1) + ")");
        System.out.println("Função objetivo: " + funcaoObjetivo.calculate(S));
        System.out.print("\n");

        for (i = 0; (i < this.MAX_ITERACOES) && limitCheking(); i++) {
            //pertuba a solução a partir do resultado da busca local
            R = new ArrayList<Double>();
            R.addAll(0, this.S); // copia S para R
            pertubaSolucao(R);

            //Começa uma nova busca local a partir da solução pertubada
            W = hc.hillClimbing_minimize(R);

            if (this.funcaoObjetivo.calculate(W) < this.funcaoObjetivo.calculate(S)) {
                System.out.println("Ponto atual : (" + S.get(0) + ", " + S.get(1) + ")");
                System.out.println("Valor da função objetivo: " + funcaoObjetivo.calculate(S));
                System.out.print("\n");
                this.S = new ArrayList<>();
                this.S = W;
                W = new ArrayList<>();

            }

            //parar quando atingir uma solução satisfatória
            if ((funcaoObjetivo.calculate(S) - epsilon) <= 0) {
                break;
            }
        }

        System.out.println("Ponto final : (" + S.get(0) + ", " + S.get(1) + ")");
        System.out.println("Valor da função objetivo: " + funcaoObjetivo.calculate(S));
        System.out.print("\n");
        System.out.println("Quantidade de iterações: " + (i + 1) + "\n");

        System.out.println("FIM\n");

        System.out.println("----------------------------------------\n");

        arquivo.write(S.get(0), S.get(1), funcaoObjetivo.calculate(S), (i + 1));

        return -1;
    }

    private double iteratedLocalSearch_maximize(ArrayList<Double> solucaoLocal) {
        return 0.0;
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
            return iteratedLocalSearch_maximize(Sl);
        } else if (this.n == 2) {
            Sl = hc.hillClimbing_minimize(S);
//            System.out.println(funcaoObjetivo.calculate(Sl));
            return iteratedLocalSearch_minimize(Sl);
        } else {
            try {
                throw new Exception("Quantidade inválida de variáveis.");
            } catch (Exception ex) {

            }
        }

        return 0;

    }

}
