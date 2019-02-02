package put.poznan.Solvers;

import put.poznan.Structures.Graph;
import put.poznan.Structures.Nodes;
import put.poznan.TSPSolution;

import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class BaseHeuristic implements ISolver {
    protected String name;
    protected Nodes nodes;
    protected Graph usedNodes;

    protected Graph primaryGraph;
    protected Graph secondaryGraph;

    public BaseHeuristic(Nodes nodes, String name) {
        this.nodes = nodes;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
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

    public void init() {
        usedNodes = new Graph();
        primaryGraph = new Graph();
        secondaryGraph = new Graph();
    }

    public TSPSolution solve() {
        Nodes nodesCopy = nodes.copy();
        Collections.shuffle(nodesCopy);

        init();

        constructSolution(primaryGraph, nodes.size() / 2);
        constructSolution(secondaryGraph, nodes.size() / 2);

        return new TSPSolution(nodes, primaryGraph, secondaryGraph, getName());
    }

    public void constructSolution(Graph graph, int maxSize) {
        addNode(graph, getStartNode());

        while (graph.size() < maxSize) {
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
