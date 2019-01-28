import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;

public class BaseHeuristic {
    protected String name;
    protected ArrayList<HashMap> nodes;
    protected ArrayList<Integer> usedNodes;


    protected ArrayList<Integer> primaryGraph;
    protected ArrayList<Integer> secondaryGraph;

    public BaseHeuristic(ArrayList nodes, String name) {
        this.name = name;
        this.nodes = nodes;
        this.usedNodes = new ArrayList<>();
        this.primaryGraph = new ArrayList<>();
        this.secondaryGraph = new ArrayList<>();
    }

    protected void addNode(ArrayList graph, int node) {
        graph.add(node);
        usedNodes.add(node);
    }

    protected int getStartNode() {
        while(true) {
            Random rand = new Random();
            int drawnNode = rand.nextInt(nodes.size());

            if (!usedNodes.contains(drawnNode))
                return drawnNode;
        }
    }

    protected int euclideanDistance(int x1, int y1, int x2, int y2) {
        return (int)Math.round(Math.sqrt((Math.pow(x2 - x1, 2.0) + Math.pow(y2 - y1, 2.0))));
    }

    public TSPSolution solve() {
        constructSolution(primaryGraph);
        constructSolution(secondaryGraph);

        return new TSPSolution(nodes, primaryGraph, secondaryGraph, name);
    }

    private void constructSolution(ArrayList<Integer> graph) {
        int currentNode = getStartNode();
        addNode(graph, currentNode);

        while (graph.size() < 50) {
            int newNode = getBestNode(graph, currentNode);
            addNode(graph, newNode);
            currentNode = newNode;
        }
    }

    private int getBestNode(ArrayList<Integer> graph, int currentNodeId) {
        ArrayList<HashMap<String, Integer>> candidates = evaluateCandidates(graph);

        candidates.sort(Comparator.comparing(o -> o.get("distance")));

        return candidates.get(0).get("id");
    }

    protected ArrayList<HashMap<String, Integer>> evaluateCandidates(ArrayList<Integer> graph) {
        return new ArrayList<>();
    }
}
