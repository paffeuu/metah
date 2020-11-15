package metah.sa.strategy;

import metah.ea.model.Genotype;
import metah.ea.model.Solution;
import metah.ea.strategy.Strategy;
import metah.model.DataSet;
import metah.model.DistanceMatrix;
import metah.sa.strategy.configuration.SimulatedAnnealingStrategyConfiguration;
import metah.service.StatisticsService;
import metah.ts.model.NeighborhoodType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SimulatedAnnealingStrategy extends Strategy {
    private SimulatedAnnealingStrategyConfiguration conf;


    public SimulatedAnnealingStrategy(SimulatedAnnealingStrategyConfiguration conf, DataSet dataSet,
                                      DistanceMatrix distanceMatrix) {
        super(resolveNameFromConfiguration(conf), conf.getRepetitions(), dataSet, distanceMatrix);
        this.conf = conf;
    }

    @Override
    public Solution findOptimalSolution() {
        int coolingRounds = (int) (Math.log(conf.getStartTemperature() - conf.getEndTemperature())
                / Math.log(conf.getCoolingFactor()) * (-1));
        StatisticsService statistics = new StatisticsService((coolingRounds + 5) *
                conf.getIterations() * repetitions);

        Genotype bestGenotype = null;
        double minimalDistance = Double.MAX_VALUE;
        for (int i = 0; i < conf.getRepetitions(); i++) {
            Genotype currGenotype = initializeGenotypeRandomly();
            double currDistance = evaluator.evaluateGenotype(currGenotype);
            double currTemp = conf.getStartTemperature();

            Genotype bestInRepGenotype = currGenotype;
            double minimalInRepDistance = currDistance;
            int counter = 0;
            while (currTemp >= conf.getEndTemperature()) {
                for (int j = 0; j < conf.getIterations(); j++) {
                    List<Genotype> neighborhood =
                            neighborhood(currGenotype, conf.getNeighborhoodSize(), conf.getNeighborhoodType());
                    Genotype bestNeighbor = neighborhood.stream()
                            .sorted(Comparator.comparingDouble(evaluator::evaluateGenotype))
                            .findFirst().get();
                    double bestNeighborDistance = evaluator.evaluateGenotype(bestNeighbor);
                    if (bestNeighborDistance < currDistance) {
                        currGenotype = bestNeighbor;
                        currDistance = bestNeighborDistance;
                    } else {
                        double x = Math.exp((currDistance - bestNeighborDistance) / currTemp);
                        if (Math.random() < x) {
                            currGenotype = bestNeighbor;
                            currDistance = bestNeighborDistance;
                        }
                    }
                    if (currDistance < minimalInRepDistance) {
                        minimalInRepDistance = currDistance;
                        bestInRepGenotype = currGenotype;
                    }
                    logCurrentBestResult(++counter, minimalInRepDistance, currDistance);

                }
                if (minimalInRepDistance < minimalDistance) {
                    minimalDistance = minimalInRepDistance;
                    bestGenotype = bestInRepGenotype;
                }
                System.out.println(currDistance + ", " + minimalInRepDistance + ", " + String.format("%.1f", currTemp));
                currTemp *= conf.getCoolingFactor();
            }
            statistics.addResult((int)minimalInRepDistance);
        }
        logBestGenotype(bestGenotype, minimalDistance);
        getLogger().logStatistics(statistics);
        getLogger().writeToFile();
        return new Solution(bestGenotype, minimalDistance);
    }

    private List<Genotype> neighborhood(Genotype currGenotype, int neighborhoodSize, NeighborhoodType neighborhoodType) {
        List<Genotype> neighborhood = new ArrayList<>(neighborhoodSize);
        for (int i = 0; i < neighborhoodSize; i++) {
            Genotype neighbor;
            if (neighborhoodType == NeighborhoodType.SWAP) {
                neighbor = neighborhoodSwap(currGenotype);
            } else {
                neighbor = neighborhoodInverse(currGenotype);
            }
            neighborhood.add(neighbor);
        }
        return neighborhood;
    }

    private Genotype neighborhoodSwap(Genotype currGenotype) {
        Genotype neighbor = new Genotype(currGenotype);
        int firstPlace = (random.nextInt(neighbor.size()));
        int secondPlace;
        do {
            secondPlace = (random.nextInt(neighbor.size()));
        } while (firstPlace == secondPlace);
        neighbor.swapTwoGenes(firstPlace, secondPlace);
        return neighbor;
    }

    private Genotype neighborhoodInverse(Genotype currGenotype) {
        int firstIntersection = (random.nextInt(currGenotype.size()));
        int secondIntersection;
        do {
            secondIntersection = (random.nextInt(currGenotype.size()));
        } while (firstIntersection == secondIntersection);
        if (firstIntersection > secondIntersection) {
            int temp = secondIntersection;
            secondIntersection = firstIntersection;
            firstIntersection = temp;
        }

        List<Integer> placesToRelocate = new ArrayList<>();
        for (int j = firstIntersection; j <= secondIntersection; j++) {
            placesToRelocate.add(currGenotype.get(j));
        }
        Collections.reverse(placesToRelocate);

        List<Integer> mutatedVector = new ArrayList<>();
        for (int j = 0; j < currGenotype.size(); j++) {
            if (j >= firstIntersection && j <= secondIntersection) {
                mutatedVector.add(placesToRelocate.get(j - firstIntersection));
            } else {
                mutatedVector.add(currGenotype.get(j));
            }
        }
        Genotype neighbor = new Genotype(mutatedVector);
        return neighbor;
    }

    private static String resolveNameFromConfiguration(SimulatedAnnealingStrategyConfiguration conf) {
        StringBuilder sb = new StringBuilder();
        sb.append("SA");
        sb.append("_i-");
        sb.append(conf.getIterations());
        sb.append("_ntype-");
        if (conf.getNeighborhoodType() == NeighborhoodType.SWAP) {
            sb.append("SWAP");
        } else {
            sb.append("INV");
        }
        sb.append("_nsize-");
        sb.append(conf.getNeighborhoodSize());
        sb.append("-startT-");
        sb.append(conf.getStartTemperature());
        sb.append("-endT-");
        sb.append(conf.getEndTemperature());
        sb.append("-coolF-");
        sb.append(conf.getCoolingFactor());
        sb.append("_init-RAND");
        return sb.toString();
    }
}
