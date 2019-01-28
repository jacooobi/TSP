import java.lang.reflect.Array;
import java.util.*;

public class LSSolver {
    protected ArrayList<HashMap> allNodes;
    protected ArrayList<Integer> usedNodes;

    protected ArrayList<Integer> primaryGraph;
    protected ArrayList<Integer> secondaryGraph;

    public LSSolver(ArrayList nodes) {
        this.allNodes = nodes;
        this.usedNodes = new ArrayList<>();
        this.primaryGraph = new ArrayList<>();
        this.secondaryGraph = new ArrayList<>();
    }


    public TSPSolution solve() {
        ArrayList<HashMap> nodesCopy = getNodesClone();
        Collections.shuffle(nodesCopy);
        int size = nodesCopy.size();

        ArrayList<HashMap> primaryNodes = new ArrayList<>(nodesCopy.subList(0, size / 2));
        ArrayList<HashMap> secondaryNodes = new ArrayList<>(nodesCopy.subList(size / 2, size));

        constructSolution(primaryGraph, primaryNodes);
        constructSolution(secondaryGraph, secondaryNodes);

        return new TSPSolution(allNodes, primaryGraph, secondaryGraph, "LSSolver");
    }

    private int pathTotalSum(ArrayList<HashMap> nodes) {
        int totalSum = 0;

        for (int i=0; i< nodes.size()-1; i++) {
            HashMap<String, Integer> curNode = nodes.get(i);
            HashMap<String, Integer> nextNode = nodes.get(i+1);
            totalSum += euclideanDistance(curNode.get("x"), curNode.get("y"), nextNode.get("x"), nextNode.get("y"));
        }

        return totalSum;
    }

    private void constructSolution(ArrayList<Integer> graph, ArrayList<HashMap> nodes) {
        boolean switched = true;

        while (switched) {
//            System.out.println(pathTotalSum(nodes));
            switched = false;

            for (int i = 0; i < nodes.size() - 2; i++) {
                for (int j = i + 2; j < nodes.size() - 1; j++) {
                    int currentCost = euclideanDistance(nodes.get(i), nodes.get(i + 1))
                            + euclideanDistance(nodes.get(j), nodes.get(j + 1));
                    int nextCost = euclideanDistance(nodes.get(i), nodes.get(j))
                            + euclideanDistance(nodes.get(i+1), nodes.get(j+ 1));

                    if (nextCost < currentCost) {
                        Collections.swap(nodes, i + 1, j);
                        switched = true;
                    }
                }
            }
        }

        for (int i = 0; i < nodes.size(); i++) {
            graph.add((int)nodes.get(i).get("id"));
        }
    }

    private int[] getNeighbours(ArrayList<HashMap> nodes, int element) {
        int before = element == 0 ? nodes.size() - 1 : element - 1;
        int after = element == nodes.size() - 1 ? 0 : element + 1;
        return new int[] { before, after };
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

    private ArrayList<HashMap> getNodesClone() {
        ArrayList<HashMap> clone = new ArrayList<>();

        for (HashMap node : allNodes) {
            clone.add((HashMap) node.clone());
        }

        return clone;
    }
}
