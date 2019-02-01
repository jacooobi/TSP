package put.poznan.Solvers;

import put.poznan.TSPSolution;

public interface ISolver {
    TSPSolution solve();
    String getName();
}
