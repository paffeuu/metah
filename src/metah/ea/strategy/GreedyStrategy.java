package metah.ea.strategy;

import metah.ea.Evaluator;
import metah.ea.model.Genotype;
import metah.ea.model.Solution;
import metah.model.DistanceMatrix;
import metah.model.Location;
import metah.model.Shop;
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
    public Solution findOptimalSolution(Map<Integer, Location> locations, int depotNr) {
//        DistanceCalculator distanceCalculator = new DistanceCalculator();
        Evaluator evaluator = new Evaluator();
        Genotype bestGenotype = null;
        double minimalDistance = Double.MAX_VALUE;
        List<Double> results = new ArrayList<>();
        for (int i = 1; i < locations.size() + 1; i++) {
            Genotype genotype = findGreedySolutionStartingFrom(i, locations, 30, depotNr);
            double distance = evaluator.evaluateGenotype(genotype, 30, distanceMatrix, locations, depotNr);
//            double distance = distanceCalculator.sumDistance(genotype, distanceMatrix);
            results.add(distance);
            if (distance < minimalDistance) {
                minimalDistance = distance;
                bestGenotype = genotype;
            }
        }
//        statisticsPrinter.printStatistics(results, places.size());
        return new Solution(bestGenotype, minimalDistance);
    }

    private Genotype findGreedySolutionStartingFrom(int startId, Map<Integer, Location> locations, int capacity,
                                                    int depotNr) {
        List<Integer> vector = new ArrayList<>();
        int fromId = startId;
        if (fromId != depotNr) {
            vector.add(fromId);
        }
        int currCapacity = capacity;
        while(vector.size() != locations.size() - 1) {
            int maxDemand = Integer.MIN_VALUE;
            int nextId = -1;
            for (int i = 1; i < locations.size() + 1; i++) {
                if (i == fromId || i == depotNr || vector.contains(i)) {
                    continue;
                }
                int demand = ((Shop) locations.get(i)).getDemand();
                if (demand > maxDemand && currCapacity - demand > 0) {
                    maxDemand = demand;
                    nextId = i;
                }
            }
            if (maxDemand > Integer.MIN_VALUE) {
                vector.add(nextId);
                currCapacity -= maxDemand;
                fromId = nextId;
            } else {
                currCapacity = capacity;
                fromId = depotNr;
            }
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
