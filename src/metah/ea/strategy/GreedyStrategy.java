package metah.ea.strategy;

import metah.ea.Evaluator;
import metah.ea.model.Genotype;
import metah.ea.model.Solution;
import metah.model.DistanceMatrix;
import metah.model.Location;
import metah.model.Shop;
import metah.service.StatisticsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GreedyStrategy extends Strategy {

    public GreedyStrategy() {
        super("Greedy", 1);
    }

    @Override
    public Solution findOptimalSolution(Map<Integer, Location> locations, int depotNr, int capacity,
                                        DistanceMatrix distanceMatrix) {
        Evaluator evaluator = new Evaluator();
        StatisticsService statistics = new StatisticsService(locations.size());
        Genotype bestGenotype = null;
        double minimalDistance = Double.MAX_VALUE;
        List<Double> results = new ArrayList<>();
        for (int i = 1; i < locations.size() + 1; i++) {
            Genotype genotype = findGreedySolutionStartingFrom(i, locations, capacity, depotNr);
            double distance = evaluator.evaluateGenotype(genotype, capacity, distanceMatrix, locations, depotNr);
            statistics.addResult((int) distance);
            results.add(distance);
            if (distance < minimalDistance) {
                minimalDistance = distance;
                bestGenotype = genotype;
            }
        }
        logBestGenotype(bestGenotype, minimalDistance);
        getLogger().logStatistics(statistics);
        getLogger().writeToFile();
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


}
