package put.poznan.Solvers;

import put.poznan.Structures.Graph;
import put.poznan.Structures.Nodes;
import put.poznan.TSPMath;
import put.poznan.TSPSolution;

import java.util.*;

public class LSSolver implements ISolver {
    private String name = "LSSolver";

    protected Nodes allNodes;

    protected Graph primaryGraph;
    protected Graph secondaryGraph;

    public LSSolver(Nodes nodes) {
        this.allNodes = nodes;
        this.primaryGraph = new Graph();
        this.secondaryGraph = new Graph();
    }

    @Override
    public String getName() {
        return name;
    }

    public TSPSolution solve() {
        Nodes nodesCopy = allNodes.copy();
        Collections.shuffle(nodesCopy);
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

            for (int i = 0; i < nodes.size() - 1; i++) {
                int beforeI = i == 0 ? nodes.size() - 1 : i - 1;

                for (int j = i + 1; j < nodes.size() - 1; j++) {
                    int afterJ = j + 1;

                    int currentCost = TSPMath.distance(nodes.get(beforeI), nodes.get(i)) +
                            TSPMath.distance(nodes.get(afterJ), nodes.get(j));
                    int nextCost = TSPMath.distance(nodes.get(beforeI), nodes.get(j)) +
                            TSPMath.distance(nodes.get(i), nodes.get(afterJ));

                    if (nextCost < currentCost) {
                        Collections.swap(nodes, i, j);
                        improved = true;
                    }
                }
            }
        }

        Graph graph = Graph.fromNodes(nodes);
        return graph;
    }
}
