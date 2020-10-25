package metah.ea.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    public void swapTwoGenes(int a, int b) {
        int tempValue = vector.get(a);
        vector.set(a, vector.get(b));
        vector.set(b, tempValue);
    }

    public List<Integer> getVector() {
        return vector;
    }

    public void setNormalizedVector(List<Integer> normalizedVector) {
        this.normalizedVector = normalizedVector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Genotype genotype = (Genotype) o;
        return Arrays.equals(vector.toArray(), genotype.vector.toArray()) ||
                (normalizedVector == null || genotype.normalizedVector == null) ? false :
                Arrays.equals(normalizedVector.toArray(), genotype.normalizedVector.toArray());
    }

    @Override
    public int hashCode() {
        return Objects.hash(vector);
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
