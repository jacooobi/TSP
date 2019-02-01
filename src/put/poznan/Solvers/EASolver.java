package put.poznan.Solvers;

import put.poznan.Structures.Graph;
import put.poznan.Structures.Nodes;
import put.poznan.TSPMath;
import put.poznan.TSPSolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class EASolver implements ISolver {
    private Nodes allNodes;
    private ArrayList<Nodes> population;
//    private Random random;
    private int popSize;

    public EASolver(Nodes tspInstance, int popSize) {
        this.allNodes = tspInstance;
        this.popSize = popSize;
    }

    public EASolver(Nodes tspInstance) {
        this.allNodes = tspInstance;
        this.popSize = (int)(0.2 * allNodes.size());
    }

    @Override
    public TSPSolution solve() {
        ArrayList<Nodes> population = initPopulation(allNodes);

        for (int i = 0; i < 1000; i++) {
            ArrayList<Nodes> selectedNodes = selection(population);
            ArrayList<Nodes> mutatedNodes = mutation(selectedNodes);
            ArrayList<Nodes> crossedOver = crossover(mutatedNodes);

            fixPopulation(crossedOver);
            population = crossedOver;
        }

        return null;
    }

    private ArrayList<Nodes> initPopulation(Nodes nodes) {
        population = new ArrayList<>();

        for (int i = 0; i < popSize; i++) {
            Nodes newNodes = nodes.copy();
            Collections.shuffle(newNodes);
            population.add(newNodes);
        }

        return population;
    }

    private void fixPopulation(ArrayList<Nodes> population) {
        if (population.size() == popSize) {
            return;
        }

        while (population.size() < popSize) {
            Nodes randomElement = allNodes.copy();
            Collections.shuffle(randomElement);
            population.add(randomElement);
        }
    }

    private ArrayList<Nodes> selection(ArrayList<Nodes> population) {
        population.sort(Comparator.comparing(o -> -TSPMath.getCost(o)));

        Nodes worstElement = population.get(population.size() - 1);
        int worstElementCost = TSPMath.getCost(worstElement);

        for (int i = 0; i < population.size(); i += 5) {
            // TODO: TUTAJ!
        }

        return population;
    }

    private ArrayList<Nodes> mutation(ArrayList<Nodes> population) {
        return population;
    }

    private ArrayList<Nodes> crossover(ArrayList<Nodes> population) {
        return population;
    }

    private Nodes mutateOne(Nodes nodes) {
        return null;
    }

    @Override
    public String getName() {
        return "EASolver";
    }
}
