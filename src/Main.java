import put.poznan.Benchmark;
import put.poznan.DataReader;
import put.poznan.Structures.Nodes;

public class Main {

    public static void main(String[] args) {
        DataReader dr = new DataReader("kroA100");
        Nodes tspInstance = dr.load();

        Benchmark bench = new Benchmark(tspInstance);
        bench.test(1);
    }
}
