import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        DataReader dr = new DataReader("kroA100");
        ArrayList<HashMap> tspInstance = dr.load();

        NNHeuristic solver = new NNHeuristic(tspInstance);
        TSPSolution solution = solver.solve();

        solution.print();
    }
}
