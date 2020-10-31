package metah;

import metah.service.CVRPSolver;
import metah.ea.model.CrossoverType;
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
import metah.ts.strategy.TabuSearchStrategy;
import metah.ts.strategy.configuration.TabuSearchStrategyConfiguration;
import metah.ts.model.InitializationType;
import metah.ts.model.NeighborhoodType;

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
                1000, 10, NeighborhoodType.SWAP, 100 ,
                InitializationType.RANDOM, 10), dataSet, distanceMatrix));
        CVRPSolver.findOptimalSolution();
        System.out.println(CVRPSolver.getLastResultDescription());


//        for (int i = 0; i < instances.length; i++) {
//            instanceName = instances[i];
//
//            dataSet = dataLoader.loadDataSetFromFile(instanceName);
//            distanceMatrix = new DistanceMatrix(dataSet.getLocations());
//
//            // swap
//
//            // iterations
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 10, NeighborhoodType.SWAP, 100 ,
//                    InitializationType.RANDOM, 10)));
//            CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
//                    distanceMatrix, instanceName);
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    2000, 10, NeighborhoodType.SWAP, 100 ,
//                    InitializationType.RANDOM, 10)));
//            CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
//                    distanceMatrix, instanceName);
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    5000, 10, NeighborhoodType.SWAP, 100 ,
//                    InitializationType.RANDOM, 10)));
//            CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
//                    distanceMatrix, instanceName);
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    10000, 10, NeighborhoodType.SWAP, 100 ,
//                    InitializationType.RANDOM, 10)));
//            CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
//                    distanceMatrix, instanceName);
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            // nsize / tabu list size
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 10, NeighborhoodType.SWAP, 100 ,
//                    InitializationType.RANDOM, 10)));
//            CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
//                    distanceMatrix, instanceName);
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 20, NeighborhoodType.SWAP, 100 ,
//                    InitializationType.RANDOM, 10)));
//            CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
//                    distanceMatrix, instanceName);
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 50, NeighborhoodType.SWAP, 100 ,
//                    InitializationType.RANDOM, 10)));
//            CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
//                    distanceMatrix, instanceName);
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 100, NeighborhoodType.SWAP, 100 ,
//                    InitializationType.RANDOM, 10)));
//            CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
//                    distanceMatrix, instanceName);
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 10, NeighborhoodType.SWAP, 50 ,
//                    InitializationType.RANDOM, 10)));
//            CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
//                    distanceMatrix, instanceName);
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 20, NeighborhoodType.SWAP, 50 ,
//                    InitializationType.RANDOM, 10)));
//            CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
//                    distanceMatrix, instanceName);
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 50, NeighborhoodType.SWAP, 50 ,
//                    InitializationType.RANDOM, 10)));
//            CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
//                    distanceMatrix, instanceName);
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 100, NeighborhoodType.SWAP, 200 ,
//                    InitializationType.RANDOM, 10)));
//            CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
//                    distanceMatrix, instanceName);
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 10, NeighborhoodType.SWAP, 20 ,
//                    InitializationType.RANDOM, 10)));
//            CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
//                    distanceMatrix, instanceName);
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 200, NeighborhoodType.SWAP, 400 ,
//                    InitializationType.RANDOM, 10)));
//            CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
//                    distanceMatrix, instanceName);
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//
//            // inverse
//
//            // iterations
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 10, NeighborhoodType.INVERSE, 100 ,
//                    InitializationType.RANDOM, 10)));
//            CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
//                    distanceMatrix, instanceName);
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    2000, 10, NeighborhoodType.INVERSE, 100 ,
//                    InitializationType.RANDOM, 10)));
//            CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
//                    distanceMatrix, instanceName);
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    5000, 10, NeighborhoodType.INVERSE, 100 ,
//                    InitializationType.RANDOM, 10)));
//            CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
//                    distanceMatrix, instanceName);
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    10000, 10, NeighborhoodType.INVERSE, 100 ,
//                    InitializationType.RANDOM, 10)));
//            CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
//                    distanceMatrix, instanceName);
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            // nsize / tabu list size
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 10, NeighborhoodType.INVERSE, 100 ,
//                    InitializationType.RANDOM, 10)));
//            CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
//                    distanceMatrix, instanceName);
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 20, NeighborhoodType.INVERSE, 100 ,
//                    InitializationType.RANDOM, 10)));
//            CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
//                    distanceMatrix, instanceName);
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 50, NeighborhoodType.INVERSE, 100 ,
//                    InitializationType.RANDOM, 10)));
//            CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
//                    distanceMatrix, instanceName);
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 100, NeighborhoodType.INVERSE, 100 ,
//                    InitializationType.RANDOM, 10)));
//            CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
//                    distanceMatrix, instanceName);
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 10, NeighborhoodType.INVERSE, 50 ,
//                    InitializationType.RANDOM, 10)));
//            CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
//                    distanceMatrix, instanceName);
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 20, NeighborhoodType.INVERSE, 50 ,
//                    InitializationType.RANDOM, 10)));
//            CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
//                    distanceMatrix, instanceName);
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 50, NeighborhoodType.INVERSE, 50 ,
//                    InitializationType.RANDOM, 10)));
//            CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
//                    distanceMatrix, instanceName);
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 100, NeighborhoodType.INVERSE, 200 ,
//                    InitializationType.RANDOM, 10)));
//            CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
//                    distanceMatrix, instanceName);
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 10, NeighborhoodType.INVERSE, 20 ,
//                    InitializationType.RANDOM, 10)));
//            CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
//                    distanceMatrix, instanceName);
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 200, NeighborhoodType.INVERSE, 400 ,
//                    InitializationType.RANDOM, 10)));
//            CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), dataSet.getCapacity(),
//                    distanceMatrix, instanceName);
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//
//
//
//
//
//        }



























        CVRPSolver = new CVRPSolver(new RandomStrategy(new RandomStrategyConfiguration(1000000, 1),
                dataSet, distanceMatrix));
        CVRPSolver.findOptimalSolution();
        System.out.println(CVRPSolver.getLastResultDescription());

        CVRPSolver = new CVRPSolver(new GreedyStrategy(dataSet, distanceMatrix));
        CVRPSolver.findOptimalSolution();
        System.out.println(CVRPSolver.getLastResultDescription());

        CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
                SelectionType.ROULETTE, CrossoverType.PMX, MutationType.INVERSION, 1000,
                1000, 0.7, 0.1, 1), dataSet, distanceMatrix));
        CVRPSolver.findOptimalSolution();
        System.out.println(CVRPSolver.getLastResultDescription());


    }
}
