package metah.ea.strategy;

import metah.ea.Evaluator;
import metah.ea.RandomGenotypeGenerator;
import metah.ea.model.Genotype;
import metah.ea.model.Solution;
import metah.ea.strategy.configuration.RandomStrategyConfiguration;
import metah.model.DistanceMatrix;
import metah.model.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RandomStrategy extends Strategy {
    private RandomStrategyConfiguration conf;

    public RandomStrategy(RandomStrategyConfiguration conf) {
        super("Rand_attmpts-" + conf.getAttempts() + "_rep-" + conf.getRepetitions(),
                conf.getRepetitions());
        this.conf = conf;
    }

    public Solution findOptimalSolution(Map<Integer, Location> places, int depotNr, int capacity,
                                        DistanceMatrix distanceMatrix) {
        RandomGenotypeGenerator randomGenotypeGenerator = new RandomGenotypeGenerator();
        Evaluator evaluator = new Evaluator();
        Genotype bestGenotype = null;
        double minimalDistance = Double.MAX_VALUE;
        List<Double> results = new ArrayList<>();
        for (int i = 0; i < repetitions; i++) {
            double bestInRep = Double.MAX_VALUE;
            for (int j = 0; j < conf.getAttempts(); j++) {
                Genotype genotype = randomGenotypeGenerator.generate(places, depotNr);
                double distance = evaluator.evaluateGenotype(genotype, capacity, distanceMatrix, places, depotNr);
                if (distance < minimalDistance) {
                    minimalDistance = distance;
                    bestGenotype = genotype;
                }
                if (distance < bestInRep) {
                    bestInRep = distance;
                }
            }
            System.out.println(bestInRep);
            results.add(bestInRep);
        }
        logBestGenotype(bestGenotype, minimalDistance);
        getLogger().writeToFile();
        return new Solution(bestGenotype, minimalDistance);
    }
}
