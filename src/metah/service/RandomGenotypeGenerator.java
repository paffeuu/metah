package metah.service;

import metah.ea.model.Genotype;
import metah.model.DataSet;
import metah.model.DistanceMatrix;

import java.util.ArrayList;
import java.util.Collections;

public class RandomGenotypeGenerator {
    private DataSet dataSet;
    private DistanceMatrix distanceMatrix;

    public RandomGenotypeGenerator(DataSet dataSet, DistanceMatrix distanceMatrix) {
        this.dataSet = dataSet;
        this.distanceMatrix = distanceMatrix;
    }

    public Genotype generate() {
        ArrayList<Integer> genotypeVector = new ArrayList<>((dataSet.getLocations().size() - 1) * 2);
        for (int i = 1; i < dataSet.getLocations().size() + 1; i++) {
            if (i != dataSet.getDepotNr()) {
                genotypeVector.add(i);
            }
        }
        Collections.shuffle(genotypeVector);
        for (int i = dataSet.getLocations().size() + 1; i < dataSet.getLocations().size() * 2; i++) {
            genotypeVector.add(i);
        }

        Collections.shuffle(genotypeVector);

        return new Genotype(genotypeVector);
    }
}
