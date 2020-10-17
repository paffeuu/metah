package metah;

import metah.ea.Evaluator;
import metah.ea.CVRPSolver;
import metah.ea.model.Genotype;
import metah.ea.strategy.GreedyStrategy;
import metah.ea.strategy.RandomStrategy;
import metah.model.DataSet;
import metah.model.DistanceMatrix;
import metah.service.DataLoader;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DataLoader dataLoader = new DataLoader();
        DataSet dataSet = dataLoader.loadDataSetFromFile("toy");
        DistanceMatrix distanceMatrix = new DistanceMatrix(dataSet.getLocations());

//
//
        CVRPSolver CVRPSolver = new CVRPSolver(new RandomStrategy(10000, 1));
        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), distanceMatrix);
        System.out.println(CVRPSolver.getLastResultDescription());


        CVRPSolver = new CVRPSolver(new GreedyStrategy());
        CVRPSolver.findOptimalSolution(dataSet.getLocations(), dataSet.getDepotNr(), distanceMatrix);
        System.out.println(CVRPSolver.getLastResultDescription());



        Evaluator evaluator = new Evaluator();
        List<Integer> ints = new ArrayList<>();
        ints.add(1);
        ints.add(2);
        ints.add(5);
        ints.add(1);
        ints.add(4);
        ints.add(3);
        ints.add(6);
        ints.add(1);
        Genotype genotype = new Genotype(ints);
        System.out.println(evaluator.evaluateGenotype(genotype, 30, distanceMatrix, dataSet.getLocations(), dataSet.getDepotNr()));



    }
}
