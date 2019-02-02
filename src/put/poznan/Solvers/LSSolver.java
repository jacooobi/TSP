package put.poznan.Solvers;

import put.poznan.Structures.Graph;
import put.poznan.Structures.Nodes;
import put.poznan.TSPMath;
import put.poznan.TSPSolution;

import java.util.*;

public class LSSolver implements ISolver {
    private String name;
    private Nodes allNodes;
    private Graph primaryGraph;
    private Graph secondaryGraph;
    private InitializationType initializationType;
    private NNHeuristic nnHeuristic;

    public LSSolver(Nodes nodes, String name) {
        this.allNodes = nodes;
        this.primaryGraph = new Graph();
        this.secondaryGraph = new Graph();
        this.initializationType = InitializationType.RANDOM;
        this.name = name;
    }

    public LSSolver(Nodes nodes, String name, InitializationType type) {
        this.allNodes = nodes;
        this.primaryGraph = new Graph();
        this.secondaryGraph = new Graph();
        this.initializationType = type;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public TSPSolution solve() {
        Nodes nodesCopy = allNodes.copy();

        if (initializationType == InitializationType.RANDOM) {
            Collections.shuffle(nodesCopy);
        } else if (initializationType == InitializationType.NN) {
            nnHeuristic = new NNHeuristic(nodesCopy, "any");

            Graph tempGraph = new Graph();
            nnHeuristic.init();
            nnHeuristic.constructSolution(tempGraph, nodesCopy.size());

            nodesCopy.sortByGraph(tempGraph);
        }

        return solve(nodesCopy);
    }

    public TSPSolution solve(Nodes nodes) {
        int size = nodes.size();

        Nodes primaryNodes = nodes.subList(0, size / 2);
        Nodes secondaryNodes = nodes.subList(size / 2, size);

        primaryGraph = constructSolution(primaryNodes);
        secondaryGraph = constructSolution(secondaryNodes);

        Nodes finalNodes = primaryNodes.copy();
        finalNodes.addAll(secondaryNodes.copy());

        return new TSPSolution(finalNodes, primaryGraph, secondaryGraph, "put.poznan.Solvers.LSSolver");
    }

    private Graph constructSolution(Nodes nodes) {
        boolean improved = true;

        while (improved) {
            improved = false;

            for (int i = 0; i < nodes.size(); i++) {
                for (int j = i + 1; j < nodes.size(); j++) {
                    int currentCost = getCostOf(nodes, i) + getCostOf(nodes, j);
                    Nodes nextNodes = nodes.copy();
                    Collections.swap(nextNodes, i, j);
                    int nextCost = getCostOf(nextNodes, i) + getCostOf(nextNodes, j);

                    if (nextCost < currentCost) {
                        nodes = nextNodes;
                        improved = true;
                    }
                }
            }
        }

        return Graph.fromNodes(nodes);
    }

    private int getCostOf(Nodes nodes, int idx) {
        int beforeIdx = idx == 0 ? nodes.size() - 1 : idx - 1;
        int afterIdx = (idx + 1) % nodes.size();

        return TSPMath.distance(nodes.get(beforeIdx), nodes.get(idx)) +
                TSPMath.distance(nodes.get(afterIdx), nodes.get(idx));
    }
}
