package put.poznan.Solvers;

import put.poznan.Structures.Graph;
import put.poznan.Structures.Node;
import put.poznan.Structures.Nodes;
import put.poznan.TSPMath;
import put.poznan.TSPSolution;

import java.lang.reflect.Array;
import java.util.*;

public class EASolver implements ISolver {
    private Nodes allNodes;
    private ArrayList<Nodes> population;
    private Random random = new Random();
    private int popSize;
    private LSSolver localSolver;

    private double POPULATION_PERCENTAGE = 0.25;
    private int CROSS_OVER_SIZE = 15;


    public EASolver(Nodes tspInstance, int popSize) {
        this.allNodes = tspInstance;
        this.popSize = popSize;
        this.localSolver = new LSSolver(tspInstance, "put.poznan.Solvers.LSSolver");
    }

    public EASolver(Nodes tspInstance) {
        this.allNodes = tspInstance;
        this.popSize = (int)(POPULATION_PERCENTAGE * allNodes.size());
        this.localSolver = new LSSolver(tspInstance, "put.poznan.Solvers.LSSolver");
    }

    @Override
    public TSPSolution solve() {
        ArrayList<Nodes> population = initPopulation(allNodes);

        for (int i = 0; i < 10; i++) {
            ArrayList<Nodes> crossedOver = crossover(population);
            ArrayList<Nodes> mutatedNodes = mutation(crossedOver);
            population = selection(population, mutatedNodes);
        }

        return constructSolution();
    }

    private TSPSolution constructSolution() {
        int size = population.get(0).size();

        Nodes primaryNodes = population.get(0).subList(0, size / 2);
        Nodes secondaryNodes = population.get(0).subList(size / 2, size);

        Graph primaryGraph = Graph.fromNodes(primaryNodes);
        Graph secondaryGraph = Graph.fromNodes(secondaryNodes);

        Nodes finalNodes = primaryNodes.copy();
        finalNodes.addAll(secondaryNodes.copy());

        return new TSPSolution(finalNodes, primaryGraph, secondaryGraph, "put.poznan.Solvers.EASolver");
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

    private ArrayList<Nodes> selection(ArrayList<Nodes> population, ArrayList<Nodes> mutatedNodes) {
        ArrayList<Nodes> selectedPopulation = new ArrayList<>();
        ArrayList<Nodes> currentPopulation = new ArrayList<>(population);
        currentPopulation.addAll(new ArrayList<>(mutatedNodes));

        for (int i = 0; i < popSize; i++) {
            selectedPopulation.add(tournamentSelection(currentPopulation));
        }

        return selectedPopulation;
    }

    private Nodes tournamentSelection(ArrayList<Nodes> population) {
        Collections.shuffle(population);

        List<Nodes> selectedTournament = population.subList(0, 5);

        selectedTournament.sort(Comparator.comparing(o -> TSPMath.getCost(o)));

        return selectedTournament.get(0);
    }

    private ArrayList<Nodes> mutation(ArrayList<Nodes> population) {
        for(int i = 0; i<population.size(); i++) {
            if (Math.random() > 0.9) {
                Nodes element = population.get(i);

                Random rand = new Random();

                int startPoint = rand.nextInt(element.size());
                int endPoint = rand.nextInt(element.size());

                Node tempNode = element.get(startPoint);
                element.set(startPoint, element.get(endPoint));
                element.set(endPoint, tempNode);

            }
        }

        return population;
    }

    private ArrayList<Nodes> crossover(ArrayList<Nodes> population) {

        population.sort(Comparator.comparing(o -> TSPMath.getCost(o)));
        ArrayList<Nodes> offspringPopulation = new ArrayList<>();

        for (int el=0; el<CROSS_OVER_SIZE; el++) {
            offspringPopulation.add(produceOffspring(population.subList(0, popSize/2)));
        }

        return offspringPopulation;
    }

    private Nodes produceOffspring(List<Nodes> population) {
        Random rand = new Random();
        int parent1, parent2;

        do {
            parent1 = rand.nextInt(population.size());
            parent2 = rand.nextInt(population.size());
        } while (parent1 == parent2);

        Nodes parent1Nodes = population.get(parent1).copy();
        Nodes parent2Nodes = population.get(parent2).copy();

        int startPoint = rand.nextInt(parent1Nodes.size()/2);
        int endPoint = rand.nextInt(parent1Nodes.size()/2 + parent2Nodes.size()/2);

        while (startPoint >= endPoint) {
            endPoint = rand.nextInt(parent2Nodes.size());
        }

        Nodes offspring = parent1Nodes.copy();

        for (int i=startPoint; i<endPoint; i++) {
            offspring.set(i, parent2Nodes.get(i));
        }

        for (int i=0; i<offspring.size(); i++) {
            if (i >= startPoint && i < endPoint) continue;
            final int id = i;

            boolean nodeExists = offspring
                    .subList(startPoint, endPoint)
                    .stream()
                    .anyMatch(el -> el.get("id") == offspring.get(id).get("id"));

            if (!nodeExists) continue;

            for (int j=0; j<parent1Nodes.size(); j++) {
                final int idd = j;
                boolean parentNodeExists = offspring
                        .stream()
                        .anyMatch(el -> el.get("id") == parent1Nodes.get(idd).get("id"));

                if (!parentNodeExists) {
                    offspring.set(i, parent1Nodes.get(j));
                    break;
                }
            }
        }

        return offspring;
    }

    @Override
    public String getName() {
        return "EASolver";
    }
}
