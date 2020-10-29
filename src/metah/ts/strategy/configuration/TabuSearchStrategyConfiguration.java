package metah.ts.strategy.configuration;

import metah.ts.model.InitializationType;
import metah.ts.model.NeighborhoodType;

public class TabuSearchStrategyConfiguration {
    private final int iterations;
    private final int neighborhoodSize;
    private final int tabuListSize;
    private final InitializationType initializationType;
    private final int repetitions;
    private final NeighborhoodType neighborhoodType;

    public TabuSearchStrategyConfiguration(int iterations, int neighborhoodSize, NeighborhoodType neighborhoodType, int tabuListSize, InitializationType initializationType, int repetitions) {
        this.iterations = iterations;
        this.neighborhoodSize = neighborhoodSize;
        this.neighborhoodType = neighborhoodType;
        this.tabuListSize = tabuListSize;
        this.initializationType = initializationType;
        this.repetitions = repetitions;
    }

    public int getIterations() {
        return iterations;
    }

    public int getNeighborhoodSize() {
        return neighborhoodSize;
    }

    public NeighborhoodType getNeighborhoodType() {
        return neighborhoodType;
    }

    public int getTabuListSize() {
        return tabuListSize;
    }

    public InitializationType getInitializationType() {
        return initializationType;
    }

    public int getRepetitions() {
        return repetitions;
    }

}
