package metah;

import metah.model.DataSet;
import metah.model.DistanceMatrix;
import metah.service.DataLoader;

public class Main {
    public static void main(String[] args) {
        DataLoader dataLoader = new DataLoader();
        DataSet dataSet = dataLoader.loadDataSetFromFile("A-n32-k5");
        DistanceMatrix distanceMatrix = new DistanceMatrix(dataSet.getLocations());
    }
}
