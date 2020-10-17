package metah.ea.strategy;

import metah.ea.model.Solution;
import metah.model.DistanceMatrix;
import metah.model.Location;

import java.util.Map;

public abstract class Strategy {
    private final String name;
    protected final int repetitions;

    protected Strategy(String name, int repetitions) {
        this.name = name;
        this.repetitions = repetitions;
    }

    public abstract Solution findOptimalSolution(Map<Integer, Location> places, int depotNr, DistanceMatrix distanceMatrix);

    public String getName() {
        return name;
    }

    public int getRepetitions() {
        return repetitions;
    }
}
