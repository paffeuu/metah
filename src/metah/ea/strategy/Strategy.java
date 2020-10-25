package metah.ea.strategy;

import metah.ea.model.Genotype;
import metah.ea.model.Solution;
import metah.model.DistanceMatrix;
import metah.model.Location;
import metah.service.Logger;

import java.util.Map;

public abstract class Strategy {
    private final String name;
    protected final int repetitions;

    protected Strategy(String name, int repetitions) {
        this.name = name;
        this.repetitions = repetitions;
        getLogger();
    }

    public Solution findOptimalSolution(Map<Integer, Location> places, int depotNr, int capacity,
                                                 DistanceMatrix distanceMatrix, String instanceName) {
        getLogger().setInstanceName(instanceName);
        return findOptimalSolution(places, depotNr, capacity, distanceMatrix);
    }

    public abstract Solution findOptimalSolution(Map<Integer, Location> places, int depotNr, int capacity,
                                        DistanceMatrix distanceMatrix);

    public String getName() {
        return name;
    }

    public int getRepetitions() {
        return repetitions;
    }

    protected Logger getLogger() {
        return Logger.getLogger(getName());
    }

    protected void logBestGenotype(Genotype bestGenotype, double minimalDistance) {
        getLogger().log(bestGenotype + " - " + (int) minimalDistance + "\n");
    }
}
