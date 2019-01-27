import java.util.ArrayList;
import java.util.HashMap;

public class TSPSolution {
    private ArrayList<HashMap> nodes;
    private ArrayList<Integer> primaryGraph;
    private ArrayList<Integer> secondaryGraph;

    public TSPSolution(ArrayList<HashMap> nodes, ArrayList<Integer> primaryGraph, ArrayList<Integer> secondaryGraph) {
        this.nodes = nodes;
        this.primaryGraph = primaryGraph;
        this.secondaryGraph = secondaryGraph;
    }

    public void print() {
        System.out.println("Results:");

        System.out.println("Primary Graph nodes:");

        for (int i=0;i<primaryGraph.size();i++) {
            System.out.print(primaryGraph.get(i));
            if (i != primaryGraph.size() - 1)
                System.out.print("-");

        }
        System.out.println();

        System.out.println(("Total cost: " + calculateTotalCost(primaryGraph)));

        System.out.println("Secondary Graph nodes:");

        for (int i=0;i<secondaryGraph.size();i++) {
            System.out.print(secondaryGraph.get(i));
            if (i != secondaryGraph.size() - 1)
                System.out.print("-");

        }
        System.out.println();

        System.out.println(("Total cost: " + calculateTotalCost(secondaryGraph)));
    }

    private int calculateTotalCost(ArrayList<Integer> graph) {
        int totalCost = 0;

        for (int i=0; i<graph.size()-1; i++) {
            HashMap<String, Integer> curNode = nodes.get(graph.get(i));
            HashMap<String, Integer> nextNode = nodes.get(graph.get(i+1));

            totalCost += euclideanDistance(curNode.get("x"), curNode.get("y"), nextNode.get("x"), nextNode.get("y"));
        }

        HashMap<String, Integer> firstNode = nodes.get(graph.get(0));
        HashMap<String, Integer> lastNode = nodes.get(graph.get(graph.size()-1));

        totalCost += euclideanDistance(firstNode.get("x"), firstNode.get("y"), lastNode.get("x"), lastNode.get("y"));

        return totalCost;
    }


    private int euclideanDistance(int x1, int y1, int x2, int y2) {
        return (int)Math.round(Math.sqrt((Math.pow(x2 - x1, 2.0) + Math.pow(y2 - y1, 2.0))));
    }
}
