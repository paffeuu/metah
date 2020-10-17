package metah.service;

import metah.ea.model.Genotype;
import metah.model.DistanceMatrix;
import metah.model.Location;

public class DistanceCalculator {
    public double calculateDistance(Location object1, Location object2) {
        double distanceX = Math.abs(object1.getX() - object2.getY());
        double distanceY = Math.abs(object1.getY() - object2.getX());
        return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    }

    public double sumDistance(Genotype genotype, DistanceMatrix distanceMatrix) {
        double sum = 0;
        for (int i = 0; i < genotype.size() - 1; i++) {
            sum += distanceMatrix.getDistance(genotype.get(i),genotype.get(i+1));
        }
        if (genotype.size() != 0) {
            sum += distanceMatrix.getDistance(genotype.get(genotype.size() - 1), genotype.get(0));
        }
        return sum;
    }
}
