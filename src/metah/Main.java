package metah;

import metah.ea.SolutionFinder;
import metah.ea.strategy.RandomStrategy;
import metah.model.DataSet;
import metah.model.DistanceMatrix;
import metah.service.DataLoader;

public class Main {
    public static void main(String[] args) {
        DataLoader dataLoader = new DataLoader();
        DataSet dataSet = dataLoader.loadDataSetFromFile("toy");
        DistanceMatrix distanceMatrix = new DistanceMatrix(dataSet.getLocations());



        SolutionFinder solutionFinder;

        solutionFinder = new SolutionFinder(new RandomStrategy(1000000, 10, distanceMatrix));
        solutionFinder.findOptimalSolution(dataSet.getLocations());
        System.out.println(solutionFinder.getLastResultDescription());
    }
}
