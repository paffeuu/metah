package metah.ea;

import metah.ea.model.Genotype;
import metah.model.DistanceMatrix;
import metah.model.Location;
import metah.model.Shop;
import metah.service.DistanceCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Evaluator {

    public double evaluateGenotype(Genotype genotype, int initialCapacity, DistanceMatrix distanceMatrix,
                                   Map<Integer, Location> locations, int depotNr) {
//        System.out.print("initial genotype: " + genotype + "\t\t\t\t\t");
        Genotype normalizedGenotype = normalizeGenotype(genotype, initialCapacity, distanceMatrix, locations, depotNr);
//        System.out.println("normalized genotype: " + normalizedGenotype);
        DistanceCalculator calculator = new DistanceCalculator();
        double distance = calculator.sumDistance(normalizedGenotype, distanceMatrix);
        return distance;
    }

    public Genotype normalizeGenotype(Genotype genotype, int initialCapacity, DistanceMatrix distanceMatrix,
                                      Map<Integer, Location> locations, int depotNr) {
        List<Integer> normalizedGenotypeList = new ArrayList<>();
        normalizedGenotypeList.add(depotNr);
        int currCapacity = initialCapacity;
        for (int i = 0; i < genotype.size(); i++) {
            Location location = locations.get(genotype.get(i));
            if (location.isShop()) {
                Shop shop = (Shop) location;
                currCapacity -= shop.getDemand();
                if (currCapacity < 0) {
                    currCapacity = initialCapacity - shop.getDemand();
                    normalizedGenotypeList.add(depotNr);
                }
                normalizedGenotypeList.add(shop.getNumber());

            } else {
                if (i != 0 && genotype.get(i - 1) != depotNr) {
                    currCapacity = initialCapacity;
                    normalizedGenotypeList.add(depotNr);
                }
            }
        }
        if (genotype.get(genotype.size() - 1) != depotNr) {
            normalizedGenotypeList.add(depotNr);
        }
        return new Genotype(normalizedGenotypeList);
    }

}
