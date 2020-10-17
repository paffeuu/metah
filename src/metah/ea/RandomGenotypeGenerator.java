package metah.ea;

import metah.ea.model.Genotype;
import metah.model.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class RandomGenotypeGenerator {

    public Genotype generate(Map<Integer, Location> places) {
        ArrayList<Integer> genotypeVector = new ArrayList<>(places.size());
        for (int i = 0; i < places.size(); i++) {
            genotypeVector.add(i);
        }
        Collections.shuffle(genotypeVector);
        return new Genotype(genotypeVector);
    }
}
