package put.poznan.Solvers;

import put.poznan.Structures.Nodes;
import put.poznan.TSPSolution;

import java.util.ArrayList;
import java.util.Collections;

public class ILSSolver implements ISolver {
    protected Nodes allNodes;

    private LSSolver lsSolver;

    public ILSSolver(Nodes nodes) {
        this.allNodes = nodes;
        this.lsSolver = new LSSolver(nodes);
    }

    public TSPSolution solve() {
        Nodes nodesCopy = allNodes.copy();
        Collections.shuffle(nodesCopy);

        TSPSolution solution = lsSolver.solve(nodesCopy);
        int bestCost = solution.calculateTotalCost(solution.getFinalGraph());

        for (int i = 1; i < 100; i++) {
            Nodes newNodes = perturbation(solution.getNodes());
            TSPSolution newSolution = lsSolver.solve(newNodes);
            int newCost = solution.calculateTotalCost(newSolution.getFinalGraph());

            if (newCost < bestCost) {
                bestCost = newCost;
                solution = newSolution;
            }
        }

        return solution;
    }

    private Nodes perturbation(Nodes nodes) {
        int size = nodes.size();

        Nodes firstQuarter = nodes.subList(0, size / 4);
        Nodes secondQuarter = nodes.subList(size / 4, size / 2);
        Nodes thirdQuarter = nodes.subList(size / 2, 3 * size / 4);
        Nodes fourthQuarter = nodes.subList(3 * size / 4, size);

        ArrayList<Nodes> allQuarters = new ArrayList<>();
        allQuarters.add(firstQuarter);
        allQuarters.add(secondQuarter);
        allQuarters.add(thirdQuarter);
        allQuarters.add(fourthQuarter);

        Collections.shuffle(allQuarters);

        Nodes finalNodes = new Nodes();
        finalNodes.addAll(allQuarters.get(0));
        finalNodes.addAll(allQuarters.get(1));
        finalNodes.addAll(allQuarters.get(2));
        finalNodes.addAll(allQuarters.get(3));

        return finalNodes;
    }
}
