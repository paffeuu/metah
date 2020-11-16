package metah;

import metah.ea.model.CrossoverType;
import metah.ea.model.MutationType;
import metah.ea.model.SelectionType;
import metah.ea.strategy.EvolutionaryAlgorithmStrategy;
import metah.ea.strategy.configuration.EvolutionaryAlgorithmStrategyConfiguration;
import metah.sa.strategy.SimulatedAnnealingStrategy;
import metah.sa.strategy.configuration.SimulatedAnnealingStrategyConfiguration;
import metah.service.CVRPSolver;
import metah.model.DataSet;
import metah.model.DistanceMatrix;
import metah.service.DataLoader;
import metah.ts.model.InitializationType;
import metah.ts.model.NeighborhoodType;
import metah.ts.strategy.TabuSearchStrategy;
import metah.ts.strategy.configuration.TabuSearchStrategyConfiguration;

public class Main {
    public static void main(String[] args) {

        int instanceNr = Integer.parseInt(args[0]);


//        instanceName = "toy";
        String[] instances = new String[] {
                "A-n32-k5",
                "A-n37-k6",
                "A-n39-k5",
                "A-n45-k6",
                "A-n48-k7",
                "A-n54-k7",
                "A-n60-k9"
        };
        String instanceName = instances[instanceNr];

        DataLoader dataLoader = new DataLoader();
        DataSet dataSet = dataLoader.loadDataSetFromFile(instanceName);
        DistanceMatrix distanceMatrix = new DistanceMatrix(dataSet.getLocations());
        CVRPSolver CVRPSolver = null;





        String strategyType = args[1];
        if (strategyType.equals("EA")) {
            CrossoverType crossoverType = (args[3].equals("OX")) ? CrossoverType.OX : CrossoverType.PMX;
            MutationType mutationType = (args[4].equals("INV")) ? MutationType.INVERSION : MutationType.SWAP;
            int popSize = Integer.parseInt(args[5]);
            int generations = Integer.parseInt(args[6]);
            double crossoverLikelihood = Double.parseDouble(args[7]);
            double mutationLikelihood = Double.parseDouble(args[8]);
            int repetitions = Integer.parseInt(args[9]);



            SelectionType selectionType = (args[2].split("-")[0].equals("TOUR")) ? SelectionType.TOURNAMENT : SelectionType.ROULETTE;
            if (selectionType.equals(SelectionType.TOURNAMENT)) {
                int tourSize = Integer.parseInt(args[2].split("-")[1]);
                CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
                        SelectionType.TOURNAMENT, crossoverType, mutationType, popSize, tourSize,
                        generations, crossoverLikelihood, mutationLikelihood, repetitions), dataSet, distanceMatrix));

            } else  {
                CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
                        SelectionType.ROULETTE, crossoverType, mutationType, popSize,
                        generations, crossoverLikelihood, mutationLikelihood, repetitions), dataSet, distanceMatrix));

            }
        } else if (strategyType.equals("TS")) {
            int i = Integer.parseInt(args[2]);
            int nSize = Integer.parseInt(args[3]);
            NeighborhoodType neighborhoodType = (args[4].equals("INV")) ? NeighborhoodType.INVERSE
                    : NeighborhoodType.SWAP;
            int tabuListSize = Integer.parseInt(args[5]);
            int repetitions = Integer.parseInt(args[6]);

            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
                    i, nSize, neighborhoodType, tabuListSize ,
                    InitializationType.RANDOM, repetitions), dataSet, distanceMatrix));
        } else if (strategyType.equals("SA")) {
            int repetitions = Integer.parseInt(args[2]);
            int i = Integer.parseInt(args[3]);
            int nSize = Integer.parseInt(args[4]);
            NeighborhoodType neighborhoodType = (args[5].equals("INV")) ? NeighborhoodType.INVERSE
                    : NeighborhoodType.SWAP;
            double startTemp = Double.parseDouble(args[6]);
            double endTemp = Double.parseDouble(args[7]);
            double coolingF = Double.parseDouble(args[8]);



            CVRPSolver = new CVRPSolver(new SimulatedAnnealingStrategy(new SimulatedAnnealingStrategyConfiguration(
                    repetitions, i, nSize, neighborhoodType,
                    startTemp, endTemp, coolingF), dataSet, distanceMatrix));
            CVRPSolver.findOptimalSolution();
            System.out.println(CVRPSolver.getLastResultDescription());
        }

        CVRPSolver.findOptimalSolution();
        System.out.println(CVRPSolver.getLastResultDescription());




//        for (int i = 0; i < instances.length; i++) {
//            instanceName = instances[i];
////        instanceName = "toy";
//
//            DataLoader dataLoader = new DataLoader();
//            DataSet dataSet = dataLoader.loadDataSetFromFile(instanceName);
//            DistanceMatrix distanceMatrix = new DistanceMatrix(dataSet.getLocations());
//
//
//            CVRPSolver CVRPSolver;
//
//            // EA STRATEGY
//
//            // selection
//
//            CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
//                SelectionType.TOURNAMENT, CrossoverType.OX, MutationType.INVERSION, 1000, 5,
//                1000, 0.7, 0.1, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
//                SelectionType.TOURNAMENT, CrossoverType.OX, MutationType.INVERSION, 1000, 6,
//                1000, 0.7, 0.1, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
//                SelectionType.TOURNAMENT, CrossoverType.OX, MutationType.INVERSION, 1000, 7,
//                1000, 0.7, 0.1, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
//                SelectionType.ROULETTE, CrossoverType.OX, MutationType.INVERSION, 1000,
//                1000, 0.7, 0.1, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//
//            // crossover
//
//            CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
//                    SelectionType.TOURNAMENT, CrossoverType.OX, MutationType.INVERSION, 1000, 5,
//                    1000, 0.5, 0.1, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
//                    SelectionType.TOURNAMENT, CrossoverType.OX, MutationType.INVERSION, 1000, 5,
//                    1000, 0.6, 0.1, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
//                    SelectionType.TOURNAMENT, CrossoverType.OX, MutationType.INVERSION, 1000, 5,
//                    1000, 0.7, 0.1, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
//                    SelectionType.TOURNAMENT, CrossoverType.PMX, MutationType.INVERSION, 1000, 5,
//                    1000, 0.5, 0.1, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
//                    SelectionType.TOURNAMENT, CrossoverType.PMX, MutationType.INVERSION, 1000, 5,
//                    1000, 0.6, 0.1, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
//                    SelectionType.TOURNAMENT, CrossoverType.PMX, MutationType.INVERSION, 1000, 5,
//                    1000, 0.7, 0.1, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//
//            // mutation
//            CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
//                    SelectionType.TOURNAMENT, CrossoverType.OX, MutationType.INVERSION, 1000, 5,
//                    1000, 0.7, 0.05, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
//                    SelectionType.TOURNAMENT, CrossoverType.OX, MutationType.INVERSION, 1000, 5,
//                    1000, 0.7, 0.1, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
//                    SelectionType.TOURNAMENT, CrossoverType.OX, MutationType.INVERSION, 1000, 5,
//                    1000, 0.7, 0.15, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
//                    SelectionType.TOURNAMENT, CrossoverType.OX, MutationType.SWAP, 1000, 5,
//                    1000, 0.7, 0.05, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
//                    SelectionType.TOURNAMENT, CrossoverType.OX, MutationType.SWAP, 1000, 5,
//                    1000, 0.7, 0.1, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
//                    SelectionType.TOURNAMENT, CrossoverType.OX, MutationType.SWAP, 1000, 5,
//                    1000, 0.7, 0.15, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//
//
//            // TS STRATEGY
//
//
//            // nsize/tabu = 0.25
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 5, NeighborhoodType.INVERSE, 20 ,
//                    InitializationType.RANDOM, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 10, NeighborhoodType.INVERSE, 40 ,
//                    InitializationType.RANDOM, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 25, NeighborhoodType.INVERSE, 100 ,
//                    InitializationType.RANDOM, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 50, NeighborhoodType.INVERSE, 200 ,
//                    InitializationType.RANDOM, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            // nsize/tabu = 0.5
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 10, NeighborhoodType.INVERSE, 20 ,
//                    InitializationType.RANDOM, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 20, NeighborhoodType.INVERSE, 40 ,
//                    InitializationType.RANDOM, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 50, NeighborhoodType.INVERSE, 100 ,
//                    InitializationType.RANDOM, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 100, NeighborhoodType.INVERSE, 200 ,
//                    InitializationType.RANDOM, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            // nsize/tabu = 0.75
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 15, NeighborhoodType.INVERSE, 20 ,
//                    InitializationType.RANDOM, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 30, NeighborhoodType.INVERSE, 40 ,
//                    InitializationType.RANDOM, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 75, NeighborhoodType.INVERSE, 100 ,
//                    InitializationType.RANDOM, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 150, NeighborhoodType.INVERSE, 200 ,
//                    InitializationType.RANDOM, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//
//
//            // nsize/tabu = 1.0
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 20, NeighborhoodType.INVERSE, 20 ,
//                    InitializationType.RANDOM, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 40, NeighborhoodType.INVERSE, 40 ,
//                    InitializationType.RANDOM, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 100, NeighborhoodType.INVERSE, 100 ,
//                    InitializationType.RANDOM, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 150, NeighborhoodType.INVERSE, 150 ,
//                    InitializationType.RANDOM, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//
//            // swap
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 50, NeighborhoodType.SWAP, 200 ,
//                    InitializationType.RANDOM, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 100, NeighborhoodType.SWAP, 200 ,
//                    InitializationType.RANDOM, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                    1000, 150, NeighborhoodType.SWAP, 200 ,
//                    InitializationType.RANDOM, 10), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
////            CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
////                    1000, 200, NeighborhoodType.SWAP, 200 ,
////                    InitializationType.RANDOM, 10), dataSet, distanceMatrix));
////            CVRPSolver.findOptimalSolution();
////            System.out.println(CVRPSolver.getLastResultDescription());
//
//
//
//
//
//
//
//
//            // SA STRATEGY
//
//            // coolingF
//
//            CVRPSolver = new CVRPSolver(new SimulatedAnnealingStrategy(new SimulatedAnnealingStrategyConfiguration(
//                    10, 1000, 3, NeighborhoodType.INVERSE,
//                    200.0, 1.0, 0.99), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new SimulatedAnnealingStrategy(new SimulatedAnnealingStrategyConfiguration(
//                    10, 1000, 3, NeighborhoodType.INVERSE,
//                    200.0, 1.0, 0.98), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new SimulatedAnnealingStrategy(new SimulatedAnnealingStrategyConfiguration(
//                    10, 1000, 3, NeighborhoodType.INVERSE,
//                    200.0, 1.0, 0.95), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new SimulatedAnnealingStrategy(new SimulatedAnnealingStrategyConfiguration(
//                    10, 1000, 3, NeighborhoodType.INVERSE,
//                    200.0, 1.0, 0.93), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//
//            // start/end temp
//
//            CVRPSolver = new CVRPSolver(new SimulatedAnnealingStrategy(new SimulatedAnnealingStrategyConfiguration(
//                    10, 1000, 3, NeighborhoodType.INVERSE,
//                    500.0, 20.0, 0.98), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new SimulatedAnnealingStrategy(new SimulatedAnnealingStrategyConfiguration(
//                    10, 1000, 3, NeighborhoodType.INVERSE,
//                    200.0, 10.0, 0.98), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new SimulatedAnnealingStrategy(new SimulatedAnnealingStrategyConfiguration(
//                    10, 1000, 3, NeighborhoodType.INVERSE,
//                    100, 5.0, 0.98), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new SimulatedAnnealingStrategy(new SimulatedAnnealingStrategyConfiguration(
//                    10, 1000, 3, NeighborhoodType.INVERSE,
//                    50, 2.0, 0.98), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new SimulatedAnnealingStrategy(new SimulatedAnnealingStrategyConfiguration(
//                    10, 1000, 3, NeighborhoodType.INVERSE,
//                    20.0, 1.0, 0.98), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new SimulatedAnnealingStrategy(new SimulatedAnnealingStrategyConfiguration(
//                    10, 1000, 3, NeighborhoodType.INVERSE,
//                    200.0, 1.0, 0.98), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new SimulatedAnnealingStrategy(new SimulatedAnnealingStrategyConfiguration(
//                    10, 1000, 3, NeighborhoodType.INVERSE,
//                    100, 1.0, 0.98), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new SimulatedAnnealingStrategy(new SimulatedAnnealingStrategyConfiguration(
//                    10, 1000, 3, NeighborhoodType.INVERSE,
//                    50, 1.0, 0.98), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new SimulatedAnnealingStrategy(new SimulatedAnnealingStrategyConfiguration(
//                    10, 1000, 3, NeighborhoodType.INVERSE,
//                    100.0, 0.1, 0.98), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//            CVRPSolver = new CVRPSolver(new SimulatedAnnealingStrategy(new SimulatedAnnealingStrategyConfiguration(
//                    10, 1000, 3, NeighborhoodType.INVERSE,
//                    50, 0.1, 0.98), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//
//            // swap
//
//            CVRPSolver = new CVRPSolver(new SimulatedAnnealingStrategy(new SimulatedAnnealingStrategyConfiguration(
//                    10, 1000, 3, NeighborhoodType.SWAP,
//                    200.0, 1.0, 0.98), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//
//
//        }


//
//            CVRPSolver = new CVRPSolver(new SimulatedAnnealingStrategy(new SimulatedAnnealingStrategyConfiguration(
//                    10, 1000, 3, NeighborhoodType.INVERSE,
//                    200.0, 1.0, 0.98), dataSet, distanceMatrix));
//            CVRPSolver.findOptimalSolution();
//            System.out.println(CVRPSolver.getLastResultDescription());
//
//        }


//

//        CVRPSolver = new CVRPSolver(new TabuSearchStrategy(new TabuSearchStrategyConfiguration(
//                1000, 50, NeighborhoodType.INVERSE, 100 ,
//                InitializationType.RANDOM, 10), dataSet, distanceMatrix));
//        CVRPSolver.findOptimalSolution();
//        System.out.println(CVRPSolver.getLastResultDescription());


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



























//        CVRPSolver = new CVRPSolver(new RandomStrategy(new RandomStrategyConfiguration(1000000, 1),
//                dataSet, distanceMatrix));
//        CVRPSolver.findOptimalSolution();
//        System.out.println(CVRPSolver.getLastResultDescription());
//
//        CVRPSolver = new CVRPSolver(new GreedyStrategy(dataSet, distanceMatrix));
//        CVRPSolver.findOptimalSolution();
//        System.out.println(CVRPSolver.getLastResultDescription());
//
//        CVRPSolver = new CVRPSolver(new EvolutionaryAlgorithmStrategy(new EvolutionaryAlgorithmStrategyConfiguration(
//                SelectionType.ROULETTE, CrossoverType.PMX, MutationType.INVERSION, 1000,
//                1000, 0.7, 0.1, 3), dataSet, distanceMatrix));
//        CVRPSolver.findOptimalSolution();
//        System.out.println(CVRPSolver.getLastResultDescription());


    }
}
