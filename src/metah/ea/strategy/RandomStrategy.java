package metah.ea.strategy;

import metah.ea.model.Genotype;
import metah.ea.model.Solution;
import metah.ea.strategy.configuration.RandomStrategyConfiguration;
import metah.model.DataSet;
import metah.model.DistanceMatrix;
import metah.service.StatisticsService;

import java.util.ArrayList;
import java.util.List;

public class RandomStrategy extends Strategy {
    private RandomStrategyConfiguration conf;

    public RandomStrategy(RandomStrategyConfiguration conf, DataSet dataSet, DistanceMatrix distanceMatrix) {
        super("Rand_attmpts-" + conf.getAttempts() + "_rep-" + conf.getRepetitions(),
                conf.getRepetitions(), dataSet, distanceMatrix);
        this.conf = conf;
    }

    public Solution findOptimalSolution() {
        StatisticsService statistics = new StatisticsService(conf.getRepetitions() * conf.getAttempts());
        Genotype bestGenotype = null;
        double minimalDistance = Double.MAX_VALUE;
        List<Double> results = new ArrayList<>();
        for (int i = 0; i < repetitions; i++) {
            double bestInRep = Double.MAX_VALUE;
            for (int j = 0; j < conf.getAttempts(); j++) {
                Genotype genotype = genotypeGenerator.generate();
                double distance = evaluator.evaluateGenotype(genotype);
                statistics.addResult((int) distance);
                if (distance < minimalDistance) {
                    minimalDistance = distance;
                    bestGenotype = genotype;
                }
                if (distance < bestInRep) {
                    bestInRep = distance;
                }
            }
            results.add(bestInRep);
        }
        logBestGenotype(bestGenotype, minimalDistance);
        getLogger().logStatistics(statistics);
        getLogger().writeToFile();
        return new Solution(bestGenotype, minimalDistance);
    }
}
