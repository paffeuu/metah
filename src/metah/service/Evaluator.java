package metah.service;

import metah.ea.model.Genotype;
import metah.model.DataSet;
import metah.model.DistanceMatrix;
import metah.model.Location;
import metah.model.Shop;
import metah.service.DistanceCalculator;

import java.util.ArrayList;
import java.util.List;

public class Evaluator {
    private DataSet dataSet;
    private DistanceMatrix distanceMatrix;

    public Evaluator(DataSet dataSet, DistanceMatrix distanceMatrix) {
        this.dataSet = dataSet;
        this.distanceMatrix = distanceMatrix;
    }

    public double evaluateGenotype(Genotype genotype) {
        Genotype normalizedGenotype = normalizeGenotype(genotype);
        DistanceCalculator calculator = new DistanceCalculator();
        double distance = calculator.sumDistance(normalizedGenotype, distanceMatrix);
        return distance;
    }

    public Genotype normalizeGenotype(Genotype genotype) {
        List<Integer> normalizedGenotypeList = new ArrayList<>();
        normalizedGenotypeList.add(dataSet.getDepotNr());
        int currCapacity = dataSet.getCapacity();
        for (int i = 0; i < genotype.size(); i++) {
            Location location;
            if (genotype.get(i) > dataSet.getLocations().size()) {
                location = dataSet.getLocations().get(dataSet.getDepotNr());
            } else {
                location = dataSet.getLocations().get(genotype.get(i));
            }
            if (location.isShop()) {
                Shop shop = (Shop) location;
                currCapacity -= shop.getDemand();
                if (currCapacity < 0) {
                    currCapacity = dataSet.getCapacity() - shop.getDemand();
                    normalizedGenotypeList.add(dataSet.getDepotNr());
                }
                normalizedGenotypeList.add(shop.getNumber());

            } else {
                if (i != 0 && genotype.get(i - 1) != dataSet.getDepotNr()
                        && genotype.get(i - 1) <= dataSet.getLocations().size()) {
                    currCapacity = dataSet.getCapacity();
                    normalizedGenotypeList.add(dataSet.getDepotNr());
                }
            }
        }
        if (genotype.get(genotype.size() - 1) != dataSet.getDepotNr()
                && genotype.get(genotype.size() - 1) <= dataSet.getLocations().size()) {
            normalizedGenotypeList.add(dataSet.getDepotNr());
        }
        genotype.setNormalizedVector(normalizedGenotypeList);
        return new Genotype(normalizedGenotypeList);
    }

}
