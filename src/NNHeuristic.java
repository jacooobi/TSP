import java.util.ArrayList;
import java.util.HashMap;

public class NNHeuristic extends BaseHeuristic {
    public NNHeuristic(ArrayList nodes) {
        super(nodes);
    }

    @Override
    protected ArrayList<HashMap<String, Integer>> evaluateCandidates(ArrayList<Integer> graph) {
        ArrayList<HashMap<String, Integer>> distances = new ArrayList<>();

        for (int i = 0; i < nodes.size(); i++) {
            if (usedNodes.contains(i)) continue;

            HashMap<String, Integer> candidateNode = new HashMap<>();
            HashMap<String, Integer> curNode = nodes.get(graph.get(graph.size() - 1));
            HashMap<String, Integer> newNode = nodes.get(i);

            candidateNode.put("id", newNode.get("id"));
            candidateNode.put("distance", euclideanDistance(curNode.get("x"), curNode.get("y"), newNode.get("x"), newNode.get("y")));

            distances.add(candidateNode);
        }

        return distances;
    }
}
