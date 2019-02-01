import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ILSSolver {
    protected ArrayList<Integer> usedNodes;
    protected ArrayList<HashMap> allNodes;

    private LSSolver lsSolver;

    public ILSSolver(ArrayList nodes) {
        this.allNodes = nodes;
        this.usedNodes = new ArrayList<>();
        this.lsSolver = new LSSolver(nodes);
    }

    public TSPSolution solve() {
        ArrayList<HashMap> nodesCopy = lsSolver.getNodesClone(allNodes);
        Collections.shuffle(nodesCopy);

        TSPSolution solution = lsSolver.solve(nodesCopy);
        int bestCost = solution.calculateTotalCost(solution.getFinalGraph());

        for (int i = 1; i < 100; i++) {
            ArrayList<HashMap> newNodes = perturbation(solution.getNodes());
            TSPSolution newSolution = lsSolver.solve(newNodes);
            int newCost = solution.calculateTotalCost(newSolution.getFinalGraph());

            if (newCost < bestCost) {
                bestCost = newCost;
                solution = newSolution;
            }
        }

        return solution;
    }

    private ArrayList<HashMap> perturbation(ArrayList<HashMap> nodes) {
        int size = nodes.size();

        ArrayList<HashMap> firstQuarter = new ArrayList<>(nodes.subList(0, size / 4));
        ArrayList<HashMap> secondQuarter = new ArrayList<>(nodes.subList(size / 4, size / 2));
        ArrayList<HashMap> thirdQuarter = new ArrayList<>(nodes.subList(size / 2, 3 * size / 4));
        ArrayList<HashMap> fourthQuarter = new ArrayList<>(nodes.subList(3 * size / 4, size));

        ArrayList<ArrayList<HashMap>> allQuarters = new ArrayList<>();
        allQuarters.add(firstQuarter);
        allQuarters.add(secondQuarter);
        allQuarters.add(thirdQuarter);
        allQuarters.add(fourthQuarter);

        Collections.shuffle(allQuarters);

        ArrayList<HashMap> finalNodes = new ArrayList<>();
        finalNodes.addAll(allQuarters.get(0));
        finalNodes.addAll(allQuarters.get(1));
        finalNodes.addAll(allQuarters.get(2));
        finalNodes.addAll(allQuarters.get(3));

        return finalNodes;
    }

//    private ArrayList<ArrayList<HashMap>> partition(ArrayList<HashMap> nodes) {
//        int partitionSize = 4;
//
//        ArrayList<ArrayList<HashMap>> partitions = new ArrayList<>();
//
//        for (int i = 0; i < nodes.size(); i += partitionSize) {
//            partitions.add(nodes.subList(i, Math.min(i + partitionSize, nodes.size())));
//        }
//    }
}
