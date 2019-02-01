package put.poznan;

import put.poznan.Solvers.GCHeuristic;
import put.poznan.Solvers.ILSSolver;
import put.poznan.Solvers.LSSolver;
import put.poznan.Solvers.NNHeuristic;
import put.poznan.Structures.Nodes;
import put.poznan.TSPSolution;

public class Benchmark {
    private LSSolver lsSolver;
    private ILSSolver ilsSolver;
    private NNHeuristic nnSolver;
    private GCHeuristic gcSolver;

    private TSPSolution bestLsSolution;
    private TSPSolution bestIlsSolution;
    private TSPSolution bestGcSolution;
    private TSPSolution bestNnSolution;

    public Benchmark(Nodes tspInstance) {
        this.lsSolver = new LSSolver(tspInstance);
        this.ilsSolver = new ILSSolver(tspInstance);
        this.nnSolver = new NNHeuristic(tspInstance);
        this.gcSolver = new GCHeuristic(tspInstance);
    }

    public void test(int times) {
        bestLsSolution = lsSolver.solve();
        bestIlsSolution = ilsSolver.solve();
        bestGcSolution = gcSolver.solve();
        bestNnSolution = nnSolver.solve();

        for (int i = 0; i < times - 1; i++) {
            TSPSolution solution;
        }
    }
}
