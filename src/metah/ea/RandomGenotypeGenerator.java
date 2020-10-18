package metah.ea;

import metah.ea.model.Genotype;
import metah.model.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class RandomGenotypeGenerator {

    public Genotype generate(Map<Integer, Location> places, int depotNr) {
        ArrayList<Integer> genotypeVector = new ArrayList<>((places.size() - 1) * 2);
        for (int i = 1; i < places.size() + 1; i++) {
            if (i != depotNr) {
                genotypeVector.add(i);
            }
        }
        Collections.shuffle(genotypeVector);
        for (int i = places.size() + 1; i < (places.size() - 1) * 2; i++) {
            genotypeVector.add(i);
        }

        Collections.shuffle(genotypeVector);

        return new Genotype(genotypeVector);
    }
}
