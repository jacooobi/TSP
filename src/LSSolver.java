import java.lang.reflect.Array;
import java.util.*;

public class LSSolver {
    protected ArrayList<Integer> usedNodes;
    protected ArrayList<HashMap> allNodes;

    protected ArrayList<Integer> primaryGraph;
    protected ArrayList<Integer> secondaryGraph;

    public LSSolver(ArrayList nodes) {
        this.allNodes = nodes;
        this.usedNodes = new ArrayList<>();
        this.primaryGraph = new ArrayList<>();
        this.secondaryGraph = new ArrayList<>();
    }

    public TSPSolution solve() {
        ArrayList<HashMap> nodesCopy = getNodesClone(allNodes);
        Collections.shuffle(nodesCopy);
        return solve(nodesCopy);
    }


    public TSPSolution solve(ArrayList<HashMap> nodes) {
        int size = nodes.size();

        ArrayList<HashMap> primaryNodes = new ArrayList<>(nodes.subList(0, size / 2));
        ArrayList<HashMap> secondaryNodes = new ArrayList<>(nodes.subList(size / 2, size));

        constructSolution(primaryGraph, primaryNodes);
        constructSolution(secondaryGraph, secondaryNodes);

        ArrayList<HashMap> finalNodes = getNodesClone(primaryNodes);
        finalNodes.addAll(getNodesClone(secondaryNodes));
        finalNodes.sort(Comparator.comparing(o -> (int)o.get("id")));

        return new TSPSolution(finalNodes, primaryGraph, secondaryGraph, "LSSolver");
    }

    private void constructSolution(ArrayList<Integer> graph, ArrayList<HashMap> nodes) {
        boolean improved = true;

        while (improved) {
            improved = false;

            for (int i = 0; i < nodes.size() - 1; i++) {
                int beforeI = i == 0 ? nodes.size() - 1 : i - 1;

                for (int j = i + 2; j < nodes.size() - 1; j++) {
                    int afterJ = j + 1;

                    int currentCost = euclideanDistance(nodes.get(beforeI), nodes.get(i)) +
                            euclideanDistance(nodes.get(afterJ), nodes.get(j));
                    int nextCost = euclideanDistance(nodes.get(beforeI), nodes.get(j)) +
                            euclideanDistance(nodes.get(i), nodes.get(afterJ));

                    if (nextCost < currentCost) {
                        Collections.swap(nodes, i, j);
                        improved = true;
                    }
                }
            }
        }

        graph.clear();
        for (int i = 0; i < nodes.size(); i++) {
            graph.add((int)nodes.get(i).get("id"));
        }
    }

    protected int euclideanDistance(HashMap nodeA, HashMap nodeB) {
        return euclideanDistance(
                (int)nodeA.get("x"),
                (int)nodeA.get("y"),
                (int)nodeB.get("x"),
                (int)nodeB.get("y"));
    }

    protected int euclideanDistance(int x1, int y1, int x2, int y2) {
        return (int)Math.round(Math.sqrt((Math.pow(x2 - x1, 2.0) + Math.pow(y2 - y1, 2.0))));
    }

    public ArrayList<HashMap> getNodesClone(ArrayList<HashMap> nodes) {
        ArrayList<HashMap> clone = new ArrayList<>();

        for (HashMap node : nodes) {
            clone.add((HashMap) node.clone());
        }

        return clone;
    }

    public int calculateTotalCost(ArrayList<HashMap> graph) {
        int totalCost = 0;

        HashMap<String, Integer> firstNode = graph.get(0);
        HashMap<String, Integer> lastNode = graph.get(graph.size() - 1);
        totalCost += euclideanDistance(firstNode.get("x"), firstNode.get("y"), lastNode.get("x"), lastNode.get("y"));

        for (int i=0; i<graph.size()-1; i++) {
            HashMap<String, Integer> curNode = graph.get(i);
            HashMap<String, Integer> nextNode = graph.get(i + 1);

            totalCost += euclideanDistance(curNode.get("x"), curNode.get("y"), nextNode.get("x"), nextNode.get("y"));
        }



        return totalCost;
    }
}
