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
        String instanceName;
//        instanceName = "toy";
        String[] instances = new String[] {
                "A-n32-k5",
                "A-n37-k6",
                "A-n39-k5",
                "A-n45-k6",
                "A-n48-k7"
        };
        instanceName = instances[0];


        DataLoader dataLoader = new DataLoader();
        DataSet dataSet = dataLoader.loadDataSetFromFile(instanceName);
        DistanceMatrix distanceMatrix = new DistanceMatrix(dataSet.getLocations());

//
        CVRPSolver CVRPSolver;
//        CVRPSolver = new CVRPSolver(new RandomStrategy(new RandomStrategyConfiguration(1000000, 1)));
//        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
//                distanceMatrix, instanceName);
//        System.out.println(CVRPSolver.getLastResultDescription());

//        CVRPSolver = new CVRPSolver(new GreedyStrategy());
//        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
//                distanceMatrix, instanceName);
//        System.out.println(CVRPSolver.getLastResultDescription());

//        CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
//                SelectionType.ROULETTE, CrossoverType.PMX, MutationType.INVERSION, 1000,
//                1000, 0.7, 0.1, 1
//        )));
//        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(), distanceMatrix,
//        instanceName);
//        System.out.println(CVRPSolver.getLastResultDescription());


    }
}
