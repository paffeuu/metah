package metah.ea.strategy.configuration;

import metah.ea.model.CrossoverType;
import metah.ea.model.MutationType;
import metah.ea.model.SelectionType;
import metah.exception.ConfigurationException;

public class EvolutionaryAlgorithmStrategyConfiguration {
    private final SelectionType selectionType;
    private final CrossoverType crossoverType;
    private final MutationType mutationType;
    private final int populationSize;
    private final int tournamentSize;
    private final int generations;
    private final double crossoverLikelihood;
    private final double mutationLikelihood;
    private final int repetitions;

    public EvolutionaryAlgorithmStrategyConfiguration(SelectionType selectionType, CrossoverType crossoverType,
                                                      MutationType mutationType, int populationSize,
                                                      int tournamentSize, int generations,
                                                      double crossoverLikelihood, double mutationLikelihood,
                                                      int repetitions) {
        this.selectionType = selectionType;
        this.crossoverType = crossoverType;
        this.mutationType = mutationType;
        this.populationSize = populationSize;
        this.tournamentSize = tournamentSize;
        this.generations = generations;
        this.crossoverLikelihood = crossoverLikelihood;
        this.mutationLikelihood = mutationLikelihood;
        this.repetitions = repetitions;
    }

    public EvolutionaryAlgorithmStrategyConfiguration(SelectionType selectionType, CrossoverType crossoverType,
                                                      MutationType mutationType, int populationSize,
                                                      int generations, double crossoverLikelihood,
                                                      double mutationLikelihood, int repetitions) {
        this.selectionType = selectionType;
        this.crossoverType = crossoverType;
        this.mutationType = mutationType;
        this.populationSize = populationSize;
        if (selectionType.equals(SelectionType.TOURNAMENT)) {
            throw new ConfigurationException("Tournament selection with no tournamentSize value.");
        }
        this.tournamentSize = -1;
        this.generations = generations;
        this.crossoverLikelihood = crossoverLikelihood;
        this.mutationLikelihood = mutationLikelihood;
        this.repetitions = repetitions;
    }

    public SelectionType getSelectionType() {
        return selectionType;
    }

    public CrossoverType getCrossoverType() {
        return crossoverType;
    }

    public MutationType getMutationType() {
        return mutationType;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public int getTournamentSize() {
        return tournamentSize;
    }

    public int getGenerations() {
        return generations;
    }

    public double getCrossoverLikelihood() {
        return crossoverLikelihood;
    }

    public double getMutationLikelihood() {
        return mutationLikelihood;
    }

    public int getRepetitions() {
        return repetitions;
    }
}
