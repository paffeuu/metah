package metah.ts.strategy;

import metah.ea.model.EvaluationResults;
import metah.ea.model.Genotype;
import metah.ea.model.Solution;
import metah.ea.strategy.GreedyStrategy;
import metah.ea.strategy.Strategy;
import metah.model.DataSet;
import metah.model.DistanceMatrix;
import metah.service.CVRPSolver;
import metah.service.Logger;
import metah.service.StatisticsService;
import metah.ts.model.InitializationType;
import metah.ts.model.NeighborhoodType;
import metah.ts.strategy.configuration.TabuSearchStrategyConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TabuSearchStrategy extends Strategy {
    private TabuSearchStrategyConfiguration conf;


    public TabuSearchStrategy(TabuSearchStrategyConfiguration conf, DataSet dataSet, DistanceMatrix distanceMatrix) {
        super(TabuSearchStrategy.resolveNameFromConfiguration(conf), conf.getRepetitions(), dataSet, distanceMatrix);
        this.conf = conf;
    }

    @Override
    public Solution findOptimalSolution() {
        StatisticsService statistics = new StatisticsService(conf.getIterations() * conf.getNeighborhoodSize() * repetitions);
        Genotype bestGenotype = null;
        double minimalDistance = Double.MAX_VALUE;
        for (int j = 0; j < repetitions; j++) {
            System.out.println("rep = " + j);
            Genotype currGenotype;
            double bestInRep = Double.MAX_VALUE;
            List<Genotype> tabuList = new ArrayList<>(conf.getTabuListSize());
            if (conf.getInitializationType() == InitializationType.RANDOM) {
                currGenotype = initializeGenotypeRandomly();
            } else {
                currGenotype = initializeGenotypeGreedy();
            }
            for (int i = 0; i < conf.getIterations(); i++) {
                if (i * 10 % conf.getIterations() == 0) {
                    System.out.println(i);
                }
                List<Genotype> neighborhood = neighborhood(currGenotype, conf.getNeighborhoodSize(),
                        conf.getNeighborhoodType(), tabuList);
                EvaluationResults evaluationResults = evaluation(neighborhood, bestInRep, i);
                if (evaluationResults.getMinimalDistance() < minimalDistance) {
                    bestGenotype = evaluationResults.getBestGenotype();
                    minimalDistance = evaluationResults.getMinimalDistance();
                }
                if (evaluationResults.getMinimalDistance() < bestInRep) {
                    bestInRep = evaluationResults.getMinimalDistance();
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
            statistics.addResult((int)bestInRep);
        }
        logBestGenotype(bestGenotype, minimalDistance);
        getLogger().logStatistics(statistics);
        getLogger().writeToFile();
        return new Solution(bestGenotype, minimalDistance);
    }

    private List<Genotype> neighborhood(Genotype currGenotype, int neighborhoodSize,
                                        NeighborhoodType neighborhoodType, List<Genotype> tabuList) {
        List<Genotype> neighborhood = new ArrayList<>(neighborhoodSize);
        for (int i = 0; i < neighborhoodSize; i++) {
            Genotype neighbor;
            if (neighborhoodType == NeighborhoodType.SWAP) {
                neighbor = neighborhoodSwap(currGenotype);
            } else {
                neighbor = neighborhoodInverse(currGenotype);
            }
            evaluator.normalizeGenotype(neighbor);
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

    private EvaluationResults evaluation(List<Genotype> neighborhood, double minimalDistance, int genNumber) {
        double bestInNeighboorhod = Double.MAX_VALUE;
        double worstInNeighborhood = Double.MIN_VALUE;
        double sumInNeighborhood = 0;
        Genotype bestGenotypeInNeighborhood = null;
        for (Genotype genotype : neighborhood) {
            double distance = evaluator.evaluateGenotype(genotype);
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
//        logCurrentBestResult(genNumber, bestInNeighboorhod, minimalDistance);
        logCurrBestWorstAvgResult(genNumber, bestInNeighboorhod, minimalDistance, worstInNeighborhood, avgOfNeighborhood);
        return new EvaluationResults(bestInNeighboorhod, bestGenotypeInNeighborhood);
    }

    private void logCurrBestWorstAvgResult(int i, double curr, double best, double worst, double avg) {
        String bestStr = String.format("%.0f", best);
        StringBuilder sb = new StringBuilder();
        sb.append(i+1);
        sb.append(",");
        sb.append(String.format("%.0f", curr));
        sb.append(",");
        sb.append(bestStr);
        sb.append(",");
        sb.append(String.format("%.0f", worst));
        sb.append(",");
        sb.append(String.format("%.0f", avg));
        sb.append("\n");
        String logStr = sb.toString();

        Logger logger = getLogger();
        logger.log(logStr);
    }



    private static String resolveNameFromConfiguration(TabuSearchStrategyConfiguration conf) {
        StringBuilder sb = new StringBuilder();
        sb.append("TS");
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
