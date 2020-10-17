package metah.ea;

import metah.ea.model.Solution;
import metah.ea.strategy.Strategy;
import metah.model.Location;

import java.util.Map;

public class SolutionFinder {
    private Strategy strategy;
    private String lastResultDescription;

    public SolutionFinder(Strategy strategy) {
        this.strategy = strategy;
        this.lastResultDescription = "No solution found yet.";
    }

    public Solution findOptimalSolution(Map<Integer, Location> places, int depotNr) {
        Solution optimalSolution = strategy.findOptimalSolution(places, depotNr);
        setLastResultDescription(optimalSolution);
        return optimalSolution;
    }

    public String getLastResultDescription() {
        return lastResultDescription;
    }

    private void setLastResultDescription(Solution optimalSolution) {
        StringBuilder sb = new StringBuilder();
        sb.append("Using strategy: ");
        sb.append(strategy.getName());
        sb.append(" (");
        sb.append(strategy.getRepetitions());
        sb.append(" repetitions)\n");
        sb.append("Best found solution:\n");
        sb.append(optimalSolution);
        lastResultDescription = sb.toString();
    }
}
