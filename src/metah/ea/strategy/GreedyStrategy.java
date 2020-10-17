package metah.ea.strategy;

import metah.ea.model.Genotype;
import metah.ea.model.Solution;
import metah.model.DistanceMatrix;
import metah.model.Location;
import metah.service.DistanceCalculator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GreedyStrategy extends Strategy {
//    private StatisticsPrinter statisticsPrinter;
//    private ResultLogger logger;

    public GreedyStrategy(DistanceMatrix distanceMatrix) {
        super("Greedy strategy", 1, distanceMatrix);
//        this.statisticsPrinter = new StatisticsPrinter();
    }

    @Override
    public Solution findOptimalSolution(Map<Integer, Location> places) {
        DistanceCalculator distanceCalculator = new DistanceCalculator();
        Genotype bestGenotype = null;
        double minimalDistance = Double.MAX_VALUE;
        List<Double> results = new ArrayList<>();
        for (int i = 1; i < places.size() + 1; i++) {
            Genotype genotype = findGreedySolutionStartingFrom(i, places);
            double distance = distanceCalculator.sumDistance(genotype, distanceMatrix);
            results.add(distance);
            if (distance < minimalDistance) {
                minimalDistance = distance;
                bestGenotype = genotype;
            }
        }
//        statisticsPrinter.printStatistics(results, places.size());
        return new Solution(bestGenotype, minimalDistance);
    }

    private Genotype findGreedySolutionStartingFrom(int startId, Map<Integer, Location> places) {
        List<Integer> vector = new ArrayList<>();
        int fromId = startId;
        vector.add(fromId);
        while(vector.size() != places.size()) {
            double minimalDistance = Double.MAX_VALUE;
            int nextId = -1;
            for (int i = 1; i < places.size() + 1; i++) {
                if (i == fromId || vector.contains(i)) {
                    continue;
                }
                double distance = distanceMatrix.getDistance(fromId, i);
                if (distance < minimalDistance) {
                    minimalDistance = distance;
                    nextId = i;
                }
            }
            vector.add(nextId);
            fromId = nextId;
        }
        return new Genotype(vector);
    }

    private void getNewLogFile(String params) {
//        try {
//            this.logger = ResultLogger.getResultLogger(params);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
