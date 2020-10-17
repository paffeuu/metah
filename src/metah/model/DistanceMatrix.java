package metah.model;

import metah.service.DistanceCalculator;

import java.util.Map;

public class DistanceMatrix {
    private final double[][] matrix;

    public DistanceMatrix(Map<Integer, Location> places) {
        matrix = new double[places.size()][places.size()];

        DistanceCalculator distanceCalculator = new DistanceCalculator();

        for (int i = 0; i < places.size(); i++) {
            for (int j = 1; j < places.size(); j++) {
                if (i == j) {
                    matrix[j][i] = -1;
                    continue;
                }
                matrix[j][i] = distanceCalculator.calculateDistance(places.get(j), places.get(i));
            }
        }
    }

    public double getDistance(int id1, int id2) {
        return matrix[id1][id2];
    }
}
