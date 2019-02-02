package put.poznan;

import put.poznan.Solvers.LSSolver;
import put.poznan.Structures.Nodes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;

public class QualityCorrelationBenchmark {
    private Nodes nodes;
    private ArrayList<TSPSolution> solutions = new ArrayList<>();
    private ArrayList<Double> similarities = new ArrayList<>();
    private ArrayList<Double> qualities = new ArrayList<>();

    public QualityCorrelationBenchmark(Nodes nodes) {
        this.nodes = nodes;
    }

    public void test() {
        for (int i=0; i<1000; i++) {
            LSSolver solver  = new LSSolver(nodes, "local solver");
            solutions.add(solver.solve());

        }

        solutions.sort(Comparator.comparing(o -> o.calculateTotalCost()));

        TSPSolution best = solutions.get(0);

        Integer bestScore = best.calculateTotalCost();

        for (int i=0; i<solutions.size(); i++) {
            similarities.add(TSPMath.getSimilarity(best, solutions.get(i)));
            qualities.add((double)bestScore/(double)solutions.get(i).calculateTotalCost());
        }
    }

    public void writeToFile(String filename) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new File(filename));
        StringBuilder sb = new StringBuilder();

        sb.append("similarity,quality\n");

        for (int i=0; i<1000; i++) {
            sb.append(similarities.get(i));
            sb.append(",");
            sb.append(qualities.get(i));
            sb.append("\n");
        }

        pw.write(sb.toString());
        pw.close();
        System.out.println("Successfully saved to: " + filename + " file.");
    }
}
