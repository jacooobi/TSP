package put.poznan;

import put.poznan.Structures.Graph;
import put.poznan.Structures.Node;
import put.poznan.Structures.Nodes;

public class TSPMath {
    public static int getCost(Nodes nodes, Graph graph) {
        int totalSum = 0;

        Node firstNode = nodes.get(graph.get(0));
        Node lastNode = nodes.get(graph.get(graph.size() - 1));
        totalSum += distance(firstNode, lastNode);

        for (int i = 0; i < graph.size() - 1; i++) {
            Node curNode = nodes.get(graph.get(i));
            Node nextNode = nodes.get(graph.get(i+1));

            totalSum += distance(curNode, nextNode);
        }

        return totalSum;
    }

    public static int getCost(Nodes nodes) {
        return getCost(nodes, Graph.fromNodes(nodes));
    }


    public static int distance(int x1, int y1, int x2, int y2) {
        return (int)Math.round(Math.sqrt((Math.pow(x2 - x1, 2.0) + Math.pow(y2 - y1, 2.0))));
    }

    public static int distance(Node node1, Node node2) {
        return distance(node1.get("x"), node1.get("y"), node2.get("x"), node2.get("y"));
    }

    public static double getSimilarity(TSPSolution solution1, TSPSolution solution2) {
        return TSPMath.getSimilarity(solution1.getFinalGraph(), solution2.getFinalGraph());
    }

    public static double getSimilarity(Graph g1, Graph g2) {
        int matchesCount = 0;

        for (int i=0; i<g1.size(); i++) {
            int g2Index = g2.indexOf(g1.get(i));

            if (g1.getNext(i) == g2.getNext(g2Index))
                matchesCount++;
        }

        return matchesCount/(double)g1.size();
    }
}
