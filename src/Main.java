import javafx.util.Pair;
import put.poznan.Benchmark;
import put.poznan.DataReader;
import put.poznan.Structures.Nodes;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        DataReader dr = new DataReader("kroA100");
        Nodes tspInstance100 = dr.load();
        dr = new DataReader("kroA150");
        Nodes tspInstance150 = dr.load();

        ArrayList<Pair<String, Nodes>> tspInstances = new ArrayList<>();
        tspInstances.add(new Pair<>("kroA100", tspInstance100));
        tspInstances.add(new Pair<>("kroA150", tspInstance150));

        Benchmark bench = new Benchmark(tspInstances);
        bench.test(100);
        bench.writeToFile("results.csv");
    }
}
