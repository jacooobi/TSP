package put.poznan;

import put.poznan.Structures.Graph;
import put.poznan.Structures.Nodes;

import java.util.Comparator;

public class TSPSolution {
    private Nodes nodes;
    private Graph primaryGraph;
    private Graph secondaryGraph;
    private String name;


    public TSPSolution(Nodes nodes, Graph primaryGraph, Graph secondaryGraph, String name) {
        this.name = name;
        this.nodes = nodes;
        this.primaryGraph = primaryGraph;
        this.secondaryGraph = secondaryGraph;
        this.nodes.sort(Comparator.comparing(o -> (int)o.get("id")));
    }

    public void print() {
        System.out.println(name);

        printResults(primaryGraph, "Primary Graph:");
        printResults(secondaryGraph, "Secondary Graph:");
    }

    public Graph getPrimaryGraph() {
        return primaryGraph;
    }

    public Graph getSecondaryGraph() {
        return secondaryGraph;
    }

    public Nodes getNodes() {
        return nodes;
    }

    public Graph getFinalGraph() {
        Graph finalGraph = new Graph();
        finalGraph.addAll((Graph)primaryGraph.clone());
        finalGraph.addAll((Graph)secondaryGraph.clone());
        return finalGraph;
    }

    private void printResults(Graph graph, String name) {
        System.out.println(name);

        for (int i=0;i<graph.size();i++) {
            System.out.print(graph.get(i));
            if (i != graph.size() - 1)
                System.out.print("-");

        }

        System.out.println("\nTotal cost: " + TSPMath.getCost(nodes, graph) + "\n");
    }

    public int calculateTotalCost(Graph graph) {
        return TSPMath.getCost(nodes, graph);
    }

    public int calculateTotalCost() {
        return TSPMath.getCost(nodes, primaryGraph) + TSPMath.getCost(nodes, secondaryGraph);
    }
}
