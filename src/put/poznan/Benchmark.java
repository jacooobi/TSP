package put.poznan;

import put.poznan.Structures.InstancePair;
import put.poznan.Solvers.*;
import put.poznan.Structures.Graph;
import put.poznan.Structures.Nodes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Benchmark {
    private ArrayList<SolverPair> solvers;

    private class DataRow {
        private Graph primaryGraph;
        private Graph secondaryGraph;
        private long time;
        private int totalCost;
        private int primaryGraphCost;
        private int secondaryGraphCost;

        public DataRow(TSPSolution solution, long timeElapsed) {
            primaryGraph = solution.getPrimaryGraph();
            secondaryGraph = solution.getSecondaryGraph();
            time = timeElapsed;
            totalCost = solution.calculateTotalCost();
            primaryGraphCost = solution.calculateTotalCost(primaryGraph);
            secondaryGraphCost = solution.calculateTotalCost(secondaryGraph);
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
                    + primaryGraphCost + delimiter
                    + secondaryGraphCost + delimiter
                    + totalCost;
        }
    }

    private class SolverPair {
        private ISolver solver;
        private TSPSolution solution;
        private String instanceName;
        private ArrayList<DataRow> results;

        public SolverPair(ISolver solver, String instanceName) {
            this.solver = solver;
            this.results = new ArrayList<>();
            this.instanceName = instanceName;
        }

        private boolean swapSolution(TSPSolution newSolution) {
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

    public Benchmark(ArrayList<InstancePair> tspInstances) {
        this.solvers = new ArrayList<>();

        for (InstancePair instancePair : tspInstances) {
            String instanceName = instancePair.getName();
            Nodes tspInstance = instancePair.getNodes();

            this.solvers.add(new SolverPair(new GCHeuristic(tspInstance, "gc"), instanceName));
            this.solvers.add(new SolverPair(new NNHeuristic(tspInstance, "nn"), instanceName));
            this.solvers.add(new SolverPair(new LSSolver(tspInstance, "ls-random"), instanceName));
            this.solvers.add(new SolverPair(new LSSolver(tspInstance, "ls-nn", InitializationType.NN), instanceName));
            this.solvers.add(new SolverPair(new ILSSolver(tspInstance, "ils"), instanceName));
        }
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

        sb.append("name,instance_name,primary_graph,secondary_graph,time_ns,primary_cost,secondary_cost,total_cost\n");

        for (SolverPair pair : solvers) {
            String solverName = pair.solver.getName();

            for (DataRow row : pair.results) {
                sb.append(solverName);
                sb.append(",");
                sb.append(pair.instanceName);
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
