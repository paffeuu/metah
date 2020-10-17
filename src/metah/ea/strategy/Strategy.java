package metah.ea.strategy;

import metah.ea.model.Solution;
import metah.model.DistanceMatrix;
import metah.model.Location;

import java.util.Map;

public abstract class Strategy {
    private final String name;
    protected final int repetitions;
    protected final DistanceMatrix distanceMatrix;

    protected Strategy(String name, int repetitions, DistanceMatrix distanceMatrix) {
        this.name = name;
        this.repetitions = repetitions;
        this.distanceMatrix = distanceMatrix;
    }

    public abstract Solution findOptimalSolution(Map<Integer, Location> places);

    public String getName() {
        return name;
    }

    public int getRepetitions() {
        return repetitions;
    }
}
