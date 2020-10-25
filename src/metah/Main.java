package metah;

import metah.ea.CVRPSolver;
import metah.model.DataSet;
import metah.model.DistanceMatrix;
import metah.service.DataLoader;
import metah.ts.strategy.TabuSearchStrategy;
import metah.ts.strategy.configuration.TabuSearchStrategyConfiguration;
import metah.ts.strategy.model.InitializationType;
import metah.ts.strategy.model.NeighborhoodType;

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
//        instanceName = "toy";

        DataLoader dataLoader = new DataLoader();
        DataSet dataSet = dataLoader.loadDataSetFromFile(instanceName);
        DistanceMatrix distanceMatrix = new DistanceMatrix(dataSet.getLocations());

//
        CVRPSolver CVRPSolver;

        CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
                2000, 30, NeighborhoodType.SWAP, 50 ,
                InitializationType.RANDOM, 10)));
        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
                distanceMatrix, instanceName);
        System.out.println(CVRPSolver.getLastResultDescription());


























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
