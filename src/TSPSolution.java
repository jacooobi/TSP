import java.util.ArrayList;
import java.util.HashMap;

public class TSPSolution {
    private ArrayList<HashMap> nodes;
    private ArrayList<Integer> primaryGraph;
    private ArrayList<Integer> secondaryGraph;
    private String name;


    public TSPSolution(ArrayList<HashMap> nodes, ArrayList<Integer> primaryGraph, ArrayList<Integer> secondaryGraph, String name) {
        this.name = name;
        this.nodes = nodes;
        this.primaryGraph = primaryGraph;
        this.secondaryGraph = secondaryGraph;
    }

    public void print() {
        System.out.println(name);

        printResults(primaryGraph, "Primary Graph:");
        printResults(secondaryGraph, "Secondary Graph:");
    }

    private void printResults(ArrayList<Integer> graph, String name) {
        System.out.println(name);

        for (int i=0;i<graph.size();i++) {
            System.out.print(graph.get(i));
            if (i != graph.size() - 1)
                System.out.print("-");

        }

        System.out.println("\nTotal cost: " + calculateTotalCost(graph) + "\n");
    }

    private int calculateTotalCost(ArrayList<Integer> graph) {
        graph.add(graph.get(0));
        int totalCost = 0;

        for (int i=0; i<graph.size()-1; i++) {
            HashMap<String, Integer> curNode = nodes.get(graph.get(i));
            HashMap<String, Integer> nextNode = nodes.get(graph.get(i+1));

            totalCost += euclideanDistance(curNode.get("x"), curNode.get("y"), nextNode.get("x"), nextNode.get("y"));
        }

        return totalCost;
    }


    private int euclideanDistance(int x1, int y1, int x2, int y2) {
        return (int)Math.round(Math.sqrt((Math.pow(x2 - x1, 2.0) + Math.pow(y2 - y1, 2.0))));
    }
}
