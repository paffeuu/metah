package metah.ea.model;

import java.util.ArrayList;
import java.util.List;

public class Genotype {
    private final List<Integer> vector;
    private List<Integer> normalizedVector;

    public Genotype(List<Integer> vector) {
        this.vector = vector;
    }

    public Genotype(Genotype originalGenotype) {
        this.vector = new ArrayList<>(originalGenotype.vector);
    }

    public Integer get(int index) {
        return vector.get(index);
    }

    public Integer size() {
        return vector.size();
    }

    public List<Integer> getVector() {
        return vector;
    }

    public void setNormalizedVector(List<Integer> normalizedVector) {
        this.normalizedVector = normalizedVector;
    }

    @Override
    public String toString() {
        List<Integer> vectorToPrint;
        if (normalizedVector != null) {
            vectorToPrint = normalizedVector;
        } else {
            vectorToPrint = vector;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Integer number : vectorToPrint) {
            sb.append(number + " ");
        }
        sb.append("]");
        return sb.toString();
    }
}
