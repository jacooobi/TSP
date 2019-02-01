import put.poznan.Benchmark;
import put.poznan.DataReader;
import put.poznan.Structures.Nodes;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        DataReader dr = new DataReader("kroA100");
        Nodes tspInstance = dr.load();

        Benchmark bench = new Benchmark(tspInstance);
        bench.test(100);
        bench.writeToFile("results.csv");
    }
}
