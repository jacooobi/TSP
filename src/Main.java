import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        ArrayList<TSPSolution> solutions = new ArrayList();

        DataReader dr = new DataReader("kroA100");
        ArrayList<HashMap> tspInstance = dr.load();

        NNHeuristic nnSolver = new NNHeuristic(tspInstance);
        GCHeuristic gcSolver = new GCHeuristic(tspInstance);
        LSSolver lsSolver = new LSSolver(tspInstance);
        ILSSolver ilsSolver = new ILSSolver(tspInstance);

//        solutions.add(nnSolver.solve());
//        solutions.add(gcSolver.solve());
        solutions.add(lsSolver.solve());
//        solutions.add(ilsSolver.solve());

        for (TSPSolution solution : solutions) {
            solution.print();
        }
    }
}
