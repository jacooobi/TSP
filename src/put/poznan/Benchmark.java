package put.poznan;

import put.poznan.Solvers.*;
import put.poznan.Structures.Graph;
import put.poznan.Structures.Nodes;
import put.poznan.TSPSolution;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Benchmark {
    private ArrayList<SolverPair> solvers;

    private class DataRow {
        private Graph primaryGraph;
        private Graph secondaryGraph;
        private long time;
        private int cost;

        public DataRow(TSPSolution solution, long timeElapsed) {
            primaryGraph = solution.getPrimaryGraph();
            secondaryGraph = solution.getSecondaryGraph();
            cost = solution.calculateTotalCost();
            time = timeElapsed;
        }

        public String toString(String delimiter) {
            String primaryGraphString = primaryGraph.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining("-"));
            String secondaryGraphString = secondaryGraph.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining("-"));

            return primaryGraphString + delimiter
                    + secondaryGraphString + delimiter
                    + time + delimiter
                    + cost;
        }
    }

    private class SolverPair {
        private ISolver solver;
        private TSPSolution solution;
        private ArrayList<TSPSolution> solutionHistory;
        private ArrayList<DataRow> results;

        public SolverPair(ISolver solver) {
            this.solver = solver;
            this.solutionHistory = new ArrayList<>();
            this.results = new ArrayList<>();
        }

        private boolean swapSolution(TSPSolution newSolution) {
//            solutionHistory.add(solution);

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
            long timeStart = System.nanoTime();
            TSPSolution newSolution = this.solver.solve();
            long timeEnd = System.nanoTime();

            results.add(new DataRow(newSolution, timeEnd - timeStart));

            swapSolution(newSolution);
        }

        public void print() {
            solution.print();
        }
    }

    public Benchmark(Nodes tspInstance) {
        this.solvers = new ArrayList<>();
        this.solvers.add(new SolverPair(new GCHeuristic(tspInstance)));
        this.solvers.add(new SolverPair(new NNHeuristic(tspInstance)));
        this.solvers.add(new SolverPair(new LSSolver(tspInstance)));
//        this.solvers.add(new SolverPair(new ILSSolver(tspInstance)));
//        this.solvers.add(new SolverPair(new EASolver(tspInstance)));
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

    public void writeToFile(String filename) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new File(filename));
        StringBuilder sb = new StringBuilder();

        sb.append("name,primary_graph,secondary_graph,time_ns,cost\n");

        for (SolverPair pair : solvers) {
            String solverName = pair.solver.getName();

            for (DataRow row : pair.results) {
                sb.append(solverName);
                sb.append(",");
                sb.append(row.toString(","));
                sb.append("\n");
            }
        }

        pw.write(sb.toString());
        pw.close();
        System.out.println("Successfully saved to: " + filename + " file.");
    }
}
