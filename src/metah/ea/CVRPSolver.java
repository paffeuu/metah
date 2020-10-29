package metah.ea;

import metah.ea.model.Solution;
import metah.ea.strategy.Strategy;

public class CVRPSolver {
    private final Strategy strategy;
    private String lastResultDescription;

    public CVRPSolver(Strategy strategy) {
        this.strategy = strategy;
        this.lastResultDescription = "No solution found yet.";
    }

    public Solution findOptimalSolution() {
        Solution optimalSolution = strategy.findOptimalSolution();
        setLastResultDescription(optimalSolution, strategy.getName());
        return optimalSolution;
    }

    public String getLastResultDescription() {
        return lastResultDescription;
    }

    private void setLastResultDescription(Solution optimalSolution, String strategyName) {
        StringBuilder sb = new StringBuilder();
        sb.append("Using strategy: ");
        sb.append(strategyName);
        sb.append("\nBest found solution:\n");
        sb.append(optimalSolution);
        lastResultDescription = sb.toString();
    }
}
