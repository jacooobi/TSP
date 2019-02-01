package put.poznan.Solvers;

import put.poznan.Structures.Graph;
import put.poznan.Structures.Nodes;
import put.poznan.TSPSolution;

import java.util.Comparator;
import java.util.Random;

public class BaseHeuristic implements ISolver {
    protected String name;
    protected Nodes nodes;
    protected Graph usedNodes;


    protected Graph primaryGraph;
    protected Graph secondaryGraph;

    public BaseHeuristic(Nodes nodes, String name) {
        this.name = name;
        this.nodes = nodes;
        this.usedNodes = new Graph();
        this.primaryGraph = new Graph();
        this.secondaryGraph = new Graph();
    }

    protected void addNode(Graph graph, int node) {
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

    public TSPSolution solve() {
        constructSolution(primaryGraph);
        constructSolution(secondaryGraph);

        return new TSPSolution(nodes, primaryGraph, secondaryGraph, name);
    }

    private void constructSolution(Graph graph) {
        addNode(graph, getStartNode());

        while (graph.size() < nodes.size() / 2) {
            int newNode = getBestNode(graph);
            addNode(graph, newNode);
        }
    }

    private int getBestNode(Graph graph) {
        Nodes candidates = evaluateCandidates(graph);

        candidates.sort(Comparator.comparing(o -> o.get("distance")));

        return candidates.get(0).get("id");
    }

    protected Nodes evaluateCandidates(Graph graph) {
        return new Nodes();
    }
}
