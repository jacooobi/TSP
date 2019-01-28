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

    protected void addNode(ArrayList graph, int node) {
        graph.add(node);
        usedNodes.add(node);
    }

    protected int getStartNode() {
        while (true) {
            Random rand = new Random();
            int drawnNode = rand.nextInt(allNodes.size());

            if (!usedNodes.contains(drawnNode))
                return drawnNode;
        }
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

    private void constructSolution(ArrayList<Integer> graph, ArrayList<HashMap> nodes) {
        boolean switched = true;

        while (switched) {
            switched = false;

            for (int i = 0; i < nodes.size(); i++) {
                for (int j = i + 1; j < nodes.size(); j++) {
                    int[] iN = getNeighbours(nodes, i);
                    int[] jN = getNeighbours(nodes, j);

                    int currentCost = euclideanDistance(nodes.get(i), nodes.get(iN[0]))
                            + euclideanDistance(nodes.get(j), nodes.get(jN[1]));

                    int nextCost = euclideanDistance(nodes.get(j), nodes.get(iN[0]))
                            + euclideanDistance(nodes.get(i), nodes.get(jN[1]));

                    if (nextCost < currentCost) {
                        Collections.swap(nodes, i, j);
                        switched = true;
                        break;
                    }
                }

                if (switched) {
                    break;
                }
            }
        }
    }

    private int getPredecesor(ArrayList nodes, int element) {
        return element == 0 ? nodes.size() - 1 : element - 1;
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
