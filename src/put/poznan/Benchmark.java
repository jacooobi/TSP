package put.poznan;

import put.poznan.Solvers.*;
import put.poznan.Structures.Graph;
import put.poznan.Structures.Nodes;
import put.poznan.TSPSolution;

import java.util.ArrayList;

public class Benchmark {
    private ArrayList<SolverPair> solvers;

    private class SolverPair {
        private ISolver solver;
        private TSPSolution solution;
        private ArrayList<TSPSolution> solutionHistory;

        public SolverPair(ISolver solver) {
            this.solver = solver;
            this.solutionHistory = new ArrayList<>();
        }

        private boolean swapSolution(TSPSolution newSolution) {
            solutionHistory.add(solution);

            if (solution == null) {
                solution = newSolution;
                return true;
            }

            int currentCost = this.solution.calculateTotalCost();
            int newCost = newSolution.calculateTotalCost();

            if (newCost < currentCost) {
                solution = newSolution;
                return true;
            }

            return false;
        }

        public void update() {
            TSPSolution newSolution = this.solver.solve();
            swapSolution(newSolution);
        }

        public void print() {
            solution.print();
        }
    }

    public Benchmark(Nodes tspInstance) {
        this.solvers = new ArrayList<>();
//        this.solvers.add(new SolverPair(new GCHeuristic(tspInstance)));
//        this.solvers.add(new SolverPair(new NNHeuristic(tspInstance)));
//        this.solvers.add(new SolverPair(new LSSolver(tspInstance)));
//        this.solvers.add(new SolverPair(new ILSSolver(tspInstance)));
        this.solvers.add(new SolverPair(new EASolver(tspInstance)));
    }

    public void test(int times) {
        for (SolverPair pair : solvers) {
            for (int i = 0; i < times; i++) {
                pair.update();
                System.out.println(pair.solver.getName() + ": " + i);
            }

            pair.print();
        }
    }
}
