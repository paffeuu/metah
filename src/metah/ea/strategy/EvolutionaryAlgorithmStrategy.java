package metah.ea.strategy;

import metah.ea.Evaluator;
import metah.ea.RandomGenotypeGenerator;
import metah.ea.model.*;
import metah.ea.strategy.configuration.EvolutionaryAlgorithmStrategyConfiguration;
import metah.model.DistanceMatrix;
import metah.model.Location;
import metah.service.Logger;
import metah.service.StatisticsService;

import java.util.*;

public class EvolutionaryAlgorithmStrategy extends Strategy {

    private RandomGenotypeGenerator genotypeGenerator;
    private Random random;
    private Evaluator evaluator;
    private EvolutionaryAlgorithmStrategyConfiguration conf;

    public EvolutionaryAlgorithmStrategy(EvolutionaryAlgorithmStrategyConfiguration conf) {
        super(EvolutionaryAlgorithmStrategy.resolveNameFromConfiguration(conf), conf.getRepetitions());
        this.genotypeGenerator = new RandomGenotypeGenerator();
        this.random = new Random();
        this.evaluator = new Evaluator();
        this.conf = conf;
    }

    @Override
    public Solution findOptimalSolution(Map<Integer, Location> locations, int depotNr, int capacity,
                                        DistanceMatrix distanceMatrix) {
        StatisticsService statistics = new StatisticsService(conf.getPopulationSize() * conf.getGenerations() * repetitions);
        Genotype bestGenotype = null;
        double minimalDistance = Double.MAX_VALUE;
        for (int j = 0; j < repetitions; j++) {
            List<Genotype> population = initializePopulationRandomly(locations, depotNr);
            for (int i = 0; i < conf.getGenerations(); i++) {
                population = selection(population, conf, locations, depotNr, capacity, distanceMatrix);
                population = crossover(population, conf);
                population = mutation(population, conf);
                EvaluationResults evaluationResults = evaluation(population, capacity, distanceMatrix, locations, depotNr,
                        minimalDistance, bestGenotype, i, statistics);
                bestGenotype = evaluationResults.getBestGenotype();
                minimalDistance = evaluationResults.getMinimalDistance();
            }
        }
        logBestGenotype(bestGenotype, minimalDistance);
        getLogger().logStatistics(statistics);
        getLogger().writeToFile();
        return new Solution(bestGenotype, minimalDistance);
    }

    private List<Genotype> initializePopulationRandomly(Map<Integer, Location> locations, int depotNr) {
        List<Genotype> population = new ArrayList<>(conf.getPopulationSize());
        for (int i = 0; i < conf.getPopulationSize(); i++) {
            population.add(genotypeGenerator.generate(locations, depotNr));
        }
        return population;
    }

    private List<Genotype> selection(List<Genotype> population, EvolutionaryAlgorithmStrategyConfiguration conf,
                                     Map<Integer, Location> locations, int depotNr, int capacity,
                                     DistanceMatrix distanceMatrix) {
        if (conf.getSelectionType() == SelectionType.TOURNAMENT) {
            return selectionByTournament(population, conf.getTournamentSize(), locations, depotNr, capacity, distanceMatrix);
        } else {
            return selectionByRoulette(population, locations, depotNr, capacity, distanceMatrix);
        }
    }

    private List<Genotype> selectionByTournament(List<Genotype> population, int tournamentSize,
                                                 Map<Integer, Location> locations, int depotNr, int capacity,
                                                 DistanceMatrix distanceMatrix) {
        List<Genotype> selectedPopulation = new ArrayList<>(population.size());
        while (selectedPopulation.size() != population.size()) {
            List<Genotype> tournament = new ArrayList<>(tournamentSize);
            for (int i = 0; i < tournamentSize; i++) {
                int randomIndex = (int) (Math.random() * population.size());
                Genotype randomGenotype = population.remove(randomIndex);
                tournament.add(randomGenotype);
            }
//            DistanceCalculator distanceCalculator = new DistanceCalculator();
            Genotype bestGenotype = null;
            double minimalDistance = Double.MAX_VALUE;
            for (Genotype genotype : tournament) {
                double distance = evaluator.evaluateGenotype(genotype, capacity, distanceMatrix, locations, depotNr);
                if (distance < minimalDistance) {
                    minimalDistance = distance;
                    bestGenotype = genotype;
                }
            }
            selectedPopulation.add(bestGenotype);
            population.addAll(tournament);
        }
        return selectedPopulation;
    }

    private List<Genotype> selectionByRoulette(List<Genotype> population, Map<Integer, Location> locations,
                                               int depotNr, int capacity, DistanceMatrix distanceMatrix) {
        Map<Genotype, Double> distanceMap = new HashMap<>();
//        DistanceCalculator distanceCalculator = new DistanceCalculator();
        double sum = 0;
        for (Genotype genotype : population) {
            double distance = evaluator.evaluateGenotype(genotype, capacity, distanceMatrix, locations, depotNr);
            distanceMap.put(genotype, distance);
            sum += distance;
        }
        Map<Genotype, Double> likelihoodMap = new HashMap<>();
        double reversedLikelihoodSum = 0;
        for (Genotype genotype : population) {
            double distance = distanceMap.get(genotype);
            double likelihood = Math.pow(distance / sum, 10);
            double reversedLikelihood = 1 / likelihood;
            reversedLikelihoodSum += reversedLikelihood;
            likelihoodMap.put(genotype, reversedLikelihood);
        }
        for (Genotype genotype : population) {
            double reversedLikelihood = likelihoodMap.get(genotype);
            double reversedLikelihoodNormalized = reversedLikelihood / reversedLikelihoodSum;
            likelihoodMap.put(genotype, reversedLikelihoodNormalized);
        }
        List<Double> limitList = new ArrayList<>();
        double limit = 0;
        for (Genotype genotype : population) {
            double likelihood = likelihoodMap.get(genotype);
            limit += likelihood;
            limitList.add(limit);
        }
        List<Genotype> selectionPopulation = new ArrayList<>();
        for (int i = 0; i < population.size(); i++) {
            double randomNr = Math.random();
            for (int j = 0; j < population.size(); j++) {
                if (randomNr < limitList.get(j)) {
                    selectionPopulation.add(new Genotype(population.get(j)));
                    break;
                }
            }
        }
        return selectionPopulation;
    }

    private List<Genotype> crossover(List<Genotype> population, EvolutionaryAlgorithmStrategyConfiguration conf) {
        if (conf.getCrossoverType() == CrossoverType.OX) {
            return crossoverOrdered(population, conf.getCrossoverLikelihood());
        } else {
            return crossoverPartiallyMatched(population, conf.getCrossoverLikelihood());
        }
    }

    private List<Genotype> crossoverOrdered(List<Genotype> population, double crossoverLikelihood) {
        List<Genotype> populationAfterCrossover = new ArrayList<>();
        for (int i = 0; i < population.size(); i++) {
            if (random.nextDouble() < crossoverLikelihood) {
                int firstIntersection = (random.nextInt(population.get(0).size()));
                int secondIntersection;
                do {
                    secondIntersection = (random.nextInt(population.get(0).size()));
                } while (firstIntersection == secondIntersection);
                if (firstIntersection > secondIntersection) {
                    int temp = secondIntersection;
                    secondIntersection = firstIntersection;
                    firstIntersection = temp;
                }
                Genotype firstParent = population.get(i);
                Genotype secondParent = population.get((i + 1) % population.size());
                Genotype child = new Genotype(secondParent);
                List<Integer> placesToRelocate = new ArrayList<>();
                for (int j = firstIntersection; j <= secondIntersection; j++) {
                    placesToRelocate.add(firstParent.get(j));
                }
                for (Integer placeToRelocate : placesToRelocate) {
                    child.getVector().removeIf(number -> number.equals(placeToRelocate));
                }
                for (int k = 0; k < placesToRelocate.size(); k++ ) {
                    child.getVector().add(firstIntersection + k, placesToRelocate.get(k));
                }
                populationAfterCrossover.add(child);
            } else {
                populationAfterCrossover.add(population.get(i));
            }
        }
        return populationAfterCrossover;
    }

    private List<Genotype> crossoverPartiallyMatched(List<Genotype> population, double crossoverLikelihood) {
        List<Genotype> populationAfterCrossover = new ArrayList<>();
        for (int i = 0; i < population.size(); i++) {
            if (random.nextDouble() < crossoverLikelihood) {
                int firstIntersection = (random.nextInt(population.get(0).size()));
                int secondIntersection;
                do {
                    secondIntersection = (random.nextInt(population.get(0).size()));
                } while (firstIntersection == secondIntersection);
                if (firstIntersection > secondIntersection) {
                    int temp = secondIntersection;
                    secondIntersection = firstIntersection;
                    firstIntersection = temp;
                }
                Genotype firstParent = population.get(i);
                Genotype secondParent = population.get((i + 1) % population.size());

                List<Integer> placesToRelocate = new ArrayList<>();
                for (int j = firstIntersection; j <= secondIntersection; j++) {
                    placesToRelocate.add(firstParent.get(j));
                }
                List<Integer> childVector = new ArrayList<>();
                for (int j = 0; j < firstParent.size(); j++) {
                    if (j < firstIntersection || j > secondIntersection) {
                        childVector.add(-1);
                    } else {
                        int placesToRelocateNumber = j - firstIntersection;
                        childVector.add(placesToRelocate.get(placesToRelocateNumber));
                    }
                }
                for (int j = firstIntersection; j <= secondIntersection; j++) {
                    int index = j;
                    int initialValue = secondParent.get(index);
                    boolean valueAssigned = false;
                    while (!valueAssigned && !childVector.contains(initialValue)) {
                        int currValue = firstParent.get(index);
                        int currIndex = secondParent.getVector().indexOf(currValue);
                        if (currIndex < firstIntersection || currIndex > secondIntersection) {
                            childVector.set(currIndex, initialValue);
                            valueAssigned = true;
                        } else {
                            index = currIndex;
                        }
                    }
                }
                for (int j = 0; j < childVector.size(); j++) {
                    if (childVector.get(j) == -1) {
                        childVector.set(j, secondParent.get(j));
                    }
                }
                Genotype child = new Genotype(childVector);
                populationAfterCrossover.add(child);
            } else {
                populationAfterCrossover.add(population.get(i));
            }
        }
        return populationAfterCrossover;

    }

    private List<Genotype> mutation(List<Genotype> population, EvolutionaryAlgorithmStrategyConfiguration conf) {
        if (conf.getMutationType() == MutationType.SWAP) {
            return mutationSwap(population, conf.getMutationLikelihood());
        } else {
            return mutationInversion(population, conf.getMutationLikelihood());
        }
    }

    private List<Genotype> mutationSwap(List<Genotype> population, double mutationLikelihood) {
        List<Genotype> mutatedPopulation = new ArrayList<>();
        for (Genotype genotype : population) {
            genotype = new Genotype(genotype);
            mutatedPopulation.add(genotype);
            if (random.nextDouble() < mutationLikelihood) {
                int firstPlace = (random.nextInt(genotype.size()));
                int secondPlace;
                do {
                    secondPlace = (random.nextInt(genotype.size()));
                } while (firstPlace == secondPlace);
                genotype.swapTwoGenes(firstPlace, secondPlace);
            }
        }
        return mutatedPopulation;
    }

    private List<Genotype> mutationInversion(List<Genotype> population, double mutationLikelihood) {
        List<Genotype> mutatedPopulation = new ArrayList<>();
        for (Genotype genotype : population) {
            if (random.nextDouble() < mutationLikelihood) {
                int firstIntersection = (random.nextInt(population.get(0).size()));
                int secondIntersection;
                do {
                    secondIntersection = (random.nextInt(population.get(0).size()));
                } while (firstIntersection == secondIntersection);
                if (firstIntersection > secondIntersection) {
                    int temp = secondIntersection;
                    secondIntersection = firstIntersection;
                    firstIntersection = temp;
                }

                List<Integer> placesToRelocate = new ArrayList<>();
                for (int j = firstIntersection; j <= secondIntersection; j++) {
                    placesToRelocate.add(genotype.get(j));
                }
                Collections.reverse(placesToRelocate);

                List<Integer> mutatedVector = new ArrayList<>();
                for (int j = 0; j < genotype.size(); j++) {
                    if (j >= firstIntersection && j <= secondIntersection) {
                        mutatedVector.add(placesToRelocate.get(j - firstIntersection));
                    } else {
                        mutatedVector.add(genotype.get(j));
                    }
                }
                Genotype mutatedGenotype = new Genotype(mutatedVector);
                mutatedPopulation.add(mutatedGenotype);
            } else {
                mutatedPopulation.add(genotype);
            }
        }
        return mutatedPopulation;
    }

    private EvaluationResults evaluation(List<Genotype> population, int capacity, DistanceMatrix distanceMatrix,
                                         Map<Integer, Location> locations, int depotNr, double minimalDistance,
                                         Genotype bestGenotype, int genNumber, StatisticsService statistics
                                         ) {
        double bestInPop = Double.MAX_VALUE;
        double worstInPop = Double.MIN_VALUE;
        double sumInPop = 0;
        Genotype bestGenotypeInPop = null;
        for (Genotype genotype : population) {
            double distance = evaluator.evaluateGenotype(genotype, capacity, distanceMatrix, locations, depotNr);
            statistics.addResult((int)distance);
            if (distance < bestInPop) {
                bestInPop = distance;
                bestGenotypeInPop = genotype;
            }
            if (distance > worstInPop) {
                worstInPop = distance;
            }
            sumInPop += distance;
        }
        double avgOfPop = sumInPop / conf.getPopulationSize();
        logResult(genNumber, bestInPop, worstInPop, avgOfPop);
        if (bestInPop < minimalDistance) {
            minimalDistance = bestInPop;
            bestGenotype = bestGenotypeInPop;
        }
//        if (i+1 == conf.getGenerations()) {
//            System.out.println(bestInPop);
//            results.add(bestInPop);
//        }
        return new EvaluationResults(minimalDistance, bestGenotype);
    }



    private static String resolveNameFromConfiguration(EvolutionaryAlgorithmStrategyConfiguration conf) {
        StringBuilder sb = new StringBuilder();
        sb.append("EA");
        sb.append("_pop-");
        sb.append(conf.getPopulationSize());
        sb.append("_gen-");
        sb.append(conf.getGenerations());
        sb.append("_sel-");
        if (conf.getSelectionType() == SelectionType.TOURNAMENT) {
            sb.append("TOUR-");
            sb.append(conf.getTournamentSize());
        } else {
            sb.append("ROUL");
        }
        sb.append("_mut-");
        if (conf.getMutationType() == MutationType.SWAP) {
            sb.append("SWAP-");
        } else {
            sb.append("INV-");
        }
        sb.append(conf.getMutationLikelihood());
        sb.append("_cross-");
        sb.append(conf.getCrossoverType());
        sb.append("-");
        sb.append(conf.getCrossoverLikelihood());
        sb.append("_rep-");
        sb.append(conf.getRepetitions());
        return sb.toString();
    }



    private void logResult(int i, double best, double worst, double avg) {
        String bestStr = String.format("%.0f", best);
        StringBuilder sb = new StringBuilder();
        sb.append(i+1);
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

}
