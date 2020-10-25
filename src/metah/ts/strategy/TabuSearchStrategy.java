package metah.ts.strategy;

import metah.ea.CVRPSolver;
import metah.ea.Evaluator;
import metah.ea.RandomGenotypeGenerator;
import metah.ea.model.EvaluationResults;
import metah.ea.model.Genotype;
import metah.ea.model.Solution;
import metah.ea.strategy.GreedyStrategy;
import metah.ea.strategy.Strategy;
import metah.model.DistanceMatrix;
import metah.model.Location;
import metah.service.StatisticsService;
import metah.ts.strategy.configuration.TabuSearchStrategyConfiguration;
import metah.ts.strategy.model.InitializationType;
import metah.ts.strategy.model.NeighborhoodType;

import java.util.*;

public class TabuSearchStrategy extends Strategy {
    private RandomGenotypeGenerator genotypeGenerator;
    private Random random;
    private Evaluator evaluator;
    private TabuSearchStrategyConfiguration conf;


    public TabuSearchStrategy(TabuSearchStrategyConfiguration conf) {
        super(TabuSearchStrategy.resolveNameFromConfiguration(conf), conf.getRepetitions());
        this.genotypeGenerator = new RandomGenotypeGenerator();
        this.random = new Random();
        this.evaluator = new Evaluator();
        this.conf = conf;
    }

    @Override
    public Solution findOptimalSolution(Map<Integer, Location> locations, int depotNr, int capacity,
                                        DistanceMatrix distanceMatrix) {
        StatisticsService statistics = new StatisticsService(conf.getIterations() * conf.getNeighborhoodSize() * repetitions);
        Genotype bestGenotype = null;
        double minimalDistance = Double.MAX_VALUE;
        for (int j = 0; j < repetitions; j++) {
            Genotype currGenotype;
            List<Genotype> tabuList = new ArrayList<>(conf.getTabuListSize());
            if (conf.getInitializationType() == InitializationType.RANDOM) {
                currGenotype = initializeGenotypeRandomly(locations, depotNr);
            } else {
                currGenotype = initializeGenotypeGreedy(locations, depotNr, capacity, distanceMatrix);
            }
            for (int i = 0; i < conf.getIterations(); i++) {
                List<Genotype> neighborhood = neighborhood(currGenotype, conf.getNeighborhoodSize(),
                        conf.getNeighborhoodType(), tabuList, capacity, distanceMatrix, locations, depotNr);
                EvaluationResults evaluationResults = evaluation(neighborhood, capacity, distanceMatrix, locations,
                        depotNr, minimalDistance, bestGenotype, i, statistics);
                if (evaluationResults.getMinimalDistance() < minimalDistance) {
                    bestGenotype = evaluationResults.getBestGenotype();
                    minimalDistance = evaluationResults.getMinimalDistance();
                }
                if (evaluationResults.getBestGenotype() != null) {
                    currGenotype = evaluationResults.getBestGenotype();
                }
                for (Genotype neighbor : neighborhood) {
                    if (!tabuList.isEmpty()) {
                        tabuList.add(0, neighbor);
                    } else {
                        tabuList.add(neighbor);
                    }
                    if (tabuList.size() > conf.getTabuListSize()) {
                        tabuList.remove(conf.getTabuListSize());
                    }
                }
            }
        }
        logBestGenotype(bestGenotype, minimalDistance);
        getLogger().logStatistics(statistics);
        getLogger().writeToFile();
        return new Solution(bestGenotype, minimalDistance);
    }

    private Genotype initializeGenotypeRandomly(Map<Integer, Location> locations, int depotNr) {
        return genotypeGenerator.generate(locations, depotNr);
    }

    private Genotype initializeGenotypeGreedy(Map<Integer, Location> locations, int depotNr, int capacity,
                                              DistanceMatrix distanceMatrix) {
        CVRPSolver greedySolver = new CVRPSolver(new GreedyStrategy());
        Solution greedySolution =
                greedySolver.findOptimalSolution(locations, depotNr, capacity, distanceMatrix, null);
        return greedySolution.getBestGenotype();
    }

    private List<Genotype> neighborhood(Genotype currGenotype, int neighborhoodSize,
                                        NeighborhoodType neighborhoodType, List<Genotype> tabuList,
                                        int capacity, DistanceMatrix distanceMatrix,
                                        Map<Integer, Location> locations, int depotNr) {
        List<Genotype> neighborhood = new ArrayList<>(neighborhoodSize);
        for (int i = 0; i < neighborhoodSize; i++) {
            Genotype neighbor;
            if (neighborhoodType == NeighborhoodType.SWAP) {
                neighbor = neighborhoodSwap(currGenotype);
            } else {
                neighbor = neighborhoodInverse(currGenotype);
            }
            evaluator.normalizeGenotype(neighbor, capacity, distanceMatrix, locations, depotNr);
            if (tabuList.stream().noneMatch(genotype -> genotype.equals(neighbor))) {
                neighborhood.add(neighbor);
            }
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

    private EvaluationResults evaluation(List<Genotype> neighborhood, int capacity, DistanceMatrix distanceMatrix,
                                         Map<Integer, Location> locations, int depotNr, double minimalDistance,
                                         Genotype bestGenotype, int genNumber, StatisticsService statistics) {
        double bestInNeighboorhod = Double.MAX_VALUE;
        double worstInNeighborhood = Double.MIN_VALUE;
        double sumInNeighborhood = 0;
        Genotype bestGenotypeInNeighborhood = null;
        for (Genotype genotype : neighborhood) {
            double distance = evaluator.evaluateGenotype(genotype, capacity, distanceMatrix, locations, depotNr);
            statistics.addResult((int)distance);
            if (distance < bestInNeighboorhod) {
                bestInNeighboorhod = distance;
                bestGenotypeInNeighborhood = genotype;
            }
            if (distance > worstInNeighborhood) {
                worstInNeighborhood = distance;
            }
            sumInNeighborhood += distance;
        }
        double avgOfNeighborhood = sumInNeighborhood / neighborhood.size();
        logBestWorstAvgResult(genNumber, bestInNeighboorhod, worstInNeighborhood, avgOfNeighborhood);
//        if (bestInNeighboorhod < minimalDistance) {
//            minimalDistance = bestInNeighboorhod;
//            bestGenotype = bestGenotypeInNeighborhood;
//        }
        return new EvaluationResults(bestInNeighboorhod, bestGenotypeInNeighborhood);

    }

    private static String resolveNameFromConfiguration(TabuSearchStrategyConfiguration conf) {
        StringBuilder sb = new StringBuilder();
        sb.append("TS");
        sb.append("_i-");
        sb.append(conf.getIterations());
        sb.append("_nsize-");
        sb.append(conf.getNeighborhoodSize());
        sb.append("_tabu-");
        sb.append(conf.getTabuListSize());
        sb.append("_init-");
        if (conf.getInitializationType() == InitializationType.RANDOM) {
            sb.append("RAND");
        } else {
            sb.append("GREEDY");
        }
        return sb.toString();
    }

}
