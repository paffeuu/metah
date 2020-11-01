package metah.sa.strategy.configuration;

import metah.ts.model.NeighborhoodType;

public class SimulatedAnnealingStrategyConfiguration {
    private final int repetitions;
    private final int iterations;
    private final int neighborhoodSize;
    private final NeighborhoodType neighborhoodType;
    private final double startTemperature;
    private final double endTemperature;
    private final double coolingFactor;


    public SimulatedAnnealingStrategyConfiguration(int repetitions, int iterations, int neighborhoodSize, NeighborhoodType neighborhoodType, double startTemperature, double endTemperature, double coolingFactor) {
        this.repetitions = repetitions;
        this.iterations = iterations;
        this.neighborhoodSize = neighborhoodSize;
        this.neighborhoodType = neighborhoodType;
        this.startTemperature = startTemperature;
        this.endTemperature = endTemperature;
        this.coolingFactor = coolingFactor;
    }

    public int getRepetitions() {
        return repetitions;
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

    public double getStartTemperature() {
        return startTemperature;
    }

    public double getEndTemperature() {
        return endTemperature;
    }

    public double getCoolingFactor() {
        return coolingFactor;
    }
}
