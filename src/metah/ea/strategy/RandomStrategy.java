package metah.ea.strategy;

import metah.ea.Evaluator;
import metah.ea.RandomGenotypeGenerator;
import metah.ea.model.Genotype;
import metah.ea.model.Solution;
import metah.ea.strategy.configuration.RandomStrategyConfiguration;
import metah.model.DistanceMatrix;
import metah.model.Location;
import metah.service.DistanceCalculator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RandomStrategy extends Strategy {
    private RandomStrategyConfiguration conf;
//    private StatisticsPrinter statisticsPrinter;
//    private ResultLogger logger;

    public RandomStrategy(RandomStrategyConfiguration conf) {
        super("Random strategy", conf.getRepetitions());
        this.conf = conf;
//        this.statisticsPrinter = new StatisticsPrinter();
    }

    public Solution findOptimalSolution(Map<Integer, Location> places, int depotNr, int capacity,
                                        DistanceMatrix distanceMatrix) {
        RandomGenotypeGenerator randomGenotypeGenerator = new RandomGenotypeGenerator();
//        DistanceCalculator distanceCalculator = new DistanceCalculator();
        Evaluator evaluator = new Evaluator();
        Genotype bestGenotype = null;
        double minimalDistance = Double.MAX_VALUE;
        List<Double> results = new ArrayList<>();
        for (int i = 0; i < repetitions; i++) {
            double bestInRep = Double.MAX_VALUE;
            for (int j = 0; j < conf.getAttempts(); j++) {
                Genotype genotype = randomGenotypeGenerator.generate(places, depotNr);
                double distance = evaluator.evaluateGenotype(genotype, capacity, distanceMatrix, places, depotNr);
//                double distance = distanceCalculator.sumDistance(genotype, distanceMatrix);
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
//        statisticsPrinter.printStatistics(results, repetitions);

        return new Solution(bestGenotype, minimalDistance);
    }

    private void getNewLogFile(String params) {
//        try {
//            this.logger = ResultLogger.getResultLogger(params);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    private void logResult(int i, double best, double worst, double avg) {
//        if (logger != null) {
//            try {
//                logger.saveToLog(i, best, worst, avg);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public void setConfiguration(RandomStrategyConfiguration conf) {
        this.conf = conf;
    }
}
