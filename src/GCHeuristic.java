import java.util.ArrayList;
import java.util.HashMap;

public class GCHeuristic extends BaseHeuristic {

    public GCHeuristic(ArrayList nodes) {
        super(nodes);
    }

    @Override
    protected ArrayList<HashMap<String, Integer>> evaluateCandidates(ArrayList<Integer> graph) {
        ArrayList<HashMap<String, Integer>> distances = new ArrayList<>();

        for(int i=0;i<nodes.size(); i++) {
            if (usedNodes.contains(i)) continue;

            HashMap<String,Integer> candidateNode = new HashMap<>();
            HashMap<String, Integer> firstNode = nodes.get(graph.get(0));
            HashMap<String, Integer> lastNode = nodes.get(graph.get(graph.size()-1));
            HashMap<String, Integer> curNode = nodes.get(i);

            int curToFirst = euclideanDistance(curNode.get("x"), curNode.get("y"), firstNode.get("x"), firstNode.get("y"));
            int curToLast = euclideanDistance(curNode.get("x"), curNode.get("y"), lastNode.get("x"), lastNode.get("y"));
            int newCost = pathTotalSum(graph) + curToFirst + curToLast;

            candidateNode.put("id", curNode.get("id"));
            candidateNode.put("distance", newCost);

            distances.add(candidateNode);
        }

        return distances;

    }

    private int pathTotalSum(ArrayList<Integer> graph) {
        if (graph.size() <= 1) return 0;

        int totalSum = 0;

        for (int i=0; i<graph.size()-1; i++) {
            HashMap<String, Integer> curNode = nodes.get(graph.get(i));
            HashMap<String, Integer> nextNode = nodes.get(graph.get(i+1));

            totalSum += euclideanDistance(curNode.get("x"), curNode.get("y"), nextNode.get("x"), nextNode.get("y"));
        }

        return totalSum;
    }


}
