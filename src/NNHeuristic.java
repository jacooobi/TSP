import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class NNHeuristic {
    private ArrayList<HashMap> nodes;
    private ArrayList<Integer> usedNodes;

    private ArrayList<Integer> primaryGraph;
    private ArrayList<Integer> secondaryGraph;

    public NNHeuristic(ArrayList nodes) {
        this.nodes = nodes;
        this.usedNodes = new ArrayList<>();
        this.primaryGraph = new ArrayList<>();
        this.secondaryGraph = new ArrayList<>();
    }

    public TSPSolution solve() {
        int currentNode = getStartNode();
        addNode(primaryGraph, currentNode);

        while (primaryGraph.size() < 50) {
            int nearestNode = getNearestAvailable(currentNode);
            addNode(primaryGraph, nearestNode);
            currentNode = nearestNode;
        }

        currentNode = getStartNode();
        addNode(secondaryGraph, currentNode);

        while (secondaryGraph.size() < 50) {
            int nearestNode = getNearestAvailable(currentNode);
            addNode(secondaryGraph, nearestNode);
            currentNode = nearestNode;
        }

        return new TSPSolution(nodes, primaryGraph, secondaryGraph);
    }

    private void addNode(ArrayList graph, int node) {
        graph.add(node);
        usedNodes.add(node);
    }

    private int getStartNode() {
        while(true) {
            Random rand = new Random();
            int drawnNode = rand.nextInt(nodes.size());

            if (!usedNodes.contains(drawnNode))
                return drawnNode;
        }

    }

    private int euclideanDistance(int x1, int y1, int x2, int y2) {
        return (int)Math.round(Math.sqrt((Math.pow(x2 - x1, 2.0) + Math.pow(y2 - y1, 2.0))));
    }

    private ArrayList<HashMap<String, Integer>> calculateDistances(HashMap<String, Integer> startNode) {
        ArrayList<HashMap<String, Integer>> distances = new ArrayList<>();


        for(HashMap<String, Integer> node : nodes) {
            HashMap<String,Integer> distanceNode = new HashMap<>();
            distanceNode.put("id", node.get("id"));
            distanceNode.put("distance", euclideanDistance(startNode.get("x"), startNode.get("y"), node.get("x"), node.get("y")));

            distances.add(distanceNode);
        }

        return distances;
    }

    private int getNearestAvailable(int currentNodeId) {
        HashMap<String, Integer> startNode = nodes.get(currentNodeId);

        ArrayList<HashMap<String, Integer>> distances = calculateDistances(startNode);
        distances.sort((o1, o2) -> o1.get("distance").compareTo(o2.get("distance")));

        for (HashMap<String, Integer> node: distances) {
            if (!usedNodes.contains(node.get("id"))) {
                return node.get("id");
            }
        }

        return -1;
    }
}
