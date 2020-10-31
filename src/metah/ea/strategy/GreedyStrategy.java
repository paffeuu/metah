package metah.ea.strategy;

import metah.service.Evaluator;
import metah.ea.model.Genotype;
import metah.ea.model.Solution;
import metah.model.DataSet;
import metah.model.DistanceMatrix;
import metah.model.Shop;
import metah.service.StatisticsService;

import java.util.ArrayList;
import java.util.List;

public class GreedyStrategy extends Strategy {

    public GreedyStrategy(DataSet dataSet, DistanceMatrix distanceMatrix) {
        super("Greedy", 1, dataSet, distanceMatrix);
    }

    @Override
    public Solution findOptimalSolution() {
        Evaluator evaluator = new Evaluator(dataSet, distanceMatrix);
        StatisticsService statistics = new StatisticsService(dataSet.getLocations().size());
        Genotype bestGenotype = null;
        double minimalDistance = Double.MAX_VALUE;
        List<Double> results = new ArrayList<>();
        for (int i = 1; i < dataSet.getLocations().size() + 1; i++) {
            Genotype genotype = findGreedySolutionStartingFrom(i);
            double distance = evaluator.evaluateGenotype(genotype);
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

    private Genotype findGreedySolutionStartingFrom(int startId) {
        List<Integer> vector = new ArrayList<>();
        int fromId = startId;
        if (fromId != dataSet.getDepotNr()) {
            vector.add(fromId);
        }
        int currCapacity = dataSet.getCapacity();
        while(vector.size() != dataSet.getLocations().size() - 1) {
            int maxDemand = Integer.MIN_VALUE;
            int nextId = -1;
            for (int i = 1; i < dataSet.getLocations().size() + 1; i++) {
                if (i == fromId || i == dataSet.getDepotNr() || vector.contains(i)) {
                    continue;
                }
                int demand = ((Shop) dataSet.getLocations().get(i)).getDemand();
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
                currCapacity = dataSet.getCapacity();
                fromId = dataSet.getDepotNr();
            }
        }
        return new Genotype(vector);
    }


}
