package metah;

import metah.ea.Evaluator;
import metah.ea.CVRPSolver;
import metah.ea.model.CrossoverType;
import metah.ea.model.Genotype;
import metah.ea.model.MutationType;
import metah.ea.model.SelectionType;
import metah.ea.strategy.EvolutionaryAlgorithmStrategy;
import metah.ea.strategy.GreedyStrategy;
import metah.ea.strategy.RandomStrategy;
import metah.ea.strategy.configuration.EvolutionaryAlgorithmStrategyConfiguration;
import metah.ea.strategy.configuration.RandomStrategyConfiguration;
import metah.model.DataSet;
import metah.model.DistanceMatrix;
import metah.service.DataLoader;

public class Main {
    public static void main(String[] args) {
//        String instanceName = "toy";
        String instanceName = "A-n32-k5";

        DataLoader dataLoader = new DataLoader();
        DataSet dataSet = dataLoader.loadDataSetFromFile(instanceName);
        DistanceMatrix distanceMatrix = new DistanceMatrix(dataSet.getLocations());

//
        CVRPSolver CVRPSolver;
//        CVRPSolver = new CVRPSolver(new RandomStrategy(new RandomStrategyConfiguration(1000000, 1)));
//        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
//                distanceMatrix, instanceName);
//        System.out.println(CVRPSolver.getLastResultDescription());
////
////
//        CVRPSolver = new CVRPSolver(new GreedyStrategy());
//        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
//                distanceMatrix, instanceName);
//        System.out.println(CVRPSolver.getLastResultDescription());

        //TOURNAMENT OR ROULETTE

        CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
                SelectionType.ROULETTE, CrossoverType.PMX, MutationType.INVERSION, 1000,
                1000, 0.7, 0.1, 10
        )));
        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(), distanceMatrix,
                instanceName);
        System.out.println(CVRPSolver.getLastResultDescription());

        CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
                SelectionType.TOURNAMENT, CrossoverType.PMX, MutationType.INVERSION, 1000,
                3, 1000, 0.7, 0.1, 10
        )));
        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(), distanceMatrix,
                instanceName);
        System.out.println(CVRPSolver.getLastResultDescription());

        CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
                SelectionType.TOURNAMENT, CrossoverType.PMX, MutationType.INVERSION, 1000,
                5, 1000, 0.7, 0.1, 10
        )));
        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(), distanceMatrix,
                instanceName);
        System.out.println(CVRPSolver.getLastResultDescription());

        CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
                SelectionType.TOURNAMENT, CrossoverType.PMX, MutationType.INVERSION, 1000,
                7, 1000, 0.7, 0.1, 10
        )));
        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(), distanceMatrix,
                instanceName);
        System.out.println(CVRPSolver.getLastResultDescription());

        CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
                SelectionType.TOURNAMENT, CrossoverType.PMX, MutationType.INVERSION, 1000,
                10, 1000, 0.7, 0.1, 10
        )));
        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(), distanceMatrix,
                instanceName);
        System.out.println(CVRPSolver.getLastResultDescription());



        // POPULATION SIZE

        CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
                SelectionType.TOURNAMENT, CrossoverType.PMX, MutationType.INVERSION, 100,
                5, 1000, 0.7, 0.1, 10
        )));
        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(), distanceMatrix,
                instanceName);
        System.out.println(CVRPSolver.getLastResultDescription());

        CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
                SelectionType.TOURNAMENT, CrossoverType.PMX, MutationType.INVERSION, 500,
                5, 1000, 0.7, 0.1, 10
        )));
        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(), distanceMatrix,
                instanceName);
        System.out.println(CVRPSolver.getLastResultDescription());

        CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
                SelectionType.TOURNAMENT, CrossoverType.PMX, MutationType.INVERSION, 1000,
                5, 1000, 0.7, 0.1, 10
        )));
        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(), distanceMatrix,
                instanceName);
        System.out.println(CVRPSolver.getLastResultDescription());

        CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
                SelectionType.TOURNAMENT, CrossoverType.PMX, MutationType.INVERSION, 2000,
                5, 1000, 0.7, 0.1, 10
        )));
        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(), distanceMatrix,
                instanceName);
        System.out.println(CVRPSolver.getLastResultDescription());

        CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
                SelectionType.TOURNAMENT, CrossoverType.PMX, MutationType.INVERSION, 5000,
                5, 1000, 0.7, 0.1, 10
        )));
        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(), distanceMatrix,
                instanceName);
        System.out.println(CVRPSolver.getLastResultDescription());


        // GENERATIONS

        CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
                SelectionType.TOURNAMENT, CrossoverType.PMX, MutationType.INVERSION, 1000,
                5, 100, 0.7, 0.1, 10
        )));
        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(), distanceMatrix,
                instanceName);
        System.out.println(CVRPSolver.getLastResultDescription());

        CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
                SelectionType.TOURNAMENT, CrossoverType.PMX, MutationType.INVERSION, 1000,
                5, 500, 0.7, 0.1, 10
        )));
        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(), distanceMatrix,
                instanceName);
        System.out.println(CVRPSolver.getLastResultDescription());

        CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
                SelectionType.TOURNAMENT, CrossoverType.PMX, MutationType.INVERSION, 1000,
                5, 1000, 0.7, 0.1, 10
        )));
        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(), distanceMatrix,
                instanceName);
        System.out.println(CVRPSolver.getLastResultDescription());

        CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
                SelectionType.TOURNAMENT, CrossoverType.PMX, MutationType.INVERSION, 1000,
                5, 2000, 0.7, 0.1, 10
        )));
        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(), distanceMatrix,
                instanceName);
        System.out.println(CVRPSolver.getLastResultDescription());

        CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
                SelectionType.TOURNAMENT, CrossoverType.PMX, MutationType.INVERSION, 1000,
                5, 5000, 0.7, 0.1, 10
        )));
        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(), distanceMatrix,
                instanceName);
        System.out.println(CVRPSolver.getLastResultDescription());


        // MUTATION

        CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
                SelectionType.TOURNAMENT, CrossoverType.PMX, MutationType.INVERSION, 1000,
                5, 1000, 0.7, 0.05, 10
        )));
        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(), distanceMatrix,
                instanceName);
        System.out.println(CVRPSolver.getLastResultDescription());

        CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
                SelectionType.TOURNAMENT, CrossoverType.PMX, MutationType.INVERSION, 1000,
                5, 1000, 0.7, 0.1, 10
        )));
        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(), distanceMatrix,
                instanceName);
        System.out.println(CVRPSolver.getLastResultDescription());

        CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
                SelectionType.TOURNAMENT, CrossoverType.PMX, MutationType.INVERSION, 1000,
                5, 1000, 0.7, 0.2, 10
        )));
        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(), distanceMatrix,
                instanceName);
        System.out.println(CVRPSolver.getLastResultDescription());

        CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
                SelectionType.TOURNAMENT, CrossoverType.PMX, MutationType.SWAP, 1000,
                5, 1000, 0.7, 0.05, 10
        )));
        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(), distanceMatrix,
                instanceName);
        System.out.println(CVRPSolver.getLastResultDescription());

        CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
                SelectionType.TOURNAMENT, CrossoverType.PMX, MutationType.SWAP, 1000,
                5, 1000, 0.7, 0.1, 10
        )));
        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(), distanceMatrix,
                instanceName);
        System.out.println(CVRPSolver.getLastResultDescription());

        CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
                SelectionType.TOURNAMENT, CrossoverType.PMX, MutationType.SWAP, 1000,
                5, 1000, 0.7, 0.2, 10
        )));
        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(), distanceMatrix,
                instanceName);
        System.out.println(CVRPSolver.getLastResultDescription());



        // CROSSOVER

        CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
                SelectionType.TOURNAMENT, CrossoverType.PMX, MutationType.INVERSION, 1000,
                5, 1000, 0.5, 0.1, 10
        )));
        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(), distanceMatrix,
                instanceName);
        System.out.println(CVRPSolver.getLastResultDescription());

        CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
                SelectionType.TOURNAMENT, CrossoverType.PMX, MutationType.INVERSION, 1000,
                5, 1000, 0.7, 0.1, 10
        )));
        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(), distanceMatrix,
                instanceName);
        System.out.println(CVRPSolver.getLastResultDescription());

        CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
                SelectionType.TOURNAMENT, CrossoverType.PMX, MutationType.INVERSION, 1000,
                5, 1000, 0.9, 0.1, 10
        )));
        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(), distanceMatrix,
                instanceName);
        System.out.println(CVRPSolver.getLastResultDescription());

        CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
                SelectionType.TOURNAMENT, CrossoverType.OX, MutationType.INVERSION, 1000,
                5, 1000, 0.5, 0.1, 10
        )));
        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(), distanceMatrix,
                instanceName);
        System.out.println(CVRPSolver.getLastResultDescription());

        CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
                SelectionType.TOURNAMENT, CrossoverType.OX, MutationType.INVERSION, 1000,
                5, 1000, 0.7, 0.1, 10
        )));
        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(), distanceMatrix,
                instanceName);
        System.out.println(CVRPSolver.getLastResultDescription());

        CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
                SelectionType.TOURNAMENT, CrossoverType.OX, MutationType.INVERSION, 1000,
                5, 1000, 0.9, 0.1, 10
        )));
        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(), distanceMatrix,
                instanceName);
        System.out.println(CVRPSolver.getLastResultDescription());































//        Evaluator evaluator = new Evaluator();
//        List<Integer> ints = new ArrayList<>();
//        ints.add(1);
//        ints.add(2);
//        ints.add(5);
//        ints.add(1);
//        ints.add(4);
//        ints.add(3);
//        ints.add(6);
//        ints.add(1);
//        Genotype genotype = new Genotype(ints);
//        System.out.println(evaluator.evaluateGenotype(genotype, 30, distanceMatrix, dataSet.getLocations(), dataSet.getDepotNr()));



    }
}
