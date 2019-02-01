package put.poznan.Solvers;

import put.poznan.Structures.Graph;
import put.poznan.Structures.Node;
import put.poznan.Structures.Nodes;
import put.poznan.TSPMath;

public class NNHeuristic extends BaseHeuristic {
    public NNHeuristic(Nodes nodes) {
        super(nodes, "Nearest Neighbour");
    }

    @Override
    protected Nodes evaluateCandidates(Graph graph) {
        Nodes distances = new Nodes();

        for (int i = 0; i < nodes.size(); i++) {
            if (usedNodes.contains(i)) continue;

            Node candidateNode = new Node();
            Node curNode = nodes.get(graph.get(graph.size() - 1));
            Node newNode = nodes.get(i);

            candidateNode.put("id", newNode.get("id"));
            candidateNode.put("distance", TSPMath.distance(curNode, newNode));

            distances.add(candidateNode);
        }

        return distances;
    }
}
