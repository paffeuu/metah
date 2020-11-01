package metah.ea.strategy;

import metah.ea.model.Genotype;
import metah.ea.model.Solution;
import metah.model.DataSet;
import metah.model.DistanceMatrix;
import metah.service.CVRPSolver;
import metah.service.Evaluator;
import metah.service.Logger;
import metah.service.RandomGenotypeGenerator;

import java.util.Random;

public abstract class Strategy {
    private final String name;
    protected final int repetitions;
    protected DataSet dataSet;
    protected DistanceMatrix distanceMatrix;
    protected RandomGenotypeGenerator genotypeGenerator;
    protected Random random;
    protected Evaluator evaluator;

    protected Strategy(String name, int repetitions, DataSet dataSet, DistanceMatrix distanceMatrix) {
        this.name = name;
        this.repetitions = repetitions;
        this.dataSet = dataSet;
        this.distanceMatrix = distanceMatrix;
        this.genotypeGenerator = new RandomGenotypeGenerator(dataSet, distanceMatrix);
        this.random = new Random();
        this.evaluator = new Evaluator(dataSet, distanceMatrix);
        getLogger().setInstanceName(dataSet.getName());
    }

    public abstract Solution findOptimalSolution();

    public String getName() {
        return name;
    }

    public int getRepetitions() {
        return repetitions;
    }

    protected Genotype initializeGenotypeRandomly() {
        return genotypeGenerator.generate();
    }

    protected Genotype initializeGenotypeGreedy() {
        CVRPSolver greedySolver = new CVRPSolver(new GreedyStrategy(dataSet, distanceMatrix));
        Solution greedySolution = greedySolver.findOptimalSolution();
        return greedySolution.getBestGenotype();
    }

    protected Logger getLogger() {
        return Logger.getLogger(getName());
    }

    protected void logBestGenotype(Genotype bestGenotype, double minimalDistance) {
        getLogger().log(bestGenotype + " - " + (int) minimalDistance + "\n");
    }

    protected void logBestWorstAvgResult(int i, double best, double worst, double avg) {
        String bestStr = String.format("%.0f", best);
        StringBuilder sb = new StringBuilder();
        sb.append(i+1);
        sb.append(",");
        sb.append(bestStr);
        sb.append(",");
        sb.append(String.format("%.0f", worst));
        sb.append(",");
        sb.append(String.format("%.0f", avg));
        sb.append("\n");
        String logStr = sb.toString();

        Logger logger = getLogger();
        logger.log(logStr);
    }

    protected void logCurrentBestResult(int i, double best, double curr) {
        StringBuilder sb = new StringBuilder();
        sb.append(i+1);
        sb.append(",");
        sb.append(String.format("%.0f", best));
        sb.append(",");
        sb.append(String.format("%.0f", curr));
        sb.append("\n");
        String logStr = sb.toString();

        Logger logger = getLogger();
        logger.log(logStr);

    }
}
