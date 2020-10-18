package metah.ea.model;

public class EvaluationResults {
    private final double minimalDistance;
    private final Genotype bestGenotype;

    public EvaluationResults(double minimalDistance, Genotype bestGenotype) {
        this.minimalDistance = minimalDistance;
        this.bestGenotype = bestGenotype;
    }

    public double getMinimalDistance() {
        return minimalDistance;
    }

    public Genotype getBestGenotype() {
        return bestGenotype;
    }
}
